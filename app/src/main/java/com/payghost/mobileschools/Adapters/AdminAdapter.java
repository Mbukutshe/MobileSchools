package com.payghost.mobileschools.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Activities.MediaUpload;
import com.payghost.mobileschools.Fragments.Delete;
import com.payghost.mobileschools.Fragments.RegisterStaff;
import com.payghost.mobileschools.Fragments.SendMessage;
import com.payghost.mobileschools.Fragments.UploadDocument;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Functions.Functions;
import com.payghost.mobileschools.Holders.AdminHolder;
import com.payghost.mobileschools.Objects.Dashboard;
import com.payghost.mobileschools.R;

import java.util.List;


/**
 * Created by Wiseman on 2018-01-29.
 */

public class AdminAdapter extends RecyclerView.Adapter<AdminHolder> implements View.OnClickListener{
    List<Dashboard> mDataset;
    Context context;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Animation anim;
    public static  int COUNT_DOWN=200;
    CountDownTimer countDownTimer;
    Functions functions;
    FragmentManager fragmentManager;
    Animation animation;
    SharedPreferences pref;
    public AdminAdapter(Context context, List<Dashboard> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, final FragmentManager fragmentManager)
    {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.fragmentManager = fragmentManager;
        functions = new Functions(this.context);
        animation = new Animation(context);
        pref = this.context.getSharedPreferences("Users", Context.MODE_PRIVATE);

    }
    @Override
    public AdminHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.administrator_fragment_items, parent, false);
        // set the view's size, margins, paddings and layout parameters

        AdminHolder vh = new AdminHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(final AdminHolder holder, final int position) {
        holder.name.setText(mDataset.get(position).getName());
        holder.icon.setBackgroundResource(mDataset.get(position).getIcon());
        animation.toRight(holder.itemView);
       // holder.text_message.setOnKeyListener(functions);
//        functions.setDoneAction(holder.text_message);
        switch (position)
        {
            case 0:
                holder.proceed.setVisibility(View.VISIBLE);
            break;
            case 2:
                holder.proceed.setVisibility(View.VISIBLE);
            break;
        }
        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                anim = new Animation(context);
                if(pref.getString("who_log_on","").equalsIgnoreCase("instructor"))
                {
                    switch (position)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(R.id.content,new UploadDocument()).addToBackStack("upload").commit();
                            break;
                        case 1:
                            context.startActivity(new Intent(context, MediaUpload.class));
                            break;

                        case 2:
                            fragmentManager.beginTransaction().replace(R.id.content,new Delete()).addToBackStack("delete").commit();
                            break;
                    }
                }
                else
                {
                    switch (position)
                    {
                        case 0:
                            fragmentManager.beginTransaction().replace(R.id.content,new SendMessage()).addToBackStack("Message").commit();
                            break;
                        case 1:
                            fragmentManager.beginTransaction().replace(R.id.content,new UploadDocument()).addToBackStack("upload").commit();
                            break;
                        case 2:
                            context.startActivity(new Intent(context, MediaUpload.class));
                            break;
                        case 3:
                            fragmentManager.beginTransaction().replace(R.id.content,new RegisterStaff()).addToBackStack("staff").commit();
                            break;
                        case 4:
                            fragmentManager.beginTransaction().replace(R.id.content,new Delete()).addToBackStack("delete").commit();
                            break;
                    }
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {

        }
    }
}
