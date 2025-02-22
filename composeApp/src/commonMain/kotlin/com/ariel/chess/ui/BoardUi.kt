package com.ariel.chess.ui

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.ariel.chess.board.*

@Composable
fun BoardUi(
    board: Board,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .aspectRatio(1f)
            .fillMaxWidth()
            .border(width = 8.dp,
                color = Color.White
            )
            .padding(8.dp)
    ) {
        BoardYCoordinates.forEach { y ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ){
                BoardXCoordinates.forEach { x ->
                    val piece = board.rememberPieceAt(x,y)

                    val isAvailableMove = board.rememberIsAvailableMove(x,y)

                    boardCell(
                        x = x,
                        y = y,
                        piece = piece,
                        board = board,
                        isAvailableMove = isAvailableMove,
                        modifier = Modifier
                            .weight(1f)
                            .fillMaxHeight()
                    )
                }
            }
        }
    }
}