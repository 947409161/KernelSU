package me.weishu.kernelsu.ui.component.material3

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp

/**
 * Material 3 NavigationBar wrapper
 * Replaces Miuix's NavigationBar with standard Material 3 implementation
 */
@Composable
fun M3NavigationBar(
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    tonalElevation: Dp = 3.dp, // 替换 Haze 模糊效果
    items: List<M3NavigationItem>,
    selectedItem: Int,
    onItemClick: (Int) -> Unit
) {
    NavigationBar(
        modifier = modifier,
        containerColor = containerColor.copy(alpha = 0.9f), // 半透明背景
        tonalElevation = tonalElevation
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItem == index,
                onClick = { onItemClick(index) },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label
                    )
                },
                label = {
                    androidx.compose.material3.Text(text = item.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}

/**
 * Navigation item data class
 */
data class M3NavigationItem(
    val label: String,
    val icon: ImageVector
)
