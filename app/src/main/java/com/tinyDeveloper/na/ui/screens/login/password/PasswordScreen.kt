package com.tinyDeveloper.na.ui.screens.login.password

import android.app.Activity
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.theme.LARGE_PADDING
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.PHONE_SCREEN_CONTENT_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING
import com.tinyDeveloper.na.utils.Resource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordScreen(
    navigateToVerificationScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    appViewModel: AppViewModel
){
    var sendCodeIsOpen by remember { mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    var noAccessIsOpen by remember { mutableStateOf(false) }
    var notResponse by remember { mutableStateOf(false) }
    var visiblePassword by remember { mutableStateOf(false) }
    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { errorIsOpen = false },
        onYesClicked = {  appViewModel.loginRequest(); errorIsOpen = false },
        yesTitle = "تلاش مجدد",
        noTitle = "انصراف",
        dismiss = false
    )
    DisplayAlertDialog(
        openDialog = noAccessIsOpen,
        title = "عدم تطابق",
        description = "کاربر با این شماره موبایل و گذرواژه دسترسی استفاده ندارد، ایا مایل به ویرایش اطلاعات خود هستید؟",
        onCloseClicked = { noAccessIsOpen = false },
        onYesClicked = { navigateToPhoneScreen(); noAccessIsOpen = false },
        yesTitle = "ویرایش موبایل",
        noTitle = "ویرایش گذرواژه",
        dismiss = false
    )
    val app = LocalContext.current as Activity
    DisplayAlertDialog(
        openDialog = notResponse,
        title = "کارگزار پیامکی پاسخ نمیدهد!",
        description = "کارگزار پیامکی درحال حاضر قادر به ارسال پیامک نیست شما میتوانید دوباره تلاش کنید و درصورت تداوم مشکل مدتی بعد دوباره تلاش کنید و یا با ابوالقاسمی تماس بگیرید!",
        onCloseClicked = { app.finishAffinity() },
        onYesClicked = { appViewModel.loginRequest(); notResponse = false },
        yesTitle = "تلاش مجدد",
        noTitle = "بعدا تلاش میکنم",
        dismiss = false
    )
    val loginState = appViewModel.loginValue.value?.data?.message?: ""
    LaunchedEffect(key1 = appViewModel.loginValue.value){
        when(appViewModel.loginValue.value){
            is Resource.Success -> {
               when(loginState){
                   "Verification Code Successfully Sent!" -> {
                       navigateToVerificationScreen()
                       appViewModel.setShouldStartSMSRetrievalChange(true)
                   }
                   "You Have No Access!" -> noAccessIsOpen = true
                   "To Send Verification Code An Error Occurred!" -> notResponse = true
                   else -> errorIsOpen = true
               }
            }
            is Resource.Error -> errorIsOpen = true
            else -> {}
        }
    }
    Column(modifier = Modifier
        .fillMaxSize()
        .padding(bottom = PHONE_SCREEN_CONTENT_PADDING),
        verticalArrangement = Arrangement.Center) {
        Text(
            modifier = Modifier.padding(start = MEDIUM_PADDING),
            text = "شماره موبایل وارد شده ${appViewModel.phoneValue.value} میباشد"
        )
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
                .padding(bottom = MEDIUM_PADDING),
            value = appViewModel.passwordValue.value,
            onValueChange = appViewModel::onPasswordChange,
            label = { Text(text = "گذرواژه") },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    sendCodeIsOpen = true
                }
            ),
            singleLine = true,
            visualTransformation = if(visiblePassword) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Icon(
                    painter = painterResource(id = if (visiblePassword) R.drawable.visible else R.drawable.invisible),
                    contentDescription = "Password Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .clip(RoundedCornerShape(MEDIUM_PADDING))
                        .clickable { visiblePassword = !visiblePassword }
                )
            }
        )
        Row {
            OutlinedButton(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = MEDIUM_PADDING, end = SMALL_PADDING),
                onClick = { navigateToPhoneScreen() }) {
                Text(text = "ویرایش شماره موبایل")
            }
            DisplayAlertDialog(
                openDialog = sendCodeIsOpen,
                title = "ارسال کد؟",
                description = "ایا از صحت شماره موبایل  ${appViewModel.phoneValue.value} اطمینان دارید؟",
                onCloseClicked = { navigateToPhoneScreen(); sendCodeIsOpen = false},
                onYesClicked = { appViewModel.loginRequest(); sendCodeIsOpen = false },
                yesTitle = "ارسال کد",
                noTitle = "ویرایش شماره موبایل",
                dismiss = false,
                onDismissRequest = { sendCodeIsOpen = false }
            )
            Button(
                modifier = Modifier
                    .weight(0.5f)
                    .padding(start = SMALL_PADDING, end = MEDIUM_PADDING),
                enabled = appViewModel.passwordValue.value.isNotEmpty() &&
                        appViewModel.loginValue.value !is Resource.Loading,
                onClick = { sendCodeIsOpen = true }) {
                Text(text = "ثبت گذرواژه")
            }
        }
        Spacer(modifier = Modifier.height(SMALL_PADDING))
        LinearProgressIndicator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MEDIUM_PADDING)
                .alpha(if (appViewModel.loginValue.value is Resource.Loading) 1f else 0f)
        )
        Spacer(modifier = Modifier.height(MEDIUM_PADDING))
        Text(modifier = Modifier.padding(MEDIUM_PADDING), text = "اگر از دو کاربر از قبل ساخته شده استفاده میکنید رمز 123456 را وارد کنید. کدتایید برای این شماره ها ارسال نمیگردد.")
    }
}