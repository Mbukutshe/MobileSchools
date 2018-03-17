package com.payghost.mobileschools.Services;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.messaging.RemoteMessage;
import com.payghost.mobileschools.Activities.MainActivity;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class FirebaseMessagingService extends com.google.firebase.messaging.FirebaseMessagingService
{
    public static final String INTENT_FILTER = "INTENT_FILTER";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage)
    {
        String message,subject,url,key;
        message = remoteMessage.getData().get("desc");
        subject = remoteMessage.getData().get("subject");
        url = remoteMessage.getData().get("url");
        key = remoteMessage.getData().get("key");
        showNotification(message, subject,url,key);

        try
        {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    private void showNotification(String message, String subject,String url,String key)
    {
        switch (key) {
            case "image":
                new downloadImage(getApplicationContext(), url).execute();
                Intent in = new Intent(this, MainActivity.class);
                in.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, in, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(this).setAutoCancel(true).setContentTitle(subject).setSubText(message).setSmallIcon(R.drawable.logo).setStyle(new NotificationCompat.BigPictureStyle().bigPicture(Config.bitmap));
                NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                manager.notify(0, builder.build());
            break;
            default:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent Intent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder Builder = new NotificationCompat.Builder(this).setAutoCancel(true).setContentTitle(subject).setSubText(message).setSmallIcon(R.drawable.logo);
                NotificationManager Manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Manager.notify(0, Builder.build());
            break;
        }
    }
    public class downloadImage extends AsyncTask<Void,Void,Bitmap>
    {
        Context context;
        String url;
        public downloadImage(Context context, String url) {
            super();
            this.context = context;
            this.url = url;
        }
        @Override
        protected Bitmap doInBackground(Void... voids) {
            try
            {
                URLConnection connection = new URL(url).openConnection();
                connection.setConnectTimeout(1000*30);
                connection.setReadTimeout(1000*30);
                return BitmapFactory.decodeStream((InputStream)connection.getContent(),null,null);
            }
            catch(Exception ex)
            {
                ex.printStackTrace();
                return null;
            }

        }
        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null)
            {
                //imageView to display the downloaded image
                Config.bitmap = bitmap;
            }
        }
    }
}