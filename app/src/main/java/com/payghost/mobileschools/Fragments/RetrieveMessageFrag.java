package com.payghost.mobileschools.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

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

public class RetrieveMessageFrag extends Fragment {

    private String JSON_STRING;
    String message,time,title,sender;
    ProgressDialog myProgressDialog;
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_retrieve_message, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "messages";
        getJSON();
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

                sender = jo.getString(Config.TAG_MESSAGE_SENDER);
                time = jo.getString(Config.TAG_MESSAGE_TIME);
                title = jo.getString(Config.TAG_MESSAGE_TITLE);
                message = jo.getString(Config.TAG_MESSAGE_MESSAGE);


                HashMap<String,String> Tenants = new HashMap<>();

                Tenants.put(Config.TAG_MESSAGE_SENDER,sender);
                Tenants.put(Config.TAG_MESSAGE_TIME,time);
                Tenants.put(Config.TAG_MESSAGE_TITLE,title);
                Tenants.put(Config.TAG_MESSAGE_MESSAGE,message);

                list.add(Tenants);

                arrList.add(new RetrieveService(title,message,time,sender));

            }
            recyclerviewAdapter = new RecyclerviewAdapter(getActivity().getApplicationContext(),arrList);
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
                String s = rh.sendGetRequest(Config.URL_GET_ALL_MESSAGES);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
