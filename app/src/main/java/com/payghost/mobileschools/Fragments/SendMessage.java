package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class SendMessage extends Fragment implements View.OnClickListener{
    View view;
    Animation anim;
    RequestQueue requestQueue;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    RelativeLayout message_layout;
    LinearLayout all,staff,parents,learner,send_button,learner_grade_layout,instructor_grade_layout;
    EditText subject_message,text_message;
    AppCompatCheckBox all_check,staff_check,parents_check,learner_check;
    AppCompatSpinner staff_grade,learner_grade,learner_subject;
    String all_,staff_="",parents_="",learner_="",staf_grade="All",learners_grade="All";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    List<String> spinnerArray;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_message, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        message_layout = (RelativeLayout)view.findViewById(R.id.message);
        anim = new Animation(view.getContext());
        anim.fromSides(message_layout);

        learner_grade_layout = (LinearLayout)view.findViewById(R.id.learner_grade_layout);
        instructor_grade_layout = (LinearLayout)view.findViewById(R.id.instructor_grade_layout);

        staff = (LinearLayout)view.findViewById(R.id.staff);
        staff.setOnClickListener(this);
        parents = (LinearLayout)view.findViewById(R.id.parents);
        parents.setOnClickListener(this);
        learner = (LinearLayout)view.findViewById(R.id.learner);
        learner.setOnClickListener(this);
        send_button = (LinearLayout)view.findViewById(R.id.send_button);
        send_button.setOnClickListener(this);

        staff_check = (AppCompatCheckBox)view.findViewById(R.id.staff_check);
        staff_check.setOnClickListener(this);
        parents_check = (AppCompatCheckBox)view.findViewById(R.id.parents_check);
        parents_check.setOnClickListener(this);
        learner_check = (AppCompatCheckBox)view.findViewById(R.id.learner_check);
        learner_check.setOnClickListener(this);

        subject_message = (EditText)view.findViewById(R.id.subject_message);
        text_message = (EditText)view.findViewById(R.id.text_message);
        staff_grade = (AppCompatSpinner)view.findViewById(R.id.staff_grade);
        learner_grade = (AppCompatSpinner)view.findViewById(R.id.learner_grade);
        learner_subject = (AppCompatSpinner)view.findViewById(R.id.learner_subject);
        spinnerArray =  new ArrayList<String>();
        spinnerArray.add("All");
        for(int i=0; i<pref.getInt("grade_size",0);i++)
        {
            spinnerArray.add(pref.getString("grade:"+i,""));
        }

        //INSTRUCTORS DROPDOWN GRADES

        ArrayAdapter<String> adaptor =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staff_grade.setAdapter(adaptor);
        staff_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                staf_grade = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                staf_grade = adapterView.getItemAtPosition(0).toString();
            }
        });
        //LEARNERS DROPDOWN GRADES

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        learner_grade.setAdapter(adapter);
        learner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learners_grade = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                learners_grade = adapterView.getItemAtPosition(0).toString();
            }
        });

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.send_button:
                if(staff_check.isChecked()||parents_check.isChecked()||learner_check.isChecked())
                {
                    Register(view.getContext());
                }
                else
                {
                    Toast.makeText(view.getContext(),"Select the receiver please!",Toast.LENGTH_LONG).show();
                }
            break;
            case R.id.staff:
                if(staff_check.isChecked())
                {
                    staff_check.setChecked(false);
                    instructor_grade_layout.setVisibility(View.GONE);
                    staff_ = "";
                }
                else
                {
                    staff_check.setChecked(true);
                    instructor_grade_layout.setVisibility(View.VISIBLE);
                    staff_ = "staff";
                }
                break;
            case R.id.staff_check:

                if(staff_check.isChecked())
                {
                    staff_ = "staff";
                    instructor_grade_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    instructor_grade_layout.setVisibility(View.GONE);
                    staff_ = "";
                }
                break;
            case R.id.parents:

                if(parents_check.isChecked())
                {
                    parents_ = "";
                    parents_check.setChecked(false);
                }
                else
                {
                    parents_ = "parents";
                    parents_check.setChecked(true);
                }
                break;
            case R.id.parents_check:
                if(parents_check.isChecked())
                {
                    parents_ = "parents";
                }
                else
                {
                    parents_ = "";
                }
                break;
            case R.id.learner:
                if(learner_check.isChecked())
                {
                    learner_ = "";
                    learner_grade_layout.setVisibility(View.GONE);
                    learner_check.setChecked(false);
                }
                else
                    {
                        learner_ = "learners";
                        learner_grade_layout.setVisibility(View.VISIBLE);
                        learner_check.setChecked(true);
                    }
                break;
            case R.id.learner_check:
                if(learner_check.isChecked())
                {
                    learner_ = "learners";
                    learner_grade_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    learner_grade_layout.setVisibility(View.GONE);
                    learner_ = "";
                }
                break;
        }
    }
    public void Register(final Context context)
    {
        progress = new ProgressDialog(context);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SEND_MESSAGE,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(view.getContext(),response,Toast.LENGTH_LONG).show();
                        progress.dismiss();
                        if(!response.equalsIgnoreCase("fail"))
                        {
                            myProgressDialog = new ProgressDialog(context);
                            myProgressDialog.show();
                            myProgressDialog.setContentView(R.layout.success_layout);
                            mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.success_image);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l)
                                {

                                }
                                @Override
                                public void onFinish(){
                                    mImgCheck.setVisibility(View.GONE);
                                    myProgressDialog.dismiss();
                                }
                            };
                            countDownTimer.start();
                        }
                        else
                        if(response.equalsIgnoreCase("fail"))
                        {
                            myProgressDialog = new ProgressDialog(context);
                            myProgressDialog.show();
                            myProgressDialog.setContentView(R.layout.error_layout);
                            mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.error_image);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l)
                                {

                                }
                                @Override
                                public void onFinish(){
                                    mImgCheck.setVisibility(View.GONE);
                                    myProgressDialog.dismiss();
                                }
                            };
                            countDownTimer.start();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(view.getContext(),error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("subject",subject_message.getText().toString());
                parameters.put("message",text_message.getText().toString());
                parameters.put("staff",staff_);
                parameters.put("learner",learner_);
                parameters.put("parent",parents_);
                parameters.put("learner_grade",learners_grade);
                parameters.put("staff_grade",staf_grade);
                parameters.put("school",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
