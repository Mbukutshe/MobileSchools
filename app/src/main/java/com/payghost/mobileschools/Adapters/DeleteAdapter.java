package com.payghost.mobileschools.Adapters;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Fragments.Trash;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.DeleteHolder;
import com.payghost.mobileschools.Holders.ItemsHolder;
import com.payghost.mobileschools.Holders.TrashDocumentHolder;
import com.payghost.mobileschools.Holders.TrashHolder;
import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Wiseman on 2018-02-17.
 */

public class DeleteAdapter extends RecyclerView.Adapter{
    Context context;
    List<DeleteOptions> list;
    public static final int VIEW_TYPE_DELETE = 1;
    public static final int VIEW_TYPE_OPTIONS = 2;
    FragmentManager fragmentManager;
    Animation anim;
    RequestQueue requestQueue;
    ProgressDialog progress;
    static String table="";
    public DeleteAdapter(Context context, List<DeleteOptions> list, FragmentManager fragmentManager)
    {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
        anim = AnimationUtils.loadAnimation(this.context,R.anim.to_left);
        requestQueue = Volley.newRequestQueue(this.context);
    }
    @Override
    public int getItemViewType(int position)
    {
        if(Config.whichType.equalsIgnoreCase("delete"))
        {
            return Config.VIEW_TYPE_DELETE;
        }
        else
            if(Config.whichType.equalsIgnoreCase("options"))
            {
                return Config.VIEW_TYPE_OPTIONS;
            }
            else
                if(Config.whichType.equalsIgnoreCase("delete_messages"))
                {
                    return Config.DELETE_MESSAGE;
                }
                else
                    if(Config.whichType.equalsIgnoreCase("delete_documents"))
                    {
                        return Config.DELETE_DOCUMENTS;
                    }
                    else
                        if(Config.whichType.equalsIgnoreCase("delete_videos"))
                        {
                            return Config.DELETE_VIDEOS;
                        }
                        else
                            if(Config.whichType.equalsIgnoreCase("delete_images"))
                            {
                                return Config.DELETE_IMAGES;
                            }
        return 0;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view;
        if(viewType == Config.VIEW_TYPE_DELETE)
        {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.delete_options, parent, false);
          return  new DeleteHolder(view);
        }
        else
            if(viewType == Config.VIEW_TYPE_OPTIONS)
            {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.trash_items, parent, false);
               return new ItemsHolder(view);
            }
            else
                if(viewType == Config.DELETE_MESSAGE)
                {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.trash_items, parent, false);
                    return new TrashHolder(view);
                }
                else
                    if(viewType == Config.DELETE_DOCUMENTS)
                    {
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.trash_document_item, parent, false);
                        return new TrashHolder(view);
                    }
                    else
                        if(viewType == Config.DELETE_VIDEOS)
                        {
                            view = LayoutInflater.from(parent.getContext())
                                    .inflate(R.layout.trash_items, parent, false);
                            return new TrashHolder(view);
                        }
                        else
                            if(viewType == Config.DELETE_IMAGES)
                            {
                                view = LayoutInflater.from(parent.getContext())
                                        .inflate(R.layout.trash_items, parent, false);
                                return new TrashHolder(view);
                            }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position)
    {
        switch(holder.getItemViewType())
        {
            case VIEW_TYPE_DELETE:
                holder.itemView.startAnimation(anim);
                ((DeleteHolder)holder).name.setText(list.get(position).name);
                ((DeleteHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        switch (view.getId())
                        {
                            case R.id.delete_item_view:
                                if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Messages"))
                                {
                                    Config.whichType="delete_messages";
                                    table="messageTable";
                                    fragmentManager.beginTransaction().replace(R.id.content,new Trash()).addToBackStack("").commit();
                                }
                                else
                                if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Documents"))
                                {
                                    Config.whichType="delete_documents";
                                    table="Resources";
                                    fragmentManager.beginTransaction().replace(R.id.content,new Trash()).addToBackStack("").commit();
                                }
                                else
                                if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Videos"))
                                {
                                    Config.whichType="delete_videos";
                                    table="media";
                                    fragmentManager.beginTransaction().replace(R.id.content,new Trash()).addToBackStack("").commit();
                                }
                                else
                                if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Images"))
                                {
                                    Config.whichType="delete_images";
                                    table="media";
                                    fragmentManager.beginTransaction().replace(R.id.content,new Trash()).addToBackStack("").commit();
                                }
                                break;
                        }
                    }
                });
                break;
            case Config.DELETE_MESSAGE:
                ((TrashHolder)holder).name.setText(list.get(position).name);
                ((TrashHolder)holder).time.setText(list.get(position).time);
                ((TrashHolder)holder).subject.setText(list.get(position).subject);
                ((TrashHolder)holder).message.setText(list.get(position).message);
            break;
            case Config.DELETE_DOCUMENTS:
                ((TrashHolder)holder).name.setText(list.get(position).name);
                ((TrashHolder)holder).time.setText(list.get(position).time);
                ((TrashHolder)holder).subject.setText(list.get(position).subject);
                ((TrashHolder)holder).message.setText(list.get(position).message);
            break;
            case Config.DELETE_VIDEOS:
                ((TrashHolder)holder).name.setText(list.get(position).name);
                ((TrashHolder)holder).time.setText(list.get(position).time);
                ((TrashHolder)holder).subject.setText(list.get(position).subject);
                ((TrashHolder)holder).message.setText(list.get(position).message);
                Picasso.with(context).load(list.get(position).url).into(((TrashHolder)holder).thumbnail);
                ((TrashHolder)holder).container_thumnail.setVisibility(View.VISIBLE);
            break;
            case Config.DELETE_IMAGES:
                ((TrashHolder)holder).name.setText(list.get(position).name);
                ((TrashHolder)holder).time.setText(list.get(position).time);
                ((TrashHolder)holder).subject.setText(list.get(position).subject);
                ((TrashHolder)holder).message.setText(list.get(position).message);
                 Picasso.with(context).load(list.get(position).url).into(((TrashHolder)holder).thumbnail);
                ((TrashHolder)holder).container_thumnail.setVisibility(View.VISIBLE);
            break;
        }
        if(holder.getClass()==TrashHolder.class || holder.getClass()== TrashDocumentHolder.class)
        {
            ((TrashHolder)holder).delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    eradicate(list.get(position).id,position,"DELETE","","");
                }
            });
            ((TrashHolder)holder).edit.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    if(((TrashHolder) holder).test_edit.getText().toString().equalsIgnoreCase("edit"))
                    {
                        ((TrashHolder) holder).text_subject.setText(list.get(position).subject);
                        ((TrashHolder) holder).text_message.setText(list.get(position).message);
                        ((TrashHolder) holder).edit_subject.setVisibility(View.VISIBLE);
                        ((TrashHolder) holder).edit_message.setVisibility(View.VISIBLE);
                        ((TrashHolder) holder).subject.setVisibility(View.GONE);
                        ((TrashHolder) holder).message.setVisibility(View.GONE);
                        ((TrashHolder) holder).edit_icon.setBackgroundResource(R.drawable.okay);
                        ((TrashHolder) holder).test_edit.setText("okay");
                    }
                    else
                    {
                        list.get(position).subject=((TrashHolder) holder).text_subject.getText().toString();
                        list.get(position).message=((TrashHolder) holder).text_message.getText().toString();
                        ((TrashHolder) holder).subject.setText(list.get(position).subject);
                        ((TrashHolder) holder).message.setText(list.get(position).message);
                        ((TrashHolder) holder).edit_subject.setVisibility(View.GONE);
                        ((TrashHolder) holder).edit_message.setVisibility(View.GONE);
                        ((TrashHolder) holder).subject.setVisibility(View.VISIBLE);
                        ((TrashHolder) holder).message.setVisibility(View.VISIBLE);
                        ((TrashHolder) holder).edit_icon.setBackgroundResource(R.drawable.edit);
                        ((TrashHolder) holder).test_edit.setText("edit");
                        eradicate(list.get(position).id,0,"UPDATE",list.get(position).subject,list.get(position).message);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
    public void eradicate(final int id,final int position,final String action,final String title,final String message)
    {
        progress = new ProgressDialog(context,R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_DELETE_ITEMS,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response)
                    {
                        if(title.isEmpty()) {
                            list.remove(position);
                            notifyItemRangeChanged(position, list.size());
                            notifyDataSetChanged();
                        }
                        progress.dismiss();
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
                parameters.put("table",table);
                parameters.put("id",id+"");
                parameters.put("device","true");
                parameters.put("title",title);
                parameters.put("message",message);
                parameters.put("action",action);
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
