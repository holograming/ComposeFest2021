package com.wino.basicscodelabtutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.wino.basicscodelabtutorial.ui.theme.BasicsCodelabTutorialTheme
import androidx.compose.ui.Alignment

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BasicsCodelabTutorialTheme {
                // A surface container using the 'background' color from the theme
                MyApp()
            }
        }
    }
}

@Composable
private fun MyApp(names: List<String> = listOf("World", "Compose")) {
    // Hoist를 하는 목적은 stateless를 하기 위함.
    // @Compose가 state/event에 의해 recompose가 발생되면, 상태가 변경되어야 하는데,
    // 이를 위해 mutableState 객체를 이용해 상태를 관리하도록 하고,
    // 해당 함수에서는 변경된 상태값만 readable로 사용하기 위함.
    var shouldShowOnboarding by remember { mutableStateOf(true) }

    if(shouldShowOnboarding) {
        OnboardingScreen(onContinueClicked = {shouldShowOnboarding = false})
    } else {
        Greetings()
    }
}

@Composable
fun OnboardingScreen(onContinueClicked: () -> Unit) {

    var shouldShowOnboarding by remember { mutableStateOf(true) }

    Surface {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Welcome to the Basics Codelab tutorial!")
            Button(
                modifier = Modifier.padding(vertical = 24.dp),
                onClick = onContinueClicked
            ) {
                Text("Continue")
            }
        }
    }
}

@Composable
fun Greetings(names: List<String> = List(1000) { "$it" }) {
    LazyColumn(modifier = Modifier.padding(vertical = 4.dp)) {
        items(names) { name->
            Greeting(name = name)
        }
    }
}

@Composable
fun Greeting(name: String) {
    val expanded = remember { mutableStateOf(false) }
    val extraPadding = if(expanded.value) 48.dp else 0.dp

    Surface(color = MaterialTheme.colors.primary,
            modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
        ) {
        Row(modifier = Modifier.padding(24.dp)){
            Column(modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding)) {
                Text(text = "Hello, ")
                Text(text = name)
            }
            OutlinedButton(onClick = { expanded.value = !expanded.value }) {
                Text(if(expanded.value) "Show less" else "Show more")
            }
        }
    }
}

@Preview(showBackground = true, name = "MyApp preview", widthDp = 320)
@Composable
fun DefaultPreview() {
    BasicsCodelabTutorialTheme {
        MyApp()
    }
}


@Preview(showBackground = true, widthDp = 320, heightDp = 320)
@Composable
fun OnboardingPreview() {
    BasicsCodelabTutorialTheme {
        OnboardingScreen(onContinueClicked = {}) // Do nothing on click.
    }
}