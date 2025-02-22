package com.ariel.chess.pieces

import androidx.compose.ui.unit.IntOffset
import chessgamektm.composeapp.generated.resources.*
import chessgamektm.composeapp.generated.resources.Res
import com.ariel.chess.pieces.dsl.getPieceMoves
import org.jetbrains.compose.resources.DrawableResource

class Queen(
    override val color: Piece.Color,
    override var position: IntOffset,
): Piece {
    override val type: Char = Type

    override val drawable: String =
        if (color.isWhite) "queen_white.svg"
        else "queen_black.svg"

    override fun getAvailableMoves(pieces: List<Piece>): Set<IntOffset> =
        getPieceMoves(pieces){
            diagonalMoves()
            straightMoves()
        }

    companion object{
        const val Type = 'Q'
    }

}