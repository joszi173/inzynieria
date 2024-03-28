import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Handler
import android.os.Looper
import androidx.core.app.NotificationCompat
import com.example.tamagotchi.R

/*
aby utworzyc powiadomienie
1. import showNoti
2. showNoti(this, "Test tytuł", "Treść")
 */

fun showNoti(context: Context, title: String, message: String) {
    // Identyfikator kanału powiadomień
    val channelId = "default_channel_id"
    // Nazwa kanału powiadomień
    val channelName = "Default Channel"

    // Utwórz menedżera powiadomień
    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    // Sprawdź wersję systemu i utwórz kanał powiadomień (wymagane dla Androida 8.0 i nowszych)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        // Sprawdź, czy kanał już istnieje
        var channel = notificationManager.getNotificationChannel(channelId)
        if (channel == null) {
            // Jeśli kanał nie istnieje, utwórz nowy
            channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            // Dodaj kanał do menedżera powiadomień
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Utwórz powiadomienie za pomocą NotificationCompat.Builder
    val builder = NotificationCompat.Builder(context, channelId).apply {
        // Ustawienia powiadomienia
        setSmallIcon(R.drawable.noti) // Ikona powiadomienia
        setContentTitle(title) // Tytuł powiadomienia
        setContentText(message) // Treść powiadomienia
        setPriority(NotificationCompat.PRIORITY_DEFAULT) // Priorytet powiadomienia
        setAutoCancel(true) // Powiadomienie zostanie automatycznie usunięte po kliknięciu
    }

    // Wyświetl powiadomienie
    notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())

    // Ukryj powiadomienie po 3 sekundach (3000 milisekund) - opcjonalne
    Handler(Looper.getMainLooper()).postDelayed({
        notificationManager.cancel(System.currentTimeMillis().toInt())
    }, 3000)
}
