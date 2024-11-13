@file:OptIn(ExperimentalMaterial3Api::class)

package com.moondroid.project01_meetingapp.ui.features.common.location.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.ui.features.common.location.locationList
import com.moondroid.project01_meetingapp.ui.theme.Gray04
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField

@Composable
fun LocationListScreen(navController: NavController) {
    val query = remember { mutableStateOf("") }

    BaseLayout("관심지역 선택", onBack = { navController.popBackStack() }) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                CustomTextField(query.value, onTextChanged = {
                    query.value = it
                }, label = "지역 검색")
            }
            val item = if (query.value.isBlank()) {
                locationList
            } else {
                locationList.filter { it.contains(query.value) }
            }

            if (item.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("\"${query.value}\"를 포함하는 지역이 없습니다.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .padding()
                        .fillMaxWidth()
                ) {
                    items(item.size) {
                        LocationCard(item[it]) { location ->
                            navController.previousBackStackEntry?.savedStateHandle?.set(
                                IntentParam.LOCATION,
                                location
                            )
                            navController.popBackStack()
                        }

                        if (it < item.lastIndex) {
                            HorizontalDivider(color = Gray04, thickness = 1.dp)
                        }
                    }
                }
            }
        }
    }
}