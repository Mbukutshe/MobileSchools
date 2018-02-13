package com.payghost.mobileschools;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction transaction = fragmentManager.beginTransaction();


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {


        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_message:
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveMessageFrag()).commit();
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_images:
                    fragmentManager.beginTransaction().replace(R.id.content,new RetrieveImagesFrag()).commit();
                    Toast.makeText(getApplicationContext(),"",Toast.LENGTH_LONG).show();
                    return true;
                case R.id.navigation_videos:
                  fragmentManager.beginTransaction().replace(R.id.content,new RtetrieveVideosFrag()).commit();
                    return true;
                case R.id.navigation_documents:
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
