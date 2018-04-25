package com.payghost.mobileschools.Adapters;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Fragments.GradesList;
import com.payghost.mobileschools.Fragments.SubjectList;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.DocumentHolder;
import com.payghost.mobileschools.Holders.GradesListHolder;
import com.payghost.mobileschools.Holders.MediaHolder;
import com.payghost.mobileschools.Holders.MessageHolder;
import com.payghost.mobileschools.Holders.PastYearHolder;
import com.payghost.mobileschools.Holders.SchoolsHolder;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter{
    List<RetrieveService> arrList;
    Context context;
    Animation anim;
    FragmentManager fragmentManager;
    public static  int COUNT_DOWN=1000;
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
        Config.prefSchools = new ArrayList<>();
        Config.prefSchoolsName = new ArrayList<>();
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
                            else
                                if(Config.fragment.equals("past"))
                                {
                                    return Config.VIEW_TYPE_PAST;
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
                            else
                                if(viewType == Config.VIEW_TYPE_PAST)
                                {
                                    view = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_year_item, parent, false);
                                    return new PastYearHolder(view);
                                }
        return null;
    }
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        anim.toRight(holder.itemView);
        final RetrieveService data = arrList.get(position);
        switch (holder.getItemViewType()){
            case Config.VIEW_TYPE_PAST:
                ((PastYearHolder)holder).bind(data);
                ((PastYearHolder)holder).download.getBackground().setAlpha(130);
                ((PastYearHolder)holder).download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view)
                    {
                        String url = data.link;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((Activity)view.getContext()).startActivity(browserIntent);
                    }
                });
                break;
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
                ((DocumentHolder)holder).download.getBackground().setAlpha(120);
                 break;
            case Config.VIEW_TYPE_IMAGE:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_VIDEOS:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_SCHOOLS:
                ((SchoolsHolder)holder).bind(data,context);
                if (Config.which_one.equalsIgnoreCase("learner"))
                {
                    ((SchoolsHolder)holder).pointer.setVisibility(View.VISIBLE);
                }
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
                                if(((SchoolsHolder)holder).checked.getText().equals("false"))
                                {
                                    ((SchoolsHolder)holder).checked.setText("true");
                                    ((SchoolsHolder)holder).tick.setVisibility(View.VISIBLE);
                                    ((Animatable)((SchoolsHolder)holder).tick.getDrawable()).start();
                                     Config.prefSchools.add(data.id);
                                    Config.prefSchoolsName.add(((SchoolsHolder)holder).name.getText().toString());
                                }
                                else
                                {
                                    ((SchoolsHolder)holder).checked.setText("false");
                                    ((SchoolsHolder)holder).tick.setVisibility(View.GONE);
                                    if(Config.prefSchools.size()>0)
                                    {
                                        Config.prefSchools.remove(Config.prefSchools.size()-1);
                                        Config.prefSchoolsName.remove(Config.prefSchoolsName.size()-1);
                                    }
                                }
                                // Register(view.getContext());
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

}
