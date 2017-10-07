package posts.facebook.pranika.facebookapi;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

/**
 * Created by nikhiljain on 9/3/17.
 */

public class FcmMessagingService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String title=remoteMessage.getNotification().getTitle();
        String body=remoteMessage.getNotification().getBody();
        String patient_id =remoteMessage.getData().get("patient_id");


        NotificationCompat.Builder notificationbuilder=new NotificationCompat.Builder(this);
        notificationbuilder.setContentTitle(title);
        notificationbuilder.setSmallIcon(R.drawable.notify);
        notificationbuilder.setContentText(body);
        notificationbuilder.setAutoCancel(true);

        Intent intent=new Intent(this,PatientStatus.class);
        intent.putExtra("patient_id",patient_id);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        notificationbuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager= (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0,notificationbuilder.build());

    }
}
