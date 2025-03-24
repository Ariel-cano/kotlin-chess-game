import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ariel.chess.board.rememberBoard
import com.ariel.chess.settings.SoundWinManager
import com.ariel.chess.ui.BoardUi
import kotlinx.coroutines.delay
import kotlin.random.Random

@Composable
fun App() {
    val board = rememberBoard()
    val winnerText by remember { board.winner }
    var showConfetti by remember { mutableStateOf(false) }

    LaunchedEffect(winnerText) {
        if (winnerText != null) {
            showConfetti = true
            SoundWinManager.playMoveSound()
            delay(3000)  // 🎉 Показываем конфетти 3 секунды после победы
            showConfetti = false
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize().background(Color(0xFFEEEEEE))
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            AnimatedVisibility(
                visible = winnerText != null,
                enter = fadeIn() + expandVertically(),
                exit = fadeOut() + shrinkVertically()
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .shadow(6.dp, RoundedCornerShape(12.dp))
                        .background(MaterialTheme.colors.primary)
                        .padding(16.dp)
                ) {
                    Text(
                        text = winnerText ?: "",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            AnimatedVisibility(
                visible = winnerText != null,
                enter = scaleIn(initialScale = 0.8f) + fadeIn(),
                exit = scaleOut(targetScale = 0.8f) + fadeOut()
            ) {
                Button(
                    onClick = { board.resetGame() },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Перезапустить игру", fontSize = 18.sp)
                }
            }

            BoardUi(board = board)
        }

        if (showConfetti) {
            ConfettiAnimation()
        }
    }
}

@Composable
fun ConfettiAnimation() {
    val confettiCount = 30
    val confettiList = remember { List(confettiCount) { ConfettiParticle() } }
    val infiniteTransition = rememberInfiniteTransition()

    val yOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 100f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    Canvas(modifier = Modifier.fillMaxSize()) {
        confettiList.forEach { confetti ->
            rotate(confetti.rotation) {
                drawCircle(
                    color = confetti.color,
                    center = Offset(confetti.x, confetti.y + yOffset),
                    radius = 10f
                )
            }
        }
    }
}

@Stable
data class ConfettiParticle(
    val x: Float = Random.nextFloat() * 800f,
    val y: Float = Random.nextFloat() * 600f,
    val rotation: Float = Random.nextFloat() * 360f,
    val color: Color = listOf(Color.Red, Color.Blue, Color.Green, Color.Yellow).random()
)
