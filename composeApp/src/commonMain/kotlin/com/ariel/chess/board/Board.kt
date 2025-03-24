package com.ariel.chess.board

import androidx.compose.runtime.*
import androidx.compose.ui.unit.IntOffset
import canProtectKing
import com.ariel.chess.pieces.King
import com.ariel.chess.pieces.Piece
import com.ariel.chess.settings.SoundManager
import isCheckmate
import isStalemate
import isTheKingInThreat

@Composable
fun rememberBoard(): Board =
    remember {
        Board()
    }


class Board {
    private val _pieces = mutableListOf<Piece>()
    val pieces get() = _pieces.toList()

    init {
        _pieces.addAll(
            decodePieces(InitialEncodedPiecesPosition)
        )
    }
    var winner = mutableStateOf<String?>(null)

    var selectedPiece by mutableStateOf<Piece?>(null)
        private set

    var selectedPieceMoves by mutableStateOf(emptySet<IntOffset>())
        private set

    var moveIncrement by mutableStateOf(0)
        private set

    var playerTurn by mutableStateOf(Piece.Color.White)

    fun moveSelectedPiece(x: Int, y: Int) {
        selectedPiece?.let { piece ->
            if (!isAvailableMove(x, y)) return
            if (piece.color != playerTurn) return

            SoundManager.playMoveSound()

            val king = pieces.firstOrNull { it is King && it.color == piece.color } ?: return
            val originalPosition = piece.position
            val targetPosition = IntOffset(x, y)
            val updatedPieces = pieces.filterNot { it.position == targetPosition }.toMutableList()
            piece.position = targetPosition
            updatedPieces.add(piece)
            val kingUnderThreatAfterMove = isTheKingInThreat(updatedPieces, king, king.position.x, king.position.y)
            if (kingUnderThreatAfterMove) {
                piece.position = originalPosition
                selectedPieceMoves = canProtectKing(pieces, piece)
                return
            }
            _pieces.clear()
            _pieces.addAll(updatedPieces)



            clearSelection()
            switchPlayerTurn()
            moveIncrement++

        }
    }
    fun resetGame() {
        _pieces.clear()
        _pieces.addAll(decodePieces(InitialEncodedPiecesPosition))
        playerTurn = Piece.Color.White
        winner.value = null
        clearSelection()
        moveIncrement++
    }


    fun getPiece(x: Int, y: Int): Piece? =
        _pieces.find { it.position.x == x && it.position.y == y }

    fun isAvailableMove(x: Int, y: Int): Boolean {
        val piece = selectedPiece ?: return false
        val king = pieces.firstOrNull { it is King && it.color == piece.color } ?: return false
        selectedPieceMoves = if (isTheKingInThreat(pieces, king, king.position.x, king.position.y)) {
            canProtectKing(pieces, piece)
        } else {
            piece.getAvailableMoves(pieces).filter { move ->
                val originalPosition = piece.position
                val targetPosition = move
                val updatedPieces = pieces.filterNot { it.position == targetPosition }.toMutableList()
                piece.position = targetPosition
                updatedPieces.add(piece)
                val kingUnderThreat = isTheKingInThreat(updatedPieces, king, king.position.x, king.position.y)
                piece.position = originalPosition
                !kingUnderThreat

            }.toSet()
        }

        if (isCheckmate(pieces, playerTurn)) {
            val winColor = if (playerTurn == Piece.Color.White) "Черные" else "Белые"
            winner.value = "Мат! Победили $winColor"
            println("МАТ! ПОБЕДИЛИ $winColor")
        }

        if (isStalemate(pieces, playerTurn)) {
            winner.value = "Пат! Безвыходное положение! Ничья!"
            println("Пат! Безвыходное положение! Ничья!")
        }


        return selectedPieceMoves.any { it.x == x && it.y == y }
    }

    fun selectPiece(piece: Piece) {
        if (piece.color !== playerTurn) return
        if (piece == selectedPiece) {
            clearSelection()
        } else {
            selectedPiece = piece
            selectedPieceMoves = piece.getAvailableMoves(pieces = pieces)
        }
    }

    private fun clearSelection() {
        selectedPiece = null
        selectedPieceMoves = emptySet()
    }

    private fun switchPlayerTurn() {
        playerTurn = if (playerTurn.isWhite)
            Piece.Color.Black
        else
            Piece.Color.White
    }
}

@Composable
fun Board.rememberPieceAt(x: Int, y: Int): Piece? =
    remember(x, y, moveIncrement) {
        getPiece(x = x, y = y)
    }

@Composable
fun Board.rememberIsAvailableMove(x: Int, y: Int): Boolean =
    remember(x, y, selectedPieceMoves) {
        isAvailableMove(x = x, y = y)
    }

