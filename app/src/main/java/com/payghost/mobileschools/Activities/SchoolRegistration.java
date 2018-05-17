package com.payghost.mobileschools.Activities;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
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
import com.payghost.mobileschools.Adapters.SchoolRegistrationAdapter;
import com.payghost.mobileschools.Fragments.AddSujects;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.SubjAndGrades;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.payghost.mobileschools.Globals.Config.CAMERA_REQUEST;
import static com.payghost.mobileschools.Globals.Config.RESULT_LOAD_IMAGE;

/**
 * Created by Wiseman on 2018-02-15.
 */

public class SchoolRegistration extends AppCompatActivity implements View.OnClickListener,View.OnTouchListener{
    private String JSON_STRING;
    String message,time,title,subject,link;

    LinearLayoutManager linearlayout;
    RecyclerView recyclerView,subjects_recyclerview;
    SchoolRegistrationAdapter recyclerviewAdapter;
    List<SubjectAndGrade> list;
    LinearLayout add_grades,add_grade,all_grades;
    EditText grade,school_name;
    LinearLayout logo_school,register_school_button;
    CircleImageView school_logo;
    Intent intent;
    Bitmap bitmap;
    TextView school_name_validator,grade_validator;
    TextView subject_name;
    public static  int COUNT_DOWN=1000;
    CountDownTimer countDownTimer;
    ProgressDialog myProgressDialog,progress;
    AppCompatImageView mImgCheck;
    AnimatedVectorDrawableCompat avd;
    Drawable drawable;
    RequestQueue requestQueue;
    byte[] bytes;
    String encodeImage="";
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_school_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("School Registration");
        fragmentManager = getFragmentManager();
        pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        logo_school = (LinearLayout)findViewById(R.id.logo_school);
        school_logo = (CircleImageView)findViewById(R.id.school_logo);
        school_logo.setOnClickListener(this);
        add_grades = (LinearLayout)findViewById(R.id.add_grades);
        add_grade = (LinearLayout)findViewById(R.id.add_grade);
    //    all_grades = (LinearLayout)findViewById(R.id.all_grades);
        grade = (EditText)findViewById(R.id.grade);
      //  grade.setOnTouchListener(this);
        school_name = (EditText)findViewById(R.id.name_of_school);
        register_school_button = (LinearLayout)findViewById(R.id.register_school_button);
        register_school_button.setOnClickListener(this);
        school_name_validator = (TextView)findViewById(R.id.school_name_validator);
        subject_name = (TextView)findViewById(R.id.subject_name);
        grade_validator = (TextView)findViewById(R.id.grade_validator);
/*
        subjects_recyclerview = (RecyclerView)findViewById(R.id.subjects_recycler_view);
        recyclerView = (RecyclerView)findViewById(R.id.grades_recycler_view);
        linearlayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);*/
        Config.fragment = "grades";
        Config.modify = "register";
        list = getList();
        /*
        recyclerviewAdapter = new SchoolRegistrationAdapter(getApplicationContext(),recyclerView,list,grade,add_grade,grade_validator);
        recyclerView.setAdapter(recyclerviewAdapter);
        */
        fragmentManager.beginTransaction().replace(R.id.grade_steps,new AddSujects()).commit();
    }
    public List<SubjectAndGrade> getList()
    {
        List<SubjectAndGrade> list = new ArrayList<>();
        list.add(new SubjectAndGrade("Grade R"));

        return  list;
    }
    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.school_logo:
                final Dialog dialog = new Dialog(view.getContext());
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.select_images_layout);
                LinearLayout gallery = (LinearLayout)dialog.findViewById(R.id.gallery);
                LinearLayout camera = (LinearLayout)dialog.findViewById(R.id.camera);
                gallery.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, RESULT_LOAD_IMAGE);
                    }
                });
                camera.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(cameraIntent,CAMERA_REQUEST);
                    }
                });
                dialog.show();
            break;
            case R.id.register_school_button:
               /**/
             //  String grade = subjects.get(1).grade;

                JSONArray object = new JSONArray();
                JSONObject obj = new JSONObject();
                try
                {

                    if(!SubjAndGrades.Grader.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grader);
                    }
                    if(!SubjAndGrades.Grade1.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade1);
                    }
                    if(!SubjAndGrades.Grade2.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade2);
                    }
                    if(!SubjAndGrades.Grade3.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade3);
                    }
                    if(!SubjAndGrades.Grade4.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade4);
                    }
                    if(!SubjAndGrades.Grade5.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade5);
                    }
                    if(!SubjAndGrades.Grade6.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade6);
                    }
                    if(!SubjAndGrades.Grade7.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade7);
                    }
                    if(!SubjAndGrades.Grade8.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade8);
                    }
                    if(!SubjAndGrades.Grade9.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade9);
                    }
                    if(!SubjAndGrades.Grade10.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade10);
                    }
                    if(!SubjAndGrades.Grade11.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade11);
                    }
                    if(!SubjAndGrades.Grade12.equalsIgnoreCase("null"))
                    {
                        object.put(SubjAndGrades.Grade12);
                    }
                    obj.put("json",object);
                }
                catch (Exception ex)
                {

                }
                register(object,school_name.getText().toString(),view.getContext());
            break;
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        switch (view.getId())
        {
            case R.id.grade:
                grade_validator.setVisibility(View.GONE);
            break;
        }
        return false;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        ByteArrayOutputStream stream;
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK )
        {
            Uri photo =  data.getData();
            school_logo.setDrawingCacheEnabled(true);
            school_logo.buildDrawingCache();
            school_logo.setImageURI(photo);

            bitmap = ((BitmapDrawable)school_logo.getDrawable()).getBitmap();
            stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
            bytes = stream.toByteArray();
            encodeImage = Base64.encodeToString(bytes, Base64.DEFAULT);
        }
        else
            if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
            {
                bitmap  = (Bitmap) data.getExtras().get("data");
                school_logo.setDrawingCacheEnabled(true);
                school_logo.buildDrawingCache();
                school_logo.setImageBitmap(bitmap);
                //showing it on the image view widget
            }

        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        bytes = stream.toByteArray();
        encodeImage = Base64.encodeToString(bytes, Base64.DEFAULT);
    }
    public void register(final JSONArray obj, final String name, final Context context)
    {
        editor.putString("school_name",name);
        editor.commit();
        progress = new ProgressDialog(context,R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SCHOOL_REGISTRATION,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(final String response) {

                        progress.dismiss();
                        myProgressDialog = new ProgressDialog(context);
                        myProgressDialog.show();
                        myProgressDialog.setContentView(R.layout.success_layout);
                        mImgCheck = (AppCompatImageView)myProgressDialog.findViewById(R.id.success_image);
                        avd = AnimatedVectorDrawableCompat.create(context,R.drawable.animated_check);
                        // drawable = mImgCheck.getDrawable();
                        // mImgCheck.setImageDrawable(avd);
                        ((Animatable) mImgCheck.getDrawable()).start();
                        countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                            @Override
                            public void onTick(long l) {
                            }
                            @Override
                            public void onFinish(){
                                editor.putString("school",response);
                                editor.commit();
                                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                mImgCheck.setVisibility(View.GONE);
                                myProgressDialog.dismiss();
                                finish();
                            }
                        };
                        countDownTimer.start();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }){
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("json",obj.toString());
                parameters.put("icon",encodeImage);
                parameters.put("name",name);
                parameters.put("action","INSERT");
                parameters.put("device_registration","yep");
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
