package com.moondroid.project01_meetingapp.ui.features.home.composable

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavOptions
import com.moondroid.project01_meetingapp.core.navigation.Destination

@Composable
fun HomeRootScreen(onAccessTokenExpired: (() -> Unit) -> Unit, navigate: (Destination, NavOptions?) -> Unit) {
    val mContext = LocalContext.current
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Button({
            onAccessTokenExpired {
                Toast.makeText(mContext, "aaaa", Toast.LENGTH_SHORT).show()
            }
        }) {
            Text("로그인 정보 만료")
        }
    }
}