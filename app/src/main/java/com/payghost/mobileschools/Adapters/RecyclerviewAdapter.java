package com.payghost.mobileschools.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.v7.widget.AppCompatImageView;
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
import com.google.firebase.iid.FirebaseInstanceId;
import com.payghost.mobileschools.Activities.MainActivity;
import com.payghost.mobileschools.Fragments.GradesList;
import com.payghost.mobileschools.Fragments.SubjectList;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.DocumentHolder;
import com.payghost.mobileschools.Holders.GradesListHolder;
import com.payghost.mobileschools.Holders.MediaHolder;
import com.payghost.mobileschools.Holders.MessageHolder;
import com.payghost.mobileschools.Holders.SchoolsHolder;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter{
    List<RetrieveService> arrList;
    Context context;
    Animation anim;
    FragmentManager fragmentManager;
    RequestQueue requestQueue;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    public RecyclerviewAdapter(Context applicationContext, List<RetrieveService> arrList, FragmentManager fragmentManager)
    {
        this.arrList = arrList;
        context =  applicationContext;
        this.fragmentManager =fragmentManager;
        anim = new Animation(context);
        pref = this.context.getSharedPreferences("Users",context.MODE_PRIVATE);
        editor = pref.edit();
    }
    @Override
    public int getItemViewType(int position)
    {
        if (Config.fragment.equals("messages")) {
            // If the current user is the sender of the message
            return Config.VIEW_TYPE_MESSAGE;
        } else
            if(Config.fragment.equals("documents"))
            {
                // If some other user sent the message
                return Config.VIEW_TYPE_DOCUMENT;
            }
            else
                if(Config.fragment.equals("images"))
                {
                    return Config.VIEW_TYPE_IMAGE;
                }
                else
                    if(Config.fragment.equals("videos"))
                    {
                        return Config.VIEW_TYPE_VIDEOS;
                    }
                    else
                        if(Config.fragment.equals("schools"))
                        {
                            return Config.VIEW_TYPE_SCHOOLS;
                        }
                        else
                            if(Config.fragment.equals("grades"))
                            {
                                return Config.VIEW_TYPE_GRADE_LIST;
                            }
        return 0;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {

        View view;
        // set the view's size, margins, paddings and layout parameters
        if (viewType == Config.VIEW_TYPE_MESSAGE) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_item, parent, false);
            return new MessageHolder(view);
        }
        else
            if (viewType == Config.VIEW_TYPE_DOCUMENT)
            {
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_document_item, parent, false);
                return new DocumentHolder(view);
            }
            else
                if (viewType == Config.VIEW_TYPE_IMAGE)
                {
                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_items, parent, false);
                    return new MediaHolder(view);
                }
                else
                    if(viewType == Config.VIEW_TYPE_VIDEOS)
                    {
                        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_video_item, parent, false);
                        return new MediaHolder(view);
                    }
                    else
                        if(viewType == Config.VIEW_TYPE_SCHOOLS)
                        {
                            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.school_item, parent, false);
                            return new SchoolsHolder(view);
                        }
                        else
                            if(viewType == Config.VIEW_TYPE_GRADE_LIST)
                            {
                                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.grade_item, parent, false);
                                return new GradesListHolder(view);
                            }
        return null;
    }
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position)
    {
        anim.toRight(holder.itemView);
        final RetrieveService data = arrList.get(position);
        switch (holder.getItemViewType()){
            case Config.VIEW_TYPE_MESSAGE:
                ((MessageHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_DOCUMENT:
                ((DocumentHolder)holder).bind(data);
                ((DocumentHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = data.link;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((Activity)view.getContext()).startActivity(browserIntent);
                    }
                });
                 break;
            case Config.VIEW_TYPE_IMAGE:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_VIDEOS:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_SCHOOLS:
                ((SchoolsHolder)holder).bind(data,context);
                ((SchoolsHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch ( Config.which_one)
                        {
                            case "learner":
                                Config.school_id = data.id;
                                editor.putString("school",data.id);
                                editor.commit();
                                fragmentManager.beginTransaction().replace(R.id.schools_and_grades,new GradesList()).addToBackStack("").commit();
                                break;
                            case "parent":
                                Config.school_id = data.id;
                                 Register(view.getContext());
                                 editor.putString("school",Config.school_id);
                                 editor.putBoolean("check",true);
                                 editor.commit();
                            break;
                        }

                    }
                });
            break;
            case Config.VIEW_TYPE_GRADE_LIST:
                ((GradesListHolder)holder).bind(data);
                ((GradesListHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        Config.grade_id = data.id;
                        editor.putString("grade", data.id);
                        editor.commit();
                        fragmentManager.beginTransaction().replace(R.id.schools_and_grades,new SubjectList()).addToBackStack("").commit();
                    }
                });
            break;
        }
    }
    @Override
    public int getItemCount()
    {
        return arrList.size();
    }
    public void Register(final Context context)
    {
        addToshared("which_one","parent");
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
                                    context.startActivity(new Intent(context,MainActivity.class));
                                    mImgCheck.setVisibility(View.GONE);
                                    ((Activity)context).finish();
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
                parameters.put("school",pref.getString("school",""));
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
}
