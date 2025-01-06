package com.moondroid.project01_meetingapp.core.navigation

import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlinx.serialization.Serializable

interface Destination
@Serializable
object App: Destination

// common destination
@Serializable
object Splash : Destination

@Serializable
object InterestList : Destination

@Serializable
object LocationList : Destination

@Serializable
data class ImageList(val aspectRatio: Int): Destination


// sign destination
@Serializable
object Sign : Destination

@Serializable
data class SignIn(val isTokenExpired: Boolean = false) : Destination

@Serializable
data class SignUp(val socialSignData: SocialSignData) : Destination


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

@Serializable
object MyInfo: Destination

@Serializable
data class UserProfile(val id: String): Destination




