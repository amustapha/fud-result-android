package com.camphus.fudresult.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;
import android.widget.VideoView;

import com.camphus.fudresult.R;

public class CalculationGuideFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        setRetainInstance(true);

        return inflater.inflate(R.layout.fragment_guide, parent, false);



    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        VideoView videoView =(VideoView) view.findViewById(R.id.videoView1);

        //Creating MediaController
        MediaController mediaController= new MediaController(getContext());
        mediaController.setAnchorView(videoView);

        Uri videoUri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.tut);
        videoView.setMediaController(mediaController);
        videoView.setVideoURI(videoUri);
        videoView.requestFocus();
        videoView.start();
        

    }
}
