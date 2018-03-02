package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Activities.SchoolRegistration;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-24.
 */

public class Splash extends Fragment implements View.OnClickListener{
    View view;
    AppCompatImageView mImgCheck,parent_tick,instructor_tick;
    AnimatedVectorDrawableCompat avd;
    LinearLayout learner,parent,instructor,proceed;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.splash_layout,container,false);
        mImgCheck = (AppCompatImageView)view.findViewById(R.id.learner_tick);
        parent_tick = (AppCompatImageView)view.findViewById(R.id.parent_tick);
        instructor_tick = (AppCompatImageView)view.findViewById(R.id.instructor_tick);
        proceed = (LinearLayout)view.findViewById(R.id.proceed);
        avd = AnimatedVectorDrawableCompat.create(view.getContext(),R.drawable.animated_check);
        // drawable = mImgCheck.getDrawable();
        // mImgCheck.setImageDrawable(avd);
        learner = (LinearLayout)view.findViewById(R.id.learner);
        parent = (LinearLayout)view.findViewById(R.id.parent);
        instructor = (LinearLayout)view.findViewById(R.id.instructor);
        learner.setOnClickListener(this);
        parent.setOnClickListener(this);
        instructor.setOnClickListener(this);
        proceed.setOnClickListener(this);
        fragmentManager = getFragmentManager();
        return view;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId())
        {
            case R.id.learner:
                Config.which_one="learner";
                instructor_tick.setVisibility(View.GONE);
                parent_tick.setVisibility(View.GONE);
                mImgCheck.setVisibility(View.VISIBLE);
                ((Animatable) mImgCheck.getDrawable()).start();
                break;
            case R.id.parent:
                Config.which_one ="parent";
                instructor_tick.setVisibility(View.GONE);
                mImgCheck.setVisibility(View.GONE);
                parent_tick.setVisibility(View.VISIBLE);
                ((Animatable) parent_tick.getDrawable()).start();
                break;
            case R.id.instructor:
                Config.which_one ="instructor";
                mImgCheck.setVisibility(View.GONE);
                parent_tick.setVisibility(View.GONE);
                instructor_tick.setVisibility(View.VISIBLE);
                ((Animatable) instructor_tick.getDrawable()).start();
                break;
            case R.id.proceed:
                switch ( Config.which_one)
                {
                    case "learner":
                        fragmentManager.beginTransaction().replace(R.id.information_fragment,new LearnerRagistration()).commit();
                        break;
                    case "parent":
                        fragmentManager.beginTransaction().replace(R.id.information_fragment,new LearnerRagistration()).commit();
                        break;
                    case "instructor":
                        startActivity(new Intent(view.getContext(), SchoolRegistration.class));
                        getActivity().finish();
                        break;
                }
                break;
        }
    }
}
