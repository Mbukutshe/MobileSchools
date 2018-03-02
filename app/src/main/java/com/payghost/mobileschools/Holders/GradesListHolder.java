package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-25.
 */

public class GradesListHolder extends RecyclerView.ViewHolder{
    public TextView grade;
    public GradesListHolder(View view)
    {
        super(view);
        grade = (TextView)view.findViewById(R.id.grade_list);
    }
    public void bind(RetrieveService data)
    {
       grade.setText(data.school_name);
    }
}
