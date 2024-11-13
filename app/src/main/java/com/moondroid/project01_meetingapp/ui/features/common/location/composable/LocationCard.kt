package com.moondroid.project01_meetingapp.ui.features.common.location.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun LocationCard(location: String, onSelectLocation: (String) -> Unit) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 20.dp)
        .clickable {
            onSelectLocation(location)
        }
    ) {
        Text(location, style = Typography.bodyMedium)
    }
}