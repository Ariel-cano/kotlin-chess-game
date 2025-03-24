package com.ariel.chess.settings

object SoundManager {
    var soundPlayer: SoundPlayer? = null

    fun playMoveSound() {
        soundPlayer?.playMoveSound()
    }
}