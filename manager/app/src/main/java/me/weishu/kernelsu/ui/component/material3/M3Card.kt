package me.weishu.kernelsu.ui.component.material3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * Material 3 Card wrapper
 * Replaces Miuix's Card with standard Material 3 implementation
 */
@Composable
fun M3Card(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape? = null,
    color: Color = Color.Transparent,
    elevation: Dp = 0.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    Card(
        modifier = modifier,
        enabled = enabled,
        shape = shape ?: CardDefaults.shape,
        colors = CardDefaults.cardColors(
            containerColor = color
        ),
        elevation = {
            elevation
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            content()
        }
    }
}
