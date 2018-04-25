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
import com.payghost.mobileschools.Adapters.DeleteAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trash extends Fragment implements View.OnClickListener{
    RecyclerView recyclerView;
    DeleteAdapter adapter;
    LinearLayoutManager layoutManager;
    List<DeleteOptions> list;
    FragmentManager fragmentManager;
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog progress;
    RequestQueue requestQueue;
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
        view = inflater.inflate(R.layout.delete_option_layout, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        requestQueue = Volley.newRequestQueue(view.getContext());
        recyclerView = (RecyclerView) view.findViewById(R.id.delete_recycler_view);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        getData();
        fragmentManager = getFragmentManager();
        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try_again = (FrameLayout)view.findViewById(R.id.try_again);
        no_internet = (LinearLayout)view.findViewById(R.id.no_internet);
        try_again.setOnClickListener(this);

        empty = (LinearLayout)view.findViewById(R.id.empty_layout);
        icon = (TextView)view.findViewById(R.id.empty_icon);
        icon_message =(TextView)view.findViewById(R.id.empty_icon_message);
        return view;
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
    public void getData()
    {
        list = new ArrayList<>();
        progress = new ProgressDialog(view.getContext(),R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST,Config.URL_GET_MESSAGES, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                progress.dismiss();
                JSONObject json = null;
                try
                {
                    json = new JSONObject(response);
                    JSONArray array = json.getJSONArray("result");
                    if(array.length()>0) {
                        if (Config.whichType.equalsIgnoreCase("delete_messages")) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                list.add(new DeleteOptions(object.getInt("id"), object.getString("sender"), object.getString("date"), object.getString("title"), object.getString("message")));
                            }
                        } else if (Config.whichType.equalsIgnoreCase("delete_documents")) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);

                                list.add(new DeleteOptions(object.getInt("id"), object.getString("sender"), object.getString("date"), object.getString("title"), object.getString("message"), object.getString("link")));
                            }
                        } else if (Config.whichType.equalsIgnoreCase("delete_videos") || Config.whichType.equalsIgnoreCase("delete_images")) {
                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                list.add(new DeleteOptions(object.getInt("id"), object.getString("uploader"), object.getString("time"), object.getString("title"), object.getString("message"), object.getString("url")));
                            }
                        }
                        adapter = new DeleteAdapter(view.getContext(), list, fragmentManager);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setVisibility(View.VISIBLE);
                        empty.setVisibility(View.GONE);
                    }
                    else
                    {
                        recyclerView.setVisibility(View.GONE);
                        empty.setVisibility(View.VISIBLE);
                        icon_message.setText("Nothing to Show!");
                    }
                }
                catch (JSONException ex)
                {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("school",pref.getString("school",""));
                parameters.put("user",pref.getString("uploader",""));
                parameters.put("resource", Config.whichType);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(0,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
