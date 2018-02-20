package com.payghost.mobileschools.Functions;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wiseman on 2017-10-19.
 */

public class InsertToDatabase {
    RequestQueue requestQueue;
    Context context;
    ProgressDialog myProgressDialog;
    public InsertToDatabase(Context context)
    {
        this.context = context;
        myProgressDialog = new ProgressDialog(context);

    }
    public void createGroup(final EditText group_name, final String group_users)
    {

        if(!(group_name.getText().toString().isEmpty()))
        {
            myProgressDialog.show();
            myProgressDialog.setContentView(R.layout.progress);
            ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
            StringRequest request = new StringRequest(Request.Method.POST, Config.GROUP_INSERTION,

                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(Config.GROUP_CREATION_SUCCESS.equalsIgnoreCase(response))
                            {
                                myProgressDialog.dismiss();
                                Toast.makeText(context,"Group has been created!",Toast.LENGTH_LONG) .show();
                            }
                            else
                            {
                                myProgressDialog.dismiss();
                                Toast.makeText(context,"Group hasn't created. try again",Toast.LENGTH_LONG).show();
                            }


                        }
                    },

                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put(Config.SOURCE,Config.SOURCE);
                    parameters.put(Config.GROUP_NAME,group_name.getText().toString());
                    parameters.put(Config.GROUP_USERS,group_users);
                    return parameters;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(request);
        }
        else
        {
            Toast.makeText(context,"Group name is required!",Toast.LENGTH_LONG) .show();
        }
    }
    public void insertDeviceId(final String Token)
    {
        StringRequest request = new StringRequest(Request.Method.POST, Config.REGISTER_DEVICE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put(Config.TOKEN,Token);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }

}
