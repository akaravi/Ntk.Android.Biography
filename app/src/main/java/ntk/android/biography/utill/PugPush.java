package ntk.android.biography.utill;

/**
 * Created by Mehrdad Safari on 20-Mar-17.
 */

public class PugPush {

//    public static void ShowNotification(Context context, Notification notification) {
//        notification.ID = (int) System.currentTimeMillis() + 1;
//        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        NotificationCompat.Builder mBuilder;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            NotificationChannel channel = new NotificationChannel("Biography", context.getString(R.string.app_name), NotificationManager.IMPORTANCE_HIGH);
//            notificationManager.createNotificationChannel(channel);
//            mBuilder = new NotificationCompat.Builder(context, "Biography");
//        } else {
//            mBuilder = new NotificationCompat.Builder(context);
//        }
//        PendingIntent intent = null;
//        Intent i = new Intent(context, ActSplash.class);
//        i.setData(Uri.parse(notification.Content));
//        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
//        intent = PendingIntent.getActivity(context, 0, i, 0);
//        if (notification.ContentType == 1) {
//            mBuilder.setSmallIcon(R.drawable.logo)
//                    .setStyle(new NotificationCompat.BigTextStyle().bigText(notification.Content))
//                    .setContentTitle(notification.Title)
//                    .setLargeIcon(ImageLoader.getInstance().loadImageSync(notification.BigImageSrc))
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setContentIntent(intent)
//                    .setContentText(notification.Content);
//        } else if (notification.ContentType == 2) {
//            mBuilder.setSmallIcon(R.drawable.logo)
//                    .setDefaults(Notification.DEFAULT_ALL)
//                    .setContentIntent(intent)
//                    .setStyle(new NotificationCompat.BigPictureStyle()
//                            .bigPicture(ImageLoader.getInstance().loadImageSync(notification.BigImageSrc))
//                            .setSummaryText(notification.Title)
//                            .setBigContentTitle(notification.Content));
//        }
//
//        notificationManager.notify(notification.ID, mBuilder.build());
//        if (MyApplication.Inbox) {
//            EventBus.getDefault().post(new EvNotify(true));
//        }
//    }
}
