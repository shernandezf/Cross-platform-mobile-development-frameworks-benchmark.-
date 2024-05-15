package com.shernandezf.kotlintesisapp

import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import App
import android.content.Context
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import androidx.media3.common.util.UnstableApi
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage

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
    val context = LocalContext.current
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController = navController)
        }
        composable("first") { VideoPlayerComposable(context,"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }
        composable("second") { ProximitySensorDisplay() }
        composable("third") { AccelerometerView() }
    }
}

@Composable
fun HomeScreen(navController: NavController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Image fetched from the internet
        AsyncImage(
            model = "https://github.com/shernandezf/resources/blob/main/logo_andes.jpg?raw=true",
            contentDescription = null,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Tesis Pregrado 2024 Santiago Hernandez",
            fontWeight = FontWeight.Bold // This sets the text to be bold
        )
        Text("Profesor: Camilo Escobar Velasquez.")

        Spacer(modifier = Modifier.height(20.dp))
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
@OptIn(UnstableApi::class) @Composable
fun VideoPlayerComposable(
    context: Context,
    videoUrl: String
) {
    var loadTime by remember { mutableStateOf("Loading...") }
    var isLoadTimeRecorded by remember { mutableStateOf(false) }

    // Remember ExoPlayer instance with its configuration
    val exoPlayer = remember {
        ExoPlayer.Builder(context).build().also { player ->
            val startTime = System.currentTimeMillis()
            // Media item with the video URL
            val mediaItem = MediaItem.fromUri(videoUrl)
            player.setMediaItem(mediaItem)
            player.prepare()
            player.playWhenReady = true  // Set the player to start playing as soon as it's ready

            player.addListener(object : Player.Listener {
                override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
                    if (playbackState == Player.STATE_READY && !isLoadTimeRecorded) {
                        val endTime = System.currentTimeMillis()
                        loadTime = "Load time: ${endTime - startTime} ms"
                        isLoadTimeRecorded = true
                    }
                }
            })
        }
    }

    // Handle the ExoPlayer lifecycle
    DisposableEffect(exoPlayer) {
        onDispose {
            exoPlayer.release() // Release the player when the composable is disposed
        }
    }

    Column(modifier = Modifier.fillMaxWidth()) {
        // Create the view that displays the video
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = true // Enable or disable controller as needed
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp) // Set your desired height
        )

        // Display loading or load time
        Text(text = loadTime, modifier = Modifier.padding(8.dp))
    }
}