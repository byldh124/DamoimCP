package com.moondroid.project01_meetingapp.ui.features.group.list

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CardElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.GroupItem
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Typography

@Composable
fun GroupListScreen(list: List<GroupItem>, onClick: (GroupItem) -> Unit) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 20.dp)
    ) {
        items(list.size) {
            GroupListItem(list[it], onClick)
        }
    }
}

@Composable
fun GroupListItem(groupItem: GroupItem, onClick: (GroupItem) -> Unit) {
    Card(
        modifier = Modifier.padding(vertical = 5.dp),
        shape = RoundedCornerShape(12.dp),
        //border = BorderStroke(1.dp, Red02),
        onClick = {
            onClick(groupItem)
        },
        elevation = CardDefaults.elevatedCardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(10.dp)
                .fillMaxWidth()
        ) {
            Card(
                shape = RoundedCornerShape(12.dp)
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp),
                    model = groupItem.thumb,
                    contentDescription = "thumbnail",
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(Modifier.width(20.dp))

            Column(verticalArrangement = Arrangement.SpaceAround, modifier = Modifier.fillMaxSize()) {
                Text(groupItem.title, style = Typography.bodyLarge)
                Spacer(Modifier.height(20.dp))
                Text(groupItem.purpose, style = Typography.bodyMedium)
            }
        }
    }
}