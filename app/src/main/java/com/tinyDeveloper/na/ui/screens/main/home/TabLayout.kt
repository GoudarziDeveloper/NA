package com.tinyDeveloper.na.ui.screens.main.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import com.google.accompanist.pager.*
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.notifications.Notification
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatScreen
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.home.common.CommonNotificationsScreen
import com.tinyDeveloper.na.ui.screens.main.home.personal.PersonalNotificationsScreen
import io.getstream.chat.android.compose.viewmodel.messages.AttachmentsPickerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageComposerViewModel
import io.getstream.chat.android.compose.viewmodel.messages.MessageListViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(
    appViewModel: AppViewModel,
    chatViewModel: ChatViewModel,
    onItemClicked: (id:Int) -> Unit,
    commonNotification: List<Notification>?,
    privateNotification: List<Notification>?,
    updateClicked: (String) -> Unit,
    deleteClicked: (String) -> Unit,
    isLoading: Boolean,
    currentPage: Int,
    setCurrentPage: (Int) -> Unit,
    swipeToRefreshCommon: () -> Unit,
    swipeToRefreshPersonal: () -> Unit,
    adminStatus: (Boolean) -> Unit,
    showChat: Boolean
){
    val pagerState = rememberPagerState(0)
    Column(modifier = Modifier.fillMaxSize()) {
        Tabs(pagerState = pagerState, showChat = showChat)
        TabsContent(
            appViewModel = appViewModel,
            pagerState = pagerState,
            onItemClicked = onItemClicked,
            commonNotification = commonNotification,
            privateNotification = privateNotification,
            isLoading = isLoading,
            updateClicked = updateClicked,
            deleteClicked = deleteClicked,
            setCurrentPage = setCurrentPage,
            swipeToRefreshCommon = swipeToRefreshCommon,
            swipeToRefreshPersonal = swipeToRefreshPersonal,
            chatViewModel = chatViewModel,
            adminStatus = adminStatus,
            showChat = showChat
        )
    }
    if (showChat){
        LaunchedEffect(key1 = Unit){
            delay(200)
            pagerState.scrollToPage(currentPage)
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(pagerState: PagerState, showChat: Boolean){
    val tabList = mutableListOf<Pair<String, Painter>>()
    if (showChat){
        tabList.add(
            "گفتگو" to painterResource(id = R.drawable.common_notification),
        )
    }
    tabList.add("عمومی" to painterResource(id = R.drawable.common_notification))
    tabList.add("خصوصی" to painterResource(id = R.drawable.personal_notification))
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        tabs = {
            tabList.forEachIndexed { index,_ ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = { scope.launch { pagerState.animateScrollToPage(index) } },
                    text = { Text(text = tabList[index].first, maxLines = 1, overflow = TextOverflow.Ellipsis) },
                    //icon = { Icon(modifier = Modifier.size(28.dp), painter = tabList[index].second, contentDescription = "Tab Icon")}
                )
            }
        }
    )
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabsContent(
    appViewModel: AppViewModel,
    pagerState: PagerState,
    onItemClicked: (id: Int) -> Unit,
    commonNotification: List<Notification>?,
    privateNotification: List<Notification>?,
    updateClicked: (String) -> Unit,
    deleteClicked: (String) -> Unit,
    isLoading: Boolean,
    setCurrentPage: (Int) -> Unit,
    swipeToRefreshCommon: () -> Unit,
    swipeToRefreshPersonal: () -> Unit,
    chatViewModel: ChatViewModel,
    adminStatus: (Boolean) -> Unit,
    showChat: Boolean
){
    setCurrentPage(pagerState.currentPage)
    if (pagerState.currentPage == 0 && showChat)
        adminStatus(false)
    else
        adminStatus(appViewModel.roles.value?.addNotification == "1")

    HorizontalPager(state = pagerState, count = if (showChat) 3 else 2) {pager->
        if (showChat){
            when(pager){
                0 -> {
                    ChatScreen(
                        appViewModel = appViewModel,
                        chatViewModel = chatViewModel
                    )
                }
                1 -> {
                    CommonNotificationsScreen(
                        appViewModel = appViewModel,
                        commonNotifications = commonNotification,
                        onItemClicked = onItemClicked,
                        updateClicked = updateClicked,
                        deleteClicked = deleteClicked,
                        isLoading = isLoading,
                        swipeToRefresh = swipeToRefreshCommon
                    )
                }
                2 -> {
                    PersonalNotificationsScreen(
                        appViewModel = appViewModel,
                        privateNotifications = privateNotification,
                        onItemClicked = onItemClicked,
                        updateClicked = updateClicked,
                        deleteClicked = deleteClicked,
                        isLoading = isLoading,
                        swipeRefresh = swipeToRefreshPersonal
                    )
                }
            }
        }else {
            when(pager){
                0 -> {
                    CommonNotificationsScreen(
                        appViewModel = appViewModel,
                        commonNotifications = commonNotification,
                        onItemClicked = onItemClicked,
                        updateClicked = updateClicked,
                        deleteClicked = deleteClicked,
                        isLoading = isLoading,
                        swipeToRefresh = swipeToRefreshCommon
                    )
                }
                1 -> {
                    PersonalNotificationsScreen(
                        appViewModel = appViewModel,
                        privateNotifications = privateNotification,
                        onItemClicked = onItemClicked,
                        updateClicked = updateClicked,
                        deleteClicked = deleteClicked,
                        isLoading = isLoading,
                        swipeRefresh = swipeToRefreshPersonal
                    )
                }
            }
        }
    }
}