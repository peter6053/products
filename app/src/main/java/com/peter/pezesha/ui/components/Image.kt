package com.peter.pezesha.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage


@Composable
fun Image(
    modifier: Modifier = Modifier,
    width: Dp = 200.dp,
    height: Dp = 200.dp,
    url: String,
) {
    SubcomposeAsyncImage(
        modifier = modifier
            .width(width = width)
            .height(height = height)
            .clip(shape = RoundedCornerShape(12.dp)),
        model = url,
        loading = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(size = 32.dp)
                )
            }
        },
        contentDescription = null,
        contentScale = ContentScale.Crop,
    )
}
