package com.ariel.chess.pieces

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import com.caverock.androidsvg.SVG

@Composable
actual fun PieceImage(imagePath: String, modifier: Modifier) {
    val context = LocalContext.current
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }

    LaunchedEffect(imagePath) {
        bitmap = loadSvgFromAssets(context, imagePath)
    }

    bitmap?.let {
        Image(bitmap = it.asImageBitmap(), contentDescription = null, modifier = modifier)
    }
}


fun loadSvgFromAssets(context: Context, filePath: String): Bitmap? {
    return try {
        val inputStream = context.assets.open(filePath)
        val svg = SVG.getFromInputStream(inputStream)
        val picture = svg.renderToPicture()
        val bitmap = Bitmap.createBitmap(picture.width, picture.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        canvas.drawPicture(picture)
        bitmap
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

