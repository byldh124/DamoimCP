package com.moondroid.project01_meetingapp.core.navigation

import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlinx.serialization.Serializable

interface Destination

// common destination
@Serializable
object Splash : Destination

@Serializable
object InterestList : Destination

@Serializable
object LocationList : Destination

// sign destination
@Serializable
object Sign : Destination

@Serializable
data class SignUp(val socialSignData: SocialSignData) : Destination

@Serializable
data class SignIn(val isTokenExpired: Boolean = false) : Destination

// home destination
@Serializable
object Home : Destination

@Serializable
object HomeRoot : Destination


// group detail destination
@Serializable
object Group: Destination

@Serializable
data class GroupRoot(val title: String): Destination




