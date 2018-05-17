package com.payghost.mobileschools.Adapters;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.payghost.mobileschools.Fragments.MoreDetails;
import com.payghost.mobileschools.Holders.ImagesHolder;
import com.payghost.mobileschools.Objects.Item;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Wiseman on 2017-10-28.
 */

public class ImagesAdapter extends RecyclerView.Adapter<ImagesHolder>{
    List<Item> mDataset;
    Context context,c;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    com.payghost.mobileschools.Functions.Animation anim;
    Animation upAnim;
    public static  int COUNT_DOWN=500;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog;
    String url;
    Bitmap bitmap=null;
    FragmentManager fragmentManager;
    public ImagesAdapter(Context context, List<Item> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, ProgressDialog myProgressDialog, FragmentManager fragmentManager) {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.myProgressDialog = myProgressDialog;
        this.fragmentManager = fragmentManager;
        anim = new  com.payghost.mobileschools.Functions.Animation(context);
    }
    @Override
    public ImagesHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.resource_items, parent, false);
        // set the view's size, margins, paddings and layout parameters

        ImagesHolder vh = new ImagesHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final ImagesHolder holder, final int position) {
        if (mDataset.get(position).getAttach().toString().equalsIgnoreCase("image")) {
            if(!mDataset.get(position).getAuthor().isEmpty())
            {
                holder.author.setText(mDataset.get(position).getAuthor());
            }
            holder.message.setText(mDataset.get(position).getMessage());
            holder.date.setText(mDataset.get(position).getDate());
            holder.subject.setText("" + mDataset.get(position).getSubject());
            Picasso.with(context).load(mDataset.get(position).getLink()).into(holder.video_thumbnail);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view)
                {
                    Bundle bundle = new Bundle();
                    bundle.putString("message",mDataset.get(position).getMessage());
                    bundle.putString("time",mDataset.get(position).getDate());
                    bundle.putString("subject",mDataset.get(position).getSubject());
                    bundle.putString("url",mDataset.get(position).getLink());
                    MoreDetails moreDetails = new MoreDetails();
                    moreDetails.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content,moreDetails).addToBackStack("more").commit();
                }
            });
            holder.play.getBackground().setAlpha(150);

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    upAnim = AnimationUtils.loadAnimation(context, R.anim.alpha);
                    holder.play.startAnimation(upAnim);
                    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if(isConnected)
                    {
                        Intent shareIntent = new Intent();
                        shareIntent.setAction(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM,Uri.parse(mDataset.get(position).getLink()) );
                        shareIntent.setType("image/jpeg");
                        context.startActivity(Intent.createChooser(shareIntent, "Share Via:"));
                    }
                    else
                    {
                        LayoutInflater infla = LayoutInflater.from(holder.itemView.getContext());
                        View layout =infla.inflate(R.layout.toast_container_layout,(ViewGroup)holder.itemView.findViewById(R.id.toast_layout));
                        TextView textview = (TextView)layout.findViewById(R.id.toast_message);
                        textview.setText("No Internet Connection!");
                        Toast toast = new Toast(context);
                        toast.setGravity(Gravity.CENTER_VERTICAL,0,0);
                        toast.setDuration(Toast.LENGTH_LONG);
                        toast.setView(layout);
                        toast.show();
                    }
                }
            });
            anim.toRight(holder.itemView);
            anim.toRight(holder.video_thumbnail);
            anim.toRight(holder.subject);
            anim.toRight(holder.message);
            anim.toRight(holder.date);
            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                @Override
                public void onTick(long l) {

                }

                @Override
                public void onFinish() {
                }
            };
            countDownTimer.start();
        }
    }
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

}
