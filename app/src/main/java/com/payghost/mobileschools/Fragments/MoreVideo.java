package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-20.
 */
public class MoreVideo extends Fragment {
    View view;
    Animation anim;
    TextView subject,time,description,duration;
    ImageView image;
    String title,date,message,url,dura;
    CardView parent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_view_videos, container, false);

        parent = (CardView)view.findViewById(R.id.card_view_videos);
        duration = (TextView)view.findViewById(R.id.video_duration);
        subject = (TextView)view.findViewById(R.id.subject_message);
        time = (TextView)view.findViewById(R.id.time_message);
        description = (TextView)view.findViewById(R.id.message_message);
        image = (ImageView)view.findViewById(R.id.video_thumbnail);

        byte[] bytes = getArguments().getByteArray("image");
        Bitmap thumbnail = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        title = getArguments().getString("subject");
        date = getArguments().getString("time");
        message = getArguments().getString("message");
        dura = getArguments().getString("duration");
        subject.setText(title);
        description.setText(message);
        time.setText(date);
        duration.setText(dura);
        image.setImageBitmap(thumbnail);
        ((FrameLayout)view.findViewById(R.id.play_video_layout)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim.setAlphaAnimation((FrameLayout)view.findViewById(R.id.play_video_layout));
                ConnectivityManager connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                if(isConnected)
                {
                    String url = getArguments().getString("url");
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    view.getContext().startActivity(browserIntent);
                }
                else
                {
                    LayoutInflater infla = LayoutInflater.from(view.getContext());
                    View layout =infla.inflate(R.layout.toast_container_layout,(ViewGroup)view.findViewById(R.id.toast_layout));
                    TextView textview = (TextView)layout.findViewById(R.id.toast_message);
                    textview.setText("No Internet Connection!");
                    Toast toast = new Toast(view.getContext());
                    toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                    toast.setDuration(Toast.LENGTH_LONG);
                    toast.setView(layout);
                    toast.show();
                }
            }
        });
        anim = new Animation(view.getContext());
        anim.toRight(parent);
        return view;
    }
}
