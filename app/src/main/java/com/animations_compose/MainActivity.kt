package com.animations_compose


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.animations_compose.ui.theme.AnimationsincomposeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AnimationsincomposeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Box(){
                        // State goes here -> State hoisting
                        val textState = remember { mutableStateOf("Initial text") }
                        val frontScreenButtonOn = remember{ mutableStateOf(false) }

                        BackScreen(text = textState.value, onClick = {textState.value = it} )
                        FrontScreen(frontScreenButtonOn = frontScreenButtonOn.value, onButtonClicked = { frontScreenButtonOn.value = !frontScreenButtonOn.value })
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AnimationsincomposeTheme {
        Greeting("Android")
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun BackScreen(text: String, onClick: (String) -> Unit){
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { onClick("Final Text") }
        ){
            Text(text = text)
        }
    }
}

@Composable
fun FrontScreen(frontScreenButtonOn: Boolean, onButtonClicked: ()-> Unit){
    Column(
        modifier = Modifier
            .graphicsLayer {
                if (frontScreenButtonOn) {
                    this.translationX = this.size.width / 2
                    this.scaleX = 0.8F
                    this.scaleY = 0.8F
                    this.shape = RoundedCornerShape(32.dp)
                    this.clip = true
                }
            }
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Button(
            onClick = { onButtonClicked() }
        ) {
            Text("This is the first screen")
        }
    }
}