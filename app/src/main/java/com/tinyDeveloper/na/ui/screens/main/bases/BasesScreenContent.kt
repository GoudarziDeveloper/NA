package com.tinyDeveloper.na.ui.screens.main.bases

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.domain.models.response.bases.get_all.Base
import com.tinyDeveloper.na.ui.component.EmptyScreen
import com.tinyDeveloper.na.ui.theme.MEDIUM_PADDING
import com.tinyDeveloper.na.ui.theme.SMALL_PADDING

@Composable
fun BasesScreenContent(
    isLoading: Boolean,
    bases: List<Base>,
    onItemClicked: (String) -> Unit,
    onDeleteClicked: (String) -> Unit,
    swipeToRefresh: () -> Unit
){
    SwipeRefresh(state = rememberSwipeRefreshState(isRefreshing = isLoading), onRefresh = swipeToRefresh) {
        if (isLoading){
            Column(modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(MEDIUM_PADDING), verticalArrangement = Arrangement.spacedBy(MEDIUM_PADDING)) {
                repeat(10){
                    BasesScreenItemShimmer()
                }
            }
        }else {
            if (bases.isNotEmpty()){
                LazyColumn(
                    modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(
                        MEDIUM_PADDING
                    ), contentPadding = PaddingValues(MEDIUM_PADDING)
                ){
                    items(bases){
                        BasesScreenItem(
                            base = it,
                            onItemClicked = onItemClicked,
                            onDeleteClicked = onDeleteClicked
                        )
                    }
                }
            }else {
                EmptyScreen(
                    image = R.drawable.ic_empty_screen2,
                    text = "درحال حاضر هیچ پایگاهی وجود ندارد با دکمه + پایگاه جدیدی اضافه کنید."
                )
            }
        }
    }
}


