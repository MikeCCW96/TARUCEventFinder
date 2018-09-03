package com.example.mike.tarucevent;

import android.*;
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;


import com.google.zxing.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRScan extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView zXingScannerView;
    private String studentId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_qrscan);

        zXingScannerView = new ZXingScannerView(getApplicationContext());
        setContentView(zXingScannerView);
        zXingScannerView.setResultHandler(this);
        zXingScannerView.startCamera();

        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
    }

    @Override
    protected void onPause(){
        super.onPause();
        zXingScannerView.stopCamera();
    }

    @Override
    public void handleResult(Result result) {
        if (result.getText().indexOf("TARUCEventApp://EV") > -1){
            String remove = "TARUCEventApp://";
            String string = removeWords(result.getText(), remove);
            String type = "SelectedEvent";
            SelectedEventTask selectedEventTask = new SelectedEventTask(this);
            selectedEventTask.execute(type, string, studentId);
            finish();
        } else if(result.getText().indexOf("TARUCEventApp://SHARE/") > -1) {
            String remove = "TARUCEventApp://SHARE/";
            String string = removeWords(result.getText(), remove);
            String studentIdGetQR = "";
            String eventIdGetQR = "";

            try {
                JSONObject response = new JSONObject(string);
                studentIdGetQR = response.getString("studentId");
                eventIdGetQR = response.getString("eventId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (studentIdGetQR.equals(studentId)){
                Toast.makeText(getApplicationContext(),"You cannot refer to yourself!", Toast.LENGTH_SHORT).show();
                zXingScannerView.resumeCameraPreview(this);
            } else {
                String type = "SelectedEvent";
                SelectedEventReferredTask selectedEventReferredTask = new SelectedEventReferredTask(this);
                selectedEventReferredTask.execute(type, eventIdGetQR, studentId, studentIdGetQR);
                finish();
            }



        } else {
            Toast.makeText(getApplicationContext(),"Not valid QR Code", Toast.LENGTH_SHORT).show();
            zXingScannerView.resumeCameraPreview(this);
        }
    }

    public static String removeWords(String word ,String remove) {
        return word.replace(remove,"");
    }


}