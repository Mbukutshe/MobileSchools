package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
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
import com.payghost.mobileschools.Handler.RequestHandler;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RetrieveDocumentsFrag extends Fragment {

    private String JSON_STRING;
    String message,time,title,subject,link;
    RequestQueue requestQueue;
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    ProgressDialog myProgressDialog;
    View view;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =inflater.inflate(R.layout.fragment_retrieve_documents, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "documents";
        fetch();
        return view;
    }
    private void showMessages(){

        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        List<RetrieveService> arrList = new ArrayList<RetrieveService>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);

                time = jo.getString(Config.TAG_MESSAGE_TIME);
                title = jo.getString(Config.TAG_MESSAGE_TITLE);
                message = jo.getString(Config.TAG_RESOURCE_DESCRIPTION);
                link = jo.getString(Config.TAG_RESOURCE_LINK);

                arrList.add(new RetrieveService(title,message,time,link));

            }
            recyclerviewAdapter = new RecyclerviewAdapter(getActivity().getApplicationContext(),arrList,getFragmentManager());
            recyclerView.setAdapter(recyclerviewAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    private void getJSON(){

        class GetJSON extends AsyncTask<Void,Void,String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                myProgressDialog = new ProgressDialog(view.getContext());
                myProgressDialog.show();
                myProgressDialog.setContentView(R.layout.progress);
                ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
                progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);


                //   loading = ProgressDialog.show(getActivity().getApplicationContext(),"Fetching Messages"," Please Wait...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                JSON_STRING = s;
                showMessages();
                myProgressDialog.dismiss();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL_RESOURCES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
    public void fetch()
    {

        myProgressDialog = new ProgressDialog(view.getContext());
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);


        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_GET_ALL_RESOURCES,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        List<RetrieveService> arrList = new ArrayList<RetrieveService>();
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                            for(int i = 0; i<result.length(); i++){
                                JSONObject jo = result.getJSONObject(i);

                                time = jo.getString(Config.TAG_MESSAGE_TIME);
                                title = jo.getString(Config.TAG_MESSAGE_TITLE);
                                message = jo.getString(Config.TAG_RESOURCE_DESCRIPTION);
                                link = jo.getString(Config.TAG_RESOURCE_LINK);

                                arrList.add(new RetrieveService(title,message,time,link));

                            }
                            recyclerviewAdapter = new RecyclerviewAdapter(getActivity().getApplicationContext(),arrList,getFragmentManager());
                            recyclerView.setAdapter(recyclerviewAdapter);
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        myProgressDialog.dismiss();
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
                parameters.put("subject",Config.TAG_SUBJECT);
                parameters.put("which_one",pref.getString("which_one",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
}
