package com.moondroid.project01_meetingapp.ui.widget

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun GenderRadioButton(
    value: String,
    onChanged: (String) -> Unit,
) {
    val radius = 10.dp
    val mainColor = Red02
    Row {
        Card(
            onClick = {
                if (value == "여자") onChanged("남자")
            },
            shape = RoundedCornerShape(topStart = radius, bottomStart = radius),
            border = BorderStroke(1.dp, mainColor),
            colors = CardDefaults.cardColors(
                containerColor = if (value == "남자") mainColor else Color.White
            )
        ) {
            Text(
                "남자",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                color = if (value == "남자") Color.White else mainColor,
                style = Typography.bodyLarge
            )
        }

        Card(
            onClick = {
                if (value == "남자") onChanged("여자")
            },

            shape = RoundedCornerShape(topEnd = radius, bottomEnd = radius),
            border = BorderStroke(1.dp, mainColor),
            colors = CardDefaults.cardColors(
                containerColor = if (value == "여자") mainColor else Color.White
            )
        ) {
            Text(
                "여자",
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 15.dp),
                color = if (value == "여자") Color.White else mainColor, style = Typography.bodyLarge
            )
        }
    }
}