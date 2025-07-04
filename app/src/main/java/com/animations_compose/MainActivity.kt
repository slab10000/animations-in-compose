package com.animations_compose


import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
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
                        val frontScreenButtonOn = rememberSaveable{ mutableStateOf(false) }

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

    /** ----------------------------------- Animating with a Transition object ----------------------------------- **/
    val transitionObject = updateTransition(targetState = frontScreenButtonOn, label = "transitionObject")

    val translationAnimationFloat by transitionObject.animateFloat(){ state ->
        println("the state is $state")
        if (state){
            600F
        }else{
            0F
        }
    }
    val sizeAnimationFloat by transitionObject.animateFloat{ state ->
        if (state){
            0.9F
        }else{
            1F
        }
    }
    /** --------------------------------------------------------------------------------------------------------- **/

    /** We could animate it using animate*AsState but for multiple animations at the same time it is better to use Transition object **/

    /*val animationFloat by animateFloatAsState(
        targetValue = if (frontScreenButtonOn){
            600F
        } else {
            0F
        }
    )
    val sizeAnimationFloat by animateFloatAsState(
        targetValue = if (frontScreenButtonOn){
            0.9F
        }else{
            0F
        }
    )*/

    /** ----------------------------------------------------------------------------------------------------------------------------- **/

    Column(
        modifier = Modifier
            .graphicsLayer {
                this.translationX = translationAnimationFloat
                this.scaleX = sizeAnimationFloat
                this.scaleY = sizeAnimationFloat
                val corners = if (frontScreenButtonOn) 32.dp else 0.dp
                this.shape = RoundedCornerShape(corners)
                this.clip = true
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