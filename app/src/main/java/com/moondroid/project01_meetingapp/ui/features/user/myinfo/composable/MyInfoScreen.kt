package com.moondroid.project01_meetingapp.ui.features.user.myinfo.composable

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.Uri
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.MyInfoContract
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.MyInfoViewModel
import com.moondroid.project01_meetingapp.ui.widget.BaseLayout
import com.moondroid.project01_meetingapp.ui.widget.CustomButton
import com.moondroid.project01_meetingapp.ui.widget.ButtonDialog
import com.moondroid.project01_meetingapp.ui.widget.CustomText
import com.moondroid.project01_meetingapp.ui.widget.CustomTextField
import com.moondroid.project01_meetingapp.ui.widget.DatePickerModal
import com.moondroid.project01_meetingapp.ui.widget.GenderRadioButton
import com.moondroid.project01_meetingapp.ui.widget.LoadingDialog
import com.moondroid.project01_meetingapp.ui.widget.PositiveButton
import com.moondroid.project01_meetingapp.utils.ImageHelper
import com.moondroid.project01_meetingapp.utils.rememberGalleryPermissionHelper
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

typealias event = MyInfoContract.Event

@Composable
fun MyInfoScreen(
    pathFlow: StateFlow<String>,
    locationFlow: StateFlow<String>,
    navigateToImageList: () -> Unit,
    navigateToLocation: () -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel = hiltViewModel<MyInfoViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()
    val path by pathFlow.collectAsStateWithLifecycle()
    val location by locationFlow.collectAsStateWithLifecycle()

    val isPermissionErrorDialogShow = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(path) {
        if (path.isNotEmpty()) {
            val uri = Uri.parse(path)
            viewModel.event.send(MyInfoContract.Event.PutUri(uri))
        }
    }

    LaunchedEffect(location) {
        if (location.isNotEmpty()) {
            viewModel.event.send(MyInfoContract.Event.PutLocation(location))
        }
    }

    val context = LocalContext.current
    val requestPermissions = rememberLauncherForActivityResult(RequestMultiplePermissions()) { permissions ->
        val granted = permissions.any { it.value }
        if (granted) {
            navigateToImageList()
        } else {
            isPermissionErrorDialogShow.value = true
        }
    }

    val permissionHelper = rememberGalleryPermissionHelper({
        navigateToImageList()
    }, {
        isPermissionErrorDialogShow.value = true
    })

    val showDateModal = remember { mutableStateOf(false) }


    BaseLayout(
        "프로필 수정", navigateUp
    ) {
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
                    ProfileImage(uiState.uri) {
                        /*checkPermission(context, requestPermissions) {
                            navigateToImageList()
                        }*/

                        permissionHelper.checkGalleryPermission()
                    }
                } else {
                    ProfileImage(uiState.imageUrl) {
                        /*checkPermission(context, requestPermissions) {
                            navigateToImageList()
                        }*/
                        permissionHelper.checkGalleryPermission()
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
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
                Spacer(Modifier.width(10.dp))
                GenderRadioButton(uiState.gender) {
                    scope.launch {
                        viewModel.event.send(MyInfoContract.Event.PutGender(it))
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Box(modifier = Modifier.weight(0.7f)) {
                    CustomText(uiState.birth) {
                        showDateModal.value = true
                    }
                }
                Spacer(Modifier.width(10.dp))

                Box(modifier = Modifier.weight(1.0f)) {
                    CustomText(uiState.location) {
                        navigateToLocation()
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
                label = "message",
                maxLines = 3
            )

            Spacer(modifier = Modifier.height(16.dp))

            CustomButton("수정 완료", onClick = {
                scope.launch {
                    val realPath = uiState.uri?.let { uri ->
                        ImageHelper.getPathFromUri(context, uri)
                    }
                    viewModel.event.send(MyInfoContract.Event.Modify(realPath))
                }
            })
        }

        if (showDateModal.value) {
            DatePickerModal(uiState.birth, {
                scope.launch {
                    viewModel.event.send(MyInfoContract.Event.PutBirth(it))
                }
            }) {
                showDateModal.value = false
            }
        }

        if (isPermissionErrorDialogShow.value) {
            ButtonDialog(
                PositiveButton {isPermissionErrorDialogShow.value = false}

            ) {
                Text("권한이 없습니다.")
                Text("저장소에 대한 접근 권한이 없어\n해당 기능을 사용할 수 없습니다.\n\n앱 설정에서 저장소에 대한 권한을 승인 후 다시 시도해주세요.")
            }
        }

        if (uiState.concrete == MyInfoContract.State.Concrete.Loading) {
            LoadingDialog()
        }

        if (uiState.concrete == MyInfoContract.State.Concrete.Error) {
            ButtonDialog(
                PositiveButton {
                    scope.launch {
                        viewModel.event.send(MyInfoContract.Event.RESET)
                    }
                }

            ) {
                Text("네트워크 에러 발생")
                Text(uiState.errorMessage)
            }
        }

        if (uiState.concrete == MyInfoContract.State.Concrete.Success) {
            ButtonDialog(
                PositiveButton {
                    scope.launch {
                        viewModel.event.send(MyInfoContract.Event.RESET)
                    }
                }

            ) {
                Text("설정 완료")
                Text("프로필 설정이 완료됐습니다.")
            }
        }
    }
}

@Composable
private fun ProfileImage(
    model: Any?,
    onImageClick: () -> Unit,
) {
    AsyncImage(
        modifier = Modifier
            .width(120.dp)
            .height(120.dp)
            .clickable(onClick = onImageClick),
        model = model,
        contentDescription = "썸네일",
        contentScale = ContentScale.Crop
    )
}

private fun checkPermission(
    context: Context,
    requestPermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    permissionGranted: () -> Unit
) {
    if (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
        (ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED)
    ) {
        permissionGranted()
    } else if (
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
        ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED
    ) {
        permissionGranted()
    } else if (ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
        permissionGranted()
    } else {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES, READ_MEDIA_VISUAL_USER_SELECTED))
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            requestPermissions.launch(arrayOf(READ_MEDIA_IMAGES))
        } else {
            requestPermissions.launch(arrayOf(READ_EXTERNAL_STORAGE))
        }
    }
}
