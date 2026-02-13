package me.weishu.kernelsu.ui.component

import androidx.annotation.StringRes
import androidx.compose.animation.core.EaseInOut
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.captionBar
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Cottage
import androidx.compose.material.icons.rounded.Extension
import androidx.compose.material.icons.rounded.Security
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.weishu.kernelsu.Natives
import me.weishu.kernelsu.R
import me.weishu.kernelsu.ui.LocalMainPagerState
import me.weishu.kernelsu.ui.util.rootAvailable
// Removed: import dev.chrisbanes.haze.*
// Added: Material 3 imports
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import kotlin.math.abs

@Composable
fun BottomBar() {
    val isManager = Natives.isManager
    val fullFeatured = isManager && !Natives.requireNewKernel() && rootAvailable()

    val mainState = LocalMainPagerState.current

    if (!fullFeatured) return

    val items = BottomBarDestination.entries.map { destination ->
        NavigationItem(
            label = stringResource(destination.label),
            icon = destination.icon,
        )
    }

    NavigationBar(
        modifier = Modifier,
        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f), // Replaced Haze blur with semi-transparent background
        tonalElevation = 3.dp
    ) {
        items.forEachIndexed { index, destination ->
            NavigationBarItem(
                selected = mainState.selectedPage == index,
                onClick = {
                    mainState.animateToPage(index)
                },
                icon = {
                    Icon(
                        imageVector = destination.icon,
                        contentDescription = stringResource(destination.label),
                        tint = if (mainState.selectedPage == index) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        }
                    )
                },
                label = {
                    Text(
                        text = stringResource(destination.label),
                        fontSize = 12.sp,
                        fontWeight = if (mainState.selectedPage == index) FontWeight.Bold else FontWeight.Normal
                    )
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun NavigationBar(
    items: List<NavigationItem>,
    selected: Int,
    onClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.surface,
    showDivider: Boolean = true,
    defaultWindowInsetsPadding: Boolean = true,
) {
    require(items.size in 2..5) { "NavigationBar must have between 2 and 5 items" }

    val captionBarPaddings = WindowInsets.captionBar.only(WindowInsetsSides.Bottom).asPaddingValues()
    val captionBarBottomPaddingValue = captionBarPaddings.calculateBottomPadding()
    val animatedCaptionBarHeight by animateDpAsState(
        targetValue = if (captionBarBottomPaddingValue > 0.dp) captionBarBottomPaddingValue else 0.dp,
        animationSpec = tween(durationMillis = 300),
    )

    val currentOnClick by rememberUpdatedState(onClick)

    val isNavigating by remember { mutableStateOf(false) }
    val navJob = rememberCoroutineScope()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(color)
    ) {
        if (showDivider) {
            HorizontalDivider(
                thickness = 0.6.dp,
            )
        }

        val itemHeight = 56.dp
        val itemWeight = 1f / items.size

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(itemHeight),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEachIndexed { index, item ->
                val isSelected = selected == index
                var isPressed by remember { mutableStateOf(false) }

                val onSurfaceContainerColor = MaterialTheme.colorScheme.onSurfaceContainer
                val onSurfaceContainerVariantColor = MaterialTheme.colorScheme.onSurfaceContainerVariant
                val onSurfaceVariantColor = MaterialTheme.colorScheme.onSurfaceVariant

                val tint = when {
                    isSelected -> onSurfaceContainerColor
                    isPressed -> onSurfaceContainerVariantColor
                    else -> onSurfaceVariantColor
                }

                Column(
                    modifier = Modifier
                        .weight(itemWeight)
                        .height(itemHeight)
                        .pointerInput(Unit) {
                            detectTapGestures(
                                onPress = {
                                    isPressed = true
                                    tryAwaitRelease()
                                },
                                onTap = {
                                    currentOnClick(index)
                                },
                            )
                        },
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .size(26.dp),
                        imageVector = item.icon,
                        contentDescription = item.label,
                        colorFilter = ColorFilter.tint(tint),
                    )

                    Text(
                        modifier = Modifier.padding(top = 1.dp, bottom = 8.dp),
                        text = item.label,
                        color = tint,
                        fontSize = 12.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    )
                }
            }
        }

        if (defaultWindowInsetsPadding) {
            val navigationBarsPadding = WindowInsets.navigationBars.asPaddingValues()
            val paddingValue = navigationBarsPadding.calculateBottomPadding()

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(paddingValue + animatedCaptionBarHeight)
                    .pointerInput(Unit) { /* Do nothing to consume clicks */ }
            )
        }
    }
}

data class NavigationItem(
    val label: String,
    val icon: ImageVector,
)

enum class BottomBarDestination(
    @get:StringRes val label: Int,
    val icon: ImageVector,
) {
    Home(R.string.home, Icons.Rounded.Cottage),
    SuperUser(R.string.superuser, Icons.Rounded.Security),
    Module(R.string.module, Icons.Rounded.Extension),
    Settings(R.string.settings, Icons.Rounded.Settings),
}
