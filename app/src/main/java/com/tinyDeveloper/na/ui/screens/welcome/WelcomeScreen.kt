package com.tinyDeveloper.na.ui.screens.welcome

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.tinyDeveloper.na.ui.theme.*
import com.tinyDeveloper.na.utils.Constants.ON_BOARDING_PAGE_COUNT
import com.tinyDeveloper.na.utils.OnBoardingPage


@OptIn(ExperimentalPagerApi::class)
@Composable
fun WelcomeScreen(
    welcomeViewModel: WelcomeViewModel = hiltViewModel(),
    navigateToMainScreen: () -> Unit,
    navigateToPhoneScreen: () -> Unit,
    isMain: Boolean
){
    val pages = listOf(
        OnBoardingPage.First,
        OnBoardingPage.Second,
        OnBoardingPage.Third,
        OnBoardingPage.For,
        OnBoardingPage.Five
    )
    val pagerState = rememberPagerState()
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        HorizontalPager(
            modifier = Modifier.weight(1.3f).fillMaxSize(),
            count = ON_BOARDING_PAGE_COUNT,
            state = pagerState,
            verticalAlignment = Alignment.Top
            //reverseLayout = true
        ) { pageNumber ->
            PagerScreen(pages[pageNumber])
        }
        HorizontalPagerIndicator(
            modifier = Modifier.weight(0.1f).align(Alignment.CenterHorizontally),
            pagerState = pagerState,
            activeColor = MaterialTheme.colorScheme.primary,
            inactiveColor = MaterialTheme.colorScheme.primaryContainer,
            indicatorWidth = PAGING_INDICATOR_WIDTH,
            spacing = PAGING_INDICATOR_SPACING
        )
        FinishButton(
            modifier = Modifier.weight(0.2f).padding(top = LARGE_PADDING),
            pagerState = pagerState
        ) {
            welcomeViewModel.saveOnBoardingState(completed = true)
            if(isMain){
                navigateToMainScreen()
            }else {
                navigateToPhoneScreen()
            }
        }
    }
}

@Composable
private fun PagerScreen(onBoardingPage: OnBoardingPage){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = onBoardingPage.image),
            contentDescription = "Ordering Page Image",
            modifier = Modifier.fillMaxWidth(0.8f).fillMaxHeight(0.8f)
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = EXTRA_LARGE_PADDING),
            text = onBoardingPage.title,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            textAlign = TextAlign.Center
        )
        Text(
            modifier = Modifier.fillMaxWidth().padding(horizontal = EXTRA_LARGE_PADDING).padding(top = SMALL_PADDING),
            text = onBoardingPage.description,
            fontWeight = FontWeight.Medium,
            fontSize = MaterialTheme.typography.titleSmall.fontSize,
            textAlign = TextAlign.Center
        )
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun FinishButton(
    modifier: Modifier = Modifier,
    pagerState: PagerState,
    onClick: () -> Unit
){
    Row(
        modifier = modifier.padding(horizontal = EXTRA_LARGE_PADDING),
        verticalAlignment = Alignment.Top,
        horizontalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(
            modifier = Modifier.fillMaxWidth(),
            visible = pagerState.currentPage == ON_BOARDING_PAGE_COUNT - 1
        ){
            Button(
                onClick = onClick
            ){
                Text(
                    text = "بزن بریم!",
                    fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
