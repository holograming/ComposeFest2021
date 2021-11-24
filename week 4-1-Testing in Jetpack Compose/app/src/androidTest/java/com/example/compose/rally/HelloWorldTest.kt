package com.example.compose.rally

import androidx.compose.material.Text
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import com.example.compose.rally.ui.components.HelloWorld
import com.example.compose.rally.ui.components.RallyTopAppBar
import org.junit.Rule
import org.junit.Test

class HelloWorldTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun helloWorldTest() {

        composeTestRule.setContent {
            HelloWorld()
        }

        composeTestRule
            .onNodeWithText("Hello")
            .assertIsSelected()

    }
}