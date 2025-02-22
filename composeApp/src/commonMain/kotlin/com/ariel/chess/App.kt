package com.ariel.chess

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariel.chess.board.rememberBoard
import com.ariel.chess.ui.BoardUi

@Composable
fun App() {
    val board = rememberBoard()
    LaunchedEffect(board.winner.value) {
        println("Обновляем UI: ${board.winner.value}")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            board.winner.value?.let { winnerText ->
                Text(
                    text = winnerText,
                    fontSize = 20.sp,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { board.resetGame() }) {
                    Text("Перезапустить игру")
                }
            }

            BoardUi(board = board)
        }
    }
}
