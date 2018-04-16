package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Adapters.MoreTabAdapter;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

public class MoreFrag extends Fragment{
    LinearLayout general_layout,quick_layout,policy_layout;
    AppCompatCheckBox general_check,quick_check,policy_check;
    View view;
    LinearLayoutManager linearlayout;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    MoreTabAdapter adapter;
    List<RetrieveService> list;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

         view=   inflater.inflate(R.layout.fragment_more, container, false);
        fragmentManager = getFragmentManager();

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_document_tabs);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        list = getTabs();
        ((LinearLayout)view.findViewById(R.id.core_groups)).setVisibility(View.VISIBLE);
        adapter = new MoreTabAdapter(getActivity().getApplicationContext(),list,fragmentManager,recyclerView);
        recyclerView.setAdapter(adapter);
        fragmentManager.beginTransaction().replace(R.id.content_more,new General()).commit();

         return  view;
    }
    public List<RetrieveService> getTabs()
    {
        List<RetrieveService> list = new ArrayList<>();
        list.add(new RetrieveService("General"));
        list.add(new RetrieveService("Quick Links"));
        list.add(new RetrieveService("Policy"));
        list.add(new RetrieveService("Other"));
        return list;
    }



}
