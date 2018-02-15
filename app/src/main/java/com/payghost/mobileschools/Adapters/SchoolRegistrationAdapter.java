package com.payghost.mobileschools.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.GradesHolder;
import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SchoolRegistrationAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    Context context;
    List<SubjectAndGrade> list,subjectList;
    EditText grade;
    LinearLayout btn;
    LinearLayoutManager layoutManager;
    SubjectAdapter subjectAdapter;
    public SchoolRegistrationAdapter(Context context, List<SubjectAndGrade> list, EditText grade, LinearLayout btn)
    {
        this.context = context;
        this.list = list;
        this.grade = grade;
        this.btn = btn;
        this.btn.setOnClickListener(this);
    }

    @Override
    public int getItemViewType(int position) {
        if (Config.fragment.equals("grades")) {
            // If the current user is the sender of the message
            return Config.VIEW_TYPE_GRADES;
        } else
        if(Config.fragment.equals("subjects"))
        {
            // If some other user sent the message
            return Config.VIEW_TYPE_SUBJECTS;
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        // set the view's size, margins, paddings and layout parameters

        if (viewType == Config.VIEW_TYPE_GRADES) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_grade_laayout, parent, false);
            return new GradesHolder(view);
        }
        else
        if (viewType == Config.VIEW_TYPE_SUBJECTS)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.single_subject_layout, parent, false);
            return new SubjectsHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        layoutManager = new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false);
        ((GradesHolder)holder).subjects.setHasFixedSize(true);
        ((GradesHolder)holder).subjects.setLayoutManager(layoutManager);
        switch (holder.getItemViewType()) {
            case Config.VIEW_TYPE_GRADES:
                bind(((GradesHolder)holder).grade_name,((GradesHolder)holder).itemView,position);
            break;
        }
        ((GradesHolder)holder).grade_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<SubjectAndGrade> subjectList = getList();
                if(((GradesHolder)holder).all_subjects.getVisibility()==View.GONE && subjectList.size()==1)
                {
                    ((GradesHolder)holder).all_subjects.setVisibility(View.VISIBLE);
                     subjectAdapter = new SubjectAdapter(context,subjectList,((GradesHolder)holder).subjects,((GradesHolder)holder).edit_subject_name,((GradesHolder)holder).add_subject);
                    ((GradesHolder)holder).subjects.setAdapter(subjectAdapter);
                    ((GradesHolder)holder).grade_pointer.setRotation(270);
                }
                else
                    if(((GradesHolder)holder).grade_pointer.getRotation()==90)
                    {
                        ((GradesHolder)holder).edit_text_subject.setVisibility(View.VISIBLE);
                        ((GradesHolder)holder).add_subject.setVisibility(View.VISIBLE);
                        ((GradesHolder)holder).grade_pointer.setRotation(270);
                    }
                    else
                    {
                        ((GradesHolder)holder).edit_text_subject.setVisibility(View.GONE);
                        ((GradesHolder)holder).add_subject.setVisibility(View.GONE);
                        ((GradesHolder)holder).grade_pointer.setRotation(90);
                    }

            }
        });
        ((GradesHolder)holder).grade_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void bind(TextView grade, View view,int position)
    {
        grade.setText(list.get(position).name);

    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.add_grade:
                list.add(new SubjectAndGrade("Grade "+grade.getText().toString()));
                grade.setText("");
                notifyDataSetChanged();
            break;
        }
    }
    public List<SubjectAndGrade> getList()
    {
        List<SubjectAndGrade> list = new ArrayList<>();
        list.add(new SubjectAndGrade("English"));
        return  list;
    }
}
