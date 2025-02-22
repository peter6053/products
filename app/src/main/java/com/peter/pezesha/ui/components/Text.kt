package com.peter.pezesha.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun LargeText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    color: Color = Color.Unspecified,
) {
    Text(
        modifier = modifier
            .height(35.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        text = text,
        color = color,
        fontSize = 28.sp,
        fontWeight = FontWeight.Black,
    )
}

@Composable
fun ParagraphText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    fontWeight: FontWeight = FontWeight.Medium,
) {
    Text(
        modifier = modifier,
        text = text,
        fontWeight = fontWeight,
    )
}

@Composable
fun MediumText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    color: Color = Color.Unspecified,
    fontWeight: FontWeight = FontWeight.Bold,
) {
    Text(
        modifier = modifier
            .height(30.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        color = color,
        text = text,
        fontSize = 16.sp,
        fontWeight = fontWeight,
        maxLines = 1,
    )
}

@Composable
fun SmallText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    color: Color = Color.Unspecified,
) {
    Text(
        modifier = modifier,
        fontSize = 14.sp,
        text = text,
        color = color,
        fontWeight = FontWeight.Medium,
        overflow = TextOverflow.Ellipsis,
        minLines = 2,
        maxLines = 2,
    )
}

@Composable
fun ExtraSmallText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    color: Color = MaterialTheme.colorScheme.outline,
) {
    Text(
        modifier = modifier
            .height(30.dp)
            .wrapContentHeight(align = Alignment.CenterVertically),
        text = text,
        fontSize = 12.sp,
        fontWeight = FontWeight.Bold,
        color = color,
        maxLines = 1,
    )
}
