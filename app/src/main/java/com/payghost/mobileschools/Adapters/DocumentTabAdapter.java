package com.payghost.mobileschools.Adapters;

import android.app.FragmentManager;
import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.payghost.mobileschools.Fragments.Assignments;
import com.payghost.mobileschools.Fragments.CurrentYear;
import com.payghost.mobileschools.Fragments.OtherResources;
import com.payghost.mobileschools.Fragments.PastYear;
import com.payghost.mobileschools.Holders.DocumentTabs;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-03-31.
 */

public class DocumentTabAdapter extends RecyclerView.Adapter<DocumentTabs>{
    List<RetrieveService> list;
    Context context;
    FragmentManager fragmentManager;
    RecyclerView recyclerView;
    LinearLayoutManager linearlayout;
    public DocumentTabAdapter(Context context, List<RetrieveService>list, FragmentManager fragmentManager, RecyclerView recyclerView, LinearLayoutManager linearlayout)
    {
        this.context = context;
        this.list = list;
        this.fragmentManager = fragmentManager;
        this.recyclerView = recyclerView;
        this.linearlayout = linearlayout;
    }
    @Override
    public DocumentTabs onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_all_document_item, parent, false);
        return new DocumentTabs(view);
    }
    @Override
    public void onBindViewHolder(final DocumentTabs holder, final int position)
    {
        holder.textView.setText(list.get(position).school_name);
        holder.checkBox.setChecked(false);
        holder.view.getBackground().setAlpha(150);
        switch(position)
        {
            case 0:
                holder.checkBox.setChecked(true);
            break;
        }
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position)
                {
                    case 0:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    if(!(recyclerView.findViewHolderForAdapterPosition(i).itemView==null))
                                    {
                                        View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                        ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                    }

                                }
                                catch (Exception e)
                                {
                                    Toast.makeText(view.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new CurrentYear()).commit();
                    break;
                    case 1:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    Log.d("DcumentTabs :",e.getMessage());
                                }


                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new Assignments()).commit();
                    break;
                    case 2:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    //       Log.d("DcumentTabs :",e.getMessage());
                                }
                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new PastYear()).commit();
                    break;
                    case 3:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    //       Log.d("DcumentTabs :",e.getMessage());
                                }
                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new OtherResources()).commit();
                    break;
                }
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (position)
                {
                    case 0:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    //       Log.d("DcumentTabs :",e.getMessage());
                                }
                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new CurrentYear()).commit();
                    break;
                    case 1:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                             //       Log.d("DcumentTabs :",e.getMessage());
                                }

                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new Assignments()).commit();
                    break;
                    case 2:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    //       Log.d("DcumentTabs :",e.getMessage());
                                }
                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new PastYear()).commit();
                    break;
                    case 3:
                        holder.checkBox.setChecked(true);
                        for(int i=0;i<4;i++)
                        {
                            if(i!=position)
                            {
                                try
                                {
                                    View v = recyclerView.findViewHolderForAdapterPosition(i).itemView;
                                    ((AppCompatCheckBox)v.findViewById(R.id.tab_check)).setChecked(false);
                                }
                                catch (NullPointerException e)
                                {
                                    //       Log.d("DcumentTabs :",e.getMessage());
                                }
                            }
                        }
                        fragmentManager.beginTransaction().replace(R.id.content_documents,new OtherResources()).commit();
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
