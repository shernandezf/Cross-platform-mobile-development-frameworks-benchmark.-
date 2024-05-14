package com.shernandezf.kotlintesisapp

import android.widget.MediaController
import android.widget.VideoView
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                //app()
                MainScreen()
            }
        }
    }
}
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("first") { VideoPlayer(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp),
            url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
        ) }
        composable("second") { ProximitySensorDisplay() }
        composable("third") { AccelerometerView() }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = { navController.navigate("first") }) {
            Text("Medición de video")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("second") }) {
            Text("Medición sensor de proximidad")
        }
        Spacer(modifier = Modifier.height(20.dp))
        Button(onClick = { navController.navigate("third") }) {
            Text("Medición de sensor de acelerometro")
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}

@Composable
fun VideoPlayer(modifier: Modifier, url: String) {
    val context = LocalContext.current
    var startTime by remember { mutableLongStateOf(0L) }
    var endTime by remember { mutableLongStateOf(0L) }
    var loadTime by remember { mutableStateOf("Loading...") }
    Column(modifier = modifier.fillMaxSize()) {
        AndroidView(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),  // This will allow the VideoView to expand but not fill the entire screen
            factory = { ctx ->
                VideoView(ctx).apply {
                    val mediaController = MediaController(ctx)
                    mediaController.setAnchorView(this)
                    setMediaController(mediaController)

                    val startTime = System.currentTimeMillis()

                    setVideoPath(url)
                    setOnPreparedListener { mediaPlayer ->
                        val endTime = System.currentTimeMillis()
                        loadTime = "Load time: ${(endTime - startTime)} ms"

                        //mediaPlayer.start()
                    }

                    setOnErrorListener { _, _, _ ->
                        loadTime = "Error loading the video"
                        true
                    }
                }
            },
            update = {}
        )
        Text(text = loadTime)
    }
}
@Composable
fun AccelerometerView() {
    val context = LocalContext.current
    var sensorValues by remember { mutableStateOf(floatArrayOf(0f, 0f, 0f)) }

    DisposableEffect(context) {
        val accelerometer = Accelerometer(context) { values ->
            sensorValues = values.copyOf()
        }

        onDispose {
            accelerometer.unregister()
        }
    }

    Text(text = "X: ${sensorValues[0]}, Y: ${sensorValues[1]}, Z: ${sensorValues[2]}")
}
@Composable
fun ProximitySensorDisplay() {
    val context = LocalContext.current
    var proximityData by remember { mutableStateOf("Far") }
    var proximityValue by remember { mutableFloatStateOf(0f) }
    val sensorManager = remember {
        ProximitySensor(context) { proximity ->
            proximityData = if (proximity < 4) "Close" else "Far"
            proximityValue = proximity
        }
    }

    DisposableEffect(Unit) {
        sensorManager.startListening()
        onDispose {
            sensorManager.stopListening()
        }
    }
    Column {
        Text(text = "Proximity: $proximityData")
        Text(text = "Proximity: $proximityValue")
    }
}