package me.weishu.kernelsu.ui.component.material3

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import me.weishu.kernelsu.R
import androidx.compose.material3.Text

/**
 * Material 3 TopAppBar wrapper
 * Replaces Miuix's TopAppBar with standard Material 3 implementation
 */
@Composable
fun M3TopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    containerColor: Color = Color.Transparent
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            navigationIcon?.let { icon ->
                androidx.compose.material3.IconButton(onClick = onNavigationClick) {
                    androidx.compose.material3.Icon(imageVector = icon, contentDescription = title)
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        )
    )
}

/**
 * Material 3 Medium TopAppBar (for more prominent headers)
 */
@Composable
fun M3MediumTopAppBar(
    modifier: Modifier = Modifier,
    title: String = "",
    navigationIcon: ImageVector? = null,
    onNavigationClick: () -> Unit = {},
    actions: @Composable () -> Unit = {},
    scrollBehavior: TopAppBarScrollBehavior? = null,
    containerColor: Color = Color.Transparent
) {
    MediumTopAppBar(
        modifier = modifier,
        title = {
            Text(
                text = title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        },
        navigationIcon = {
            navigationIcon?.let { icon ->
                androidx.compose.material3.IconButton(onClick = onNavigationClick) {
                    androidx.compose.material3.Icon(imageVector = icon, contentDescription = title)
                }
            }
        },
        actions = actions,
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor
        )
    )
}
