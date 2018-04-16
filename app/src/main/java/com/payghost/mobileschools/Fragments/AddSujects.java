package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Adapters.SchoolRegistrationAdapter;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wiseman on 2018-04-03.
 */

public class AddSujects extends Fragment implements View.OnTouchListener,View.OnClickListener
{
    View view;
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView,subjects_recyclerview;
    SchoolRegistrationAdapter recyclerviewAdapter;
    List<SubjectAndGrade> list;
    LinearLayout add_grades,add_grade,all_grades,next;
    EditText grade,school_name;
    LinearLayout logo_school,register_school_button;
    CircleImageView school_logo;
    Intent intent;
    Bitmap bitmap;
    TextView school_name_validator,grade_validator;
    TextView subject_name;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    AppCompatImageView mImgCheck;
    AnimatedVectorDrawableCompat avd;
    Drawable drawable;
    RequestQueue requestQueue;
    byte[] bytes;
    String encodeImage;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager;
    Animation animation;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.add_grades_layout, container, false);
        fragmentManager = getFragmentManager();
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        requestQueue = Volley.newRequestQueue(view.getContext());
        animation = new Animation(view.getContext());
        add_grades = (LinearLayout)view.findViewById(R.id.add_grades);
        add_grade = (LinearLayout)view.findViewById(R.id.add_grade);
        all_grades = (LinearLayout)view.findViewById(R.id.all_grades);
        grade = (EditText)view.findViewById(R.id.grade);
        ((LinearLayout)view.findViewById(R.id.bottom_add_grade)).setVisibility(View.VISIBLE);
        grade.setOnTouchListener(this);
        fragmentManager = getFragmentManager();
        subject_name = (TextView)view.findViewById(R.id.subject_name);
        grade_validator = (TextView)view.findViewById(R.id.grade_validator);
        next = (LinearLayout)view.findViewById(R.id.next);
        next.setOnClickListener(this);
        recyclerView = (RecyclerView)view.findViewById(R.id.grades_recycler_view);
        linearlayout = new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "grades";
        Config.STEP = 1;
        recyclerView = (RecyclerView)view.findViewById(R.id.grades_recycler_view);
        getList();
        recyclerviewAdapter = new SchoolRegistrationAdapter(view.getContext(),recyclerView, Config.subjectList,grade,add_grade,grade_validator);
        recyclerView.setAdapter(recyclerviewAdapter);
        animation.setFadeInAnimation(all_grades);
        return view;
    }
    public void getList()
    {
        if( Config.subjectList.size()==0)
        {
            Config.subjectList.add(new SubjectAndGrade("Grade R"));
        }

    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.next:
                fragmentManager.beginTransaction().replace(R.id.grade_steps,new NextGrades()).commit();
            break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (view.getId())
        {
            case R.id.grade:
                grade_validator.setVisibility(View.GONE);
                break;
        }
        return false;
    }
}
