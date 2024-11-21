package com.moondroid.project01_meetingapp.ui.features.user.myinfo.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.damoim.common.util.debug
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.MyInfoContract
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.MyInfoViewModel
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.CustomText
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import com.moondroid.project01_meetingapp.ui.widget.GenderRadioButton
import kotlinx.coroutines.launch

typealias event = MyInfoContract.Event

@Composable
fun MyInfoScreen(navigateUp: () -> Unit) {
    val viewModel = hiltViewModel<MyInfoViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    BaseLayout("프로필 수정", navigateUp) {
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
                if (uiState.uri != null) {
                    AsyncImage(
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clickable {
                                getImage()
                            },
                        model = uiState.uri,
                        contentDescription = "썸네일"
                    )
                } else {
                    AsyncImage(
                        modifier = Modifier
                            .width(120.dp)
                            .height(120.dp)
                            .clickable {
                                getImage()
                            },
                        model = uiState.imageUrl,
                        contentDescription = "썸네일"
                    )
                }
            }
            debug("name : ${uiState.name}")
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1.0f)) {
                    CustomTextField(
                        value = uiState.name,
                        label = "name",
                        onTextChanged = {
                            scope.launch {
                                viewModel.event.send(MyInfoContract.Event.PutName(it))
                            }
                        }
                    )
                }
                GenderRadioButton(uiState.gender) {
                    scope.launch {
                        viewModel.event.send(MyInfoContract.Event.PutGender(it))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.weight(1.0f)) {
                    CustomText(uiState.birth) {

                    }
                }

                Box(modifier = Modifier.weight(1.0f)) {
                    CustomText(uiState.location) {

                    }
                }
            }

            CustomTextField(
                value = uiState.message,
                onTextChanged = {
                    scope.launch {
                        viewModel.event.send(MyInfoContract.Event.PutMessage(it))
                    }
                },
                maxLines = 3
            )

            CustomButton("수정 완료", onClick = {
                scope.launch {
                    viewModel.event.send(MyInfoContract.Event.Modify)
                }
            })
        }
    }
}

private fun getImage() {

}
