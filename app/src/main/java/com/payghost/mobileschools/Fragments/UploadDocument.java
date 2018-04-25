package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSpinner;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class UploadDocument extends Fragment implements View.OnClickListener{
    View view;
    Animation anim;
    RequestQueue requestQueue;
    RelativeLayout document_layout;
    LinearLayout file_choose,upload,staff,parents,learner,learner_grade_layout,type_grade_layout,instructor_grade_layout;
    EditText subject,description;
    TextView choose_icon;
    Uri filePath;
    AppCompatCheckBox all_check,staff_check,parents_check,learner_check;
    AppCompatSpinner staff_grade,learner_grade,learner_subject,type_grade;
    String all_,staff_="",parents_="",learner_="",staf_grade="All",learners_grade="All",learners_subject="All",type="General";
    private static final int RESULT_LOAD_DOCUMENT=2;
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    List<String> spinnerArray,subjectArray,typeArray;
    ProgressDialog progress;
    int serverResponseCode = 0;
    static String uniqueKey;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_document, container, false);
        pref = view.getContext().getSharedPreferences("Users", Context.MODE_PRIVATE);
        editor = pref.edit();
        document_layout = (RelativeLayout)view.findViewById(R.id.document);
        anim = new Animation(view.getContext());
        anim.fromSides(document_layout);

        learner_grade_layout = (LinearLayout)view.findViewById(R.id.learner_grade_layout);
        instructor_grade_layout = (LinearLayout)view.findViewById(R.id.instructor_grade_layout);
        type_grade_layout = (LinearLayout)view.findViewById(R.id.type_grade_layout);

        file_choose = (LinearLayout)view.findViewById(R.id.file_choose);
        file_choose.setOnClickListener(this);
        upload  = (LinearLayout)view.findViewById(R.id.upload_button);
        upload.setOnClickListener(this);
        subject = (EditText)view.findViewById(R.id.text_subject);
        description = (EditText)view.findViewById(R.id.text_description);
        choose_icon = (TextView)view.findViewById(R.id.choose_icon);

        staff = (LinearLayout)view.findViewById(R.id.staff);
        staff.setOnClickListener(this);
        parents = (LinearLayout)view.findViewById(R.id.parents);
        parents.setOnClickListener(this);
        learner = (LinearLayout)view.findViewById(R.id.learner);
        learner.setOnClickListener(this);

        staff_check = (AppCompatCheckBox)view.findViewById(R.id.staff_check);
        staff_check.setOnClickListener(this);
        parents_check = (AppCompatCheckBox)view.findViewById(R.id.parents_check);
        parents_check.setOnClickListener(this);
        learner_check = (AppCompatCheckBox)view.findViewById(R.id.learner_check);
        learner_check.setOnClickListener(this);

        staff_grade = (AppCompatSpinner)view.findViewById(R.id.staff_grade);
        learner_grade = (AppCompatSpinner)view.findViewById(R.id.learner_grade);
        learner_subject = (AppCompatSpinner)view.findViewById(R.id.learner_subject);
        type_grade = (AppCompatSpinner)view.findViewById(R.id.type_grade);

        spinnerArray =  new ArrayList<String>();
        subjectArray =  new ArrayList<String>();
        typeArray =  new ArrayList<String>();
        typeArray.add("General");
        typeArray.add("Homework");
        typeArray.add("Assignment");
        typeArray.add("Past Year Resource");
        typeArray.add("Other");
        spinnerArray.add("All");
        subjectArray.add("All");
        for(int i=0; i<pref.getInt("grade_size",0);i++)
        {
            spinnerArray.add(pref.getString("grade:"+i,""));
        }
        for(int i=0; i<pref.getInt("subject_size",0);i++)
        {
            subjectArray.add(pref.getString("subject:"+i,""));
        }

        //INSTRUCTORS DROPDOWN GRADES

        ArrayAdapter<String> adaptor =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adaptor.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        staff_grade.setAdapter(adaptor);
        staff_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                staf_grade = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                staf_grade = adapterView.getItemAtPosition(0).toString();
            }
        });
        //LEARNERS DROPDOWN GRADES

        ArrayAdapter<String> adapter =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        learner_grade.setAdapter(adapter);
        learner_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learners_grade = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                learners_grade = adapterView.getItemAtPosition(0).toString();
            }
        });

        ArrayAdapter<String> adaptar =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, subjectArray);
        adaptar.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        learner_subject.setAdapter(adaptar);
        learner_subject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                learners_subject = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                learners_subject = adapterView.getItemAtPosition(0).toString();
            }
        });
        ArrayAdapter<String> typeAdapter =  new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_spinner_item, typeArray);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        type_grade.setAdapter(typeAdapter);
        type_grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
            {
                type = adapterView.getItemAtPosition(i).toString();
                switch(i)
                {
                    case 0:
                        type = "general";
                    break;
                    case 1:
                        type = "0";
                    break;
                    case 2:
                        type = "1";
                    break;
                    case 3:
                        type = "2";
                    break;
                    case 4:
                        type = "3";
                    break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView)
            {
                type = adapterView.getItemAtPosition(0).toString();
            }
        });
        switch(pref.getString("who_log_on",""))
        {
            case "instructor":
                staff.setVisibility(View.GONE);
                parents.setVisibility(View.GONE);
                learner_check.setChecked(true);
                learner_grade_layout.setVisibility(View.VISIBLE);
                ((TextView)view.findViewById(R.id.title_text)).setText("Select Grade and Subject");
            break;
        }
        if(!pref.getString("who_log_on","").equalsIgnoreCase("instructor"))
        {
            staff.setVisibility(View.VISIBLE);
            parents.setVisibility(View.VISIBLE);
        }
        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.upload_button:
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmssZ");
                String currentDateandTime = sdf.format(new Date());
                Random random = new Random();
                uniqueKey = random.nextInt(2980-10)+""+currentDateandTime;

                progress = new ProgressDialog(view.getContext(),R.style.MyTheme);
                progress.setProgressStyle(android.R.style.Widget_ProgressBar_Small);
                progress.show();
                progress.setContentView(R.layout.progress);
                ProgressBar progressBar = (ProgressBar) progress.findViewById(R.id.progressBar);
                progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);

                uploadFile();

            break;
            case  R.id.file_choose:
                Intent intent = new Intent();
                intent.setType("application/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Choose a document"), 2);
            break;
            case R.id.staff:
                if(staff_check.isChecked())
                {
                    staff_check.setChecked(false);
                    instructor_grade_layout.setVisibility(View.GONE);
                    staff_ = "";
                }
                else
                {
                    staff_check.setChecked(true);
                    instructor_grade_layout.setVisibility(View.VISIBLE);
                    staff_ = "instructor";
                }
            break;
            case R.id.staff_check:

                if(staff_check.isChecked())
                {
                    staff_ = "instructor";
                    instructor_grade_layout.setVisibility(View.VISIBLE);
                }
                else
                {
                    instructor_grade_layout.setVisibility(View.GONE);
                    staff_ = "";
                }
            break;
            case R.id.parents:

                if(parents_check.isChecked())
                {
                    parents_ = "";
                    parents_check.setChecked(false);
                }
                else
                {
                    parents_ = "parent";
                    parents_check.setChecked(true);
                }
            break;
            case R.id.parents_check:
                if(parents_check.isChecked())
                {
                    parents_ = "parent";
                }
                else
                {
                    parents_ = "";
                }
            break;
            case R.id.learner:
                if(!pref.getString("who_log_on","").equalsIgnoreCase("instructor")) {
                    if (learner_check.isChecked()) {
                        learner_ = "";
                        learner_grade_layout.setVisibility(View.GONE);
                        type_grade_layout.setVisibility(View.GONE);
                        learner_check.setChecked(false);
                    }
                    else
                    {
                        learner_ = "learner";
                        learner_grade_layout.setVisibility(View.VISIBLE);
                        type_grade_layout.setVisibility(View.VISIBLE);
                        learner_check.setChecked(true);
                    }
                }
            break;
            case R.id.learner_check:
                if(!pref.getString("who_log_on","").equalsIgnoreCase("instructor"))
                {
                    if (learner_check.isChecked())
                    {
                        learner_ = "learner";
                        learner_grade_layout.setVisibility(View.VISIBLE);
                        type_grade_layout.setVisibility(View.VISIBLE);
                    }
                    else
                    {
                        learner_grade_layout.setVisibility(View.GONE);
                        type_grade_layout.setVisibility(View.GONE);
                        learner_ = "";
                    }
                }
                else
                {
                    learner_check.setChecked(true);
                }
            break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_LOAD_DOCUMENT && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            filePath = data.getData();
            choose_icon.setBackgroundResource(R.drawable.successfile);
        }

    }
    public void sendDocument()
    {

        try
        {
            new MultipartUploadRequest(view.getContext(), Config.URL_SEND_DOCUMENT)
                    .addFileToUpload(filePath.getPath(), "uploaded_file")
                    .addParameter("title",subject.getText().toString())
                    .addParameter("description", description.getText().toString())
                    .addParameter("staff", staff_)
                    .addParameter("learner", learner_)
                    .addParameter("parent", parents_)
                    .addParameter("school",pref.getString("school",""))
                    .addParameter("subject",learners_subject)
                    .addParameter("learner_grade",learners_grade)
                    .addParameter("staff_grade",staf_grade)
                    .setMethod("POST")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload();
        }
        catch (Exception ex) {
            Toast.makeText(view.getContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
    public int uploadFile() {

		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);

        String fileName = filePath.getPath();
        fileName = fileName.substring(fileName.lastIndexOf("/"));

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;

            try {

                // open a URL connection to the Servlet
                FileInputStream fileInputStream = new FileInputStream(new File(filePath.getPath()));
                URL url = new URL(Config.URL_SEND_DOCUMENT);

                // Open a HTTP  connection to  the URL
                conn = (HttpURLConnection) url.openConnection();
                conn.setDoInput(true); // Allow Inputs
                conn.setDoOutput(true); // Allow Outputs
                conn.setUseCaches(false); // Don't use a Cached Copy
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Connection", "Keep-Alive");
                conn.setRequestProperty("ENCTYPE", "multipart/form-data");
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
                conn.setConnectTimeout(1000);

                dos = new DataOutputStream(conn.getOutputStream());

                dos.writeBytes(twoHyphens + boundary + lineEnd);
                dos.writeBytes("Content-Disposition: form-data; name=\"unique\"" + lineEnd);
                dos.writeBytes("Content-Type: text/plain;charset=UTF-8" + lineEnd);
                dos.writeBytes("Content-Length: " +uniqueKey.length() + lineEnd);
                dos.writeBytes(lineEnd);
                dos.writeBytes( uniqueKey + lineEnd);
                dos.writeBytes(twoHyphens + boundary + lineEnd);

                dos.writeBytes("Content-Disposition: form-data;name=\"uploaded_file\";filename=\""
                        + fileName + "\"" + lineEnd);
                dos.writeBytes(lineEnd);



                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available();

                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                buffer = new byte[bufferSize];

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize);
                    bytesAvailable = fileInputStream.available();
                    bufferSize = Math.min(bytesAvailable, maxBufferSize);
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize);

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
                // Responses from the server (code and message)
                serverResponseCode = conn.getResponseCode();
                String serverResponseMessage = conn.getResponseMessage();

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode);

                if (serverResponseCode == 200)
                {
                    StringRequest request = new StringRequest(Request.Method.POST,Config.INSERT_POST_URL, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            progress.dismiss();
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }

                    }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("title",subject.getText().toString());
                            parameters.put("description", description.getText().toString());
                            parameters.put("staff", staff_);
                            parameters.put("learner", learner_);
                            parameters.put("parent", parents_);
                            parameters.put("school",pref.getString("school",""));
                            parameters.put("subject",learners_subject);
                            parameters.put("learner_grade",learners_grade);
                            parameters.put("staff_grade",staf_grade);
                            parameters.put("unique",uniqueKey);
                            parameters.put("type",type);
                        return parameters;
                        }
                    };
                    request.setRetryPolicy(new DefaultRetryPolicy(0,0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue = Volley.newRequestQueue(view.getContext());
                    requestQueue.add(request);
                }

                //close the streams //
                fileInputStream.close();
                dos.flush();
                dos.close();

            } catch (MalformedURLException ex) {

                progress.dismiss();
                Toast.makeText(view.getContext(), "First Catch catch : "+ex + "",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {

                progress.dismiss();
                Toast.makeText(view.getContext(), "last catch : "+e + "",
                        Toast.LENGTH_SHORT).show();
            }
            return serverResponseCode;
        }
}

