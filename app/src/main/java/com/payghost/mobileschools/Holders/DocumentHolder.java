package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class DocumentHolder extends RecyclerView.ViewHolder {
    public TextView textViewdate,textViewsubject,textViewdescription;
    public DocumentHolder(View view)
    {
        super(view);
        textViewdate = view.findViewById(R.id.date);
        textViewsubject = view.findViewById(R.id.subject);
        textViewdescription = view.findViewById(R.id.description);
    }
}
