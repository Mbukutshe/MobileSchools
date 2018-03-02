package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-20.
 */
public class MoreVideo extends Fragment {
    View view;
    Animation anim;
    TextView subject,time,description;
    ImageView image;
    String title,date,message,url;
    CardView parent;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_view_videos, container, false);

        parent = (CardView)view.findViewById(R.id.card_view_videos);

        subject = (TextView)view.findViewById(R.id.subject_message);
        time = (TextView)view.findViewById(R.id.time_message);
        description = (TextView)view.findViewById(R.id.message_message);
        image = (ImageView)view.findViewById(R.id.video_thumbnail);

        byte[] bytes = getArguments().getByteArray("image");
        Bitmap thumbnail = BitmapFactory.decodeByteArray(bytes,0,bytes.length);

        title = getArguments().getString("subject");
        date = getArguments().getString("time");
        message = getArguments().getString("message");

        subject.setText(title);
        description.setText(message);
        time.setText(date);
        image.setImageBitmap(thumbnail);

        anim = new Animation(view.getContext());
        anim.toRight(parent);
        return view;
    }
}
