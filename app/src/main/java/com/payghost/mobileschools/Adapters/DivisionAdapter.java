package com.payghost.mobileschools.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Holders.DivisionHolder;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-04-01.
 */

public class DivisionAdapter extends RecyclerView.Adapter<DivisionHolder>
{
    Context context;
    List<SubjectAndGrade>list;
    String divisions="";
    public DivisionAdapter(Context context, List<SubjectAndGrade>list)
    {
        this.context=context;
        this.list = list;
    }

    @Override
    public DivisionHolder onCreateViewHolder(ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.div_items, parent, false);
        return new DivisionHolder(view);
    }

    @Override
    public void onBindViewHolder(final DivisionHolder holder, final int position)
    {
        holder.textView.setText(list.get(position).name);
        holder.checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(holder.checkBox.isChecked())
                {
                   // divisions+=","+list.get(position).name;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
