package com.payghost.mobileschools.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.GradesHolder;
import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SchoolRegistrationAdapter extends RecyclerView.Adapter implements View.OnClickListener{
    Context context;
    List<SubjectAndGrade> list,subjectList;
    EditText grade,school_name;
    LinearLayout btn;
    LinearLayoutManager layoutManager;
    SubjectAdapter subjectAdapter;
    LinearLayout logo_school,register_button;
    CircleImageView school_logo;
    Bitmap bitmap;
    TextView name_validator,grade_validator;

    public SchoolRegistrationAdapter(Context context, List<SubjectAndGrade> list, EditText grade, LinearLayout btn,EditText school_name,LinearLayout register_button,TextView name_validator,TextView grade_validator)
    {
        this.context = context;
        this.list = list;
        this.grade = grade;
        this.btn = btn;
        this.school_name = school_name;
        this.register_button = register_button;
        this.name_validator = name_validator;
        this.grade_validator = grade_validator;
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
                if(grade.getText().toString().isEmpty())
                {
                    final Animation textAnim = new AlphaAnimation(0.0f, 1.0f);

                    textAnim.setDuration(50);
                    textAnim.setStartOffset(20);
                    textAnim.setRepeatMode(Animation.REVERSE);
                    textAnim.setRepeatCount(6);
                    grade_validator.setVisibility(View.VISIBLE);
                    grade_validator.startAnimation(textAnim);
                }
                else
                    if(Integer.parseInt(grade.getText().toString())>12 || Integer.parseInt(grade.getText().toString())<1)
                    {
                        final Dialog dialog = new Dialog(view.getContext());
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.pop_up_layout);
                        FrameLayout accept = (FrameLayout)dialog.findViewById(R.id.accept);
                        accept.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                                list.add(new SubjectAndGrade("Grade "+grade.getText().toString()));
                                grade.setText("");
                                notifyDataSetChanged();
                            }
                        });
                        FrameLayout deny = (FrameLayout)dialog.findViewById(R.id.deny);
                        deny.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                        TextView question = (TextView)dialog.findViewById(R.id.question);
                        question.setText("Really, Does your school have Grade :"+grade.getText().toString()+"?");
                        dialog.show();
                    }
                    else
                        {
                            list.add(new SubjectAndGrade("Grade "+grade.getText().toString()));
                            grade.setText("");
                            notifyDataSetChanged();
                        }
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
