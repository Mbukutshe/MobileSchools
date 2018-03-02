package com.payghost.mobileschools.Fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.payghost.mobileschools.Functions.Animation;
import com.payghost.mobileschools.R;

/**
 * Created by Wiseman on 2018-02-20.
 */

public class SendMessage extends Fragment{
    View view;
    Animation anim;
    RelativeLayout message_layout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.send_message, container, false);
        message_layout = (RelativeLayout)view.findViewById(R.id.message);
        anim = new Animation(view.getContext());
        anim.fromSides(message_layout);
        return view;
    }
}
