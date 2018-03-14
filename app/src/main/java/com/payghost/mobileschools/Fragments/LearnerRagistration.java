package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.payghost.mobileschools.Activities.SchoolsAndGrades;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-24.
 */

public class LearnerRagistration extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout layout_male,layout_female,layout_learner,layout_parent,register_button;
    EditText first_name,surname,birthday;
    AppCompatCheckBox male,female,learner,parent;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_layout,container,false);

        first_name = (EditText)view.findViewById(R.id.first_name);
        surname = (EditText)view.findViewById(R.id.surname);
        birthday = (EditText)view.findViewById(R.id.birthday);

        layout_male = (LinearLayout)view.findViewById(R.id.layout_male);
        layout_female = (LinearLayout)view.findViewById(R.id.layout_female);
        layout_learner = (LinearLayout)view.findViewById(R.id.layout_learner);
        layout_parent = (LinearLayout)view.findViewById(R.id.layout_parent);

        male = (AppCompatCheckBox)view.findViewById(R.id.male);
        female = (AppCompatCheckBox)view.findViewById(R.id.female);
        learner = (AppCompatCheckBox)view.findViewById(R.id.learner);
        parent = (AppCompatCheckBox)view.findViewById(R.id.parent);

        register_button = (LinearLayout)view.findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        layout_female.setOnClickListener(this);
        female.setOnClickListener(this);

        layout_male.setOnClickListener(this);
        male.setOnClickListener(this);

        layout_parent.setOnClickListener(this);
        parent.setOnClickListener(this);

        layout_learner.setOnClickListener(this);
        learner.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(final View view) {
        switch(view.getId())
        {
            case R.id.register_button:
                Config.TAG_FIRST_NAME = first_name.getText().toString();
                Config.TAG_SURNAME = surname.getText().toString();
                Config.TAG_DOB = birthday.getText().toString();
                progress = new ProgressDialog(view.getContext());
                progress.show();
                progress.setContentView(R.layout.progress);
                ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
                progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

                progress.dismiss();
                myProgressDialog = new ProgressDialog(view.getContext());
                myProgressDialog.show();
                myProgressDialog.setContentView(R.layout.success_layout);
                mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.success_image);
                ((Animatable) mImgCheck.getDrawable()).start();
                countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish(){
                        startActivity(new Intent(view.getContext(),SchoolsAndGrades.class));
                        mImgCheck.setVisibility(View.GONE);
                        myProgressDialog.dismiss();
                        getActivity().finish();
                    }
                };
                countDownTimer.start();

            break;
            case R.id.layout_female:
                female.setChecked(true);
                male.setChecked(false);
            break;
            case R.id.female:
                female.setChecked(true);
                male.setChecked(false);
                break;
            case R.id.layout_male:
                male.setChecked(true);
                female.setChecked(false);
            break;
            case R.id.male:
                male.setChecked(true);
                female.setChecked(false);
            break;
            case R.id.layout_learner:
                learner.setChecked(true);
                parent.setChecked(false);
            break;
            case R.id.learner:
                learner.setChecked(true);
                parent.setChecked(false);
            break;
            case R.id.layout_parent:
                parent.setChecked(true);
                learner.setChecked(false);
            break;
            case R.id.parent:
                parent.setChecked(true);
                learner.setChecked(false);
            break;
        }
    }
}
