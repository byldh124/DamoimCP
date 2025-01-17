package com.moondroid.project01_meetingapp.ui.features.group.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.R
import com.moondroid.project01_meetingapp.ui.theme.Container
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun GroupListScreen(list: List<GroupItem>, onClick: (GroupItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .padding(horizontal = 20.dp)
            .fillMaxSize()
    ) {
        items(list.size) {
            GroupListItem(list[it], onClick)
        }
    }
}

@Composable
fun GroupListItem(groupItem: GroupItem, onClick: (GroupItem) -> Unit) {
    ElevatedCard(
        modifier = Modifier.padding(vertical = 5.dp),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color(0xfff9f9f9)
        ),
        //border = BorderStroke(1.dp, Red02),
        onClick = {
            onClick(groupItem)
        },
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .width(100.dp)
                    .height(100.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = groupItem.thumb,
                placeholder = painterResource(R.drawable.placeholder),
                contentDescription = "thumbnail",
                contentScale = ContentScale.Crop
            )

            Spacer(Modifier.width(20.dp))

            Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxSize()) {
                Text(groupItem.title, style = Typography.bodyMedium)
                Spacer(Modifier.height(10.dp))
                Text(groupItem.purpose, style = Typography.bodySmall)
            }
        }
    }
}
