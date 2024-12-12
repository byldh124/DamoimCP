package com.moondroid.project01_meetingapp.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.moondroid.damoim.common.constant.IntentParam
import com.moondroid.damoim.common.util.debug
import com.moondroid.project01_meetingapp.ui.features.common.imagelist.composable.ImageListScreen
import com.moondroid.project01_meetingapp.ui.features.common.interest.composable.InterestListScreen
import com.moondroid.project01_meetingapp.ui.features.common.location.composable.LocationListScreen
import com.moondroid.project01_meetingapp.ui.features.common.splash.composable.SplashScreen
import com.moondroid.project01_meetingapp.ui.features.group.composable.GroupRootScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.HomeRootScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signin.composable.SignInScreen
import com.moondroid.project01_meetingapp.ui.features.sign.signup.composable.SignUpScreen
import com.moondroid.project01_meetingapp.ui.features.sign.social.SocialSignData
import com.moondroid.project01_meetingapp.ui.features.user.myinfo.composable.MyInfoScreen
import kotlin.reflect.typeOf

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()

    var expired: (() -> Unit)? = null

    val onAccessTokenExpired: (() -> Unit) -> Unit = {
        expired = it
        navController.navigate(SignIn(true)) {
            launchSingleTop = true
        }
    }

    NavHost(navController = navController, startDestination = Splash) {
        debug("destination ${this.route}")
        composable<Splash> {
            SplashScreen(navigate = {
                navController.navigate(it) {
                    popUpTo(Splash) { inclusive = true }
                }
            }, navigateUp = { navController.popBackStack() })
        }

        composable<InterestList> {
            InterestListScreen(navController)
        }

        composable<LocationList> {
            LocationListScreen(navController)
        }

        composable<ImageList> { backStackEntry ->
            val imageList: ImageList = backStackEntry.toRoute()
            ImageListScreen(imageList.aspectRatio, navController)
        }

        navigation<Sign>(startDestination = SignIn::class) {
            composable<SignIn> {
                SignInScreen(
                    navigateToSignUp = {
                        navController.navigate(SignUp(it))
                    }, navigateToHome = {
                        navController.navigate(Home) {
                            popUpTo<Sign> { inclusive = true }
                        }
                    }
                )
            }
            composable<SignUp>(typeMap = mapOf(typeOf<SocialSignData>() to SocialSignDataType)) { backStackEntry ->
                val interestFlow = backStackEntry.savedStateHandle.getStateFlow(IntentParam.INTEREST, "")
                val locationFlow = backStackEntry.savedStateHandle.getStateFlow(IntentParam.LOCATION, "")
                SignUpScreen(
                    interestFlow = interestFlow,
                    locationFlow = locationFlow,
                    navigateToInterest = {
                        navController.navigate(InterestList)
                    },
                    navigateToLocation = {
                        navController.navigate(LocationList)
                    },
                    navigateToHome = {
                        navController.navigate(Home) {
                            popUpTo<Sign> { inclusive = true }
                        }
                    },
                    navigateUp = {
                        navController.navigateUp()
                    }
                )
            }
        }

        navigation<Home>(startDestination = HomeRoot) {
            composable<HomeRoot> {
                HomeRootScreen(
                    navigateToGroup = {
                        navController.navigate(GroupRoot(it))
                    },
                    navigateToMyInfo = {
                        navController.navigate(MyInfo)
                    },
                    navigateToSign = {
                        navController.navigate(Sign) {
                            popUpTo<App> { inclusive = true }
                        }
                    },
                    navigateToInterest = {
                        navController.navigate(InterestList)
                    },
                    navigateToSetting = {
                        debug("navigateToSetting")
                        navController.popBackStack()
                    }
                )
            }
        }

        navigation<Group>(startDestination = GroupRoot::class) {
            composable<GroupRoot> {
                GroupRootScreen(
                    navigateToSign = {
                        navController.navigate(Sign) {
                            popUpTo<App> { inclusive = true }
                        }
                    },
                    navigateUp = { navController.navigateUp() }
                )
            }
        }

        composable<MyInfo> { backstackEntry ->
            val uriFlow = backstackEntry.savedStateHandle.getStateFlow(IntentParam.URI, "")
            val locationFlow = backstackEntry.savedStateHandle.getStateFlow(IntentParam.LOCATION, "")
            MyInfoScreen(
                uriFlow,
                locationFlow,
                navigateToImageList = { navController.navigate(ImageList(aspectRatio = 1)) },
                navigateToLocation = { navController.navigate(LocationList) },
                navigateUp = { navController.navigateUp() }
            )
        }
    }
}