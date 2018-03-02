package com.payghost.mobileschools.Holders;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    CircleImageView logo;
    TextView name;
    LinearLayout item;
    Context context;
    public SchoolsHolder(View view)
    {
        super(view);
        item = (LinearLayout)view.findViewById(R.id.school_item);
        logo = (CircleImageView)view.findViewById(R.id.profile_image);
        name = (TextView)view.findViewById(R.id.school_name);
    }
    public void bind(RetrieveService data,Context context)
    {
        this.context = context;
      name.setText(data.school_name);
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
