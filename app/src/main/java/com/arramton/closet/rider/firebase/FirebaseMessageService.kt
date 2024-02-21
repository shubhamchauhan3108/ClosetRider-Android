package com.arramton.closet.rider.firebase

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.arramton.closet.rider.R
import com.arramton.closet.rider.leftNavigation.HomePageActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (remoteMessage.notification != null){
           callNotification(remoteMessage.notification?.title.toString(),remoteMessage.notification?.body.toString())
            val intent = Intent("com.arramton.closet.rider")
                .putExtra("title",remoteMessage.notification?.title.toString())
            remoteMessage.data
            val localeManager = LocalBroadcastManager.getInstance(this)
            localeManager.sendBroadcast(intent)

        }else
            Log.d(TAG,"null Notification")

    }
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG,"onNewToken")
    }

    @SuppressLint("RemoteViewLayout")
    fun callNotification(title: String,contentText: String){

        val intent = Intent(this, HomePageActivity::class.java)
            .putExtra("data","1")
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val Id = "i.apps.notifications"

        val builder = NotificationCompat.Builder(this,Id)
        builder.setSmallIcon(R.drawable.car)
            .setContentTitle(title)
            .setContentText(contentText)
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(applicationContext)){
            if (ActivityCompat.checkSelfPermission(
                    this@MyFirebaseMessagingService,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {

                return
            }
            notify(1,builder.build())
        }


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "default_channel_id"
            val channelName = "Default Channel"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(channelId, channelName, importance)
            notificationChannel.lightColor = Color.BLUE
            notificationChannel.lockscreenVisibility = NotificationCompat.VISIBILITY_PRIVATE

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }


    }


}