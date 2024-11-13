package com.moondroid.project01_meetingapp.ui.features.common.interest.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moondroid.project01_meetingapp.ui.features.common.interest.InterestIcon

@Composable
fun InterestCard(item: InterestIcon, onSelectInterest: (String) -> Unit) {
    Column(
        modifier = Modifier
            .clickable {
                onSelectInterest(item.name)
            }
            .padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = item.drawable,
            contentDescription = item.name
        )

        Spacer(modifier = Modifier.height(5.dp))

        Text(item.name)
    }
}