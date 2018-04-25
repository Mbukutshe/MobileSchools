package com.payghost.mobileschools.Holders;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    ImageView logo;
    public TextView name,checked,pointer;
    LinearLayout item;
    Context context;
    public AppCompatImageView tick;
    public SchoolsHolder(View view)
    {
        super(view);
        item = (LinearLayout)view.findViewById(R.id.school_item);
        logo = (ImageView)view.findViewById(R.id.profile_image);
        name = (TextView)view.findViewById(R.id.school_name);
        checked = (TextView)view.findViewById(R.id.checked);
        tick = (AppCompatImageView)view.findViewById(R.id.check_image);
        pointer = (TextView)view.findViewById(R.id.grade_pointer);
    }
    public void bind(RetrieveService data,Context context)
    {
        this.context = context;
        name.setText(data.school_name);
        Picasso.with(context).load(data.school_logo).into(logo);
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.school_item:

            break;
        }
    }
}
