package com.tinyDeveloper.na.ui.screens.login.phone

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.tinyDeveloper.na.ui.component.InputChar
import com.tinyDeveloper.na.ui.component.NumberPad
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.NUMBER_PAD_HEIGHT
import com.tinyDeveloper.na.ui.theme.PHONE_SCREEN_CONTENT_PADDING

@Composable
fun PhoneScreen(
    navigateToPasswordScreen: () -> Unit,
    appViewModel: AppViewModel
){
    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(bottom = PHONE_SCREEN_CONTENT_PADDING), verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start){
            Text(modifier = Modifier.padding(start = MEDIUM_PADDING),text = "شماره موبایل خود را وارد کنید")
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MEDIUM_PADDING),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                for(item in 10 downTo 0){
                    InputChar(
                        inputChar =
                        if (appViewModel.phoneValue.value.count() > item)
                            appViewModel.phoneValue.value[item].toString()
                        else "",
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Text(
                modifier = Modifier.padding(MEDIUM_PADDING),
                text = "با توجه به این که کاربرانی قادر به ورود هستند که قبلا توسط مدیر تایید شده باشند برای تست برنامه دو کاربر با نقش های متفاوت ایجاد کرده ایم\nکاربر مدیر با تمامی دسترسی ها: 09123456789\nکاربر معمولی با دسترسی محدود: 09123456788"
            )
        }
        NumberPad(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(NUMBER_PAD_HEIGHT)
                .fillMaxWidth(),
            phone = appViewModel.phoneValue.value,
            onPhoneChange = appViewModel::onPhoneChange,
            acceptEnable = appViewModel.phoneValue.value.count() == 11,
            acceptClicked = navigateToPasswordScreen
        )
    }
}