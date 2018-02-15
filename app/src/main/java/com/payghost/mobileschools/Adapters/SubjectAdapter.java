package com.payghost.mobileschools.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SubjectAdapter extends RecyclerView.Adapter<SubjectsHolder> implements View.OnClickListener{
    List<SubjectAndGrade>list;
    Context context;
    RecyclerView recyclerView;
    EditText subject;
    LinearLayout btn;
    public SubjectAdapter(Context context, List<SubjectAndGrade>list, RecyclerView recyclerView, EditText subject, LinearLayout btn)
    {
       this.context = context;
       this.recyclerView = recyclerView;
       this.list = list;
       this.subject = subject;
       this.btn = btn;
       this.btn.setOnClickListener(this);
    }

    @Override
    public SubjectsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_subject_layout, parent, false);
        SubjectsHolder vh = new SubjectsHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(SubjectsHolder holder,final int position)
    {
        holder.subject_name.setText(list.get(position).name);
        holder.delete_subject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position,list.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.add_subject:
                list.add(new SubjectAndGrade(subject.getText().toString()));
                subject.setText("");
                notifyDataSetChanged();
                break;
        }
    }
}
