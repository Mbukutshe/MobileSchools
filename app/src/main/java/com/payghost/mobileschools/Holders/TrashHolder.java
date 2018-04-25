package com.payghost.mobileschools.Holders;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;

public class TrashHolder extends RecyclerView.ViewHolder{
    public TextView name,time,subject,message,edit_icon,test_edit;
    public FrameLayout edit,delete,container_thumnail;
    public ImageView thumbnail;
    public LinearLayout edit_subject,edit_message;
    public EditText text_subject,text_message;
    public TrashHolder(View v)
    {
        super(v);
        name = v.findViewById(R.id.user);
        time = v.findViewById(R.id.date);
        subject = v.findViewById(R.id.subject);
        message = v.findViewById(R.id.message);
        edit = v.findViewById(R.id.edit);
        delete = v.findViewById(R.id.delete);
        container_thumnail = v.findViewById(R.id.container_thumbnail);
        edit_subject = v.findViewById(R.id.edit_subject);
        edit_message = v.findViewById(R.id.edit_message);
        text_subject = v.findViewById(R.id.text_subject);
        text_message = v.findViewById(R.id.text_message);
        edit_icon = v.findViewById(R.id.edit_icon);
        test_edit = v.findViewById(R.id.test_edit);
    }
}
