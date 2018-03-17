package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.Globals.Config;
import com.payghost.mobileschools.R;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import static android.app.Activity.RESULT_OK;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class UploadDocument extends Fragment implements View.OnClickListener{
    View view;
    Animation anim;
    RelativeLayout document_layout;
    LinearLayout file_choose,upload;
    EditText subject,description;
    TextView choose_icon;
    Uri filePath;
    private static final int RESULT_LOAD_DOCUMENT=2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_document, container, false);
        document_layout = (RelativeLayout)view.findViewById(R.id.document);
        anim = new Animation(view.getContext());
        anim.fromSides(document_layout);
        file_choose = (LinearLayout)view.findViewById(R.id.file_choose);
        file_choose.setOnClickListener(this);
        upload  = (LinearLayout)view.findViewById(R.id.upload_button);
        upload.setOnClickListener(this);
        subject = (EditText)view.findViewById(R.id.text_subject);
        description = (EditText)view.findViewById(R.id.text_description);
        choose_icon = (TextView)view.findViewById(R.id.choose_icon);

        return view;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.upload_button:
                sendDocument();
            break;
            case  R.id.file_choose:
                Intent intent = new Intent();
                intent.setType("application/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(Intent.createChooser(intent, "Choose a document"), 2);
            break;
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == RESULT_LOAD_DOCUMENT && resultCode == RESULT_OK && data!=null && data.getData()!=null) {
            filePath = data.getData();
        }
        choose_icon.setBackgroundResource(R.drawable.successfile);
    }
    public void sendDocument()
    {
        try
        {
            new MultipartUploadRequest(view.getContext(), Config.URL_SEND_DOCUMENT)
                    .addFileToUpload(filePath.getPath(), "uploaded_file")
                    .addParameter("title",subject.getText().toString())
                    .addParameter("description", description.getText().toString())
                    .addParameter("receiver", "")
                    .addParameter("school",Config.school_id)
                    .addParameter("subject","")
                    .addParameter("grade","")
                    .setMethod("POST")
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(1)
                    .startUpload();
        }
        catch (Exception ex) {
            Toast.makeText(view.getContext(),ex.getMessage().toString(),Toast.LENGTH_LONG).show();
        }
    }
}
