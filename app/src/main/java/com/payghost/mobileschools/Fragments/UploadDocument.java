package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
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

/**
 * Created by Wiseman on 2018-02-20.
 */

public class UploadDocument extends Fragment {
    View view;
    Animation anim;
    RelativeLayout document_layout;
    LinearLayout file_choose;
    EditText subject,description;
    TextView choose_icon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_document, container, false);
        document_layout = (RelativeLayout)view.findViewById(R.id.document);
        anim = new Animation(view.getContext());
        anim.fromSides(document_layout);
        file_choose = (LinearLayout)view.findViewById(R.id.file_choose);
        subject = (EditText)view.findViewById(R.id.text_subject);
        description = (EditText)view.findViewById(R.id.text_description);
        choose_icon = (TextView)view.findViewById(R.id.choose_icon);

        return view;
    }
    public void sendDocument()
    {
        try
        {
            new MultipartUploadRequest(view.getContext(), Config.INSERT_POST_URL)
                    .addFileToUpload("", "uploaded_file")
                    .addParameter("subject","")
                    .addParameter("description", "")
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
