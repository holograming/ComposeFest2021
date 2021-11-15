package com.codelab.theming.ui.start.theme

import android.content.res.Resources
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

/*
* JetnewsTheme 테마 생성
* Style, Color, Shape 등 reuse composable 을 사용하기 위한 테마 정의
* */

/*
* Color definitions
* lightColors 는 주간 모드를 말하는것으로 ,Colors 를 재정의 한 functions
* Default값을 제공.
* darkColors 야간모드 역시 재정의 가능함.
* https://developer.android.com/reference/kotlin/androidx/compose/material/package-summary#lightColors(androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color,%20androidx.compose.ui.graphics.Color)
* */
private val LightColors = lightColors(
    primary = Red700,
    primaryVariant = Red900,
    onPrimary = Color.White,
    secondary = Red700,
    secondaryVariant = Red900,
    onSecondary = Color.White,
    error = Red800
)

private val DarkColors = darkColors(
    primary = Red300,
    primaryVariant = Red700,
    onPrimary = Color.Black,
    secondary = Red300,
    onSecondary = Color.Black,
    error = Red200
)

/*
* Font definitions
* */



@Composable
fun JetnewsTheme(
    dartTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable ()->Unit)
{
    MaterialTheme(
        colors = if(dartTheme) DarkColors else LightColors,
        typography = JetnewsTypography,
        shapes = JetnewsShapes,
        content = content
    )
}