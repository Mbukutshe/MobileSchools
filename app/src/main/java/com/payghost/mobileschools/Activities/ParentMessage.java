package com.payghost.mobileschools.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.R;

import java.util.HashMap;
import java.util.Map;

import static com.payghost.mobileschools.Globals.Config.URL_SEND_NEW;

public class ParentMessage  extends AppCompatActivity implements View.OnClickListener{
    EditText subject,message;
    LinearLayout button;
    String sbj="",msg="";
    TextView text;
    ProgressDialog progress,myProgressDialog;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    RequestQueue requestQueue;
    AppCompatImageView mImgCheck;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.parental_form_layout);
        requestQueue = Volley.newRequestQueue(this);
        pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
        text = (TextView)findViewById(R.id.text);
        subject = (EditText)findViewById(R.id.subject_message);
        message = (EditText)findViewById(R.id.text_message);
        button = (LinearLayout)findViewById(R.id.send_button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.send_button:
                sbj=subject.getText().toString();
                msg = message.getText().toString();
                if(!(sbj.isEmpty()||msg.isEmpty()))
                {
                    send(view.getContext(),sbj,msg);
                    subject.setText("");
                    message.setText("");
                    text.setText("");
                }
                else
                {
                    final Animation textAnim = new AlphaAnimation(0.0f, 1.0f);
                    textAnim.setDuration(50);
                    textAnim.setStartOffset(20);
                    textAnim.setRepeatMode(Animation.REVERSE);
                    textAnim.setRepeatCount(6);
                    text.setText("All fields are required !!");
                    text.startAnimation(textAnim);
                }
            break;
        }
    }
    public void send(final Context context, final String subject, final String message)
    {

        progress = new ProgressDialog(context,R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, URL_SEND_NEW,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(final String response)
                    {
                        progress.dismiss();
                        myProgressDialog = new ProgressDialog(context);
                        myProgressDialog.show();
                        myProgressDialog.setContentView(R.layout.success_layout);
                        mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.success_image);
                        ((Animatable) mImgCheck.getDrawable()).start();
                        countDownTimer = new CountDownTimer(COUNT_DOWN,16)
                        {
                            @Override
                            public void onTick(long l)
                            {
                            }
                            @Override
                            public void onFinish()
                            {
                                mImgCheck.setVisibility(View.GONE);
                                myProgressDialog.dismiss();
                                finish();
                            }
                        };
                        countDownTimer.start();
                        // controller.dropTable();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("subject",subject);
                parameters.put("message",message);
                parameters.put("school",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
