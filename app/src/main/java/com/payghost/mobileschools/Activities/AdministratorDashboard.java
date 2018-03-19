package com.payghost.mobileschools.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Adapters.AdminAdapter;
import com.payghost.mobileschools.Objects.Dashboard;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-01-29.
 */

public class AdministratorDashboard extends Fragment {
    View view;
    RecyclerView mRecyclerView;
    LinearLayoutManager mLayoutManager;
    List<Dashboard>names;
    FragmentManager fragmentManager;
    SharedPreferences pref;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.administrator_fragment_layout,container,false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        mRecyclerView = (RecyclerView)view.findViewById(R.id.admin_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        if(pref.getString("who_log_on","").equalsIgnoreCase("instructor"))
        {
            names = getData();
        }
        else
        {
            names = getAdmin();
        }

        fragmentManager = getFragmentManager();
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        AdminAdapter adapter = new AdminAdapter(view.getContext(),names,mRecyclerView,mLayoutManager,fragmentManager);
        mRecyclerView.setAdapter(adapter);
        return view;
    }
    private List<Dashboard> getAdmin()
    {
        List<Dashboard> names = new ArrayList<>();
        names.add(new Dashboard("Send Messages",R.drawable.send));
        names.add(new Dashboard("Documents",R.drawable.successfile));
        names.add(new Dashboard("Media",R.drawable.videos));
        names.add(new Dashboard("Register",R.drawable.add));
        names.add(new Dashboard("Edit or Delete",R.drawable.edit));
        return names;
    }
    private List<Dashboard> getData()
    {
        List<Dashboard> names = new ArrayList<>();
        names.add(new Dashboard("Documents",R.drawable.successfile));
        names.add(new Dashboard("Media",R.drawable.videos));
        names.add(new Dashboard("Edit or Delete",R.drawable.edit));
        return names;
    }

}
