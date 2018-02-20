package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;


/**
 * Created by Wiseman on 2018-01-29.
 */

public class AdminHolder extends RecyclerView.ViewHolder {
    public TextView name,proceed,icon;
    public EditText text_message;
    public LinearLayout item,car;
    public LinearLayout collapsing,message,document;
    public FrameLayout send,media;
    public AdminHolder(View v)
    {
        super(v);
        item = (LinearLayout)v.findViewById(R.id.admin_item_view);
        name = (TextView)v.findViewById(R.id.admin_name);
        proceed = (TextView)v.findViewById(R.id.proceed);
        icon = (TextView)v.findViewById(R.id.admin_icon);
    }
}
