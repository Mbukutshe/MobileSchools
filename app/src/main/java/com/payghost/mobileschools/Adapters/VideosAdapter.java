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
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import com.payghost.mobileschools.Fragments.MoreVideo;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Holders.VideosHolder;
import com.payghost.mobileschools.Objects.Item;
import com.payghost.mobileschools.R;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * Created by Wiseman on 2017-10-02.
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosHolder> {
    List<Item> mDataset;
    Context context,c;
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    Animation anim;
    public static  int COUNT_DOWN=500;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog;
    android.view.animation.Animation upAnim;
    FragmentManager fragmentManager;
    public VideosAdapter(Context context, List<Item> mDataset, RecyclerView recyclerView, RecyclerView.LayoutManager layoutManager, ProgressDialog myProgressDialog,  FragmentManager fragmentManager) {
        this.mDataset = mDataset;
        this.context=context;
        this.recyclerView = recyclerView;
        this.layoutManager = layoutManager;
        this.myProgressDialog = myProgressDialog;
        this.fragmentManager = fragmentManager;
        anim = new Animation(context);
    }
    @Override
    public VideosHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        c = parent.getContext();
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.resource_video_item, parent, false);
        // set the view's size, margins, paddings and layout parameters

        VideosHolder vh = new VideosHolder(view);
        return vh;
    }
    @Override
    public void onBindViewHolder(final VideosHolder holder, final int position) {
        if (mDataset.get(position).getAttach().toString().equalsIgnoreCase("video")) {
            holder.message.setText(mDataset.get(position).getMessage());
            holder.date.setText(mDataset.get(position).getDate());
            holder.subject.setText("" + mDataset.get(position).getSubject());
            holder.video_thumbnail.setImageBitmap(mDataset.get(position).getImage());
            holder.video_duration.setText(mDataset.get(position).getDuration());
            holder.play.getBackground().setAlpha(150);
            holder.video_footer.getBackground().setAlpha(180);
            upAnim = AnimationUtils.loadAnimation(context, R.anim.fromtop_translation);
            holder.itemView.clearAnimation();
            holder.itemView.startAnimation(upAnim);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                   Bitmap bitmap = mDataset.get(position).getImage();
                   bitmap.compress(Bitmap.CompressFormat.PNG,100,stream);
                   byte[] bytes = stream.toByteArray();

                    Bundle bundle = new Bundle();
                    bundle.putString("message",mDataset.get(position).getMessage());
                    bundle.putString("time",mDataset.get(position).getDate());
                    bundle.putString("subject",mDataset.get(position).getSubject());
                    bundle.putString("duration",mDataset.get(position).getDuration());
                    bundle.putString("url",mDataset.get(position).getLink());
                    bundle.putByteArray("image",bytes);
                    MoreVideo moreDetails = new  MoreVideo();
                    moreDetails.setArguments(bundle);
                    fragmentManager.beginTransaction().replace(R.id.content,moreDetails).addToBackStack("video").commit();
                }
            });

            holder.play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    anim.setAlphaAnimation(holder.play);
                    ConnectivityManager connectivityManager = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
                    boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
                    if(isConnected)
                    {
                        String url = mDataset.get(position).getLink();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                        context.startActivity(browserIntent);
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
            anim.toRight(holder.video_duration);
            anim.toRight(holder.play);
            anim.toRight(holder.video_footer);
            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                @Override
                public void onTick(long l)
                {

                }
                @Override
                public void onFinish()
                {

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
