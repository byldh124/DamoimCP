package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.moondroid.project01_meetingapp.ui.theme.Blue01
import com.moondroid.project01_meetingapp.ui.theme.Gray04
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun CustomDialog(onDismiss: () -> Unit, content: String, title: String? = null, button: String = "확인") {
    Dialog(onDismissRequest = onDismiss) {
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Column(
                    modifier = Modifier
                        .padding(vertical = 30.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (!title.isNullOrEmpty()) {
                        Text(title, fontSize = 20.sp, style = Typography.bodyLarge)
                    }
                    Spacer(Modifier.height(20.dp))
                    Text(content, fontSize = 18.sp, style = Typography.bodyMedium)
                }

                HorizontalDivider(color = Gray04, thickness = 1.dp)

                Box(
                    modifier = Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                        .clickable(onClick = onDismiss),
                    contentAlignment = Alignment.Center
                ) {
                    Text(button, fontSize = 20.sp, color = Blue01, style = Typography.bodyLarge)
                }
            }
        }
    }
}