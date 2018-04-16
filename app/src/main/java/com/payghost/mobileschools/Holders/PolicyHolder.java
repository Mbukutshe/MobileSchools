package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiseman on 2018-03-20.
 */

public class PolicyHolder extends RecyclerView.ViewHolder{
    public FrameLayout item_view,icon_picture,icon_document,download;
    public CircleImageView icon_image;
    public TextView subject,description;
    public ImageView document_icon;
    public PolicyHolder(View view)
    {
        super(view);
        item_view = (FrameLayout)view.findViewById(R.id.general_item);
        icon_picture = (FrameLayout)view.findViewById(R.id.icon_picture);
        icon_document = (FrameLayout)view.findViewById(R.id.icon_document);
        download = (FrameLayout)view.findViewById(R.id.play_video_layout);
        icon_image = (CircleImageView)view.findViewById(R.id.profile_image);
        document_icon = (ImageView) view.findViewById(R.id.picture);
        subject = (TextView)view.findViewById(R.id.subject);
        description = (TextView)view.findViewById(R.id.description);
    }
    public void bind(RetrieveService data)
    {
        subject.setText(data.title);
        description.setText(data.message);
    }
}
