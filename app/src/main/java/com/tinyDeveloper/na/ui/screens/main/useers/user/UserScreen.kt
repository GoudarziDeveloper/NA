package com.tinyDeveloper.na.ui.screens.main.useers.user

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.useers.UsersViewModel
import com.tinyDeveloper.na.utils.Constants.BASE_URL
import com.tinyDeveloper.na.utils.Constants.UPLOAD_URL
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.bitmapToBase64

@Composable
fun UserScreen(
    appViewModel: AppViewModel,
    adminStatus: (Boolean) -> Unit,
    usersViewModel: UsersViewModel = hiltViewModel(),
    phone: String,
    navigateToUsersScreen: () -> Unit
){
    val imageChanged = rememberSaveable { mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    var messageIsOpen by remember { mutableStateOf(false) }
    var isLoading by remember{ mutableStateOf(false) }
    LaunchedEffect(key1 = phone){
        if(phone != "-1" && usersViewModel.userPhone.value != phone){
            usersViewModel.setUserPhone(phone = phone)
            usersViewModel.updateImage(null)
            usersViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                user = phone
            )
        }else if(phone == "-1"){
            usersViewModel.updateUser(empty = true)
            usersViewModel.updateRoles(empty = true)
        }
    }
    val userResponse = usersViewModel.usersResponse.value
    LaunchedEffect(key1 = userResponse){
        when(userResponse){
            is Resource.Success -> {
                isLoading = false
                when(userResponse.data?.message){
                    "Phone Number Is Duplicated!" -> { messageIsOpen = true; usersViewModel.emptyUserResponse() }
                    "User And Role Added SuccessFully!" -> {
                        navigateToUsersScreen()
                        usersViewModel.emptyUserResponse()
                        usersViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                    }
                    "User Updated SuccessFully!" -> {
                        navigateToUsersScreen()
                        usersViewModel.setUserPhone("")
                        usersViewModel.emptyUserResponse()
                        usersViewModel.getAll(
                            phone = appViewModel.phoneValue.value,
                            password = appViewModel.passwordValue.value
                        )
                    }
                    else -> {  }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> {}
        }
    }
    val getResponse = usersViewModel.getUserResponse.value
    LaunchedEffect(key1 = getResponse){
        when(getResponse){
            is Resource.Success -> {
                isLoading = false
                when(getResponse.data?.message){
                    "User Successfully got!" -> {
                        if (phone != "-1" && usersViewModel.userPhone.value == phone){
                            usersViewModel.updateUser(
                                baseId = getResponse.data.user.baseId,
                                firstName = getResponse.data.user.firstName,
                                lastName = getResponse.data.user.lastName,
                                fatherName = getResponse.data.user.fatherName,
                                birthDay = getResponse.data.user.birthDay,
                                nationalCode = getResponse.data.user.nationalCode,
                                image = getResponse.data.user.image,
                                phone = getResponse.data.user.phone,
                                password = getResponse.data.user.password,
                                status = getResponse.data.user.status
                            )
                            usersViewModel.updateRoles(
                                addUser = getResponse.data.roles.addUser == "1",
                                deleteUser = getResponse.data.roles.deleteUser == "1",
                                updateUser = getResponse.data.roles.updateUser == "1",
                                updateUsers = getResponse.data.roles.updateUsers == "1",
                                getUser = getResponse.data.roles.getUser == "1",
                                getUsers = getResponse.data.roles.getUsers == "1",
                                getNotifications = getResponse.data.roles.getNotifications == "1",
                                addNotification = getResponse.data.roles.addNotification == "1",
                                deleteNotification = getResponse.data.roles.deleteNotification == "1",
                                updateNotification = getResponse.data.roles.updateNotification == "1",
                                addJob = getResponse.data.roles.addJob == "1",
                                deleteJob = getResponse.data.roles.deleteJob == "1",
                                updateJob = getResponse.data.roles.updateJob == "1",
                                getJob = getResponse.data.roles.getJob == "1",
                                getFile = getResponse.data.roles.getFile == "1",
                                addFile = getResponse.data.roles.addFile == "1",
                                deleteFile = getResponse.data.roles.deleteFile == "1",
                                addBase = getResponse.data.roles.addBase == "1",
                                deleteBase = getResponse.data.roles.deleteBase == "1",
                                getBases = getResponse.data.roles.getBases == "1",
                                updateBase = getResponse.data.roles.updateBase == "1",
                                addSubmission = getResponse.data.roles.addSubmission == "1",
                                deleteSubmission = getResponse.data.roles.deleteSubmission == "1",
                                updateSubmission = getResponse.data.roles.updateSubmission == "1",
                                getSubmissions = getResponse.data.roles.getSubmissions == "1",
                                getBaseInfo = getResponse.data.roles.getBaseInfo == "1",
                                editBaseInfo = getResponse.data.roles.editBaseInfo == "1"
                            )
                        }else {
                            usersViewModel.updateUser(empty = true)
                            usersViewModel.updateRoles(empty = true)
                        }
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> {
                isLoading = false
                errorIsOpen = true
            }
            is Resource.Loading -> { isLoading = true }
            else -> {}
        }
    }
    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if(
                (getResponse is Resource.Success && getResponse.data?.message != "User Successfully got!") ||
                        getResponse is Resource.Error
            ){
                if(phone != "-1"){
                    usersViewModel.get(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        user = phone
                    )
                }
            }
            if (userResponse is Resource.Success){
                if (userResponse.data?.message != "User And Role Added SuccessFully!" && phone == "-1"){
                    usersViewModel.add(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else if (userResponse.data?.message != "User Updated SuccessFully!" && phone != "-1"){
                    usersViewModel.update(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        imageChange = imageChanged.value
                    )
                }
            }else if (userResponse is Resource.Error){
                if (phone == "-1"){
                    usersViewModel.add(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else {
                    usersViewModel.update(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        imageChange = imageChanged.value
                    )
                }
            }
            errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )
    DisplayAlertDialog(
        openDialog = messageIsOpen,
        title = "شماره موبایل تکراری",
        description = "قبلا کاربری با این شماره موبایل ثبت شده است.",
        onCloseClicked = { },
        onYesClicked = { messageIsOpen = false },
        yesTitle = "فهمیدم",
        noTitle = ""
    )

    SwipeRefresh(
        swipeEnabled = phone != "-1",
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            usersViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                user = phone
            )
        }
    ) {
        if (!isLoading){
            appViewModel.bases.value?.let { it ->
                UserScreenContent(
                    usersViewModel = usersViewModel,
                    baseUrl = BASE_URL + (appViewModel.baseInfoValue.value?.data?.baseInfo?.uploadUrl ?: UPLOAD_URL),
                    bases = it,
                    subClicked = { image ->
                        image?.let {
                            usersViewModel.updateUser(image = image.bitmapToBase64())
                            imageChanged.value = true
                        }
                        if (phone == "-1"){
                            usersViewModel
                                .add(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                        }else{
                            usersViewModel
                                .update(
                                    phone = appViewModel.phoneValue.value,
                                    password = appViewModel.passwordValue.value,
                                    imageChange = imageChanged.value
                                )
                        }
                    },
                    isUpdate = phone != "-1"
                )
            }
        }
    }
}