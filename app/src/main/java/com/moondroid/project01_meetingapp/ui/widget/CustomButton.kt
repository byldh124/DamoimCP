package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moondroid.project01_meetingapp.ui.theme.Gray03
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Typography

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CustomButton(
    content: String,
    modifier: Modifier = Modifier,
    containerColor: Color = Red02,
    onLongClick: (() -> Unit)? = null,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    ElevatedCard(
        modifier = modifier.combinedClickable(
            onClick = onClick,
            onLongClick = onLongClick,
            enabled = enabled,
        ),
        colors = CardDefaults.cardColors(
            containerColor = if (enabled) containerColor else Gray03
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 15.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(content, style = Typography.titleLarge, color = Color.White)
        }
    }
}