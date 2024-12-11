package com.moondroid.project01_meetingapp.ui.features.common.interest.composable

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.moondroid.damoim.common.constant.IntentParam
import com.moondroid.project01_meetingapp.ui.features.common.interest.interestIconList
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout


@Composable
fun InterestListScreen(
    navController: NavController,
) {
    BaseLayout(
        title = "관심사 선택",
        onBack = {
            navController.popBackStack()
        }
    ) {
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

