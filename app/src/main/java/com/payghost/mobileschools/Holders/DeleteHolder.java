package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-17.
 */

public class DeleteHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public FrameLayout itemView;
    public DeleteHolder(View view)
    {
        super(view);
        itemView = (FrameLayout)view.findViewById(R.id.delete_item_view);
        name = (TextView)view.findViewById(R.id.delete_name);
    }
}
