package com.payghost.mobileschools.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.Fragment;

import com.payghost.mobileschools.R;

public class RetrieveDocumentsFrag extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
                View view =inflater.inflate(R.layout.fragment_retrieve_documents, container, false);
                return view;
    }


}
