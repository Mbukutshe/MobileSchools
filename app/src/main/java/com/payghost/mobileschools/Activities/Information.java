package com.payghost.mobileschools.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.payghost.mobileschools.Fragments.Splash;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-24.
 */

public class Information extends AppCompatActivity{
    FragmentManager fragmentManager;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.information_fragment,new Splash()).commit();
    }
}
