package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Adapters.RecyclerviewAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsList extends Fragment {
    private String JSON_STRING;
    String message,time,title,subject,link;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    List<RetrieveService> list;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
       view =inflater.inflate(R.layout.shools_list_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "schools";
        list = getList();
        recyclerviewAdapter = new RecyclerviewAdapter(view.getContext(),list,getFragmentManager());
        recyclerView.setAdapter(recyclerviewAdapter);
        return view;
    }
    private List<RetrieveService> getList()
    {
        List<RetrieveService> names = new ArrayList<>();
        names.add(new RetrieveService("Ntabankulu S.S.S"));
        return names;
    }
}
