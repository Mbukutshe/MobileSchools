package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SubjectsHolder extends RecyclerView.ViewHolder {

    public TextView subject_name,subject_id,grade_id;
    public LinearLayout delete_subject;
    public RecyclerView recyclerView;
    public SubjectsHolder(View itemView)
    {
        super(itemView);
        grade_id = (TextView)itemView.findViewById(R.id.grade_id);
        subject_id = (TextView)itemView.findViewById(R.id.subject_id);
        subject_name = (TextView)itemView.findViewById(R.id.subject_name);
        delete_subject = (LinearLayout)itemView.findViewById(R.id.delete_subject);
        recyclerView = itemView.findViewById(R.id.division_recycler);
    }
}
