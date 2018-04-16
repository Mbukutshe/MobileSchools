package com.payghost.mobileschools.Fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.payghost.mobileschools.Holders.SubjectsHolder;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.util.List;

/**
 * Created by Wiseman on 2018-04-03.
 */

public class AddAdapter extends RecyclerView.Adapter<SubjectsHolder>{
    Context context;
    List<SubjectAndGrade>list;
    public AddAdapter(Context context, List<SubjectAndGrade>list)
    {
        this.context = context;
        this.list = list;
    }

    @Override
    public SubjectsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_grade_laayout, parent, false);
        SubjectsHolder vh = new SubjectsHolder(view);
        return vh;
    }

    @Override
    public void onBindViewHolder(SubjectsHolder holder, int position)
    {
        holder.subject_name.setText("");
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }
}
