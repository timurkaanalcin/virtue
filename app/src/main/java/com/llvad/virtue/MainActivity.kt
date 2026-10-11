package com.llvad.virtue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.llvad.virtue.navigation.VirtueApp
import com.llvad.virtue.ui.theme.VirtueTheme

// VIRTUE'nun tek aktivitesi: tüm ekranlar Navigation Compose ile yönetilir
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { VirtueTheme { VirtueApp(onExit = { finish() }) } }
    }
}
