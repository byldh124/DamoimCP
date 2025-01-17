package com.moondroid.project01_meetingapp.ui.features.group.add.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.project01_meetingapp.ui.features.group.add.GroupAddViewModel
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.MyInfoContract
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomText
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import com.moondroid.project01_meetingapp.ui.widget.GenderRadioButton
import com.moondroid.project01_meetingapp.utils.ImageHelper
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

@Composable
fun GroupAddScreen(
    pathFlow: StateFlow<String>,
    locationFlow: StateFlow<String>,
    interestFlow: StateFlow<String>,
    navigateToImageList: () -> Unit,
    navigateToInterest: () -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel = hiltViewModel<GroupAddViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val location by locationFlow.collectAsStateWithLifecycle()
    val interest by interestFlow.collectAsStateWithLifecycle()
    val path by pathFlow.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()


    BaseLayout("그룹 만들기", navigateUp) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Card(
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
            ) {
                AsyncImage(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clickable(onClick = { navigateToImageList() }),
                    model = path,
                    contentDescription = "썸네일",
                    contentScale = ContentScale.Crop
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {

            }
        }
    }
}