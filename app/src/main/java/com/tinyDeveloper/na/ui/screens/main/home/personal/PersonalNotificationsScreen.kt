package com.tinyDeveloper.na.ui.screens.main.home.personal

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.notifications.Notification
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationItem
import com.tinyDeveloper.na.ui.screens.main.home.NotificationListShimmer

@Composable
fun PersonalNotificationsScreen(
    appViewModel: AppViewModel,
    privateNotifications: List<Notification>?,
    onItemClicked:(Int) -> Unit,
    isLoading: Boolean,
    updateClicked: (String) -> Unit,
    deleteClicked: (String) -> Unit,
    swipeRefresh: () -> Unit
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoading), onRefresh = swipeRefresh) {
        if (privateNotifications != null) {
            if (isLoading) {
                NotificationListShimmer()
            } else {
                if (privateNotifications.isNotEmpty()) {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        items(items = privateNotifications) {
                            NotificationItem(
                                uploadUrl = appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl
                                    ?: "",
                                notification = it,
                                onItemClicked = onItemClicked,
                                update = appViewModel.roles.value?.updateNotification == "1",
                                delete = appViewModel.roles.value?.deleteNotification == "1",
                                updateClicked = updateClicked,
                                deleteClicked = deleteClicked
                            )
                        }
                    }
                } else {
                    EmptyScreen(
                        image = R.drawable.ic_empty_screen2,
                        text = "در حال حاضر اعلانی برای پایگاه شما وجود ندارد."
                    )
                }
            }
        } else {
            NotificationListShimmer()
        }
    }
}