package com.payghost.mobileschools.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Adapters.SchoolRegistrationAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SchoolRegistration extends AppCompatActivity {
    private String JSON_STRING;
    String message,time,title,subject,link;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView,subjects_recyclerview;
    SchoolRegistrationAdapter recyclerviewAdapter;
    List<SubjectAndGrade> list;
    LinearLayout add_grades,add_grade,all_grades;
    EditText grade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_school_layout);
        add_grades = (LinearLayout)findViewById(R.id.add_grades);
        add_grade = (LinearLayout)findViewById(R.id.add_grade);
        all_grades = (LinearLayout)findViewById(R.id.all_grades);
        grade = (EditText)findViewById(R.id.grade);
        subjects_recyclerview = (RecyclerView)findViewById(R.id.subjects_recycler_view);
        recyclerView = (RecyclerView)findViewById(R.id.grades_recycler_view);
        linearlayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "grades";
        list = getList();
        add_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                all_grades.setVisibility(View.VISIBLE);
                recyclerviewAdapter = new SchoolRegistrationAdapter(getApplicationContext(),list,grade,add_grade);
                recyclerView.setAdapter(recyclerviewAdapter);
            }
        });
    }
    public List<SubjectAndGrade> getList()
    {
        List<SubjectAndGrade> list = new ArrayList<>();
        list.add(new SubjectAndGrade("Grade R"));
        return  list;
    }

}
