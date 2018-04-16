package com.payghost.mobileschools.Services;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import com.payghost.mobileschools.Globals.Config;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader extends AsyncTask<Void,Void,Bitmap>
{
    Context context;
    String url;
    public ImageDownloader(Context context, String url) {
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