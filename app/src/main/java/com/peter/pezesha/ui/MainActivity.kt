package com.peter.pezesha.ui

import android.graphics.Color
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.peter.pezesha.ui.navigation.MainNavHost
import com.peter.pezesha.ui.theme.DummyStoreTheme

import dagger.hilt.android.AndroidEntryPoint

private val navigationTonalElevation = 3.0.dp

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            DummyStoreTheme {
                val navigationBarScrim = MaterialTheme.colorScheme.surfaceColorAtElevation(
                    elevation = navigationTonalElevation,
                ).toArgb()

                enableEdgeToEdge(
                    navigationBarStyle = SystemBarStyle.auto(
                        lightScrim = Color.TRANSPARENT,
                        darkScrim = Color.TRANSPARENT,
                    ),
                )
                WindowCompat.setDecorFitsSystemWindows(window, false)

                MainNavHost()
            }
        }
    }
}
