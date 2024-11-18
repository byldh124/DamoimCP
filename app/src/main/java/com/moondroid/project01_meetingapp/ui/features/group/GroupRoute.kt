package com.moondroid.project01_meetingapp.ui.features.group

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
object GroupDetail

@Serializable
object GroupGallery

@Serializable
object GroupChat

data class GroupRoute(val name: String, val route: Any, val icon: ImageVector)

val groupRoutes = listOf(
    GroupRoute("상세보기", GroupDetail, Icons.Rounded.Home),
    GroupRoute("갤러리", GroupGallery, Icons.Rounded.Search),
    GroupRoute("채팅", GroupChat, Icons.Rounded.Person),
)

