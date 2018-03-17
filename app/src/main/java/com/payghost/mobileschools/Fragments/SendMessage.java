package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
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
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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

import java.util.HashMap;
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
    LinearLayout all,staff,parents,learner,send_button;
    EditText subject_message,text_message;
    AppCompatCheckBox all_check,staff_check,parents_check,learner_check;
    AppCompatSpinner staff_grade,learner_grade,learner_subject;
    String all_,staff_,parents_,learner_="false";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_message, container, false);
        message_layout = (RelativeLayout)view.findViewById(R.id.message);
        anim = new Animation(view.getContext());
        anim.fromSides(message_layout);

        staff = (LinearLayout)view.findViewById(R.id.staff);
        parents = (LinearLayout)view.findViewById(R.id.parents);
        learner = (LinearLayout)view.findViewById(R.id.learner);
        send_button = (LinearLayout)view.findViewById(R.id.send_button);
        send_button.setOnClickListener(this);

        staff_check = (AppCompatCheckBox)view.findViewById(R.id.staff_check);
        parents_check = (AppCompatCheckBox)view.findViewById(R.id.parents_check);
        learner_check = (AppCompatCheckBox)view.findViewById(R.id.learner_check);

        subject_message = (EditText)view.findViewById(R.id.subject_message);
        text_message = (EditText)view.findViewById(R.id.text_message);

        return view;
    }
    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.send_button:
                Register(view.getContext());
            break;
            case R.id.staff:
               staff_ = "true";
                break;
            case R.id.staff_check:
                staff_ = "true";
                break;
            case R.id.parents:
                parents_ = "true";
                break;
            case R.id.parents_check:
                parents_ = "true";
                break;
            case R.id.learner:
                learner_ = "true";
                break;
            case R.id.learner_check:
                learner_ = "true";
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

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("subject",subject_message.getText().toString());
                parameters.put("message",text_message.getText().toString());
                parameters.put("receiver",subject_message.getText().toString());
                parameters.put("school",Config.school_id);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
}
