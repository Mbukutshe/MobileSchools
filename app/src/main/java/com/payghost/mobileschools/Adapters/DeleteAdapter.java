package com.payghost.mobileschools.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.DeleteHolder;
import com.payghost.mobileschools.Holders.ItemsHolder;
import com.payghost.mobileschools.Objects.DeleteOptions;
import com.payghost.mobileschools.R;

import java.util.List;

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
    public DeleteAdapter(Context context, List<DeleteOptions> list, FragmentManager fragmentManager)
    {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
        anim = AnimationUtils.loadAnimation(this.context,R.anim.to_left);
    }

    @Override
    public int getItemViewType(int position) {
        if(Config.whichType.equalsIgnoreCase("delete"))
        {
            return Config.VIEW_TYPE_DELETE;
        }
        else
            if(Config.whichType.equalsIgnoreCase("options"))
            {
                return Config.VIEW_TYPE_OPTIONS;
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
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position)
    {
        holder.itemView.startAnimation(anim);
        switch(holder.getItemViewType())
        {
            case VIEW_TYPE_DELETE:
                ((DeleteHolder)holder).name.setText(list.get(position).name);
                break;
        }
        ((DeleteHolder)holder).itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId())
                {
                    case R.id.delete_item_view:
                        if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Messages"))
                        {

                        }
                        else
                            if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Documents"))
                            {

                            }
                            else
                                if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Videos"))
                                {

                                }
                                else
                                    if(((DeleteHolder)holder).name.getText().toString().equalsIgnoreCase("Images"))
                                    {

                                    }
                    break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}
