package com.moondroid.project01_meetingapp.ui.features.home

import androidx.annotation.DrawableRes
import com.moondroid.project01_meetingapp.R
import kotlinx.serialization.Serializable

@Serializable
object HomeList

@Serializable
object HomeSearch

@Serializable
object HomeMyGroup

@Serializable
object HomeMap

data class HomeRoute(val name: String, val title: String, val route: Any, @DrawableRes val drawable: Int)

val homeRoutes = listOf(
    HomeRoute("홈화면", "모임 리스트", HomeList, R.drawable.ic_home),
    HomeRoute("모임검색", "모임 검색", HomeSearch, R.drawable.ic_search),
    HomeRoute("내모임", "나의 모임", HomeMyGroup, R.drawable.ic_mygroup),
    HomeRoute("주변모임", "모임 보기", HomeMap, R.drawable.ic_map),
)
