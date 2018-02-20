package com.payghost.mobileschools.Activities;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.payghost.mobileschools.Fragments.Gallery;
import com.payghost.mobileschools.Fragments.MoreFrag;
import com.payghost.mobileschools.Fragments.RetrieveDocumentsFrag;
import com.payghost.mobileschools.Fragments.RetrieveMessageFrag;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;


public class MainActivity extends AppCompatActivity {
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.content,new AdministratorDashboard()).commit();

        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_message:
                    Config.fragment="messages";
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveMessageFrag()).addToBackStack("messages").commit();
                    return true;
                case R.id.navigation_dash:
                    Config.fragment="images";
                    fragmentManager.beginTransaction().replace(R.id.content,new AdministratorDashboard()).addToBackStack("admin").commit();
                    return true;
                case R.id.navigation_posts:
                    Config.fragment="videos";
                  fragmentManager.beginTransaction().replace(R.id.content,new Gallery()).addToBackStack("gallery").commit();
                    return true;
                case R.id.navigation_documents:
                    Config.fragment="documents";
                  fragmentManager.beginTransaction().replace(R.id.content,new RetrieveDocumentsFrag()).addToBackStack("documents").commit();
                    return true;
                case R.id.navigation_more:
                   fragmentManager.beginTransaction().replace(R.id.content,new MoreFrag()).commit();
                    return true;
            }
            return false;
        }
    };



}
