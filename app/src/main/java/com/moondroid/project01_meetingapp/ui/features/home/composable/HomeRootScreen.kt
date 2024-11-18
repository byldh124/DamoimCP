package com.moondroid.project01_meetingapp.ui.features.home.composable

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavOptions
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.moondroid.damoim.domain.model.Profile
import com.moondroid.project01_meetingapp.core.navigation.Destination
import com.moondroid.project01_meetingapp.core.navigation.GroupRoot
import com.moondroid.project01_meetingapp.ui.features.home.HomeContract
import com.moondroid.project01_meetingapp.ui.features.home.HomeList
import com.moondroid.project01_meetingapp.ui.features.home.HomeMap
import com.moondroid.project01_meetingapp.ui.features.home.HomeMyGroup
import com.moondroid.project01_meetingapp.ui.features.home.HomeRoute
import com.moondroid.project01_meetingapp.ui.features.home.HomeSearch
import com.moondroid.project01_meetingapp.ui.features.home.HomeViewModel
import com.moondroid.project01_meetingapp.ui.features.home.composable.pager.HomeListScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.pager.MapScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.pager.MyGroupScreen
import com.moondroid.project01_meetingapp.ui.features.home.composable.pager.SearchScreen
import com.moondroid.project01_meetingapp.ui.features.home.homeRoutes
import com.moondroid.project01_meetingapp.ui.theme.Gray03
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Red04
import kotlinx.coroutines.launch

@Composable
fun HomeRootScreen(onAccessTokenExpired: (() -> Unit) -> Unit, navigate: (Destination, NavOptions?) -> Unit) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.GetProfile)
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = { HomeDrawer(uiState.profile) }
    ) {
        HomeRootBody(drawerState, viewModel) {
            navigate(GroupRoot(it), null)
        }
    }
}

@Composable
private fun HomeDrawer(profile: Profile?) {
    ModalDrawerSheet {
        Box(modifier = Modifier.fillMaxWidth()) {
            Text(profile?.name ?: "AAAaaaaaa")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeRootBody(
    drawerState: DrawerState,
    viewModel: HomeViewModel,
    navigate:(String) -> Unit
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val title = remember {
        mutableStateOf("모임 리스트")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text(title.value) },
                navigationIcon = {
                    IconButton({
                        scope.launch {
                            drawerState.open()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.Menu,
                            contentDescription = "뒤로가기",
                        )
                    }
                }
            )
        },
        bottomBar = {
            MyBottomNavigation(navController) {
                title.value = it.title
                navController.navigate(it.route) {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                }
            }
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            NavHost(navController = navController, startDestination = HomeList) {
                composable<HomeList> {
                    HomeListScreen(viewModel) { title ->
                        navigate(title)
                    }
                }

                composable<HomeSearch> {
                    SearchScreen(viewModel)
                }

                composable<HomeMyGroup> {
                    MyGroupScreen(viewModel)
                }

                composable<HomeMap> {
                    MapScreen()
                }
            }
        }
    }
}


@Composable
private fun MyBottomNavigation(navController: NavController, onItemClick: (HomeRoute) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        homeRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(
                        modifier = Modifier.width(32.dp),
                        imageVector = topLevelRoute.icon,
                        contentDescription = topLevelRoute.name
                    )
                },
                label = { Text(topLevelRoute.name, fontSize = 14.sp) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Red02,
                    selectedTextColor = Red02,
                    unselectedIconColor = Gray03,
                    unselectedTextColor = Gray03,
                    indicatorColor = Red04
                ),
                selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                onClick = {
                    onItemClick(topLevelRoute)
                },
                alwaysShowLabel = false,
            )
        }
    }
}