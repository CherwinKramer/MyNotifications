package nl.ckramer.mynotifications.Util;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import nl.ckramer.mynotifications.Model.PushNotification;
import nl.ckramer.mynotifications.R;

public class NotificationUtil {
    private static final String TAG = "NotificationUtil";

    public static final String NOTIFICATION_CHANNEL = "NOTIFICATION";
    public static final String REMINDER_CHANNEL = "REMINDER";

    public static void createNotificationChannel(PushNotification pushNotification) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(pushNotification.getChannelId(),  pushNotification.getTitle(), pushNotification.getImportance());
            channel.setDescription(pushNotification.getContent());

            NotificationManager notificationManager = pushNotification.getContext().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void generateNotification(PushNotification pushNotification) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(pushNotification.getContext(), pushNotification.getChannelId())
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle(pushNotification.getTitle())
                .setContentText(pushNotification.getContent())
                .setPriority(pushNotification.getImportance())
                .setAutoCancel(true);

        if(pushNotification.getPendingIntent() != null) {
            builder.setContentIntent(pushNotification.getPendingIntent());
        }

        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(pushNotification.getContext());

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());
    }

}
