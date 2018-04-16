package com.payghost.mobileschools.Holders;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-04-01.
 */

public class DivisionHolder extends RecyclerView.ViewHolder{
    public AppCompatCheckBox checkBox;
    public AppCompatTextView textView;
    public DivisionHolder(View view)
    {
        super(view);
        checkBox = (AppCompatCheckBox)view.findViewById(R.id.tab_check);
        textView = (AppCompatTextView)view.findViewById(R.id.tab_text);
    }
}
