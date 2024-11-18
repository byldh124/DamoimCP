package com.moondroid.project01_meetingapp.ui.features.group.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SecondaryTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.moondroid.project01_meetingapp.ui.features.group.GroupViewModel
import com.moondroid.project01_meetingapp.ui.features.group.composable.pager.GroupChatScreen
import com.moondroid.project01_meetingapp.ui.features.group.composable.pager.GroupDetailScreen
import com.moondroid.project01_meetingapp.ui.features.group.composable.pager.GroupGalleryScreen
import com.moondroid.project01_meetingapp.ui.features.group.groupRoutes
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GroupRootScreen() {
    val viewModel = hiltViewModel<GroupViewModel>()
    val scope = rememberCoroutineScope()

    val pagerState = rememberPagerState(
        pageCount = { groupRoutes.size },
        initialPageOffsetFraction = 0f,
        initialPage = 0
    )
    val tabIndex = pagerState.currentPage

    BaseLayout(viewModel.title.value, onBack = {}) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            SecondaryTabRow(
                selectedTabIndex = tabIndex
            ) {
                groupRoutes.forEachIndexed { index, groupRoute ->
                    Tab(
                        selected = tabIndex == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    ) {
                        Text(groupRoute.name, modifier = Modifier.padding(vertical = 10.dp))
                    }
                }
            }
            HorizontalPager(state = pagerState, userScrollEnabled = false, modifier = Modifier.fillMaxSize()) {
                when (it) {
                    0 -> GroupDetailScreen()
                    1 -> GroupGalleryScreen()
                    2 -> GroupChatScreen()
                }
            }
        }
    }
}
