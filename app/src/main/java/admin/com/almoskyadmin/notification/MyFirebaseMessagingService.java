package admin.com.almoskyadmin.notification;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import admin.com.almoskyadmin.R;
import admin.com.almoskyadmin.activity.HomeActivity;

public class MyFirebaseMessagingService extends FirebaseMessagingService {

    public static  int NOTIFICATION_ID = 1;
    public static final String INTENT_FILTER = "INTENT_FILTER_NOTIFICATION";
    private NotificationChannel mChannel;
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        System.out.println("recvd"+remoteMessage.getData());

       // Map<String, String> data = remoteMessage.getData();
      //  remoteMessage.getNotification().getBody();

        generateNotification("New Order",remoteMessage.getData().get("orderId"));
        
        
        

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
       // String messageBody = remoteMessage.getNotification().getBody();
        // Sets an ID for the notification, so it can be updated.
//        int notifyID = Integer.parseInt(data.get("notification_type"));
        String channelId = this.getResources().getString(R.string.default_notification_channel_id);
        CharSequence name = this.getResources().getString(R.string.app_name);// The user-visible name of the channel.
        int importance = NotificationManager.IMPORTANCE_HIGH;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            Toast.makeText(this, "received", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, HomeActivity.class);
           // intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
          //  intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
//                    PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
//                            PendingIntent.FLAG_ONE_SHOT);
            NotificationChannel mChannel = new NotificationChannel(channelId, name, importance);

// Create a notification and set the notification channel.
            Notification notification = new Notification.Builder(getApplicationContext())
                    .setContentTitle("New Order")
                    .setContentText(remoteMessage.getData().get("orderId")).setAutoCancel(true)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentIntent(pendingIntent)
                    .setStyle(new Notification.BigTextStyle().bigText(remoteMessage.getData().get("orderId")))
                    .setChannelId(channelId)
                    .build();

            NotificationManager mNotificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.createNotificationChannel(mChannel);
            mNotificationManager.notify(1, notification);


        } else {
//            Toast.makeText(this, "received", Toast.LENGTH_SHORT).show();


            Intent intentNotification = new Intent(INTENT_FILTER);
            sendBroadcast(intentNotification);

            Intent intent = new Intent(this, HomeActivity.class);
                      intent.addFlags(intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 1, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this);
            builder.setSmallIcon(R.mipmap.ic_launcher).setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                    .setColor(getResources().getColor(R.color.colorPrimary))
                    .setContentTitle(getString(R.string.app_name))
                  //  .setContentIntent(pendingIntent)
                    .setContentText(remoteMessage.getData().get("orderId")).setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle().bigText(remoteMessage.getData().get("orderId")))
                    .setDefaults(Notification.DEFAULT_ALL)
                    .setPriority(Notification.PRIORITY_HIGH);

            notificationManager.notify(1, builder.build());
        }




  //      System.out.println("notf"+remoteMessage.getNotification().getBody());

        if (remoteMessage.getData().size() > 0) {
         //   scheduleJob();
        }

       // generateNotification("New Order",remoteMessage.getData().get("orderId"));


      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            String id = "id_product";
            // The user-visible name of the channel.
            CharSequence name = "Product";
            // The user-visible description of the channel.
            String description = "Notifications regarding our products";
            int importance = NotificationManager.IMPORTANCE_MAX;
            NotificationChannel mChannel = new NotificationChannel(id, name, NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            mChannel.setDescription(description);
            mChannel.enableLights(true);
            // Sets the notification light color for notifications posted to this
            // channel, if the device supports this feature.
            mChannel.setLightColor(Color.RED);
            notificationManager.createNotificationChannel(mChannel);
        }*/

    }




    public static NotificationCompat.Builder getNotificationBuilder(Context context, String channelId, int importance) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            prepareChannel(context, channelId, importance);
            builder = new NotificationCompat.Builder(context, channelId);
        } else {
            builder = new NotificationCompat.Builder(context);
        }
        return builder;
    }

    @TargetApi(26)
    private static void prepareChannel(Context context, String id, int importance) {
        final String appName = context.getString(R.string.app_name);
        String description = "notif";
        final NotificationManager nm = (NotificationManager) context.getSystemService(Activity.NOTIFICATION_SERVICE);

        if(nm != null) {
            NotificationChannel nChannel = nm.getNotificationChannel(id);

            if (nChannel == null) {
                nChannel = new NotificationChannel(id, appName, importance);
                nChannel.setDescription(description);
                nm.createNotificationChannel(nChannel);
            }
        }
    }



    private void scheduleJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        Job myJob = dispatcher.newJobBuilder()
                .setService(MyJobService.class)
                .setTag("my-job-tag")
                .build();
        dispatcher.schedule(myJob);
    }

    private void generateNotification(String title,String messageBody) {
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);


        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = getApplicationContext().getString(R.string.default_notification_channel_id);
            NotificationChannel channel = new NotificationChannel(channelId,   "Yeahamin", NotificationManager.IMPORTANCE_DEFAULT);
            channel.setDescription(messageBody);
            mNotificationManager.createNotificationChannel(channel);

            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);
            mNotifyBuilder.setChannelId(channelId);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mNotificationManager.notify(1, mNotifyBuilder.build());
        }
        else{
            Intent intent = new Intent(this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent,
                    PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder mNotifyBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(messageBody)
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setContentIntent(pendingIntent);

            mNotifyBuilder.setDefaults(Notification.DEFAULT_SOUND);
            mNotifyBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            if (NOTIFICATION_ID > 1073741824) {
                NOTIFICATION_ID = 0;
            }
            mNotificationManager.notify(1, mNotifyBuilder.build());
        }
    }
}