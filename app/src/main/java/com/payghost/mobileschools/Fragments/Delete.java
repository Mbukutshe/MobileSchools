package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Adapters.DeleteAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-02-17.
 */

public class Delete extends Fragment {
    RecyclerView recyclerView;
    DeleteAdapter adapter;
    LinearLayoutManager layoutManager;
    List<DeleteOptions> list;
    FragmentManager fragmentManager;
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view =inflater.inflate(R.layout.delete_option_layout, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        recyclerView = (RecyclerView)view.findViewById(R.id.delete_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), layoutManager.getOrientation()));
        if(pref.getString("who_log_on","").equalsIgnoreCase("instructor"))
        {
            list = getItem();
        }
        else
        {
            list = getList();
        }
        Config.whichType="delete";
        fragmentManager = getFragmentManager();
        adapter =new DeleteAdapter(view.getContext(),list, fragmentManager );
        recyclerView.setAdapter(adapter);

        return  view;
    }
    public  List<DeleteOptions> getList()
    {
        List<DeleteOptions> list = new ArrayList<>();
        list.add(new DeleteOptions("Messages"));
        list.add(new DeleteOptions("Documents"));
        list.add(new DeleteOptions("Videos"));
        list.add(new DeleteOptions("Images"));
        return list;
    }
    public  List<DeleteOptions> getItem()
    {
        List<DeleteOptions> list = new ArrayList<>();
        list.add(new DeleteOptions("Documents"));
        list.add(new DeleteOptions("Videos"));
        list.add(new DeleteOptions("Images"));
        return list;
    }
}
