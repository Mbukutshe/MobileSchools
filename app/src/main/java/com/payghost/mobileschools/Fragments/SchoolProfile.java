package com.payghost.mobileschools.Fragments;

import android.app.Dialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
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
import android.support.design.widget.AppBarLayout;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
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
import com.payghost.mobileschools.Database.SqliteController;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.RetrieveService;
import com.payghost.mobileschools.Objects.SubjAndGrades;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.payghost.mobileschools.Globals.Config.CAMERA_REQUEST;
import static com.payghost.mobileschools.Globals.Config.RESULT_LOAD_IMAGE;
import static com.payghost.mobileschools.Globals.Config.modify;

public class SchoolProfile extends Fragment implements View.OnClickListener,View.OnTouchListener{
    View view;
    Animation anim;
    LinearLayout layout;
    List<SubjectAndGrade> list;
    LinearLayout add_grades,add_grade,all_grades;
    EditText grade,school_name;
    LinearLayout logo_school,register_school_button;
    CircleImageView school_logo;
    Intent intent;
    Bitmap bitmap;
    TextView school_name_validator,grade_validator;
    TextView subject_name,test;
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
    SqliteController controller;
    static String gradeName="";
    String differ="old";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.register_school_layout, container, false);
        ((AppBarLayout)view.findViewById(R.id.app_bar)).setVisibility(View.GONE);
        requestQueue = Volley.newRequestQueue(view.getContext());
        controller = new SqliteController(view.getContext());
        layout = (LinearLayout) view.findViewById(R.id.register_school);
        anim = new Animation(view.getContext());
        anim.fromSides(layout);
        fragmentManager = getFragmentManager();
        pref = getActivity().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        requestQueue = Volley.newRequestQueue(getActivity());
        logo_school = (LinearLayout)view.findViewById(R.id.logo_school);
        school_logo = (CircleImageView)view.findViewById(R.id.school_logo);
        school_logo.setOnClickListener(this);
        add_grades = (LinearLayout)view.findViewById(R.id.add_grades);
        add_grade = (LinearLayout)view.findViewById(R.id.add_grade);
        all_grades = (LinearLayout)view.findViewById(R.id.all_grades);
        grade = (EditText)view.findViewById(R.id.grade);
      //  grade.setOnTouchListener(this);
        school_name = (EditText)view.findViewById(R.id.name_of_school);
        register_school_button = (LinearLayout)view.findViewById(R.id.register_school_button);
        register_school_button.setOnClickListener(this);
        school_name_validator = (TextView)view.findViewById(R.id.school_name_validator);
        subject_name = (TextView)view.findViewById(R.id.subject_name);
        grade_validator = (TextView)view.findViewById(R.id.grade_validator);

        ((TextView)view.findViewById(R.id.btn_login)).setText("Update");
        test =  (TextView)view.findViewById(R.id.instruction);
        test.setText("Only Grades below : click \"Next\" to view subjects");
        modify="update";
        fetch();
        return view;
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

                JSONArray object = new JSONArray();
                JSONArray subj = null;
                JSONArray second= null;
                JSONObject subjects = null;
                second= new JSONArray();
                List<SubjAndGrades> allItems = new ArrayList<>();
                Cursor data = new SqliteController(getActivity().getApplicationContext()).getAllRecords();
                if(data.moveToFirst())
                {
                    do
                    {
                        allItems.add(new SubjAndGrades(data.getString(3),data.getString(4),data.getString(5),data.getString(6),data.getString(7),data.getString(8)));
                    }
                    while(data.moveToNext());

                }

                JSONObject obj = new JSONObject();
                try
                {

                    subj = new JSONArray();
                    for(int i=0;i<allItems.size();i++)
                    {
                        subjects = new JSONObject();
                        subjects.put("GradeId",allItems.get(i).GradeId);
                        subjects.put("GradeName",allItems.get(i).GradeName);
                        subjects.put("SubjectId",allItems.get(i).SubjectId);
                        subjects.put("SubjectName",allItems.get(i).SubjectName);
                        subjects.put("SubjectAction",allItems.get(i).SubjectAction);
                        subjects.put("GradeAction",allItems.get(i).GradeAction);
                        subj.put(subjects);
                    }

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
                    obj.put("json",second);

                }
                catch (Exception ex)
                {

                }
                register(subj,school_name.getText().toString(),view.getContext());
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
        if(!encodeImage.equalsIgnoreCase(""))
        {
            differ="fresh";
        }
    }

    public List<SubjectAndGrade> getList()
    {
        List<SubjectAndGrade> list = new ArrayList<>();
        list.add(new SubjectAndGrade("Grade R"));
        return  list;
    }
    public void fetch()
    {
        myProgressDialog = new ProgressDialog(view.getContext(),R.style.MyTheme);
        myProgressDialog.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        myProgressDialog.show();
        myProgressDialog.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SCHOOL_PROFILE,

                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {

                        JSONObject jsonObject = null;
                        List<RetrieveService> arrList = new ArrayList<RetrieveService>();
                        try {
                            jsonObject = new JSONObject(response);
                            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);
                            Config.SchoolProfile = response;
                            if(result.length()>0)
                            {
                                Picasso.with(view.getContext()).load(result.getJSONObject(0).getString("SchoolLogo")).into(school_logo);
                                for (int i = 0; i < result.length(); i++)
                                {
                                    JSONObject jo = result.getJSONObject(i);
                                    school_name.setText(jo.getString("SchoolName"));
                                    controller.insertProfile(jo.getString("SchoolId"),jo.getString("SchoolName"),jo.getString("SchoolLogo"),jo.getString("GradeId"),
                                            jo.getString("GradeName"),jo.getString("SubjectId"),jo.getString("SubjectName"),jo.getString("SubjectAction"),jo.getString("GradeAction"));
                                    if(Config.subjectList.size()==0)
                                    {
                                        Config.subjectList.add(new SubjectAndGrade(jo.getString("GradeName"),jo.getString("GradeId"),jo.getString("SubjectId"),""));
                                    }
                                    else
                                    {
                                        boolean test = false;
                                        for(int j=0;j<Config.subjectList.size();j++)
                                        {
                                            if( Config.subjectList.get(j).name.equalsIgnoreCase(jo.getString("GradeName")))
                                            {
                                                test=true;
                                            }
                                        }
                                        if(test==false)
                                        {
                                            Config.subjectList.add(new SubjectAndGrade(jo.getString("GradeName"),jo.getString("GradeId"),jo.getString("SubjectId"),""));
                                        }
                                    }

                                }
                            }
                            else
                            {

                            }
                            fragmentManager.beginTransaction().replace(R.id.grade_steps,new AddSujects()).commit();
                        }
                        catch (JSONException e)
                        {
                            e.printStackTrace();
                        }
                        myProgressDialog.dismiss();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                })
        {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("school",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
    public void register(final JSONArray obj, final String name, final Context context)
    {
        progress = new ProgressDialog(view.getContext(),R.style.MyTheme);
        progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
        progress.show();
        progress.setContentView(R.layout.progress);
        ProgressBar progressBar = (ProgressBar)progress.findViewById(R.id.progressBar);
        progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

        StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SCHOOL_PROFILE_UPDATE,

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
                        avd = AnimatedVectorDrawableCompat.create(context,R.drawable.animated_check);
                        // drawable = mImgCheck.getDrawable();
                        // mImgCheck.setImageDrawable(avd);
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
                                //startActivity(new Intent(getApplicationContext(),MainActivity.class));
                                mImgCheck.setVisibility(View.GONE);
                                myProgressDialog.dismiss();
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
                parameters.put("json",obj.toString());
                parameters.put("icon",encodeImage);
                parameters.put("name",name);
                parameters.put("action","UPDATE");
                parameters.put("differ",differ);
                parameters.put("school",pref.getString("school",""));
                return parameters;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(request);
    }
}
