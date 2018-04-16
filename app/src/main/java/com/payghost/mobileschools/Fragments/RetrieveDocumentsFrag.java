package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.payghost.mobileschools.Adapters.DocumentTabAdapter;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

public class RetrieveDocumentsFrag extends Fragment{

    String message,time,title,subject,link;
    RequestQueue requestQueue;
    LinearLayoutManager linearlayout;
    ProgressDialog myProgressDialog;
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    DocumentTabAdapter adapter;
    List<RetrieveService> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_all_documents, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        fragmentManager = getFragmentManager();
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_document_tabs);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        list = getTabs();
        if(pref.getString("which_one","").equalsIgnoreCase("learner"))
        {
            ((LinearLayout)view.findViewById(R.id.core_groups)).setVisibility(View.VISIBLE);
            adapter = new DocumentTabAdapter(getActivity().getApplicationContext(),list,fragmentManager,recyclerView,linearlayout);
            recyclerView.setAdapter(adapter);
        }
        fragmentManager.beginTransaction().replace(R.id.content_documents,new CurrentYear()).commit();
        return view;
    }


    public List<RetrieveService> getTabs()
    {
        List<RetrieveService> list = new ArrayList<>();
        list.add(new RetrieveService("Homework"));
        list.add(new RetrieveService("Assignments"));
        list.add(new RetrieveService("Past Year Resources"));
        list.add(new RetrieveService("Other"));
        return list;
    }

}
