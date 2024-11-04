package com.moondroid.project01_meetingapp.core

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.canhub.cropper.parcelable
import com.moondroid.project01_meetingapp.ui.features.home.composable.HomeRootScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signin.composable.SignInScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signup.composable.SignUpScreen
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.features.splash.composable.SplashScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlin.reflect.typeOf

interface Destination

@Serializable
object Splash : Destination

@Serializable
object Sign : Destination

@Serializable
data class SignUp(val socialSignData: SocialSignData) : Destination

val SocialSignDataType = object : NavType<SocialSignData>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): SocialSignData? {
        return bundle.parcelable<SocialSignData>(key)
    }

    override fun parseValue(value: String): SocialSignData {
        return Json.decodeFromString<SocialSignData>(value)
    }

    override fun serializeAsValue(value: SocialSignData): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: SocialSignData) {
        bundle.putParcelable(key, value)
    }
}

@Serializable
data class SignIn(val isTokenExpired: Boolean = false) : Destination

@Serializable
object Home : Destination

@Serializable
object HomeRoot : Destination

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    val onBack: () -> Unit = {
        navController.popBackStack()
    }

    var expired: (() -> Unit)? = null

    val onAccessTokenExpired: (() -> Unit) -> Unit = {
        expired = it
        navController.navigate(SignIn(true)) {
            launchSingleTop = true
        }
    }

    NavHost(navController = navController, startDestination = Splash) {
        composable<Splash> {
            SplashScreen(navigate = {
                navController.navigate(it) {
                    popUpTo(Splash) { inclusive = true }
                }
            })
        }

        navigation<Sign>(startDestination = SignIn::class) {
            composable<SignIn> {
                SignInScreen(navigate = { d, b ->
                    navController.navigate(d, b)
                })
            }
            composable<SignUp>(typeMap = mapOf(typeOf<SocialSignData>() to SocialSignDataType)) { backStackEntry ->
                val signUp: SignUp = backStackEntry.toRoute()
                SignUpScreen(signUp.socialSignData, navigate = { d, b ->
                    navController.navigate(d, b)
                }) {
                    navController.popBackStack()
                }
            }
        }

        navigation<Home>(startDestination = HomeRoot) {
            composable<HomeRoot> { HomeRootScreen(onAccessTokenExpired) }
        }
    }
}