package com.tinyDeveloper.na.ui.screens.main.bases.base

import androidx.compose.runtime.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.ui.component.DisplayAlertDialog
import com.tinyDeveloper.na.ui.screens.AppViewModel
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.utils.Resource
import com.tinyDeveloper.na.utils.StateValues

@Composable
fun BaseScreen(
    appViewModel: AppViewModel,
    id: String,
    basesViewModel: BasesViewModel,
    navigateToBasesScreen: () -> Unit
){
    var isLoading by remember{ mutableStateOf(false) }
    var errorIsOpen by remember { mutableStateOf(false) }
    val getBaseResponse = basesViewModel.getResponse.value
    val basicResponse = basesViewModel.basicResponse.value

    LaunchedEffect(key1 = id){
        if (id != "-1" && id != basesViewModel.baseId.value){
            basesViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
            basesViewModel.setBaseId(id = id)
        }else if (id == "-1"){
            basesViewModel.updateAddRequest(
                baseName = "",
                address = "",
                commanderName = "",
                commanderPhone = "",
                status = StateValues.ACTIVE.value
            )
            basesViewModel.setBaseId("-1")
        }
    }

    LaunchedEffect(key1 = getBaseResponse){
        when(getBaseResponse){
            is Resource.Success -> {
                isLoading = false
                when(getBaseResponse.data?.message){
                    "Base Got Successfully!" -> {
                        if (id != "-1" && basesViewModel.baseId.value == id){
                            basesViewModel.updateAddRequest(
                                phone = appViewModel.phoneValue.value,
                                password = appViewModel.passwordValue.value,
                                address = getBaseResponse.data!!.base.address,
                                baseName = getBaseResponse.data!!.base.baseName,
                                centerId = getBaseResponse.data!!.base.centerId,
                                commanderPhone = getBaseResponse.data!!.base.commanderPhone,
                                commanderName = getBaseResponse.data!!.base.commanderName,
                                status = getBaseResponse.data!!.base.status
                            )
                        }
                    }
                    "You Cant Delete Center!" -> { }
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

    LaunchedEffect(key1 = basicResponse){
        when(basicResponse){
            is Resource.Success -> {
                isLoading = false
                when(basicResponse.data?.message){
                    "Base Updated Successfully!" -> {
                        basesViewModel.setBaseId("-1")
                        navigateToBasesScreen()
                        basesViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                        basesViewModel.emptyBasicResponse()
                    }
                    "Base Added Successfully!" -> {
                        navigateToBasesScreen()
                        basesViewModel.getAll(phone = appViewModel.phoneValue.value, password = appViewModel.passwordValue.value)
                        basesViewModel.emptyBasicResponse()
                    }
                    else -> { errorIsOpen = true }
                }
            }
            is Resource.Error -> { isLoading = false; errorIsOpen = true }
            is Resource.Loading -> { isLoading = true }
            else -> { }
        }
    }

    DisplayAlertDialog(
        openDialog = errorIsOpen,
        title = "مشکل ارتباط با سرور",
        description = "متاسفانه نتوانستیم به سرور متصل شویم، لطفا اینترنت خود را برسی کنید.",
        onCloseClicked = { },
        onYesClicked = {
            if (getBaseResponse != null && getBaseResponse.data?.message != "Base Got Successfully!"){
                basesViewModel.get(
                    phone = appViewModel.phoneValue.value,
                    password = appViewModel.passwordValue.value,
                    id = id
                )
            }
            if (basicResponse != null && (basicResponse.data?.message != "Base Updated Successfully!" ||
                        basicResponse.data?.message != "Base Added Successfully!")){
                if (id == "-1"){
                    basesViewModel.add(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else{
                    basesViewModel.update(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = basesViewModel.baseId.value
                    )
                }
            }
           errorIsOpen = false
        },
        yesTitle = "تلاش مجدد",
        noTitle = "",
        dismiss = false
    )

    SwipeRefresh(
        swipeEnabled = id != "-1",
        state = rememberSwipeRefreshState(isRefreshing = isLoading),
        onRefresh = {
            basesViewModel.get(
                phone = appViewModel.phoneValue.value,
                password = appViewModel.passwordValue.value,
                id = id
            )
        }
    ) {
        if (!isLoading){
            BaseScreenContent(basesViewModel = basesViewModel, saveClicked = {
                if (id == "-1"){
                    basesViewModel.add(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value
                    )
                }else{
                    basesViewModel.update(
                        phone = appViewModel.phoneValue.value,
                        password = appViewModel.passwordValue.value,
                        id = basesViewModel.baseId.value
                    )
                }
            })
        }else{

        }
    }
}