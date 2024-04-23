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
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                App()
                Divider(color = MaterialTheme.colors.onSurface.copy(alpha = 0.2f), thickness = 1.dp)
                VideoPlayer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp),
                    url = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"
                )
            }
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

    AndroidView(
        modifier = modifier,
        factory = { ctx ->
            VideoView(ctx).apply {
                val mediaController = MediaController(ctx)
                mediaController.setAnchorView(this)
                setMediaController(mediaController)


                startTime = System.currentTimeMillis()

                setVideoPath(url)
                setOnPreparedListener { mediaPlayer ->
                    // Se detiene el tiempo una vez est[a listo para reproducir
                    endTime = System.currentTimeMillis()
                    loadTime = "Load time: ${(endTime - startTime)} ms"

                    mediaPlayer.start()
                }

                setOnErrorListener { _, _, _ ->
                    loadTime = "Error cargando el video"
                    true
                }
            }
        },
        update = {}
    )

    // Display the loading time or error message
    androidx.compose.material.Text(text = loadTime)
}