package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.payghost.mobileschools.Activities.ZoomPicture;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class MoreDetails extends Fragment {
    View view;
    Animation anim;
    TextView subject,time,description;
    ImageView image;
    String title,date,message,url;
    CardView parent;
    FrameLayout share;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.card_view_images, container, false);

        parent = (CardView)view.findViewById(R.id.card_view_videos);
        share = (FrameLayout)view.findViewById(R.id.share);
        share.setVisibility(GONE);
        subject = (TextView)view.findViewById(R.id.subject_message);
        time = (TextView)view.findViewById(R.id.time_message);
        description = (TextView)view.findViewById(R.id.message_message);
        image = (ImageView)view.findViewById(R.id.video_thumbnail);
        title = getArguments().getString("subject");
        date = getArguments().getString("time");
        message = getArguments().getString("message");
        url = getArguments().getString("url");
        subject.setText(title);
        description.setText(message);
        time.setText(date);
        Picasso.with(view.getContext()).load(url).into(image);
        anim = new Animation(view.getContext());
        anim.toRight(parent);
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ZoomPicture.class);
                intent.putExtra("image",url);
                startActivity(intent);
            }
        });
        return view;
    }
}
