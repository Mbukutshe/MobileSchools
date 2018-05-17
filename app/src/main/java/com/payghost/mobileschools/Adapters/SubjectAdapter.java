package com.payghost.mobileschools.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Database.SqliteController;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.SubjAndGrades;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

import static com.payghost.mobileschools.Globals.Config.grade_name;
import static com.payghost.mobileschools.Globals.Config.modify;
import static com.payghost.mobileschools.Globals.Config.subjectsArray;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectsHolder> implements View.OnClickListener{
    List<SubjectAndGrade>list;
    Context context;
    EditText subject;
    LinearLayout btn;
    DivisionAdapter adapter;
    RecyclerView recyclerView;
    SqliteController controller;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String id="";
    public SubjectAdapter(Context context, List<SubjectAndGrade>list, RecyclerView recyclerView, EditText subject, LinearLayout btn)
    {
       this.context = context;
       this.recyclerView = recyclerView;
       this.list = list;
       this.subject = subject;
       this.btn = btn;
       this.btn.setOnClickListener(this);
       controller = new SqliteController(this.context);
        pref =this.context.getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
    }
    @Override
    public SubjectsHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_subject_layout, parent, false);
        SubjectsHolder vh = new SubjectsHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final SubjectsHolder holder,final int position)
    {

        if(modify.equalsIgnoreCase("update"))
        {
            holder.subject_name.setText(list.get(position).subjectName);
            holder.grade_id.setText(list.get(position).gradeId);
            holder.subject_id.setText(list.get(position).subjectId);
            id = list.get(position).gradeId;
        }
        else
        {
            holder.subject_name.setText(list.get(position).name);
        }
        holder.subject_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.rename_layout);
                final EditText question = (EditText) dialog.findViewById(R.id.subject_message);
                FrameLayout accept = (FrameLayout)dialog.findViewById(R.id.accept);
                accept.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        holder.subject_name.setText(question.getText().toString());
                        list.get(position).subjectName=question.getText().toString();
                        if(modify.equalsIgnoreCase("update"))
                        {
                            controller.updateSubject(list.get(position).gradeId,list.get(position).subjectId,question.getText().toString());
                        }
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

                question.setText(list.get(position).subjectName);
                dialog.show();
            }
        });
        holder.delete_subject.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                if(modify.equalsIgnoreCase("update"))
                {
                    controller.deleteSubject(list.get(position).subjectId,list.get(position).subjectName,"DELETE");
                }
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
                notifyDataSetChanged();
            }
        });
    }
    @Override
    public int getItemCount()
    {
        Config.size=subjectsArray.size();
        JSONObject obj = new JSONObject();
        //Collection<JSONObject> items = new ArrayList<JSONObject>();
        JSONArray json = new JSONArray();
        JSONArray array = new JSONArray();
        for(int i=0; i<Config.size; i++)
        {
            try
            {
                json.put(list.get(i).name);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        try
        {
            //items.add(json);
            if(Config.grade_name.equalsIgnoreCase("Grade R"))
            {
                obj.put("Grade R",json);
                SubjAndGrades.Grader = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 1"))
            {
                obj.put("Grade 1",json);
                SubjAndGrades.Grade1 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 2"))
            {
                obj.put("Grade 2",json);
                SubjAndGrades.Grade2 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 3"))
            {
                obj.put("Grade 3",json);
                SubjAndGrades.Grade3 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 4"))
            {
                obj.put("Grade 4",json);
                SubjAndGrades.Grade4 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 5"))
            {
                obj.put("Grade 5",json);
                SubjAndGrades.Grade5 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 6"))
            {
                obj.put("Grade 6",json);
                SubjAndGrades.Grade6 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 7"))
            {
                obj.put("Grade 7",json);
                SubjAndGrades.Grade7 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 8"))
            {
                obj.put("Grade 8",json);
                SubjAndGrades.Grade8 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 9"))
            {
                obj.put("Grade 9",json);
                SubjAndGrades.Grade9 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 10"))
            {
                obj.put("Grade 10",json);
                SubjAndGrades.Grade10 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 11"))
            {
                obj.put("Grade 11",json);
                SubjAndGrades.Grade11 = obj.toString();
            }
            else
            if(Config.grade_name.equalsIgnoreCase("Grade 12"))
            {
                obj.put("Grade 12",json);
                SubjAndGrades.Grade12 = obj.toString();
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return list.size();
    }
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.add_subject:

                if(modify.equalsIgnoreCase("update"))
                {
                    list.add(new SubjectAndGrade(subject.getText().toString(),id));
                    notifyDataSetChanged();
                    if (subjectsArray.get(0).gradeId.equalsIgnoreCase(""))
                    {
                        controller.newSubject(pref.getString("school", ""), "00", grade_name, subject.getText().toString(), "nothing", "INSERT");
                    }
                    else
                    {
                        controller.newSubject(pref.getString("school", ""), list.get(0).gradeId, grade_name, subject.getText().toString(), "INSERT", "nothing");
                    }
                }
                else
                {
                    list.add(new SubjectAndGrade(subject.getText().toString()));
                    notifyDataSetChanged();
                }
                subject.setText("");
            break;
        }
    }
}
