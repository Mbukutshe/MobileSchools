package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class RegisterStaff extends Fragment{
    View view;
    Animation anim;
    LinearLayout linearLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=   inflater.inflate(R.layout.staff_additionals, container, false);
        anim = new Animation(view.getContext());
        linearLayout = (LinearLayout)view.findViewById(R.id.register_staff);
        anim.fromSides(linearLayout);
        return view;
    }
}
