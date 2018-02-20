package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-17.
 */

public class ItemsHolder extends RecyclerView.ViewHolder {
    public FrameLayout delete;
    public ImageView name;
    public ItemsHolder(View view)
    {
        super(view);
        delete = (FrameLayout)view.findViewById(R.id.delete);
        name = (ImageView)view.findViewById(R.id.options_name);
    }
}
