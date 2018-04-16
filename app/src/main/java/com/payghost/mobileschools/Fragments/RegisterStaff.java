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

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class RegisterStaff extends Fragment implements View.OnClickListener{
    View view;
    Animation anim;
    RequestQueue requestQueue;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    LinearLayout linearLayout,layout_male,layout_female,layout_admin,layout_teacher,register_button;
    EditText first_name,surname,dob,email,username,password,retype_password;
    AppCompatCheckBox male,female,admin,teacher;
    String type="admin";
    String gender = "male";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    AppCompatSpinner staff_grade;
    String staf_grade="Mr";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=   inflater.inflate(R.layout.staff_additionals, container, false);
        pref = view.getContext().getSharedPreferences("Users",Context.MODE_PRIVATE);
        editor = pref.edit();
        anim = new Animation(view.getContext());
        linearLayout = (LinearLayout)view.findViewById(R.id.register_staff);
        anim.fromSides(linearLayout);
        layout_male = (LinearLayout)view.findViewById(R.id.layout_male);
        layout_male.setOnClickListener(this);
        layout_female = (LinearLayout)view.findViewById(R.id.layout_female);
        layout_female.setOnClickListener(this);
        layout_admin = (LinearLayout)view.findViewById(R.id.layout_admin);
        layout_admin.setOnClickListener(this);
        layout_teacher = (LinearLayout)view.findViewById(R.id.layout_teacher);
        layout_teacher.setOnClickListener(this);
        register_button = (LinearLayout)view.findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        male = (AppCompatCheckBox)view.findViewById(R.id.male);
        male.setOnClickListener(this);
        female = (AppCompatCheckBox)view.findViewById(R.id.female);
        female.setOnClickListener(this);
        admin =(AppCompatCheckBox)view.findViewById(R.id.admin);
        admin.setOnClickListener(this);
        teacher = (AppCompatCheckBox)view.findViewById(R.id.teacher);
        teacher.setOnClickListener(this);

        first_name = (EditText)view.findViewById(R.id.first_name);
        surname = (EditText)view.findViewById(R.id.surname);
        dob = (EditText)view.findViewById(R.id.dob);
        email = (EditText)view.findViewById(R.id.email);
        username = (EditText)view.findViewById(R.id.username);
        password = (EditText)view.findViewById(R.id.password);
        retype_password = (EditText)view.findViewById(R.id.retype_password);
        staff_grade = (AppCompatSpinner)view.findViewById(R.id.staff_grade);
        ArrayList<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("Mr.");
        spinnerArray.add("Mrs.");
        spinnerArray.add("Miss.");
        spinnerArray.add("Prof.");
        spinnerArray.add("Dr.");
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

        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.register_button:
                Register(view.getContext());
            break;
            case R.id.layout_male:
                gender ="male";
            break;
            case R.id.layout_female:
                gender ="female";
            break;
            case R.id.layout_admin:
                type="admin";
            break;
            case R.id.layout_teacher:
                type="instructor";
            break;
            case R.id.male:
                gender ="male";
                female.setChecked(false);
                male.setChecked(true);
            break;
            case R.id.female:
                gender ="female";
                male.setChecked(false);
                female.setChecked(true);
            break;
            case R.id.admin:
                type="admin";
                admin.setChecked(true);
                teacher.setChecked(false);
            break;
            case R.id.teacher:
                type="instructor";
                teacher.setChecked(true);
                admin.setChecked(false);
            break;
        }
    }
    public void Register(final Context context)
    {
        final String token = FirebaseInstanceId.getInstance().getToken();
        progress = new ProgressDialog(context,R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_STAFF_REGISTRATION,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        progress.dismiss();
                        if(response.equalsIgnoreCase("success"))
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
                        if(response.equalsIgnoreCase("error"))
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

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("title",staf_grade);
                parameters.put("name",first_name.getText().toString());
                parameters.put("surname",surname.getText().toString());
                parameters.put("dob",dob.getText().toString());
                parameters.put("gender",gender);
                parameters.put("school",pref.getString("school",""));
                parameters.put("email",email.getText().toString());
                parameters.put("username",username.getText().toString());
                parameters.put("password",password.getText().toString());
                parameters.put("type",type);
                parameters.put("device","");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
