package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Adapters.ImagesAdapter;
import com.payghost.mobileschools.Adapters.VideosAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.Item;
import com.payghost.mobileschools.R;
import com.payghost.mobileschools.Services.PostJSONParser;
import com.payghost.mobileschools.Services.VideoThumbnail;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wiseman on 2017-10-26.
 */

public class Gallery extends Fragment implements TabLayout.OnTabSelectedListener,View.OnClickListener{
    View view;
    List<Item> myDataset;
    RequestQueue requestQueue;
    ProgressDialog myProgressDialog;
    RecyclerView mRecyclerView;
    RecyclerView.LayoutManager mLayoutManager;
    public static VideosAdapter mAdapter;
    public static ImagesAdapter Adapter;
    LinearLayout empty;
    TextView icon,icon_message;
    ConnectivityManager connectivityManager;
    NetworkInfo activeNetwork;
    boolean isConnected;
    FrameLayout try_again;
    LinearLayout no_internet;
   CoordinatorLayout linearLayout;
   FragmentManager fragmentManager;
    com.payghost.mobileschools.Functions.Animation anim;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_gallery,container,false);
        fragmentManager = getFragmentManager();
        connectivityManager = (ConnectivityManager)view.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = connectivityManager.getActiveNetworkInfo();
        isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        try_again = (FrameLayout)view.findViewById(R.id.try_again);
        no_internet = (LinearLayout)view.findViewById(R.id.no_internet);
        try_again.setOnClickListener(this);

        requestQueue = Volley.newRequestQueue(view.getContext());

        linearLayout = (CoordinatorLayout) view.findViewById(R.id.fragment_documents);
        anim = new com.payghost.mobileschools.Functions.Animation(view.getContext());
        anim.setFadeInAnimation(linearLayout);

        empty = (LinearLayout)view.findViewById(R.id.empty_layout);
        icon = (TextView)view.findViewById(R.id.empty_icon);
        icon_message =(TextView)view.findViewById(R.id.empty_icon_message);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.gallery_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(view.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        TabLayout tabLayout = (TabLayout)view.findViewById(R.id.gallery_tabs);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.images).setText("Images"));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.videos).setText("Videos"));
        tabLayout.setOnTabSelectedListener(this);

        myProgressDialog = new ProgressDialog(view.getContext());
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Config.RETRIEVE_URL,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(!response.equalsIgnoreCase("nodata"))
                        {
                            myDataset = PostJSONParser.parseData(response);
                            Adapter = new ImagesAdapter(view.getContext(), myDataset, mRecyclerView, mLayoutManager,myProgressDialog,fragmentManager);
                            mRecyclerView.setAdapter(Adapter);
                            RelativeLayout layout = (RelativeLayout)myProgressDialog.findViewById(R.id.progress_layout);
                            ProgressBar bar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                            ImageView image = (ImageView)myProgressDialog.findViewById(R.id.progress_image);
                            Animation anim = AnimationUtils.loadAnimation(view.getContext(),R.anim.zoom_out);
                            layout.startAnimation(anim);
                            image.startAnimation(anim);
                            bar.startAnimation(anim);
                            myProgressDialog.dismiss();
                            mRecyclerView.setVisibility(View.VISIBLE);
                            empty.setVisibility(View.GONE);
                        }
                        else
                        {
                            mRecyclerView.setVisibility(View.GONE);
                            empty.setVisibility(View.VISIBLE);
                            //  icon.setBackgroundResource(R.drawable.novideos);
                            icon_message.setText("No Images To Show");
                            myProgressDialog.dismiss();
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
                parameters.put("type","image");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
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
                    mRecyclerView.setVisibility(View.GONE);
                }
                else
                {
                    no_internet.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.VISIBLE);
                }
                break;
        }
    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {
        myProgressDialog = new ProgressDialog(view.getContext());
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request;
        switch (tab.getPosition())
        {
            case 0:
                request = new StringRequest(Request.Method.POST, Config.RETRIEVE_URL,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equalsIgnoreCase("nodata"))
                                {
                                    myDataset = PostJSONParser.parseData(response);
                                    Adapter = new ImagesAdapter(view.getContext(), myDataset, mRecyclerView, mLayoutManager,myProgressDialog,fragmentManager);
                                    mRecyclerView.setAdapter(Adapter);
                                    RelativeLayout layout = (RelativeLayout)myProgressDialog.findViewById(R.id.progress_layout);
                                    ProgressBar bar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                                    ImageView image = (ImageView)myProgressDialog.findViewById(R.id.progress_image);
                                    Animation anim = AnimationUtils.loadAnimation(view.getContext(),R.anim.zoom_out);
                                    layout.startAnimation(anim);
                                    image.startAnimation(anim);
                                    bar.startAnimation(anim);
                                    myProgressDialog.dismiss();
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                }
                                else
                                {
                                    mRecyclerView.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    //  icon.setBackgroundResource(R.drawable.novideos);
                                    icon_message.setText("No Images To Show");
                                    myProgressDialog.dismiss();
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
                        parameters.put("type","image");
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);

                break;
            case 1:
                request = new StringRequest(Request.Method.POST, Config.RETRIEVE_URL,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equalsIgnoreCase("nodata"))
                                {
                                    myDataset = VideoThumbnail.parseData(response);
                                    mAdapter = new VideosAdapter(view.getContext(), myDataset, mRecyclerView, mLayoutManager,myProgressDialog);
                                    mRecyclerView.setAdapter(mAdapter);
                                    RelativeLayout layout = (RelativeLayout)myProgressDialog.findViewById(R.id.progress_layout);
                                    ProgressBar bar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                                    ImageView image = (ImageView)myProgressDialog.findViewById(R.id.progress_image);
                                    Animation anim = AnimationUtils.loadAnimation(view.getContext(),R.anim.zoom_out);
                                    layout.startAnimation(anim);
                                    image.startAnimation(anim);
                                    bar.startAnimation(anim);
                                    myProgressDialog.dismiss();
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                }
                                else
                                {
                                    mRecyclerView.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    //  icon.setBackgroundResource(R.drawable.novideos);
                                    icon_message.setText("No Videos To Show");
                                    myProgressDialog.dismiss();
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
                        parameters.put("type","video");
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;

        }
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        myProgressDialog = new ProgressDialog(view.getContext());
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request;
        switch (tab.getPosition())
        {
            case 0:
                request = new StringRequest(Request.Method.POST, Config.RETRIEVE_URL,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equalsIgnoreCase("nodata"))
                                {
                                    myDataset = PostJSONParser.parseData(response);
                                    Adapter = new ImagesAdapter(view.getContext(), myDataset, mRecyclerView, mLayoutManager,myProgressDialog,fragmentManager);
                                    mRecyclerView.setAdapter(Adapter);
                                    RelativeLayout layout = (RelativeLayout)myProgressDialog.findViewById(R.id.progress_layout);
                                    ProgressBar bar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                                    ImageView image = (ImageView)myProgressDialog.findViewById(R.id.progress_image);
                                    Animation anim = AnimationUtils.loadAnimation(view.getContext(),R.anim.zoom_out);
                                    layout.startAnimation(anim);
                                    image.startAnimation(anim);
                                    bar.startAnimation(anim);
                                    myProgressDialog.dismiss();
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                }
                                else
                                {
                                    mRecyclerView.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    icon_message.setText("No Images To Show");
                                    myProgressDialog.dismiss();
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
                        parameters.put("type","image");
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);

                break;
            case 1:
                request = new StringRequest(Request.Method.POST, Config.RETRIEVE_URL,

                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                if(!response.equalsIgnoreCase("nodata"))
                                {
                                    myDataset = VideoThumbnail.parseData(response);
                                    mAdapter = new VideosAdapter(view.getContext(), myDataset, mRecyclerView, mLayoutManager,myProgressDialog);
                                    mRecyclerView.setAdapter(mAdapter);
                                    RelativeLayout layout = (RelativeLayout)myProgressDialog.findViewById(R.id.progress_layout);
                                    ProgressBar bar = (ProgressBar)myProgressDialog.findViewById(R.id.progressBar);
                                    ImageView image = (ImageView)myProgressDialog.findViewById(R.id.progress_image);
                                    Animation anim = AnimationUtils.loadAnimation(view.getContext(), R.anim.zoom_out);
                                    layout.startAnimation(anim);
                                    image.startAnimation(anim);
                                    bar.startAnimation(anim);
                                    myProgressDialog.dismiss();
                                    mRecyclerView.setVisibility(View.VISIBLE);
                                    empty.setVisibility(View.GONE);
                                }
                                else
                                {
                                    mRecyclerView.setVisibility(View.GONE);
                                    empty.setVisibility(View.VISIBLE);
                                    //  icon.setBackgroundResource(R.drawable.novideos);
                                    icon_message.setText("No Videos To Show");
                                    myProgressDialog.dismiss();
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
                        parameters.put("type","video");
                        return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
                break;

        }
    }
}
