package com.payghost.mobileschools.Holders;

import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class MessageHolder extends RecyclerView.ViewHolder{
    public AppCompatTextView textViewuser,textViewdate,textViewsubject,textViewdescription,user;
    public MessageHolder(View view)
    {
        super(view);
        textViewuser = view.findViewById(R.id.user);
        textViewdate = view.findViewById(R.id.date);
        textViewsubject = view.findViewById(R.id.subject);
        textViewdescription = view.findViewById(R.id.description);
        user = view.findViewById(R.id.user);
    }
    public void bind(RetrieveService data){
        user.setText(data.sender);
        textViewuser.setText(data.link);
        textViewdate.setText(data.date);
        textViewsubject.setText(data.title);
        textViewdescription.setText(data.message);

    }
}
