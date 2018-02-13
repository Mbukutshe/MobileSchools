package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsHolder extends RecyclerView.ViewHolder {
    CircleImageView logo;
    TextView name;
    public SchoolsHolder(View view)
    {
        super(view);
        logo = (CircleImageView)view.findViewById(R.id.profile_image);
        name = (TextView)view.findViewById(R.id.school_name);
    }
    public void bind(RetrieveService data)
    {

    }
}
