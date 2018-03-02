package com.payghost.mobileschools.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.payghost.mobileschools.Fragments.SchoolsList;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-25.
 */

public class SchoolsAndGrades extends AppCompatActivity{
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schools_and_grades);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.schools_and_grades,new SchoolsList()).commit();
    }
}
