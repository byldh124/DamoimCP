package com.moondroid.project01_meetingapp.ui.features.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.Search
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.serialization.Serializable

@Serializable
object HomeList

@Serializable
object HomeSearch

@Serializable
object HomeMyGroup

@Serializable
object HomeMap

data class HomeRoute(val name: String, val title: String, val route: Any, val icon: ImageVector)

val homeRoutes = listOf(
    HomeRoute("home", "모임 리스트", HomeList, Icons.Rounded.Home),
    HomeRoute("search", "모임 검색", HomeSearch, Icons.Rounded.Search),
    HomeRoute("my", "나의 모임", HomeMyGroup, Icons.Rounded.Person),
    HomeRoute("map", "모임 보기", HomeMap, Icons.Rounded.MoreVert),
)
