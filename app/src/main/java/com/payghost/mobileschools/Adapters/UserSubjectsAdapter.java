package com.payghost.mobileschools.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Holders.UserSubjectHolder;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wiseman on 2018-03-14.
 */

public class UserSubjectsAdapter extends RecyclerView.Adapter<UserSubjectHolder>
{

    Context context;
    List<RetrieveService> list;
    AppCompatImageView mImgCheck;
    public static  int COUNT_DOWN=1000;
    public UserSubjectsAdapter(Context context, List<RetrieveService> list)
    {
        this.context = context;
        this.list = list;
        Config.prefSubjects = new ArrayList<>();
    }
    @Override
    public UserSubjectHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subject_item, parent, false);
        UserSubjectHolder vh = new UserSubjectHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final UserSubjectHolder holder, int position)
    {
        holder.subject.setText(list.get(position).school_name);
        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view)
            {
                if(holder.checked.getText().equals("false"))
                {
                    holder.checked.setText("true");
                    holder.tick.setVisibility(View.VISIBLE);
                    ((Animatable) holder.tick.getDrawable()).start();
                    holder.itemView.setBackgroundColor(Color.parseColor("#f5f5f5"));
                    Config.prefSubjects.add(holder.subject.getText().toString());
                }
                else
                {
                    holder.checked.setText("false");
                    holder.tick.setVisibility(View.GONE);
                    holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));
                    if(Config.prefSubjects.size()>0)
                    {
                        Config.prefSubjects.remove(Config.prefSubjects.size()-1);
                    }
                }

            }
        });
    }
    @Override
    public int getItemCount()
    {
        return list.size();
    }

}
