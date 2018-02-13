package com.payghost.mobileschools.Activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.payghost.mobileschools.Adapters.RecyclerviewAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsList extends AppCompatActivity {
    private String JSON_STRING;
    String message,time,title,subject,link;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shools_list_layout);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(getApplicationContext().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "schools";
    }
}
