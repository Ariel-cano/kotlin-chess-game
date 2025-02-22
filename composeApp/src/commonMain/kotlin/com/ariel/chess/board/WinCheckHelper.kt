import androidx.compose.ui.unit.IntOffset
import com.ariel.chess.pieces.King
import com.ariel.chess.pieces.Piece

fun isTheKingInThreat(
    pieces: List<Piece>,
    piece: Piece,
    x: Int,
    y: Int,
): Boolean {
    val originalPosition = piece.position
    val targetPosition = IntOffset(x, y)
    val king = pieces.firstOrNull { it is King && it.color == piece.color } ?: return false
    val newPieces = pieces.filterNot { it.position == targetPosition }
    piece.position = targetPosition
    val updatedPieces = newPieces + piece
    val enemyMoves = updatedPieces.filter { it.color != piece.color }
        .flatMap { it.getAvailableMoves(updatedPieces) }
    val isThreatened = enemyMoves.any { it == king.position }
    piece.position = originalPosition

    return isThreatened
}


fun canProtectKing(pieces: List<Piece>, piece: Piece): Set<IntOffset> {
    val originalPosition = piece.position
    return piece.getAvailableMoves(pieces).filter { move ->
        piece.position = move
        val newPieces = pieces.filter { it != piece } + piece
        val safe = !isTheKingInThreat(newPieces, piece, piece.position.x, piece.position.y)
        piece.position = originalPosition
        safe
    }.toSet()
}

fun isCheckmate(pieces: List<Piece>, playerTurn: Piece.Color): Boolean {
    val king = pieces.firstOrNull { it is King && it.color == playerTurn } ?: return false
    if (!isTheKingInThreat(pieces, king, king.position.x, king.position.y)) {
        return false
    }
    val kingMoves = king.getAvailableMoves(pieces)
    if (kingMoves.any { move -> !isTheKingInThreat(pieces, king, move.x, move.y) }) {
        return false
    }
    val canProtect = pieces.filter { it.color == playerTurn && it != king }
        .any { piece ->
            canProtectKing(pieces, piece).isNotEmpty()
        }

    return !canProtect
}

fun isStalemate(pieces: List<Piece>, playerTurn: Piece.Color): Boolean {
    val king = pieces.firstOrNull { it is King && it.color == playerTurn } ?: return false
    if (isTheKingInThreat(pieces, king, king.position.x, king.position.y)) {
        return false
    }
    val kingMoves = king.getAvailableMoves(pieces)
    if (kingMoves.any { move -> !isTheKingInThreat(pieces, king, move.x, move.y) }) {
        return false
    }
    val canProtect = pieces.filter { it.color == playerTurn && it != king }
        .any { piece ->
            canProtectKing(pieces, piece).isNotEmpty()
        }
    return !canProtect

}



