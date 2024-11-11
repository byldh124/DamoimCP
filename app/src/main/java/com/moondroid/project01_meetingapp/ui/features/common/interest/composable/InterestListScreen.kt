package com.moondroid.project01_meetingapp.ui.features.common.interest.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.ui.features.common.interest.InterestIcon
import com.moondroid.project01_meetingapp.ui.features.common.interest.interestIconList


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InterestListScreen(
    navController: NavController,
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("관심사 선택") }, navigationIcon = {
                IconButton({ navController.popBackStack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = "뒤로가기")
                }
            })
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                items(interestIconList.size) {
                    InterestCard(interestIconList[it]) { interest ->
                        navController.previousBackStackEntry?.savedStateHandle?.set(IntentParam.INTEREST, interest)
                        navController.popBackStack()
                    }
                }
            }
        }
    }
}

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