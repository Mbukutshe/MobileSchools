package com.payghost.mobileschools.Activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.payghost.mobileschools.Adapters.SchoolRegistrationAdapter;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.Objects.SubjectAndGrade;
import com.payghost.mobileschools.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.payghost.mobileschools.Globals.Config.CAMERA_REQUEST;
import static com.payghost.mobileschools.Globals.Config.IS_GRADES_VISIBLE;
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
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_school_layout);

        logo_school = (LinearLayout)findViewById(R.id.logo_school);
        school_logo = (CircleImageView)findViewById(R.id.school_logo);
        school_logo.setOnClickListener(this);
        add_grades = (LinearLayout)findViewById(R.id.add_grades);
        add_grade = (LinearLayout)findViewById(R.id.add_grade);
        all_grades = (LinearLayout)findViewById(R.id.all_grades);
        grade = (EditText)findViewById(R.id.grade);
        grade.setOnTouchListener(this);
        school_name = (EditText)findViewById(R.id.name_of_school);
        register_school_button = (LinearLayout)findViewById(R.id.register_school_button);
        register_school_button.setOnClickListener(this);
        school_name_validator = (TextView)findViewById(R.id.school_name_validator);
        subject_name = (TextView)findViewById(R.id.subject_name);
        grade_validator = (TextView)findViewById(R.id.grade_validator);

        subjects_recyclerview = (RecyclerView)findViewById(R.id.subjects_recycler_view);
        recyclerView = (RecyclerView)findViewById(R.id.grades_recycler_view);
        linearlayout = new LinearLayoutManager(getApplicationContext());
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearlayout);
        Config.fragment = "grades";
        list = getList();
        add_grades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                if(IS_GRADES_VISIBLE==0)
                {
                    all_grades.setVisibility(View.VISIBLE);
                    recyclerviewAdapter = new SchoolRegistrationAdapter(getApplicationContext(),list,grade,add_grade,school_name,register_school_button,school_name_validator,grade_validator);
                    recyclerView.setAdapter(recyclerviewAdapter);
                    IS_GRADES_VISIBLE = 1;
                }

            }
        });
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
        }
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        switch (view.getId())
        {
            case R.id.grade:
                grade_validator.setVisibility(View.GONE);
            break;
        }
        return false;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ByteArrayOutputStream bytes;
        if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK )
        {
            Uri photo =  data.getData();
            school_logo.setDrawingCacheEnabled(true);
            school_logo.buildDrawingCache();
            school_logo.setImageURI(photo);

            bitmap = ((BitmapDrawable) school_logo.getDrawable()).getBitmap();
            bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        }
        else
        if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK)
        {

            bitmap  = (Bitmap) data.getExtras().get("data");
            school_logo.setDrawingCacheEnabled(true);
            school_logo.buildDrawingCache();
            school_logo.setImageBitmap(bitmap);
            //showing it on the image view widget
            bytes = new ByteArrayOutputStream();
            bitmap .compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        }
    }
}
