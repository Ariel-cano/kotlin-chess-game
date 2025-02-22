package com.ariel.chess.pieces

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
expect fun PieceImage(imagePath: String, modifier: Modifier = Modifier)

