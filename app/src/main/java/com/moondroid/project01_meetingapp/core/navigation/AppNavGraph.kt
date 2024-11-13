package com.moondroid.project01_meetingapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.moondroid.damoim.common.IntentParam
import com.moondroid.project01_meetingapp.ui.features.common.interest.composable.InterestListScreen
import com.moondroid.project01_meetingapp.ui.features.common.location.composable.LocationListScreen
import com.moondroid.project01_meetingapp.ui.features.common.splash.composable.SplashScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.HomeRootScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.pager.GroupListScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signin.composable.SignInScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signup.composable.SignUpScreen
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import kotlin.reflect.typeOf



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

        composable<InterestList> {
            InterestListScreen(navController)
        }

        composable<LocationList> {
            LocationListScreen(navController)
        }

        navigation<Sign>(startDestination = SignIn::class) {
            composable<SignIn> {
                SignInScreen(navController = navController)
            }
            composable<SignUp>(typeMap = mapOf(typeOf<SocialSignData>() to SocialSignDataType)) { backStackEntry ->
                val signUp: SignUp = backStackEntry.toRoute()
                val interest = backStackEntry.savedStateHandle.get<String>(IntentParam.INTEREST) ?: ""
                val location = backStackEntry.savedStateHandle.get<String>(IntentParam.LOCATION) ?: ""
                SignUpScreen(
                    signUp.socialSignData,
                    interest = interest,
                    location = location,
                    navigate = { d, b ->
                        navController.navigate(d, b)
                    },
                    navigateUp = {
                        navController.popBackStack()
                    }
                )
            }
        }

        navigation<Home>(startDestination = HomeRoot) {
            composable<HomeRoot> {
                HomeRootScreen(
                    onAccessTokenExpired,
                    navigate = { d, o ->
                        navController.navigate(d, o)
                    }
                )
            }
        }
    }
}