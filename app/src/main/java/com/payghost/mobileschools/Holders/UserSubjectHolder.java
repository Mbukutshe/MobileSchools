package com.payghost.mobileschools.Holders;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-03-14.
 */

public class UserSubjectHolder extends RecyclerView.ViewHolder
{
    public TextView subject,checked;
    public AppCompatImageView tick;
    public UserSubjectHolder(View view)
    {
        super(view);
        subject = (TextView) view.findViewById(R.id.subject_text);
        tick = (AppCompatImageView)view.findViewById(R.id.check_image);
        checked = (TextView)view.findViewById(R.id.checked);
    }
}
