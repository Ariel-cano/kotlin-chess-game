import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.ariel.chess.R
import com.ariel.chess.settings.SoundPlayer

class AndroidWinPlayer(private val context: Context) : SoundPlayer {
    private var moveSound: MediaPlayer? = null
    init {
        moveSound = MediaPlayer.create(context, R.raw.win)
        if (moveSound == null) {
            Log.e("AndroidWinPlayer", "Ошибка! Не удалось создать MediaPlayer") // 🔴 Проблема с файлом
        } else {
            Log.d("AndroidWinPlayer", "MediaPlayer успешно создан!") // ✅ Всё норм
        }
    }

    override fun playMoveSound() {
        if (moveSound == null) {
            Log.e("AndroidWinPlayer", "Ошибка! moveSound = null")
        } else {
            Log.d("AndroidWinPlayer", "Проигрываем звук")
            moveSound?.start()
        }
    }
}
