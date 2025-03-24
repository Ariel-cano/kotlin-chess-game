import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import com.ariel.chess.R
import com.ariel.chess.settings.SoundPlayer

class AndroidSoundPlayer(private val context: Context) : SoundPlayer {
    private var moveSound: MediaPlayer? = null
    init {
        moveSound = MediaPlayer.create(context, R.raw.stepik)
        if (moveSound == null) {
            Log.e("AndroidSoundPlayer", "Ошибка! Не удалось создать MediaPlayer") // 🔴 Проблема с файлом
        } else {
            Log.d("AndroidSoundPlayer", "MediaPlayer успешно создан!") // ✅ Всё норм
        }
    }

    override fun playMoveSound() {
        if (moveSound == null) {
            Log.e("AndroidSoundPlayer", "Ошибка! moveSound = null")
        } else {
            Log.d("AndroidSoundPlayer", "Проигрываем звук")
            moveSound?.start()
        }
    }
}
