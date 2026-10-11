package com.llvad.virtue.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

// Sakin ve nötr renk paleti: kırmızı/yeşil abartılı vurgular ve aciliyet renkleri kullanılmaz
private val Light = lightColorScheme(
    primary = Color(0xFF1F4E79),
    secondary = Color(0xFF52606D),
    tertiary = Color(0xFF6B7B8C),
    surfaceVariant = Color(0xFFE8EDF2)
)
private val Dark = darkColorScheme(
    primary = Color(0xFF9CC3E6),
    secondary = Color(0xFFB0BCC8),
    tertiary = Color(0xFFA0AEBB)
)

/** Değişimleri göstermek için düşük doygunluklu renkler */
val NeutralUp = Color(0xFF3D6B5A)
val NeutralDown = Color(0xFF8A5A4A)

@Composable
fun VirtueTheme(content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (isSystemInDarkTheme()) Dark else Light, content = content)
}
