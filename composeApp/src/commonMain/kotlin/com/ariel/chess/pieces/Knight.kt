package com.ariel.chess.pieces

import androidx.compose.ui.unit.IntOffset
import com.ariel.chess.pieces.dsl.getPieceMoves

class Knight(
    override val color: Piece.Color,
    override var position: IntOffset,
): Piece {
    override val type: Char = Type
    override val drawable: String =
        if (color.isWhite) "knight_white.svg"
        else "knight_black.svg"

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> =
        getPieceMoves(pieces){
            getLMoves()
        }

    companion object{
        const val Type = 'N'
    }

}