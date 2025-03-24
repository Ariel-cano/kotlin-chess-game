package com.ariel.chess.settings

object SoundWinManager {
    var soundPlayer: SoundPlayer? = null

    fun playMoveSound() {
        soundPlayer?.playMoveSound()
    }
}