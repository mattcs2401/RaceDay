package com.mcssoft.raceday.utility.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast

class AlarmReceiver: BroadcastReceiver() {
// Note: Can't constructor inject here.

    // Test with this 1st ?
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("TAG", "BroadcastReceiver.onReceive")
        val message = intent?.getStringExtra("EXTRA_MESSAGE")
        if (message != null) {
            Toast.makeText(context, message, Toast.LENGTH_LONG).show()
        }
    }

//    override fun onReceive(context: Context?, intent: Intent?) {
//        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: return
//        context?.let { ctx ->
//            val channelId = ctx.resources.getString(R.string.notify_channel_id)
//            val builder = NotificationCompat.Builder(ctx, channelId)
//                .setSmallIcon(R.drawable.ic_launcher_foreground)
//                .setContentTitle("Alarm Demo")
//                .setContentText("Notification sent with message: $message")
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//            notificationManager.notify(1, builder.build())
//        }
//    }
}