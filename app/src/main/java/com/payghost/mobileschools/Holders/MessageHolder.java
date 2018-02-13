package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class MessageHolder extends RecyclerView.ViewHolder{
    public TextView textViewuser,textViewdate,textViewsubject,textViewdescription;
    public MessageHolder(View view)
    {
        super(view);
        textViewuser = view.findViewById(R.id.user);
        textViewdate = view.findViewById(R.id.date);
        textViewsubject = view.findViewById(R.id.subject);
        textViewdescription = view.findViewById(R.id.description);

    }
    public void bind() {

    }
}
