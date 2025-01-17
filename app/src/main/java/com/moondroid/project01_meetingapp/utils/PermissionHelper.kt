package com.moondroid.project01_meetingapp.utils

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.Manifest.permission.READ_MEDIA_IMAGES
import android.Manifest.permission.READ_MEDIA_VISUAL_USER_SELECTED
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat

class PermissionHelper(
    private val context: Context,
    private val requestPermissions: ManagedActivityResultLauncher<Array<String>, Map<String, @JvmSuppressWildcards Boolean>>,
    private val onGranted: () -> Unit,
) {
    fun checkGalleryPermission() {
        if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.UPSIDE_DOWN_CAKE &&
            (ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(context, READ_MEDIA_VISUAL_USER_SELECTED) == PERMISSION_GRANTED)
        ) {
            onGranted()
        } else if (
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(context, READ_MEDIA_IMAGES) == PERMISSION_GRANTED
        ) {
            onGranted()
        } else if (ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PERMISSION_GRANTED) {
            onGranted()
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
}


@Composable
fun rememberGalleryPermissionHelper(onGranted: () -> Unit, onDenied: () -> Unit): PermissionHelper {
    val context = LocalContext.current
    val launcher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            val granted = permissions.any { it.value }
            if (granted) {
                onGranted()
            } else {
                onDenied()
            }
        }
    return PermissionHelper(context, launcher, onGranted)
}
