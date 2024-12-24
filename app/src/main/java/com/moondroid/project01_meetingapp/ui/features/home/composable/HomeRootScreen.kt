package com.moondroid.project01_meetingapp.ui.features.home.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.moondroid.damoim.domain.model.Profile
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
import com.moondroid.project01_meetingapp.ui.theme.Gray04
import com.moondroid.project01_meetingapp.ui.theme.Mint01
import com.moondroid.project01_meetingapp.ui.theme.Red02
import com.moondroid.project01_meetingapp.ui.theme.Typography
import kotlinx.coroutines.launch

@Composable
fun HomeRootScreen(
    navigateToGroup: (title: String) -> Unit,
    navigateToInterest: () -> Unit,
    navigateToMyInfo: () -> Unit,
    navigateToSetting: () -> Unit,
    navigateToSign: () -> Unit,
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val viewModel = hiltViewModel<HomeViewModel>()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val scope = rememberCoroutineScope()

    LaunchedEffect(viewModel) {
        viewModel.event.send(HomeContract.Event.GetProfile)
        viewModel.effect.collect {
            when (it) {
                HomeContract.Effect.Expired -> navigateToSign()
            }
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            HomeDrawer(uiState.profile) {
                scope.launch {
                    drawerState.close()
                }
                navigateToMyInfo()
            }
        }
    ) {
        HomeRootBody(drawerState, viewModel, navigateToSetting = { navigateToSetting() }) {
            navigateToGroup(it)
        }
    }
}

@Composable
private fun HomeDrawer(profile: Profile, navigateToMyInfo: () -> Unit) {
    ModalDrawerSheet {
        HomeDrawerHeader(profile, navigateToMyInfo)
    }
}

@Composable
private fun HomeDrawerHeader(profile: Profile, navigateToMyInfo: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth(0.9f)
            .background(color = Mint01)
            .clickable(onClick = navigateToMyInfo),
    ) {
        Card(
            modifier = Modifier
                .width(120.dp)
                .padding(vertical = 15.dp, horizontal = 10.dp)
                .aspectRatio(1f),
            shape = CircleShape
        ) {
            AsyncImage(profile.thumb, contentDescription = "thumb")
        }

        Spacer(Modifier.width(10.dp))

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(top = 15.dp, end = 10.dp)
        ) {
            Text(
                "프로필 수정",
                style = Typography.bodyMedium,
                color = Color.White,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(profile.name, style = Typography.bodyMedium)
            Spacer(Modifier.height(10.dp))
            Text(profile.message, style = Typography.bodySmall)
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeRootBody(
    drawerState: DrawerState,
    viewModel: HomeViewModel,
    navigateToSetting: () -> Unit,
    navigateToGroup: (title: String) -> Unit,
) {
    val scope = rememberCoroutineScope()
    val navController = rememberNavController()
    val title = remember {
        mutableStateOf("모임 리스트")
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(title.value)
                },
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
                },
            )
        },
        bottomBar = {
            HomeBottomNavigation(navController) {
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
        Box(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
        ) {
            NavHost(
                navController = navController, startDestination = HomeList,
            ) {
                composable<HomeList> {
                    HomeListScreen(viewModel) { title ->
                        navigateToGroup(title)
                    }
                }

                composable<HomeSearch> {
                    SearchScreen(viewModel) { title ->
                        navigateToGroup(title)
                    }
                }

                composable<HomeMyGroup> {
                    MyGroupScreen(viewModel) { title ->
                        navigateToGroup(title)
                    }
                }

                composable<HomeMap> {
                    MapScreen()
                }
            }
        }
    }
}


@Composable
private fun HomeBottomNavigation(navController: NavController, onItemClick: (HomeRoute) -> Unit) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = Color.Transparent,
        modifier = Modifier.border(0.3.dp, Gray04)
    ) {
        homeRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                icon = {
                    Icon(painterResource(topLevelRoute.drawable), topLevelRoute.name)
                },
                label = { Text(topLevelRoute.name, fontSize = 12.sp, style = Typography.bodyMedium) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Red02,
                    selectedTextColor = Red02,
                    unselectedIconColor = Gray03,
                    unselectedTextColor = Gray03,
                    indicatorColor = Color.Transparent,
                ),
                selected = currentDestination?.hierarchy?.any { it.hasRoute(topLevelRoute.route::class) } == true,
                onClick = {
                    onItemClick(topLevelRoute)
                },
                //alwaysShowLabel = false,
            )
        }
    }
}