package com.ariel.chess

import AndroidSoundPlayer
import AndroidWinPlayer
import App
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.ariel.chess.settings.SoundManager
import com.ariel.chess.settings.SoundWinManager

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoundManager.soundPlayer = AndroidSoundPlayer(this)
        SoundWinManager.soundPlayer = AndroidWinPlayer(this)


        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}