package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;


/**
 * Created by Wiseman on 2017-10-02.
 */

public class VideosHolder extends RecyclerView.ViewHolder{
    public TextView messageId,message,subject,link,author,date,urgent,video_duration,attach,filename,sender;
    RelativeLayout card;
    public ImageView video_thumbnail;
    public FrameLayout play,duration;
    public  LinearLayout video_footer;
    public VideosHolder(View tv) {
        super(tv);
        video_thumbnail = (ImageView)tv.findViewById(R.id.video_thumbnail);
        message = (TextView)tv.findViewById(R.id.message_message);
        date = (TextView)tv.findViewById(R.id.time_message);
        subject =(TextView)tv.findViewById(R.id.subject_message);
        play = (FrameLayout)tv.findViewById(R.id.play_video_layout);
        duration = (FrameLayout)tv.findViewById(R.id.video_duration_layout);
        video_duration = (TextView)tv.findViewById(R.id.video_duration);
        video_footer = (LinearLayout)tv.findViewById(R.id.video_footer_layout);
        sender = (TextView)tv.findViewById(R.id.author_message);
    }
}
