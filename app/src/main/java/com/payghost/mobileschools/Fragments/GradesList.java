package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Adapters.RecyclerviewAdapter;
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
 * Created by Wiseman on 2018-02-25.
 */

public class GradesList extends Fragment {
    View view;
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    ProgressDialog progress;
    RecyclerviewAdapter recyclerviewAdapter;
    RequestQueue requestQueue;
    String id,grade;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.grade_layout,container,false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "grades";
        getList();
        return view;
    }
    public void getList()
    {
        progress = new ProgressDialog(view.getContext(),R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        final List<RetrieveService> names = new ArrayList<>();
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_GET_ALL_GRADES,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                            for(int i = 0; i<result.length(); i++){
                                JSONObject jo = result.getJSONObject(i);

                                id = jo.getString(Config.TAG_GRADE_ID);
                                grade = jo.getString(Config.TAG_GRADE_NAME);
                                names.add(new RetrieveService(id,grade));

                            }
                            recyclerviewAdapter = new RecyclerviewAdapter(getActivity().getApplicationContext(),names,getFragmentManager());
                            recyclerView.setAdapter(recyclerviewAdapter);
                            progress.dismiss();
                        } catch (JSONException e)
                        {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("school_id",Config.school_id);
                return parameters;
            }

        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
