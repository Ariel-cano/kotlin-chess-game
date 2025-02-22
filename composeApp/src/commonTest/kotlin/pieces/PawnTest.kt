package pieces

import androidx.compose.ui.unit.IntOffset
import com.ariel.chess.board.BoardXCoordinates
import com.ariel.chess.board.BoardYCoordinates
import com.ariel.chess.pieces.Pawn
import com.ariel.chess.pieces.Piece

import kotlin.test.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * 1. на первом шаге можем сходить вперед на 2 клетки
 * 2. если это не первый шаг можем сходить вперед на 1 клетку
 * 3. Мы можем побить врага по диагонали вперед
 * */

class PawnTest {

    private val demoWhitePiece : Piece =
        Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x = BoardXCoordinates.first(),
                y = BoardYCoordinates.first(),
            )
        )

    private val demoBlackPiece : Piece =
        Pawn(
            color = Piece.Color.Black,
            position = IntOffset(
                x = BoardXCoordinates.first(),
                y = BoardYCoordinates.first(),
            )
        )

    @Test
    fun testFirstMoveForward(){
        val pawn = Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x =  'A'.code,
                y = 2,
            )
        )

        val moves = pawn.getAvailableMoves(listOf(pawn))

        assertTrue(moves.isNotEmpty())
        assertEquals(IntOffset(
            x = 'A'.code,
            y= 3
        ),
            moves.first())
    }
    @Test
    fun testSecondMoveForwardTrue(){
        val pawn = Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x =  'A'.code,
                y = 2,
            )
        )

        val moves = pawn.getAvailableMoves(listOf(pawn))

        assertEquals(2, moves.size)
        assertTrue(
            IntOffset(
            x = 'A'.code,
            y = 3,
        ) in moves)
        assertTrue(
        IntOffset(
            x = 'A'.code,
            y = 4,
        ) in moves)
    }

    @Test
    fun testSecondMoveForwardFalse(){
        val pawn = Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x = 'A'.code,
                y = 3
            )
        )

        val moves = pawn.getAvailableMoves(listOf(pawn))

        assertEquals(1, moves.size)
        assertEquals(
            IntOffset(
            x = 'A'.code,
            y = 4,
        ) , moves.first())

    }

    @Test
    fun testNoPossibleMoves(){
        val pawn = Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x =  'A'.code,
                y = 3,
            )
        )

        demoBlackPiece.position = IntOffset(x = 'A'.code, y = 4)

        val pieces = listOf(
            pawn, demoBlackPiece
        )
        val moves = pawn.getAvailableMoves(pieces)

        assertTrue(moves.isEmpty())
    }

    @Test
    fun testCaptureEnemy(){
        val pawn = Pawn(
            color = Piece.Color.White,
            position = IntOffset(
                x =  'A'.code,
                y = 3,
            )
        )

        demoBlackPiece.position = IntOffset(x = 'B'.code, y = 4)

        val pieces = listOf(
            pawn, demoBlackPiece
        )
        val moves = pawn.getAvailableMoves(pieces)

        assertContains(moves, demoBlackPiece.position)
    }

}