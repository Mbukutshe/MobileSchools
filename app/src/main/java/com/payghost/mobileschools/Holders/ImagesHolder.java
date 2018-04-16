package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;


/**
 * Created by Wiseman on 2017-10-28.
 */

public class ImagesHolder extends RecyclerView.ViewHolder{
    public TextView messageId,message,subject,link,author,date,urgent,video_duration,attach,filename;
    RelativeLayout card;
    public ImageView video_thumbnail;
    public FrameLayout play;
    public ImagesHolder(View tv) {
        super(tv);
        video_thumbnail = (ImageView)tv.findViewById(R.id.video_thumbnail);
        message = (TextView)tv.findViewById(R.id.message_message);
        date = (TextView)tv.findViewById(R.id.time_message);
        subject =(TextView)tv.findViewById(R.id.subject_message);
        link = (TextView)tv.findViewById(R.id.link);
        play = (FrameLayout)tv.findViewById(R.id.play_video_layout);
        author = (TextView)tv.findViewById(R.id.author_message);
    }
}
