package com.payghost.mobileschools.Activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.payghost.mobileschools.Adapters.AdminAdapter;
import com.payghost.mobileschools.Database.SqliteController;
import com.payghost.mobileschools.Objects.Dashboard;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

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
    TextView name,email,birth,gender;
    CircleImageView profile;
    FloatingActionButton fab;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.administrator_fragment_layout,container,false);

        name = (TextView)view.findViewById(R.id.name);
        email = (TextView)view.findViewById(R.id.email);
        profile = (CircleImageView)view.findViewById(R.id.profile_pic);
        birth = (TextView)view.findViewById(R.id.birthday);
        gender = (TextView)view.findViewById(R.id.gender);
        fab = (FloatingActionButton)view.findViewById(R.id.fab);
        userProfile();
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
        names.add(new Dashboard("Send Message",R.drawable.send));
        names.add(new Dashboard("Upload Document",R.drawable.successfile));
        names.add(new Dashboard("Media",R.drawable.videos));
        names.add(new Dashboard("Add Instructor",R.drawable.add));
        names.add(new Dashboard("Sent",R.drawable.sent));
        names.add(new Dashboard("School Profile",R.drawable.schoolprof));
        return names;
    }
    private List<Dashboard> getData()
    {
        List<Dashboard> names = new ArrayList<>();
        names.add(new Dashboard("Documents",R.drawable.successfile));
        names.add(new Dashboard("Media",R.drawable.videos));
        names.add(new Dashboard("Sent",R.drawable.sent));
        return names;
    }
    public void userProfile()
    {
        Cursor data = new SqliteController(getActivity().getApplicationContext()).getUser();
        if(data.moveToFirst())
        {
            do
            {
               name.setText(data.getString(1)+" "+data.getString(2)+" "+data.getString(3));
               email.setText(data.getString(6));
               if(!data.getString(7).isEmpty())
               {
                   Picasso.with(view.getContext()).load(data.getString(7)).into(profile);
               }
               birth.setText(data.getString(4));
               gender.setText(data.getString(5));
            }
            while(data.moveToNext());

        }
    }
}
