package com.payghost.mobileschools.Activities;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.payghost.mobileschools.Functions.Functions;
import com.payghost.mobileschools.Functions.Visible;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Wiseman on 2018-02-18.
 */

public class MediaUpload extends AppCompatActivity implements View.OnClickListener{
        LinearLayout upload_Image,layout_choose;
        FrameLayout layout_Caption;
        FrameLayout upload_background,camera,gallery,video;
        ImageView upload_image_view;
        EditText caption,desc;
        FloatingActionButton fab_video,fab_image,fab_camera;
        FrameLayout fab_send;
        private static final int CAMERA_REQUEST = 1888;
        private static final int RESULT_LOAD_IMAGE=1;
        private static final int RESULT_LOAD_VIDEO=2;
        Visible visible;
        Functions functions;
        ProgressDialog myProgressDialog;
        RequestQueue requestQueue;
        Bitmap bitmap;
        Uri filePath;
        Animation anim;
        String encodedImage;
        SharedPreferences pref;
        SharedPreferences.Editor editor;
        com.payghost.mobileschools.Functions.Animation animation;
        public static  int COUNT_DOWN=500;
        CountDownTimer countDownTimer;
        String which_one;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.upload_media_layout);
            functions = new Functions(this);
            requestQueue = Volley.newRequestQueue(this);
            pref = getSharedPreferences("Users", Context.MODE_PRIVATE);
            caption = (EditText)findViewById(R.id.subject);
            desc = (EditText)findViewById(R.id.description);
            upload_background = (FrameLayout)findViewById(R.id.upload_background);
            upload_Image = (LinearLayout)findViewById(R.id.layout_image);
            layout_choose = (LinearLayout)findViewById(R.id.layout_choose);
            layout_Caption = (FrameLayout)findViewById(R.id.layout_caption);
            layout_Caption.getBackground().setAlpha(180);


            upload_image_view = (ImageView)findViewById(R.id.upload_image_view);
            fab_image = (FloatingActionButton)findViewById(R.id.fab_image);
            fab_video = (FloatingActionButton)findViewById(R.id.fab_video);
            fab_send = (FrameLayout) findViewById(R.id.fab_send);
            fab_camera = (FloatingActionButton)findViewById(R.id.fab_camera);
            fab_camera.setOnClickListener(this);
            fab_image.setOnClickListener(this);
            fab_video.setOnClickListener(this);
            fab_send.setOnClickListener(this);
            visible = new Visible();
            functions.setDoneAction(caption);
            myProgressDialog = new ProgressDialog(this);

            camera = (FrameLayout)findViewById(R.id.camera);
            gallery = (FrameLayout)findViewById(R.id.gallery);
            video = (FrameLayout)findViewById(R.id.video);
            animation = new com.payghost.mobileschools.Functions.Animation(getApplicationContext());

            anim = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.from_sides);

            upload_background.setVisibility(View.VISIBLE);
            upload_background.startAnimation(anim);
            countDownTimer = new CountDownTimer(COUNT_DOWN,16) {
                @Override
                public void onTick(long l) {

                }
                @Override
                public void onFinish() {
                    animation.setAlphaAnimation(camera);
                    animation.setAlphaAnimation(gallery);
                    animation.setAlphaAnimation(video);
                }
            };
            countDownTimer.start();
        }

        @Override
        public void onClick(View view)
        {
            Intent intent=null;

            switch(view.getId())
            {
                case R.id.fab_image:
                    intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent,RESULT_LOAD_IMAGE);
                    which_one="image";
                break;
                case R.id.fab_camera:
                    Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(cameraIntent,CAMERA_REQUEST);
                    which_one="camera";
                break;
                case R.id.fab_video:
                    intent = new Intent();
                    intent.setType("video/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    startActivityForResult(Intent.createChooser(intent, "Choose a Video"), 2);
                    which_one="video";
                break;
                case R.id.fab_send:
                    if(which_one.equalsIgnoreCase("image"))
                    {
                        uploadImage();
                    }
                    else
                        if(which_one.equalsIgnoreCase("camera"))
                        {
                            uploadImage();
                        }
                        else
                            if(which_one.equalsIgnoreCase("video"))
                            {
                                uploadVideo();
                            }
                break;
            }
        }
        protected void onActivityResult(int requestCode, int resultCode, Intent data)
        {
                super.onActivityResult(requestCode,resultCode,data);
                ByteArrayOutputStream bytes = null;
                if(requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK )
                {
                    Uri photo =  data.getData();
                    visible.gone(layout_choose);
                    visible.visible(upload_Image);
                    visible.visible(layout_Caption);
                    upload_background.setBackgroundColor(Color.BLACK);
                    upload_background.getBackground().setAlpha(150);
                    upload_image_view.setDrawingCacheEnabled(true);
                    upload_image_view.buildDrawingCache();
                    upload_image_view.setImageURI(photo);

                    bitmap = ((BitmapDrawable) upload_image_view.getDrawable()).getBitmap();
                    bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                }
                else
                    if(requestCode == CAMERA_REQUEST && resultCode == RESULT_OK )
                    {

                        bitmap  = (Bitmap) data.getExtras().get("data");
                        visible.gone(layout_choose);
                        visible.visible(upload_Image);
                        visible.visible(layout_Caption);
                        upload_background.setBackgroundColor(Color.BLACK);
                        upload_background.getBackground().setAlpha(150);
                        caption.getBackground().setAlpha(0);
                        upload_image_view.setDrawingCacheEnabled(true);
                        upload_image_view.buildDrawingCache();
                        upload_image_view.setImageBitmap(bitmap);
                        //showing it on the image view widget
                        bytes = new ByteArrayOutputStream();
                        bitmap .compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    }
                    else
                        if(requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK )
                        {
                            filePath =  data.getData();
                            visible.gone(layout_choose);
                            visible.visible(upload_Image);
                            visible.visible(layout_Caption);
                            upload_background.setBackgroundColor(Color.BLACK);
                            upload_background.getBackground().setAlpha(150);
                            upload_image_view.setDrawingCacheEnabled(true);
                            upload_image_view.buildDrawingCache();
                            Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(getPath(filePath), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
                            upload_image_view.setImageBitmap(bitmap);

                            bitmap= ((BitmapDrawable) upload_image_view.getDrawable()).getBitmap();
                            bytes = new ByteArrayOutputStream();
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                        }
            byte[] imageBytes =  bytes.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
        public String getPath(Uri uri)
        {
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = managedQuery(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
                cursor.moveToFirst();
                return cursor.getString(column_index);
        }
        public void uploadImage()
        {

            myProgressDialog.show();
            myProgressDialog.setContentView(R.layout.progress);
            ProgressBar progressBar = (ProgressBar) myProgressDialog.findViewById(R.id.progressBar);
            progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#FFFFFF"), PorterDuff.Mode.MULTIPLY);
            StringRequest request = new StringRequest(Request.Method.POST, Config.URL_SEND_MEDIA,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                            if( Config.GROUP_CREATION_SUCCESS.equalsIgnoreCase(response))
                            {
                                myProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),"Image has been uploaded!", Toast.LENGTH_LONG) .show();
                            }
                            else
                            {
                                myProgressDialog.dismiss();
                                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                            }

                        }
                     },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                    }){
                    @Override
                    protected Map<String, String> getParams()
                    {
                            Map<String, String> parameters = new HashMap<String, String>();
                            parameters.put("which_one","image");
                            parameters.put("image",encodedImage);
                            parameters.put("subject",caption.getText().toString());
                            parameters.put("description",desc.getText().toString());
                            parameters.put("school",pref.getString("school",""));
                            return parameters;
                    }
                };
                request.setRetryPolicy(new DefaultRetryPolicy(DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(request);
        }
        public void uploadVideo()
        {
            try
            {
                new MultipartUploadRequest(getApplicationContext(),Config.URL_SEND_MEDIA)
                .addFileToUpload(getPath(filePath), "uploaded_file")
                .addParameter("subject",caption.getText().toString())
                .addParameter("description", desc.getText().toString())
                .addParameter("receiver", "")
                .addParameter("school",Config.school_id)
                .addParameter("which_one","video")
                .addParameter("image","")
                .setMethod("POST")
                .setNotificationConfig(new UploadNotificationConfig())
                .setMaxRetries(1)
                .startUpload();
            }
            catch (Exception ex)
            {
                Toast.makeText(getApplicationContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
            }
        }
}
