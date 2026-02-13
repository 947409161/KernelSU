package me.weishu.kernelsu.ui.component.material3

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.pulltorefresh.PullToRefresh
import androidx.compose.material3.pulltorefresh.PullToRefreshDefaults
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

/**
 * Material 3 PullToRefresh wrapper
 * Replaces Miuix's PullToRefresh with Accompanist implementation
 */
@Composable
fun M3PullToRefresh(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    containerColor: Color = Color.Transparent,
    content: @Composable () -> Unit
) {
    val state = rememberPullToRefreshState()

    PullToRefresh(
        modifier = modifier,
        state = state,
        isRefreshing = isRefreshing,
        onRefresh = onRefresh,
        colors = PullToRefreshDefaults.colors(
            containerColor = containerColor
        ),
        scale = false
    ) {
        content()
    }
}
