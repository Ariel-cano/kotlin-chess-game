package com.ariel.chess.pieces

import androidx.compose.ui.unit.IntOffset
import com.ariel.chess.pieces.dsl.*

class Pawn(
    override val color: Piece.Color,
    override var position: IntOffset,
): Piece{
    override val type: Char = Type
    override val drawable: String =
        if (color.isWhite) "pawn_white.svg"
        else "pawn_black.svg"

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> {

        val isFirstMove = position.y == 2 && color.isWhite ||
                          position.y == 7 && color.isBlack


        return getPieceMoves(pieces){
            straightMoves(
                movement = if (color.isWhite) StraightMovement.Up else StraightMovement.Down,
                maxMovements = if (isFirstMove) 2 else 1,
                canCapture = false,
            )
            diagonalMoves(
                movement = if (color.isWhite) DiagonalMovement.UpRight else DiagonalMovement.DownRight,
                maxMovements = 1,
                captureOnly = true,
            )
            diagonalMoves(
                movement = if (color.isWhite) DiagonalMovement.UpLeft else DiagonalMovement.DownLeft,
                maxMovements = 1,
                captureOnly = true,
            )
        }
    }

    companion object{
        const val Type = 'P'
    }



}