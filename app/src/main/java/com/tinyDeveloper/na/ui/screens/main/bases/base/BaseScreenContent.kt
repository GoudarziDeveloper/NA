package com.tinyDeveloper.na.ui.screens.main.bases.base

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.ui.component.StatusDropDown
import com.tinyDeveloper.na.ui.screens.main.bases.BasesViewModel
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.utils.Constants.MANAGER_NAME
import com.tinyDeveloper.na.utils.Constants.PART_NAME
import com.tinyDeveloper.na.utils.toStateValue

@Composable
fun BaseScreenContent(basesViewModel: BasesViewModel, saveClicked: () -> Unit){
    val scrollState = rememberScrollState()
    val addRequest = basesViewModel.addRequest.value
    if (addRequest != null){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(MEDIUM_PADDING)
            .verticalScroll(state = scrollState)) {
            BaseScreenItem(
                modifier = Modifier,
                value = addRequest.baseName,
                onValueChange = { basesViewModel.updateAddRequest(baseName = it) },
                label = "نام $PART_NAME"
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            BaseScreenItem(
                modifier = Modifier,
                value = addRequest.commanderName,
                onValueChange = { basesViewModel.updateAddRequest(commanderName = it) },
                label = "نام $MANAGER_NAME"
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            BaseScreenItem(
                modifier = Modifier,
                value = addRequest.commanderPhone,
                onValueChange = { basesViewModel.updateAddRequest(commanderPhone = it) },
                label = "شماره تماس $MANAGER_NAME"
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            StatusDropDown(
                item = basesViewModel.addRequest.value?.status.toStateValue(),
                onItemSelected = { basesViewModel.updateAddRequest(status = it)}
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            BaseScreenItem(
                modifier = Modifier.height(160.dp),
                value = addRequest.address,
                onValueChange = { basesViewModel.updateAddRequest(address = it) },
                label = "ادرس"
            )
            Spacer(modifier = Modifier.height(MEDIUM_PADDING))
            Button(modifier = Modifier.fillMaxWidth(), onClick = saveClicked) {
                Text(text = "ثبت پایگاه", fontSize = MaterialTheme.typography.titleSmall.fontSize, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseScreenItem(
    modifier: Modifier,
    value: String,
    onValueChange: (String) -> Unit,
    label: String
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) }
    )
}