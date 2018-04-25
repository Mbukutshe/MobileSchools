package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;

public class TrashDocumentHolder extends RecyclerView.ViewHolder{
    public TextView name,time,subject,message;
    public FrameLayout edit,delete;
    public TrashDocumentHolder(View v)
    {
        super(v);
        name = v.findViewById(R.id.user);
        time = v.findViewById(R.id.date);
        subject = v.findViewById(R.id.subject);
        message = v.findViewById(R.id.message);
        edit = v.findViewById(R.id.edit);
        delete = v.findViewById(R.id.delete);
    }
}
