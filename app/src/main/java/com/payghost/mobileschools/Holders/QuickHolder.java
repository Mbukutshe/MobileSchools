package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-03-20.
 */

public class QuickHolder extends RecyclerView.ViewHolder {
    public FrameLayout link;
    public TextView subject;
    public QuickHolder(View view)
    {
        super(view);
        link = (FrameLayout)view.findViewById(R.id.link);
        subject = (TextView)view.findViewById(R.id.subject);
    }
    public void bind(RetrieveService data)
    {
        subject.setText(data.title);
    }
}
