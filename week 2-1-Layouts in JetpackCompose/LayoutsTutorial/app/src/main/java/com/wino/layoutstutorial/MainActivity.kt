package com.wino.layoutstutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.wino.layoutstutorial.ui.theme.LayoutsTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LayoutsTutorialTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    //SimpleList()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LayoutsCodelabPreview() {
    LayoutsTutorialTheme {
        //SimpleList()
    }
}
