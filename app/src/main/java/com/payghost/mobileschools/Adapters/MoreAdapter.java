package com.payghost.mobileschools.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.GeneralHolder;
import com.payghost.mobileschools.Holders.PolicyHolder;
import com.payghost.mobileschools.Holders.QuickHolder;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-03-19.
 */

public class MoreAdapter extends RecyclerView.Adapter {
    Context context;
    Animation anim;
    List<RetrieveService> arrList;
    public MoreAdapter(Context context,List<RetrieveService> arrList)
    {
        this.context = context;
        this.arrList = arrList;
        anim = new Animation(context);
    }
    @Override
    public int getItemViewType(int position) {
        if (Config.fragment.equals("general")) {
            // If the current user is the sender of the message
            return Config.VIEW_TYPE_GENERAL;
        } else
        if(Config.fragment.equals("quick"))
        {
            // If some other user sent the message
            return Config.VIEW_TYPE_QUICK;
        }
        else
        if(Config.fragment.equals("policy"))
        {
            return Config.VIEW_TYPE_POLICY;
        }
    return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == Config.VIEW_TYPE_GENERAL) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.general_item_layout, parent, false);
            return new GeneralHolder(view);
        }
        else
        if (viewType == Config.VIEW_TYPE_QUICK)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quick_links_item_layout, parent, false);
            return new QuickHolder(view);
        }
        else
        if (viewType == Config.VIEW_TYPE_POLICY)
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.policy_item_layout, parent, false);
            return new PolicyHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position)
    {
        anim.toRight(holder.itemView);
        final RetrieveService data = arrList.get(position);
        switch (holder.getItemViewType()) {
            case Config.VIEW_TYPE_GENERAL:
                ((GeneralHolder) holder).bind(data);
                ((GeneralHolder) holder).download.getBackground().setAlpha(120);
                ((GeneralHolder)holder).download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = data.link;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((Activity)view.getContext()).startActivity(browserIntent);
                    }
                });
            break;
            case Config.VIEW_TYPE_QUICK:
                ((QuickHolder) holder).bind(data);
                ((QuickHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = data.link;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((Activity)view.getContext()).startActivity(browserIntent);
                    }
                });
            break;
            case Config.VIEW_TYPE_POLICY:
                ((PolicyHolder) holder).bind(data);
                ((PolicyHolder) holder).download.getBackground().setAlpha(120);
                ((PolicyHolder)holder).download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = data.link;
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        ((Activity)view.getContext()).startActivity(browserIntent);
                    }
                });
            break;
        }
    }

    @Override
    public int getItemCount() {
        return arrList.size();
    }
}
