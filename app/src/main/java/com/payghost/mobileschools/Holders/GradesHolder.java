package com.payghost.mobileschools.Holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.R;


/**
 * Created by Wiseman on 2018-02-15.
 */

public class GradesHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
    public TextView grade_name,grade_pointer,grade_id;
    public LinearLayout grade_close,grade_proceed,subject_close,add_subject,edit_text_subject;
    public EditText edit_subject_name;
    public RecyclerView subjects;
    public FrameLayout grade_text;
    public LinearLayout all_subjects;
    public GradesHolder(View itemView)
    {
        super(itemView);
        grade_text = (FrameLayout)itemView.findViewById(R.id.grade_text);
        grade_name = (TextView)itemView.findViewById(R.id.grade_name);
        grade_id = (TextView)itemView.findViewById(R.id.grade_id);
        grade_close = (LinearLayout) itemView.findViewById(R.id.delete_grade);
        grade_proceed = (LinearLayout) itemView.findViewById(R.id.proceed_grade);
        subjects = (RecyclerView)itemView.findViewById(R.id.subjects_recycler_view);
        edit_subject_name = (EditText)itemView.findViewById(R.id.added_subject);
        add_subject = (LinearLayout)itemView.findViewById(R.id.add_subject);
        all_subjects = (LinearLayout)itemView.findViewById(R.id.all_subjects);
        grade_pointer = (TextView)itemView.findViewById(R.id.grade_pointer);
        edit_text_subject = (LinearLayout)itemView.findViewById(R.id.edit_text_subject);
    }
    public void bind()
    {

    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.add_grade:
                grade_name.setText("Mathematics");
            break;
        }
    }

}
