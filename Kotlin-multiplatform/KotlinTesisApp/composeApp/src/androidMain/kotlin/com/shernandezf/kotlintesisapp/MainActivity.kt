package com.shernandezf.kotlintesisapp

import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.platform.LocalContext
import App
import android.content.Context
import android.os.Bundle
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
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
        composable("video") { VideoPlayerComposable(context,"https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4") }
        composable("accelerometer") { AccelerometerView() }
        composable("proximity") { ProximitySensorDisplay() }
        composable("colors") { ColorBarWithTexts() }
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
            contentDescription = "University of los Andes logo",
            modifier = Modifier
                .size(240.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Tesis Pregrado 2024 Santiago Hernández",
            fontWeight = FontWeight.Bold // This sets the text to be bold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text("Profesor: Camilo Escobar Velásquez")

        Spacer(modifier = Modifier.height(15.dp))
        customButton(navController,"video","Video-Player",0xFFFFD700)
        Spacer(modifier = Modifier.height(15.dp))
        customButton(navController,"accelerometer","Accelerometer",0xFFFFD700)
        Spacer(modifier = Modifier.height(15.dp))
        customButton(navController,"proximity","Proximity Sensor",0xFFFFD700)
        Spacer(modifier = Modifier.height(15.dp))
        customButton(navController,"colors","Color Test",0xFFFFD700)

    }
}
@Composable
fun customButton(
    navController: NavController,
    navigation: String, info:String,
    backgroundColor: Long
) {
    Button(
        onClick = { navController.navigate(navigation) },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = Color(backgroundColor), // Yellow background
            contentColor = Color.Black, // Black text color
            disabledBackgroundColor = Color(0xFFF0F0F0), // Light gray background when disabled
            disabledContentColor = Color(0xFF333333) // Dark gray text color when disabled
        ),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(info)
    }
}
@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
@Composable
fun ColorBarWithTexts() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(
            text = "Color test",
            fontSize = 24.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(15.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(30f)
            .background(Color.Blue)
        )
        Spacer(modifier = Modifier.height(7.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(30f)
            .background(Color.Cyan)
        )
        Spacer(modifier = Modifier.height(7.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(30f)
            .background(Color.Green)
        )
        Spacer(modifier = Modifier.height(7.dp))
        Box(modifier = Modifier
            .fillMaxWidth()
            .weight(30f)
            .background(Color.Red)
        )

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
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Accelerometer:",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "X: %.3f, Y: %.3f, Z: %.3f".format(sensorValues[0], sensorValues[1], sensorValues[2]),
                fontSize = 14.sp,
                color = Color.Black
            )
        }
    }




}
@Composable
fun ProximitySensorDisplay() {
    val context = LocalContext.current
    var proximityData by remember { mutableStateOf("Far") }
    var proximityValue by remember { mutableFloatStateOf(0f) }
    val sensorManager = remember {
        ProximitySensor(context) { proximity ->
            proximityData = if (proximity < 5) "Close" else "Far"
            proximityValue = proximity
        }
    }

    DisposableEffect(Unit) {
        sensorManager.startListening()
        onDispose {
            sensorManager.stopListening()
        }
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Proximity Sensor",
                fontWeight = FontWeight.Bold,
                fontSize = 24.sp,
                color = Color.Black,
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "You are: $proximityData",
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.Black,
            )
        }
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