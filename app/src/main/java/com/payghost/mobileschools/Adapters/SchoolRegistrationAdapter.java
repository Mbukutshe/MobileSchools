package com.payghost.mobileschools.Adapters;

import android.app.Dialog;
import android.content.Context;
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

import com.payghost.mobileschools.Database.SqliteController;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.GradesHolder;
import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.payghost.mobileschools.Globals.Config.grade_name;
import static com.payghost.mobileschools.Globals.Config.modify;
import static com.payghost.mobileschools.Globals.Config.subjectsArray;
import static com.payghost.mobileschools.Globals.Config.grade_position;
import static com.payghost.mobileschools.Globals.Config.grades;
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
    TextView grade_validator;
    RecyclerView recyclerView;
    int j=1;
    SqliteController controller;
    public SchoolRegistrationAdapter(Context context, RecyclerView recyclerView, List<SubjectAndGrade> list, EditText grade, LinearLayout btn,TextView grade_validator)
    {
        this.context = context;
        this.list = list;
        this.grade = grade;
        this.btn = btn;
        this.grade_validator = grade_validator;
        this.recyclerView = recyclerView;
        this.btn.setOnClickListener(this);
        controller = new SqliteController(this.context);
    }
    @Override
    public int getItemViewType(int position) {
        if (Config.fragment.equals("grades"))
        {
            // If the current user is the sender of the message
            return Config.VIEW_TYPE_GRADES;
        }
        else
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

        switch (holder.getItemViewType())
        {
            case Config.VIEW_TYPE_GRADES:
                bind(((GradesHolder)holder).grade_name,((GradesHolder)holder).itemView,position);
                if(modify.equalsIgnoreCase("update"))
                {
                    ((GradesHolder)holder).grade_id.setText(list.get(position).gradeId);
                }
                if(Config.STEP==2)
                {
                    ((GradesHolder)holder).grade_close.setVisibility(View.GONE);
                    ((GradesHolder)holder).grade_pointer.setVisibility(View.VISIBLE);
                }
                else
                {
                    ((GradesHolder)holder).grade_close.setVisibility(View.VISIBLE);
                    ((GradesHolder)holder).grade_pointer.setVisibility(View.GONE);
                }
            break;
        }
        ((GradesHolder)holder).edit_subject_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                grade_name = list.get(position).name;
                grade_position = position;
            }
        });

        ((GradesHolder)holder).grade_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(Config.STEP ==2)
                {

                    grade_name = list.get(position).name;
                    grade_position = position;
                    if(((GradesHolder)holder).all_subjects.getVisibility()==View.GONE)
                    {
                        getList(grade_name);
                        ((GradesHolder)holder).all_subjects.setVisibility(View.VISIBLE);
                         subjectAdapter = new SubjectAdapter(context,subjectsArray,((GradesHolder)holder).subjects,((GradesHolder)holder).edit_subject_name,((GradesHolder)holder).add_subject);
                        ((GradesHolder)holder).subjects.setAdapter(subjectAdapter);
                        ((GradesHolder)holder).grade_pointer.setRotation(270);
                        ((GradesHolder)holder).edit_text_subject.setVisibility(View.VISIBLE);
                        ((GradesHolder)holder).add_subject.setVisibility(View.VISIBLE);
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
                else
                {
                    final Dialog dialog = new Dialog(view.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.rename_layout);
                    final EditText question = (EditText) dialog.findViewById(R.id.subject_message);
                    FrameLayout accept = (FrameLayout)dialog.findViewById(R.id.accept);
                    accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ((GradesHolder)holder).grade_name.setText(question.getText().toString());
                            list.get(position).name = question.getText().toString();
                            dialog.dismiss();

                        }
                    });
                    FrameLayout deny = (FrameLayout)dialog.findViewById(R.id.deny);
                    deny.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    question.setText(list.get(position).name);
                    dialog.show();
                }
            }
        });
        ((GradesHolder)holder).grade_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(modify.equalsIgnoreCase("update"))
                {
                    controller.deleteGrade(list.get(position).gradeId);
                }
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());

            }
        });
    }
    @Override
    public int getItemCount() {
        int size = list.size();
        grades = new ArrayList<>();
        for(int i=0;i<size;i++)
        {
            grades.add(i,new DeleteOptions(list.get(i).name));
        }

        return size;
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
                                recyclerView.scrollToPosition(list.size()-1);
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
                            recyclerView.scrollToPosition(list.size()-1);
                            notifyDataSetChanged();
                            grade.setText("");
                        }
            break;
        }

    }
    public void getList(String grade)
    {
        subjectsArray = new ArrayList<>();
        if(Config.SchoolProfile.equalsIgnoreCase(""))
        {
            subjectsArray .add(new SubjectAndGrade("English"));
        }
        else
        {
            JSONObject jsonObject = null;
            try
            {
                jsonObject = new JSONObject(Config.SchoolProfile);
                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                if (result.length() > 0)
                {
                    for (int i = 0; i < result.length(); i++)
                    {
                        JSONObject jo = result.getJSONObject(i);
                        if (jo.getString("GradeName").equalsIgnoreCase(grade))
                        {
                            subjectsArray.add(new SubjectAndGrade(jo.getString("GradeName"),jo.getString("GradeId"),jo.getString("SubjectId"),jo.getString("SubjectName")));
                        }
                    }
                }
            }
            catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

}
