package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payghost.mobileschools.Activities.SchoolsAndGrades;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-24.
 */

public class LearnerRagistration extends Fragment implements View.OnClickListener{
    View view;
    LinearLayout layout_male,layout_female,register_button;
    EditText first_name,surname,birthday,email;
    AppCompatCheckBox male,female;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    TextView btn_login;
    String gender="male";
    Animation anim;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.register_layout,container,false);

        first_name = (EditText)view.findViewById(R.id.first_name);
        surname = (EditText)view.findViewById(R.id.surname);
        birthday = (EditText)view.findViewById(R.id.birthday);
        email = (EditText)view.findViewById(R.id.email);

        layout_male = (LinearLayout)view.findViewById(R.id.layout_male);
        layout_female = (LinearLayout)view.findViewById(R.id.layout_female);

        male = (AppCompatCheckBox)view.findViewById(R.id.male);
        female = (AppCompatCheckBox)view.findViewById(R.id.female);

        btn_login = (TextView)view.findViewById(R.id.btn_login);

        register_button = (LinearLayout)view.findViewById(R.id.register_button);
        register_button.setOnClickListener(this);

        layout_female.setOnClickListener(this);
        female.setOnClickListener(this);

        layout_male.setOnClickListener(this);
        male.setOnClickListener(this);
        if(Config.which_one.equalsIgnoreCase("learner"))
        {
            btn_login.setText("Proceed to choose School,Grade &amp; Subjects");
        }
        else
        {
            btn_login.setText("Proceed to choose School");
        }
        anim = new Animation(view.getContext());
        anim.fromSides(((LinearLayout)view.findViewById(R.id.upload_vehicles)));
        return view;
    }

    @Override
    public void onClick(final View view) {
        switch(view.getId())
        {
            case R.id.register_button:
                anim.setAlphaAnimation(register_button);
                if(!(first_name.getText().toString().isEmpty()||surname.getText().toString().isEmpty()||birthday.getText().toString().isEmpty()))
                {
                    Config.TAG_FIRST_NAME = first_name.getText().toString();
                    Config.TAG_SURNAME = surname.getText().toString();
                    Config.TAG_DOB = birthday.getText().toString();
                    Config.TAG_EMAIL = email.getText().toString();
                    Config.TAG_TYPE = Config.which_one;
                    Config.TAG_GENDER = gender;
                    startActivity(new Intent(view.getContext(),SchoolsAndGrades.class));
                    getActivity().finish();
                }
                else
                {
                    Toast.makeText(view.getContext(),"All fields are required!",Toast.LENGTH_LONG).show();
                }

            break;
            case R.id.layout_female:
                female.setChecked(true);
                male.setChecked(false);
                gender = "female";
            break;
            case R.id.female:
                female.setChecked(true);
                male.setChecked(false);
                gender = "female";
                break;
            case R.id.layout_male:
                male.setChecked(true);
                female.setChecked(false);
                gender = "male";
            break;
            case R.id.male:
                male.setChecked(true);
                female.setChecked(false);
                gender = "male";
            break;
        }
    }
}
