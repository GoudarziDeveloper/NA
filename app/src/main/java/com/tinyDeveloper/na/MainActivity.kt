package com.tinyDeveloper.na

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.tinyDeveloper.na.navigation.SetupNavigation
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.screens.main.home.chat.ChatViewModel
import com.tinyDeveloper.na.ui.screens.main.jobs.JobsViewModel
import com.tinyDeveloper.na.ui.screens.main.home.NotificationsViewModel
import com.tinyDeveloper.na.ui.screens.main.profile.ProfileViewModel
import com.tinyDeveloper.na.ui.screens.main.settings.SettingsViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.ui.theme.NATheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.getstream.chat.android.compose.ui.util.mirrorRtl

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val appViewModel: AppViewModel by viewModels()
    private val notificationsViewModel: NotificationsViewModel by viewModels()
    private val jobsViewModel: JobsViewModel by viewModels()
    private val usersViewModel: UsersViewModel by viewModels()
    private val profileViewModel: ProfileViewModel by viewModels()
    private val basesViewModel: BasesViewModel by viewModels()
    private val settingsViewModel: SettingsViewModel by viewModels()
    private val chatViewModel: ChatViewModel by viewModels()
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        window.decorView.layoutDirection = View.LAYOUT_DIRECTION_RTL
        super.onCreate(savedInstanceState)
        setContent {
            NATheme {
                val navController = rememberNavController()
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    var showSnow by remember{ mutableStateOf(false) }
                    Box(modifier = Modifier.fillMaxSize()){
                        SetupNavigation(
                            navController = navController,
                            appViewModel = appViewModel,
                            notificationsViewModel = notificationsViewModel,
                            jobsViewModel = jobsViewModel,
                            usersViewModel = usersViewModel,
                            profileViewModel = profileViewModel,
                            basesViewModel = basesViewModel,
                            settingsViewModel = settingsViewModel,
                            chatViewModel = chatViewModel,
                            showSnow = { showSnow = true }
                        )
                        if (appViewModel.showSnow.value && showSnow){
                            Row(modifier = Modifier.fillMaxWidth().height(10.5.dp).align(Alignment.BottomCenter)) {
                                repeat(4){
                                    Image(
                                        modifier = Modifier.weight(1f).mirrorRtl(layoutDirection = if (it % 2 == 0) LayoutDirection.Ltr else LayoutDirection.Rtl),
                                        painter = painterResource(id = R.drawable.snow),
                                        contentDescription = "Snow Image",
                                        contentScale = ContentScale.FillBounds
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}