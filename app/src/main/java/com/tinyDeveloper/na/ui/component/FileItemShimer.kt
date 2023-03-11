package com.tinyDeveloper.na.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.tinyDeveloper.na.R
import com.tinyDeveloper.na.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FileItemShimmer(alpha: Float){
    Card(modifier = Modifier
        .fillMaxWidth()
        .alpha(alpha = alpha)
        .height(68.dp), shape = RoundedCornerShape(LARGE_PADDING)) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(MEDIUM_PADDING)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    modifier = Modifier
                        .size(48.dp).alpha(alpha = alpha),
                    shape = CircleShape,
                    color = NotShimmerColor
                ) {
                    Icon(
                        modifier = Modifier.padding(SMALL_PADDING),
                        painter = painterResource(id = R.drawable.ic_file),
                        contentDescription = "File Icon",
                        tint = ShimmerColor
                    )
                }
                Spacer(modifier = Modifier.width(MEDIUM_PADDING))
                Surface(
                    modifier = Modifier
                        .height(20.dp)
                        .width(200.dp).alpha(alpha = alpha),
                    shape = RoundedCornerShape(MEDIUM_PADDING)
                ) {
                    Box(modifier = Modifier
                        .fillMaxSize()
                        .background(color = NotShimmerColor).alpha(alpha = alpha))
                }
            }
            Surface(modifier = Modifier
                .width(100.dp)
                .height(10.dp)
                .align(Alignment.BottomEnd),
                shape = RoundedCornerShape(MEDIUM_PADDING)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .align(Alignment.BottomEnd)
                        .background(NotShimmerColor)
                )
            }
        }
    }
}