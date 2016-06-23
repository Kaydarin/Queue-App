package com.kaydarinapp.queueappv2;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wordpress.priyankvex.easyocrscannerdemo.Config;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScanner;
import com.wordpress.priyankvex.easyocrscannerdemo.EasyOcrScannerListener;

/**
 * Created by Kaydarin on 6/1/2016.
 */

//Our class extending fragment
public class Tab2 extends Fragment implements EasyOcrScannerListener {

    private LinearLayout linearLayout;
    private FragmentActivity fragActivity;
    EasyOcrScanner mEasyOcrScanner;
    TextView textView;
    ProgressDialog mProgressDialog;
    Button btnCapture;

    //Overriden method onCreateView
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragActivity = super.getActivity();
        linearLayout = (LinearLayout) inflater.inflate(R.layout.tab2, container, false);

        textView = (TextView) fragActivity.findViewById(R.id.textView);

        // initialize EasyOcrScanner instance.
        mEasyOcrScanner = new EasyOcrScanner(getActivity(), "EasyOcrScanner",
                Config.REQUEST_CODE_CAPTURE_IMAGE, "eng");

        // Set ocrScannerListener
        mEasyOcrScanner.setOcrScannerListener(this);

        btnCapture = (Button) linearLayout.findViewById(R.id.button);
        btnCapture.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                mEasyOcrScanner.takePicture();
            }
        });

        return linearLayout;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Call onImageTaken() in onActivityResult.
        if (resultCode == getActivity().RESULT_OK && requestCode == Config.REQUEST_CODE_CAPTURE_IMAGE){
            mEasyOcrScanner.onImageTaken();
        }
    }

    /**
     * Callback when after taking picture, scanning process starts.
     * Good place to show a progress dialog.
     * @param filePath file path of the image file being processed.
     */
    @Override
    public void onOcrScanStarted(String filePath) {
        mProgressDialog = new ProgressDialog(getActivity());
        mProgressDialog.setMessage("Scanning...");
        mProgressDialog.show();
    }

    /**
     * Callback when scanning is finished.
     * Good place to hide teh progress dialog.
     * @param bitmap Bitmap of image that was scanned.
     * @param recognizedText Scanned text.
     */
    @Override
    public void onOcrScanFinished(Bitmap bitmap, String recognizedText) {
        textView.setText(recognizedText);
        if (mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }
}
