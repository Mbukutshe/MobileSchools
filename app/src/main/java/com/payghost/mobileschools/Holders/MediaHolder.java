package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class MediaHolder extends RecyclerView.ViewHolder {
    public TextView textViewdate,textViewsubject,textViewdescription;
    public MediaHolder(View view)
    {
        super(view);
        textViewdate = view.findViewById(R.id.date);
        textViewsubject = view.findViewById(R.id.subject);
        textViewdescription = view.findViewById(R.id.description);
    }
    public void bind(RetrieveService data) {
        textViewdate.setText(data.date);
        textViewsubject.setText(data.title);
        textViewdescription.setText(data.message);
    }
}
