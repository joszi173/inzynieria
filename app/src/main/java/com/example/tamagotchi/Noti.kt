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
    val channelId = "default_channel_id"
    val channelName = "Default Channel"

    val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        notificationManager.createNotificationChannel(channel)
    }

    val builder = NotificationCompat.Builder(context, channelId).apply {
        setSmallIcon(R.drawable.noti)
        setContentTitle(title)
        setContentText(message)
        priority = NotificationCompat.PRIORITY_DEFAULT
        setAutoCancel(true)
    }

    notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
}
