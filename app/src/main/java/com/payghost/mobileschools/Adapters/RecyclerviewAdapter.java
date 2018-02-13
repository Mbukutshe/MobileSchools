package com.payghost.mobileschools.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.DocumentHolder;
import com.payghost.mobileschools.Holders.MediaHolder;
import com.payghost.mobileschools.Holders.MessageHolder;
import com.payghost.mobileschools.Holders.SchoolsHolder;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-02-13.
 */

public class RecyclerviewAdapter extends RecyclerView.Adapter{
    List<RetrieveService> arrList;
    Context context;

    public RecyclerviewAdapter(Context applicationContext, List<RetrieveService> arrList)
    {
        this.arrList = arrList;
        context =  applicationContext;
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
        return 0;
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        // set the view's size, margins, paddings and layout parameters

        if (viewType == Config.VIEW_TYPE_MESSAGE) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.message_item, parent, false);
            return new MessageHolder(view);
        }
        else
            if (viewType == Config.VIEW_TYPE_DOCUMENT)
            {
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.resource_document_item, parent, false);
                return new DocumentHolder(view);
            }
            else
                if (viewType == Config.VIEW_TYPE_IMAGE)
                {
                    view = LayoutInflater.from(parent.getContext())
                            .inflate(R.layout.resource_items, parent, false);
                    return new MediaHolder(view);
                }
                else
                    if (viewType == Config.VIEW_TYPE_VIDEOS)
                    {
                        view = LayoutInflater.from(parent.getContext())
                                .inflate(R.layout.resource_video_item, parent, false);
                        return new MediaHolder(view);
                    }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        RetrieveService data = arrList.get(position);

        switch (holder.getItemViewType()) {
            case Config.VIEW_TYPE_MESSAGE:
                ((MessageHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_DOCUMENT:
                ((DocumentHolder)holder).bind(data);
                 break;
            case Config.VIEW_TYPE_IMAGE:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_VIDEOS:
                ((MediaHolder)holder).bind(data);
            break;
            case Config.VIEW_TYPE_SCHOOLS:
                ((SchoolsHolder)holder).bind(data,context);
            break;
        }
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }
}
