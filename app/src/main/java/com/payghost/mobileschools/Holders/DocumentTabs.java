package com.payghost.mobileschools.Holders;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.payghost.mobileschools.R;


/**
 * Created by Wiseman on 2018-03-31.
 */

public class DocumentTabs extends RecyclerView.ViewHolder{
    public AppCompatCheckBox checkBox;
    public AppCompatTextView textView;
    public LinearLayout view;
    public DocumentTabs(View view)
    {
        super(view);
        checkBox = (AppCompatCheckBox)view.findViewById(R.id.tab_check);
        textView = (AppCompatTextView)view.findViewById(R.id.tab_text);
        this.view = (LinearLayout)view.findViewById(R.id.quick_layout);
    }
}
