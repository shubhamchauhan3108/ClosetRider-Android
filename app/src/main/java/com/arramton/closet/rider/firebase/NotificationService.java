package com.arramton.closet.rider.firebase;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.arramton.closet.rider.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class NotificationService extends FirebaseMessagingService {
    private static final String GENERAL_CHANNEL_ID = "general_channel_id";
    //    private LoginManager loginManager;
    private static final String GENERAL_CHANNEL = "General Channel";

    @Override
    public void onNewToken(@NonNull String token) {
        super.onNewToken(token);
//        loginManager = new LoginManager(this);
//        loginManager.setFirebase_token(token);
//        loginManager.setIs_firebase_token_changed(true);
        Log.d("TAG", "onNewToken: we got this token " + token);
    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage message) {
        super.onMessageReceived(message);
        sendNotification(message.getNotification().getTitle(), message.getNotification().getBody(), message.getData());

    }

    private void generalChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(
                    GENERAL_CHANNEL_ID, GENERAL_CHANNEL,
                    NotificationManager.IMPORTANCE_HIGH
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(notificationChannel);
        }
    }

    private void sendNotification(String title, String body, Map<String, String> data) {

        generalChannel();
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), GENERAL_CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.spark_management)
                .setPriority(NotificationCompat.PRIORITY_HIGH)

                .setAutoCancel(true);
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(getApplicationContext());
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        managerCompat.notify(0, builder.build());



    }
}
