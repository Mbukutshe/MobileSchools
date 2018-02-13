package com.payghost.mobileschools.Activities;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;

import com.payghost.mobileschools.Fragments.MoreFrag;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;
import com.payghost.mobileschools.Fragments.RetrieveDocumentsFrag;
import com.payghost.mobileschools.Fragments.RetrieveImagesFrag;
import com.payghost.mobileschools.Fragments.RetrieveMessageFrag;
import com.payghost.mobileschools.Fragments.RtetrieveVideosFrag;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_message:
                    Config.fragment="messages";
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveMessageFrag()).commit();
                    return true;
                case R.id.navigation_images:
                    Config.fragment="images";
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveImagesFrag()).commit();
                    return true;
                case R.id.navigation_videos:
                    Config.fragment="videos";
                  fragmentManager.beginTransaction().replace(R.id.content,new RtetrieveVideosFrag()).commit();
                    return true;
                case R.id.navigation_documents:
                    Config.fragment="documents";
                  fragmentManager.beginTransaction().replace(R.id.content,new RetrieveDocumentsFrag()).commit();
                    return true;
                case R.id.navigation_more:
                   fragmentManager.beginTransaction().replace(R.id.content,new MoreFrag()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        transaction.replace(R.id.content,new RetrieveMessageFrag()).commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

}
