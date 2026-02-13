package me.weishu.kernelsu.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import me.weishu.kernelsu.ui.webui.MonetColorsProvider.UpdateCss

/**
 * KernelSU Material 3 Theme
 * Replaces MiuixTheme with standard Material 3 implementation
 */
@Composable
fun KernelSUTheme(
    colorMode: Int = 0,
    keyColor: Color? = null,
    content: @Composable () -> Unit
) {
    val isDark = isSystemInDarkTheme()
    val context = LocalContext.current

    // Material You 动态配色 (Android 12+)
    val colorScheme = when {
        keyColor != null && Build.VERSION.SDK_INT >= 31 -> {
            if (isDark) {
                dynamicDarkColorScheme(context)
            } else {
                dynamicLightColorScheme(context)
            }
        }
        isDark -> darkColorScheme()
        else -> lightColorScheme()
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = {
            UpdateCss()
            content()
        }
    )
}

/**
 * 检查是否为深色主题
 * @param themeMode 主题模式 (0=跟随系统, 1=浅色, 2=深色, 3=跟随系统+自定义色, 4=浅色+自定义色, 5=深色+自定义色)
 */
@Composable
@ReadOnlyComposable
fun isInDarkTheme(themeMode: Int): Boolean {
    return when (themeMode) {
        1, 4 -> false  // 强制浅色
        2, 5 -> true   // 强制深色
        else -> isSystemInDarkTheme()  // 跟随系统 (0 或默认)
    }
}
