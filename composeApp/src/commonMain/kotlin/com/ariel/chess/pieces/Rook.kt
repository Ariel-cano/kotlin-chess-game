package com.ariel.chess.pieces

import androidx.compose.ui.unit.IntOffset

import com.ariel.chess.pieces.dsl.getPieceMoves


class Rook(
    override val color: Piece.Color,
    override var position: IntOffset,
): Piece {
    override val type: Char = Type
    override val drawable: String =
        if (color.isWhite) "rook_white.svg"
        else "rook_black.svg"
    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> =
        getPieceMoves(pieces){
            straightMoves()
        }

    companion object{
        const val Type = 'R'
    }

}