package com.payghost.mobileschools.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

public class RetrieveImagesFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view =inflater.inflate(R.layout.fragment_retrieve_images, container, false);
        Config.fragment = "images";
                return view;
    }

}
