package com.moondroid.project01_meetingapp.core

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moondroid.damoim.common.util.debug
import com.moondroid.project01_meetingapp.core.navigation.AppNavGraph
import com.moondroid.project01_meetingapp.ui.features.common.splash.composable.SplashScreen
import com.moondroid.project01_meetingapp.ui.theme.DamoimCPTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent { MyApp() }
    }
}

@Composable
fun MyApp() {
    DamoimCPTheme(darkTheme = false) {
        AppNavGraph()
    }
}