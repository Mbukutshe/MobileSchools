package com.payghost.mobileschools.Activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wiseman on 2017-10-12.
 */

public class Login extends AppCompatActivity implements View.OnClickListener{
    EditText username, password, forgotten_username, forgottern_email, forgotten_cell;
    FrameLayout back, send, forgotten;
    LinearLayout login_layout;
    RelativeLayout login_lay;
    TextInputLayout userframe, passframe;
    String user, pass;
    TextView errorMessage, writeUsername, writePassword, write_user, write_cell, write_email;
    AppCompatButton login;
    RelativeLayout log;
    Animation upAnim;
    RequestQueue requestQueue;
    String surname,name,title,sender,school,access, privileges = "";
    ScrollView scrollView;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    ProgressDialog myProgressDialog,progress;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    AppCompatImageView mImgCheck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        login = (AppCompatButton) findViewById(R.id.btn_login);
        log = (RelativeLayout) findViewById(R.id.login);
        errorMessage = (TextView) findViewById(R.id.error);
        username = (EditText) findViewById(R.id.input_username);
        password = (EditText) findViewById(R.id.input_password);
        writeUsername = (TextView) findViewById(R.id.write_username);
        writePassword = (TextView) findViewById(R.id.write_password);

        userframe = (TextInputLayout) findViewById(R.id.frame_username);
        passframe = (TextInputLayout) findViewById(R.id.frame_password);

        back = (FrameLayout) findViewById(R.id.back);
        send = (FrameLayout) findViewById(R.id.send_forgotten);
        forgotten = (FrameLayout) findViewById(R.id.frame_forgotten);
        forgotten_username = (EditText) findViewById(R.id.input_user);
        forgotten_cell = (EditText) findViewById(R.id.input_cell);
        forgottern_email = (EditText) findViewById(R.id.input_email);
        write_user = (TextView) findViewById(R.id.write_user);
        write_cell = (TextView) findViewById(R.id.write_cell);
        write_email = (TextView) findViewById(R.id.write_cell);
        login_layout = (LinearLayout) findViewById(R.id.login_layout);
        login_lay = (RelativeLayout) findViewById(R.id.login);
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        scrollView.getBackground().setAlpha(200);
        forgotten.getBackground().setAlpha(180);

        upAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.zoom_in);
       // scrollView.startAnimation(upAnim);
        login.setOnClickListener(this);

        upAnim= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.alpha);
        log.startAnimation(upAnim);
        username.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                writeUsername.setVisibility(View.GONE);
                return false;
            }
        });

        password.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                writePassword.setVisibility(View.GONE);
                return false;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onClick(View view) {
       user = username.getText().toString();
       pass = password.getText().toString();

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null && activeNetwork.isConnectedOrConnecting();
        if(!isConnected) {
            Dialog dialog = new Dialog(view.getContext());
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
           // dialog.setContentView(R.layout.no_internet_fragment);
            dialog.show();
        }
        else {

            if (user.isEmpty() || pass.isEmpty()) {
                final Animation textAnim = new AlphaAnimation(0.0f, 1.0f);

                textAnim.setDuration(50);
                textAnim.setStartOffset(20);
                textAnim.setRepeatMode(Animation.REVERSE);
                textAnim.setRepeatCount(6);
                if (user.isEmpty()) {
                    writeUsername.setVisibility(View.VISIBLE);
                    writeUsername.startAnimation(textAnim);
                }
                if (pass.isEmpty()) {
                    writePassword.setVisibility(View.VISIBLE);
                    writePassword.startAnimation(textAnim);
                }
            }
            else
                {
                login(user, pass,view.getContext());
            }
        }
    }

    public void login(final String username, final String password,final Context context)
    {
        errorMessage.setText("");
        myProgressDialog = new ProgressDialog(context,R.style.MyTheme);
        myProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST,Config.LOGIN_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(final String response) {
                myProgressDialog.dismiss();
                        try {

                            JSONObject obj = new JSONObject(response);
                            access = obj.getString("access");
                            privileges = obj.getString("privileges");
                            if(access.equalsIgnoreCase("granted"))
                            {
                                final String username = obj.getString("username");
                                editor.putString("username",username);
                                editor.commit();
                                final String school = obj.getString("school");
                                editor.putString("school",school);
                                editor.commit();
                                editor.putString("staff_id",obj.getString("staff_id"));
                                editor.commit();
                                final String title = obj.getString("title");
                                final String name = obj.getString("name");
                                final String surname = obj.getString("surname");
                                editor.putString("uploader",title+" "+name+" "+surname);
                                editor.commit();

                                mImgCheck = (AppCompatImageView)findViewById(R.id.success_image);
                                mImgCheck.setVisibility(View.VISIBLE);
                                // drawable = mImgCheck.getDrawable();
                                // mImgCheck.setImageDrawable(avd);
                                ((Animatable) mImgCheck.getDrawable()).start();
                                countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                    @Override
                                    public void onTick(long l) {
                                    }
                                    @Override
                                    public void onFinish(){



                                        final Boolean check = pref.getBoolean("check",false);

                                        if(check)
                                        {
                                            startActivity(new Intent(Login.this,MainActivity.class));
                                            finish();
                                        }
                                        else
                                        {
                                            if(privileges.equalsIgnoreCase("principal"))
                                            {
                                                if(!school.equalsIgnoreCase(""))
                                                {
                                                    Config.granted = true;
                                                    editor.putString("school",school);
                                                    editor.commit();
                                                    editor.putString("who_log_on","Principal");
                                                    editor.commit();
                                                    startActivity(new Intent(Login.this,MainActivity.class));
                                                    finish();
                                                }
                                                else
                                                {
                                                    editor.putString("who_log_on","Principal");
                                                    editor.commit();
                                                    editor.putString("uploader","Principal");
                                                    editor.commit();
                                                    Config.granted = true;
                                                    Intent mainIntent = new Intent(getApplicationContext(),SchoolRegistration.class);
                                                    Login.this.startActivity(mainIntent);
                                                    Login.this.finish();
                                                }

                                            }
                                            else
                                            if(privileges.equalsIgnoreCase("admin"))
                                            {
                                                editor.putString("who_log_on","admin");
                                                editor.commit();
                                                startActivity(new Intent(Login.this,MainActivity.class));
                                                finish();
                                            }
                                            else
                                            if(privileges.equalsIgnoreCase("instructor"))
                                            {
                                                editor.putString("who_log_on","instructor");
                                                editor.commit();
                                                startActivity(new Intent(Login.this,MainActivity.class));
                                                finish();
                                            }
                                        }
                                        finish();
                                    }
                                };
                                countDownTimer.start();
                            }
                            else
                            if(access.equalsIgnoreCase("denied"))
                            {
                                mImgCheck = (AppCompatImageView)findViewById(R.id.error_image);
                                mImgCheck.setVisibility(View.VISIBLE);
                               // avd = AnimatedVectorDrawableCompat.create(context,R.drawable.animated_check);
                                // drawable = mImgCheck.getDrawable();
                                // mImgCheck.setImageDrawable(avd);
                                ((Animatable) mImgCheck.getDrawable()).start();
                                countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                                    @Override
                                    public void onTick(long l) {
                                    }
                                    @Override
                                    public void onFinish() {
                                        mImgCheck.setVisibility(View.GONE);
                                        final Animation textAnim = new AlphaAnimation(0.0f, 1.0f);
                                        textAnim.setDuration(50);
                                        textAnim.setStartOffset(20);
                                        textAnim.setRepeatMode(Animation.REVERSE);
                                        textAnim.setRepeatCount(6);
                                        errorMessage.setText("Incorrect Username or Password!");
                                        errorMessage.startAnimation(textAnim);
                                    }
                                };
                                countDownTimer.start();
                            }
                        }
                        catch (JSONException ex) {
                            Toast.makeText(getApplication(),ex.getMessage().toString(), Toast.LENGTH_LONG).show();
                        }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }

        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("lock", username);
                parameters.put("key", password);
                parameters.put("device","device");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(request);
    }
}
