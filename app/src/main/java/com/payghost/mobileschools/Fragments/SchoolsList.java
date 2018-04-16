package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.payghost.mobileschools.Activities.MainActivity;
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
 * Created by Wiseman on 2018-02-13.
 */

public class SchoolsList extends Fragment implements View.OnClickListener{
    private String JSON_STRING;
    String id,logo,name;
    LinearLayout add;
    LinearLayoutManager linearlayout;
    RecyclerView recyclerView;
    RecyclerviewAdapter recyclerviewAdapter;
    List<RetrieveService> list;
    RequestQueue requestQueue;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    String school_id;
    View view;
    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
       view =inflater.inflate(R.layout.shools_list_layout,container,false);
        pref = view.getContext().getSharedPreferences("Users",view.getContext().MODE_PRIVATE);
        editor = pref.edit();
        recyclerView = (RecyclerView)view.findViewById(R.id.recyclerv_message);
        linearlayout = new LinearLayoutManager(view.getContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        add = (LinearLayout)view.findViewById(R.id.add_user);
        add.setOnClickListener(this);
        if(pref.getString("which_one","").equalsIgnoreCase("learner"))
        {
            add.setVisibility(View.GONE);
        }
        Config.fragment = "schools";
        getList();
        return view;
    }

    @Override
    public void onClick(View view)
    {
        school_id= Config.prefSchools.get(0);
        switch (view.getId())
        {
            case R.id.add_user:
                for (int i=1;i<Config.prefSchools.size();i++)
                {
                    school_id += ","+Config.prefSchools.get(i);
                }
                Register(view.getContext());
                editor.putString("school",Config.school_id);
                editor.putBoolean("check",true);
                editor.commit();
            break;
        }
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
        StringRequest request = new StringRequest(Request.Method.GET, Config.URL_GET_ALL_SCHOOLS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                            for(int i = 0; i<result.length(); i++){
                                JSONObject jo = result.getJSONObject(i);

                                id = jo.getString(Config.TAG_SCHOOL_ID);
                                name = jo.getString(Config.TAG_SCHOOL_NAME);
                                logo = jo.getString(Config.TAG_SCHOOL_LOGO);

                                names.add(new RetrieveService(id,logo,name));

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

        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(view.getContext());
        requestQueue.add(request);
    }
    public void Register(final Context context)
    {
        addToshared("which_one","parent");
        addSchoolsToshared();
        final String token = FirebaseInstanceId.getInstance().getToken();
        progress = new ProgressDialog(context);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_USER_REGISTRATION,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progress.dismiss();
                        if(response.equalsIgnoreCase("success"))
                        {
                            myProgressDialog = new ProgressDialog(context);
                            myProgressDialog.show();
                            myProgressDialog.setContentView(R.layout.success_layout);
                            mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.success_image);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l)
                                {

                                }
                                @Override
                                public void onFinish(){
                                    view.getContext().startActivity(new Intent(view.getContext(),MainActivity.class));
                                    mImgCheck.setVisibility(View.GONE);
                                    getActivity().finish();
                                    myProgressDialog.dismiss();
                                }
                            };
                            countDownTimer.start();
                        }
                        else
                        if(response.equalsIgnoreCase("error"))
                        {
                            myProgressDialog = new ProgressDialog(context);
                            myProgressDialog.show();
                            myProgressDialog.setContentView(R.layout.error_layout);
                            mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.error_image);
                            ((Animatable) mImgCheck.getDrawable()).start();
                            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                @Override
                                public void onTick(long l)
                                {

                                }
                                @Override
                                public void onFinish(){
                                    mImgCheck.setVisibility(View.GONE);
                                    myProgressDialog.dismiss();
                                }
                            };
                            countDownTimer.start();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("title",Config.school_id);
                parameters.put("name",Config.TAG_FIRST_NAME);
                parameters.put("surname",Config.TAG_SURNAME);
                parameters.put("dob",Config.TAG_DOB);
                parameters.put("gender",Config.TAG_GENDER);
                parameters.put("school",school_id);
                parameters.put("email",Config.TAG_EMAIL);
                parameters.put("grade","");
                parameters.put("type",Config.TAG_TYPE);
                parameters.put("device",token);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void addToshared(String mykey,String value){

        editor.putString(mykey,value);
        editor.commit();
    }
    public void addSchoolsToshared()
    {

        for(int i=0;i<Config.prefSchoolsName.size();i++)
        {
            editor.putString("school"+i,Config.prefSchoolsName.get(i));
            editor.commit();
        }
        editor.putInt("school_no",Config.prefSchoolsName.size());
        editor.commit();
    }
}
