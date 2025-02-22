package com.ariel.chess.pieces

import androidx.compose.ui.unit.IntOffset
import com.ariel.chess.pieces.dsl.getPieceMoves

class King(
    override val color: Piece.Color,
    override var position: IntOffset,
): Piece {
    override val type: Char = Type
    override val drawable: String =
        if (color.isWhite) "king_white.svg"
        else "king_black.svg"

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> =
        getPieceMoves(pieces){
            diagonalMoves(
                maxMovements = 1,
            )
            straightMoves(
                maxMovements = 1,
            )
        }

    companion object{
        const val Type = 'K'
    }

}