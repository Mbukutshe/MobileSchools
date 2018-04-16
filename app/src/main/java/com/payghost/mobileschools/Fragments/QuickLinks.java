package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Adapters.MoreAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wiseman on 2018-03-19.
 */

public class QuickLinks extends Fragment implements View.OnClickListener{
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    MoreAdapter adapter;
    View view;
    FragmentManager fragmentManager;
    RequestQueue requestQueue;
    ProgressDialog myProgressDialog;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    LinearLayout empty;
    TextView icon,icon_message;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    FrameLayout try_again;
    LinearLayout no_internet;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=   inflater.inflate(R.layout.quick_links_fragment_layout, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);

        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try_again = (FrameLayout)view.findViewById(R.id.try_again);
        no_internet = (LinearLayout)view.findViewById(R.id.no_internet);
        try_again.setOnClickListener(this);

        empty = (LinearLayout)view.findViewById(R.id.empty_layout);
        icon = (TextView)view.findViewById(R.id.empty_icon);
        icon_message =(TextView)view.findViewById(R.id.empty_icon_message);


        Config.fragment = "quick";
        fetch();
        return  view;
    }
    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id )
        {
            case R.id.try_again:
                if(!isConnected)
                {
                    no_internet.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                }
                else
                {
                    no_internet.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }
    public void fetch()
    {
        myProgressDialog = new ProgressDialog(view.getContext(),R.style.MyTheme);
        myProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);


        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_GET_ALL_GENERAL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        List<RetrieveService> arrList = new ArrayList<RetrieveService>();
                        if(!response.equalsIgnoreCase("nodata")) {
                            try {
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject jo = result.getJSONObject(i);
                                    String sender = jo.getString(Config.TAG_MESSAGE_SENDER);
                                    String title = jo.getString("subject");
                                    String  message = jo.getString("description");
                                    String  link = jo.getString("link");

                                    arrList.add(new RetrieveService(title,message,"",link,sender));

                                }
                                adapter = new MoreAdapter(getActivity().getApplicationContext(),arrList);
                                recyclerView.setAdapter(adapter);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                            myProgressDialog.dismiss();
                            recyclerView.setAdapter(adapter);
                            recyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }
                        else
                        {
                            myProgressDialog.dismiss();
                            recyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            //  icon.setBackgroundResource(R.drawable.novideos);
                            icon_message.setText("Nothing To Show");
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("school",pref.getString("school",""));
                parameters.put("grade",pref.getString("grade",""));
                parameters.put("subject","Khanyisa");
                parameters.put("target",pref.getString("which_one",""));
                parameters.put("identifier","quick");
                parameters.put("link","www.google.com");
                parameters.put("action","read");
                parameters.put("description","wiseman");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
