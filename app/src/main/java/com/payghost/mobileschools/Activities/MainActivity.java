package com.payghost.mobileschools.Activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;
import com.payghost.mobileschools.Fragments.Gallery;
import com.payghost.mobileschools.Fragments.MoreFrag;
import com.payghost.mobileschools.Fragments.RetrieveDocumentsFrag;
import com.payghost.mobileschools.Fragments.RetrieveMessageFrag;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    List<String> spinnerArray;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    RequestQueue requestQueue;
    MenuItem menuItem,messageItem;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        fragmentManager = getFragmentManager();
        editor.putBoolean("check",true);
        editor.commit();
        getGrades(getApplicationContext());
        getSubjects(getApplicationContext());
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        FirebaseMessaging.getInstance().subscribeToTopic("test");
        spinnerArray =  new ArrayList<String>();
        if(pref.getString("which_one","").equalsIgnoreCase("learner"))
        {
            for (int i = 0; i < pref.getInt("how_many", 0); i++)
            {
                spinnerArray.add(pref.getString(i + "", ""));
            }
            Config.TAG_SUBJECT = pref.getString("0","");
        }
        if(pref.getString("which_one","").equalsIgnoreCase("parent"))
        {
            for (int i=0;i<pref.getInt("school_no",0);i++)
            {
                spinnerArray.add(pref.getString("school"+i,""));
            }
            Config.TAG_SUBJECT = pref.getString("school0","");
        }

        if(pref.getString("which_one","").equalsIgnoreCase("learner")||pref.getString("which_one","").equalsIgnoreCase("parent")) {
            navigation.getMenu().removeItem(R.id.navigation_dash);
            fragmentManager.beginTransaction().replace(R.id.content, new RetrieveMessageFrag()).commit();
        }
        else
        {
            fragmentManager.beginTransaction().replace(R.id.content, new AdministratorDashboard()).commit();
        }
        if(!pref.getString("staff_id","").equalsIgnoreCase(""))
        {
            updateDevice();
        }
    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId())
            {
                case R.id.navigation_message:
                    Config.fragment="messages";
                    if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
                    {
                        menuItem.setVisible(false);
                    }
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveMessageFrag()).addToBackStack("messages").commit();
                    return true;
                case R.id.navigation_dash:
                    Config.fragment="images";
                    if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
                    {
                        menuItem.setVisible(false);
                    }
                    fragmentManager.beginTransaction().replace(R.id.content,new AdministratorDashboard()).addToBackStack("admin").commit();
                    return true;
                case R.id.navigation_posts:
                    Config.fragment="videos";
                    if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
                    {
                        menuItem.setVisible(false);
                    }
                    fragmentManager.beginTransaction().replace(R.id.content,new Gallery()).addToBackStack("gallery").commit();
                    return true;
                case R.id.navigation_documents:
                    Config.fragment="documents";
                    if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
                    {
                        menuItem.setVisible(true);
                    }
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveDocumentsFrag()).addToBackStack("documents").commit();
                    return true;
                case R.id.navigation_more:
                    if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
                    {
                        menuItem.setVisible(false);
                    }

                    fragmentManager.beginTransaction().replace(R.id.content,new MoreFrag()).commit();
                return true;
            }
            return false;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.dropdown_menu, menu);
        messageItem = menu.findItem(R.id.message);
        menuItem = menu.findItem(R.id.menu_spinner);
        menuItem.setVisible(false);
        if( pref.getString("which_one","").equalsIgnoreCase("parent"))
        {
            messageItem.setVisible(true);
        }
        else
        {
            messageItem.setVisible(false);
        }
        if(pref.getString("which_one","").equalsIgnoreCase("learner") || pref.getString("which_one","").equalsIgnoreCase("parent"))
        {
            Spinner spinner = (Spinner) MenuItemCompat.getActionView(menuItem);

            ArrayAdapter<String> adapter =  new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spinnerArray);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    Config.TAG_SUBJECT = adapterView.getItemAtPosition(i).toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView)
                {
                    Config.TAG_SUBJECT = adapterView.getItemAtPosition(0).toString();
                }
            });
            menuItem.setVisible(false);
        }


        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.help)
        {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse("market://details?id="+getPackageName()));
            try
            {
                startActivity(i);
            }
            catch (android.content.ActivityNotFoundException exc)
            {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
            }

        }
        else
            if(id==R.id.feedback)
            {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse("market://details?id="+getPackageName()));
                try
                {
                    startActivity(i);
                }
                catch (android.content.ActivityNotFoundException exc)
                {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
                }
            }
            else
                if(id==R.id.rate)
                {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse("market://details?id="+getPackageName()));
                    try
                    {
                        startActivity(i);
                    }
                    catch (android.content.ActivityNotFoundException exc)
                    {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id="+getPackageName())));
                    }
                }
                else
                    if(id==R.id.message)
                    {
                        startActivity(new Intent(this,ParentMessage.class));
                    }
        return super.onOptionsItemSelected(item);

    }

    public void getGrades(final Context context) {

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_GET_ALL_GRADES,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;

                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++)
                            {
                                JSONObject jo = result.getJSONObject(i);
                                editor.putString("grade:"+i,jo.getString("grade"));
                                editor.commit();
                            }
                            editor.putInt("grade_size",result.length());
                            editor.commit();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("school_id",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void getSubjects(final Context context) {

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_GET_ALL_SUBJECTS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

                            for (int i = 0; i < result.length(); i++)
                            {
                                JSONObject jo = result.getJSONObject(i);
                                editor.putString("subject:"+i,jo.getString("subject"));
                                editor.commit();
                            }
                            editor.putInt("subject_size",result.length());
                            editor.commit();
                            Config.TAG_SUBJECT = pref.getString("subject:0","");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
            {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> parameters = new HashMap<String, String>();
                    parameters.put("school_id",pref.getString("school",""));
                    parameters.put("grade_code","");
                    return parameters;
                }
            };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
    public void updateDevice() {
        final String token = FirebaseInstanceId.getInstance().getToken();
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_STAFF_DEVICE,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("token",token);
                parameters.put("id",pref.getString("staff_id",""));
                parameters.put("school",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
