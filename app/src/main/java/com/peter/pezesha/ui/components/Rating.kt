package com.peter.pezesha.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlin.math.floor


@Composable
fun Rating(
    rating: Float,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        repeat(times = floor(rating).toInt()) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = Color(red = 255, green = 168, blue = 0),
            )
        }

        Spacer(modifier = Modifier.width(width = 4.dp))

        Text(
            text = rating.toString(),
            fontWeight = FontWeight.Medium,
        )
    }
}
