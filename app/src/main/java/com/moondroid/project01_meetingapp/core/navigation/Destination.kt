package com.moondroid.project01_meetingapp.core.navigation

import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlinx.serialization.Serializable

interface Destination

@Serializable
object Splash : Destination

@Serializable
object InterestList : Destination

@Serializable
object LocationList : Destination


@Serializable
object Sign : Destination

@Serializable
data class SignUp(val socialSignData: SocialSignData) : Destination

@Serializable
data class SignIn(val isTokenExpired: Boolean = false) : Destination


@Serializable
object Home : Destination

@Serializable
object HomeRoot : Destination

@Serializable
object HomeList : Destination

@Serializable
object HomeSearch : Destination

@Serializable
object HomeMyGroup : Destination

@Serializable
object HomeMap : Destination
