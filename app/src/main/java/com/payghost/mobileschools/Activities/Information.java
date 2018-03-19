package com.payghost.mobileschools.Activities;

import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.payghost.mobileschools.Fragments.RealSplash;
import com.payghost.mobileschools.Fragments.Splash;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-24.
 */

public class Information extends AppCompatActivity{
    FragmentManager fragmentManager;
    SharedPreferences pref;
    LinearLayout splash;
    ImageView image;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.information_layout);
        splash = (LinearLayout)findViewById(R.id.splash);
        splash.getBackground().setAlpha(200);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.information_fragment,new RealSplash()).commit();
        pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
        final Boolean check = pref.getBoolean("check",false);
        countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
            @Override
            public void onTick(long l) {

            }
            @Override
            public void onFinish() {
                if(check){
                    startActivity(new Intent(Information.this,MainActivity.class));
                    finish();
                }
                else
                {
                    fragmentManager.beginTransaction().replace(R.id.information_fragment,new Splash()).commit();
                }
            }
        };
        countDownTimer.start();


    }
}
