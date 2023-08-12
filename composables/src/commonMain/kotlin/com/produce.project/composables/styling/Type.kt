package com.produce.project.composables.styling

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import styling.fontFamily

val Typography = Typography(
    defaultFontFamily = fontFamily,
    body1 = TextStyle(
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    body2 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        letterSpacing = 0.5.sp
    ),
    h1 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.W300,
        textAlign = TextAlign.Center,
        letterSpacing = 4.sp
    ),
    h2 = TextStyle(
        fontSize = 22.sp,
        fontWeight = FontWeight.W300,
        textAlign = TextAlign.Center,
        letterSpacing = 2.sp,
        lineHeight = 26.sp
    ),
    h3 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W600,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp
    ),
    h4 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        lineHeight = 20.sp,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp
    ),
    h5 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W500,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp,
        lineHeight = 18.sp
    ),
    h6 = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp,
        lineHeight = 18.sp
    ),
    button = TextStyle(
        fontSize = 14.sp,
        fontWeight = FontWeight.W300,
        textAlign = TextAlign.Center,
        letterSpacing = 0.5.sp,
        lineHeight = 18.sp
    ),
    caption = TextStyle(
        fontWeight = FontWeight.W100,
        letterSpacing = 6.sp
    ),
    overline = TextStyle(
        fontWeight = FontWeight.W300,
        fontSize = 10.sp
    ),
    subtitle1 = TextStyle(
        fontSize = 16.sp,
        fontWeight = FontWeight.W400,
        textAlign = TextAlign.Center,
        letterSpacing = 2.sp
    )
)