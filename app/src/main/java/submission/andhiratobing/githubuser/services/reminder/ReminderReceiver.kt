package submission.andhiratobing.githubuser.services.reminder

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import submission.andhiratobing.githubuser.MainActivity
import submission.andhiratobing.githubuser.R
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReminderReceiver @Inject constructor() : BroadcastReceiver() {

    companion object {
        const val TYPE_REPEATING = "RepeatingReminder"

        enum class TypeReminder(val typeId: Int, val hour: Int, val minutes: Int) {
            TYPE_REPEATING(1, 9, 0)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        when (intent.getStringExtra(TYPE_REPEATING)) {
            TypeReminder.TYPE_REPEATING.name -> showReminderNotification(context)
        }
    }

    /**
     *Calendar.HOUR = Field number for get and set indicating the hour of the morning or afternoon. HOUR is used for the 12-hour clock.
     *Calendar.HOUR E.g., at 10:04:15.250 PM the HOUR is 10.
     *Calendar.HOUR_OF_DAY = Field number for get and set indicating the hour of the day. HOUR_OF_DAY is used for the 24-hour clock.
     *Calendar.HOUR_OF_DAY E.g., at 10:04:15.250 PM the HOUR_OF_DAY is 22.
     */
    private fun getCalendarTime(typeReminder: TypeReminder): Calendar {
        return Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, typeReminder.hour)
            set(Calendar.MINUTE, typeReminder.minutes)
        }
    }


    fun setRepeatingReminder(context: Context, typeReminder: TypeReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        intent.putExtra(TYPE_REPEATING, typeReminder.name)
        val pendingIntent = PendingIntent.getBroadcast(context, typeReminder.typeId, intent, 0)
        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            getCalendarTime(typeReminder).timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }


    private fun showReminderNotification(context: Context) {
        val channelId = "channel_1"
        val channelName = "repeating_channel"
        val notificationId = 1

        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context,
            notificationId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_alarm)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setContentTitle(context.getString(R.string.repeating_set_title))
            .setContentText(context.getString(R.string.repeating_set_content))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setSound(alarmSound)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            channel.enableVibration(true)
            channel.vibrationPattern = longArrayOf(1000, 1000, 1000, 1000, 1000)
            builder.setChannelId(channelId)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()
        notificationManagerCompat.notify(notificationId, notification)
    }


    fun cancelReminders(context: Context, typeReminder: TypeReminder) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ReminderReceiver::class.java)
        val requestCode = typeReminder.typeId
        val pendingIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)
        pendingIntent.cancel()
        alarmManager.cancel(pendingIntent)
    }
}