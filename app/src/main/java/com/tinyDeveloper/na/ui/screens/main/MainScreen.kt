package com.tinyDeveloper.na.ui.screens.main

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.bases.get.GetBaseResponse
import com.tinyDeveloper.na.navigation.destinations.main.MainScreensBottomNav
import com.tinyDeveloper.na.navigation.destinations.main.SetupMainScreenNavigation
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.component.snowfall
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.Constants.BASES_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.JOBS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.JOB_DETAIL_TITLE
import com.tinyDeveloper.na.utils.Constants.NOTIFICATIONS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.PROFILE_SCREEN
import com.tinyDeveloper.na.utils.Constants.PROFILE_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.SERVER_EXT
import com.tinyDeveloper.na.utils.Constants.SETTINGS_SCREEN
import com.tinyDeveloper.na.utils.Constants.SETTINGS_SCREEN_TITLE
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import com.tinyDeveloper.na.utils.Constants.USERS_SCREEN_TITLE
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    appViewModel: AppViewModel,
    notificationsViewModel: NotificationsViewModel,
    jobsViewModel: JobsViewModel,
    usersViewModel: UsersViewModel,
    profileViewModel: ProfileViewModel,
    basesViewModel: BasesViewModel,
    settingsViewModel: SettingsViewModel,
    chatViewModel: ChatViewModel
){
    var messageIsOpened by remember{ mutableStateOf(false) }
    var isAdmin by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    var screenTitle by remember { mutableStateOf("") }
    var fabNavigate by remember { mutableStateOf("") }
    var isJobDetail by remember { mutableStateOf(false) }
    var firstScreen by remember { mutableStateOf("") }

    DisplayAlertDialog(
        openDialog = messageIsOpened,
        title = appViewModel.baseInfoValue.value?.data?.baseInfo?.messageTitle?:"",
        description = appViewModel.baseInfoValue.value?.data?.baseInfo?.messageText?:"",
        onCloseClicked = { },
        onYesClicked = { messageIsOpened = false },
        yesTitle = "باشه",
        noTitle = "",
        dismiss = false
    )

    var firstLaunch by rememberSaveable{ mutableStateOf(true) }
    LaunchedEffect(key1 = true){
        if (firstLaunch){
            if (appViewModel.baseInfoValue.value?.data?.baseInfo?.messageEnabled == "1"){
                messageIsOpened = true
            }
            firstLaunch = false
        }
    }

    val profileImageIsVisible = PROFILE_SCREEN_TITLE != screenTitle
    val settingsIsVisible = appViewModel.roles.value?.getBaseInfo == "1" && screenTitle != SETTINGS_SCREEN_TITLE
    val showSnow = appViewModel.showSnow.value

    Scaffold(
        modifier = if(appViewModel.showSnow.value){
            Modifier.snowfall(
            isActive = true,
            baseFrameDurationMillis = 5,
            baseSpeedPxAt60Fps = 8
        ) } else Modifier,
        topBar = {
            TopAppBar(
                title ={
                    Text(text = screenTitle, color = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier
                            .fillMaxWidth(
                                if(profileImageIsVisible && settingsIsVisible) 1f
                                else if (profileImageIsVisible && !settingsIsVisible) 0.8f
                                else if (!profileImageIsVisible && settingsIsVisible) 1f
                                else 0.95f
                            ),
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    if(profileImageIsVisible){
                        Image(
                            modifier = Modifier
                                .size(60.dp)
                                .padding(LARGE_PADDING)
                                .clip(CircleShape)
                                .clickable {
                                    var pop: Int? = null
                                    navController
                                        .graph.forEach {
                                            if (it.route == PROFILE_SCREEN) pop =
                                                it.id;return@forEach
                                        }
                                    if (appViewModel.roles.value.let { if (it == null) false else it.getUser == "1" })
                                        navController.navigate(MainScreensBottomNav.Profile.route) {
                                            popUpTo(if (pop != null) pop!! else navController.graph.startDestinationId)
                                            launchSingleTop = true
                                        } else return@clickable
                                },
                            painter =
                            if (appViewModel.profileImage.value != null && appViewModel.profileImage.value!!.isNotEmpty())
                                rememberAsyncImagePainter(BASE_URL + SERVER_EXT + UPLOAD_URL + appViewModel.profileImage.value)
                            else painterResource(id = R.drawable.ic_user),
                            contentDescription = "Profile Image",
                            contentScale = ContentScale.Crop
                        )
                    }
                },
                actions = {
                    if(settingsIsVisible){
                        Icon(
                            modifier = Modifier
                                .padding(MEDIUM_PADDING)
                                .size(32.dp)
                                .clip(CircleShape)
                                .clickable {
                                    var pop: Int? = null
                                    navController
                                        .graph.forEach {
                                            if (it.route == SETTINGS_SCREEN) pop =
                                                it.id;return@forEach
                                        }
                                    if (appViewModel.roles.value.let {
                                            if (it == null) false else it.getBaseInfo == "1"
                                        }) navController.navigate(MainScreensBottomNav.Settings.route) {
                                        popUpTo(if (pop != null) pop!! else navController.graph.startDestinationId)
                                        launchSingleTop = true
                                    } else return@clickable
                                },
                            imageVector = Icons.Default.Settings,
                            contentDescription = "Settings Icon"
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomNavigation(
                navController = navController,
                notificationsAccess = appViewModel.roles.value.let {
                    if (it == null) false
                    else it.addNotification == "1" ||
                            it.deleteNotification == "1" ||
                            it.getNotifications == "1" || it.updateNotification == "1"},
                jobsAccess = appViewModel.roles.value.let {
                    if (it == null) false
                    else it.addJob == "1" || it.deleteJob == "1" ||
                            it.getJob == "1" || it.updateJob == "1"
                },
                usersAccess = appViewModel.roles.value.let {
                    if (it == null) false else it.deleteUser == "1"||
                            it.addUser == "1" || it.updateUsers  == "1" || it.getUsers == "1"  },
                basesAccess = appViewModel.roles.value.let {
                    if (it == null) false else it.addBase == "1" || it.deleteBase == "1" ||
                            it.getBases == "1" || it.updateBase == "1"
                },
                settingsAccess = appViewModel.roles.value.let {
                    if (it == null) false else it.getBaseInfo == "1" && it.editBaseInfo == "1"
                },
                profileAccess = appViewModel.roles.value.let {
                    if (it == null) false else it.getUser == "1"
                },
                firstScreen = { firstScreen = it }
            ){ title -> screenTitle = title}
        },
        floatingActionButton = {
                if (isAdmin && (
                            screenTitle == NOTIFICATIONS_SCREEN_TITLE ||
                                    screenTitle == USERS_SCREEN_TITLE ||
                                    screenTitle == JOBS_SCREEN_TITLE) ||
                    screenTitle == BASES_SCREEN_TITLE
                ){
                    Fab(
                        navController = navController,
                        fabNavigate = fabNavigate,
                        showSnow = showSnow,
                        getSubmission = appViewModel.roles.value?.getSubmissions == "1"
                    )
                }else if(screenTitle == JOB_DETAIL_TITLE){
                    if (isJobDetail){
                        if (isAdmin){
                            Fab(
                                navController = navController,
                                fabNavigate = fabNavigate,
                                image = R.drawable.ic_receive_submissions,
                                showSnow = showSnow,
                                getSubmission = appViewModel.roles.value?.getSubmissions == "1"
                            )
                        }else {
                            Fab(
                                navController = navController,
                                fabNavigate = fabNavigate,
                                image = R.drawable.ic_send_submission,
                                showSnow = showSnow,
                                getSubmission = appViewModel.roles.value?.getSubmissions == "1"
                            )
                        }
                    }
                }

            }
    ) {
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(top = TOP_BAR_HEIGHT, bottom = 74.dp)){
            Divider(modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
                .height(1.dp), color = TopBarDivider)
            SetupMainScreenNavigation(
                navController = navController,
                appViewModel = appViewModel,
                adminStatus = { isAdmin = it },
                screenTitle = { screenTitle = it },
                fabNavigate = { fabNavigate = it },
                jobDetail = { isJobDetail = it},
                notificationsViewModel = notificationsViewModel,
                jobsViewModel = jobsViewModel,
                usersViewModel = usersViewModel,
                profileViewModel = profileViewModel,
                firstScreen = firstScreen,
                basesViewModel = basesViewModel,
                settingsViewModel = settingsViewModel,
                chatViewModel = chatViewModel,
            )
        }
    }
}

@Composable
fun Fab(
    navController: NavHostController,
    fabNavigate: String,
    image: Int? = null,
    showSnow: Boolean,
    getSubmission: Boolean
){
    var extended by remember { mutableStateOf(false) }
    Box {
        if (image == null){
            FloatingActionButton(modifier = Modifier.padding(4.dp), onClick = { navController.navigate(fabNavigate) }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Fab Icon")
            }
        }else{
            LaunchedEffect(key1 = Unit){
                extended = true
            }
            LaunchedEffect(key1 = extended){
                delay(3000)
                extended = !extended
            }
            ExtendedFloatingActionButton(
                expanded = extended,
                modifier = Modifier.padding(4.dp),
                onClick = { navController.navigate(fabNavigate) },
                icon = {
                    Icon(imageVector = Icons.Default.Check, contentDescription = "Fab Icon")
                },
                text = {
                    Text(text = if(getSubmission) "پاسخ ها" else "ارسال پاسخ")
                }
            )
        }
        if (showSnow && !extended){
            Image(
                modifier = Modifier
                    .width(54.dp)
                    .align(Alignment.TopCenter),
                painter = painterResource(id = R.drawable.snow2),
                contentDescription = "Snow Image",
                contentScale = ContentScale.Fit
            )
        }
    }
}

    @Composable
fun BottomNavigation(
    navController: NavHostController,
    notificationsAccess: Boolean,
    jobsAccess: Boolean,
    usersAccess: Boolean,
    basesAccess: Boolean,
    settingsAccess: Boolean,
    profileAccess: Boolean,
    firstScreen: (String) -> Unit,
    toolbarTitleChange: (String) -> Unit
){
    var firested by remember{ mutableStateOf(false) }
    val screens = mutableListOf<MainScreensBottomNav>()
    if (notificationsAccess){
        if (!firested)
            firstScreen(MainScreensBottomNav.Notifications.route)
        screens.add(MainScreensBottomNav.Notifications)
        firested = true
    }
    if (jobsAccess){
        if (!firested)
            firstScreen(MainScreensBottomNav.Jobs.route)
        screens.add(MainScreensBottomNav.Jobs)
        firested = true
    }
    if (usersAccess){
        if (!firested)
            firstScreen(MainScreensBottomNav.Users.route)
        screens.add(MainScreensBottomNav.Users)
        firested = true
    }
    if (basesAccess){
        if (!firested)
            firstScreen(MainScreensBottomNav.Bases.route)
        screens.add(MainScreensBottomNav.Bases)
    }

    val navBaskStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBaskStackEntry?.destination

    NavigationBar {
        screens.forEach { screen->
            AddBottomAppBarItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController,

                toolbarTitleChange = toolbarTitleChange
            )
        }
    }
}

@Composable
fun RowScope.AddBottomAppBarItem(
    screen: MainScreensBottomNav,
    currentDestination: NavDestination?,
    navController: NavHostController,
    toolbarTitleChange: (String) -> Unit
){
    val selected = currentDestination?.hierarchy?.any{ it.route == screen.route } == true
    if (selected)
        toolbarTitleChange(screen.title)
    NavigationBarItem(
        label = {
        Text(
            text = screen.title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            fontStyle = MaterialTheme.typography.labelSmall.fontStyle,
            fontSize = MaterialTheme.typography.labelSmall.fontSize
        )
    },
        icon = { Icon(imageVector = screen.icon, contentDescription = "Bottom Navigation Icon") },
        selected = selected,
        onClick = {
            if (!selected){
                navController.navigate(screen.route){
                    popUpTo(navController.graph.findStartDestination().id)
                    launchSingleTop = true
                }
            }
        },
    )
}