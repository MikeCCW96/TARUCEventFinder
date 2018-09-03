package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

public class RegisteredEvent extends AppCompatActivity {
    TextView txtTitle, txtTitleMessage;
    Button btnMainMenu;
    String studentId = "";
    String timeTableId = "";
    ImageView image;
    String text2Qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registered_event);
        txtTitle = (TextView)findViewById(R.id.textViewTitle);
        txtTitleMessage = (TextView)findViewById(R.id.textViewTitleMessage);

        Intent intent = getIntent();
        studentId = intent.getStringExtra("studentId");
        timeTableId = intent.getStringExtra("timeTableId");
        String titleMessage = intent.getStringExtra("registerStatus");
        txtTitleMessage.setText(titleMessage);



        if (titleMessage.equals("Registered Successful! You're in waiting list. Congratulation you earn an early bird gift!")){
            txtTitle.setText("Congratulation");
        } else if (titleMessage.equals("Registered Successful! You're in waiting list.")){
            txtTitle.setText("Congratulation");
        } else if (titleMessage.equals("Registered Successful!")){
            txtTitle.setText("Congratulation");
        } else if (titleMessage.equals("Registered Successful! Congratulation you earn an early bird gift!")){
            txtTitle.setText("Congratulation");
        }


        String type = "generateQR";
        GenerateQRForAttendance generateQRForAttendance = new GenerateQRForAttendance(this);
        generateQRForAttendance.execute(type, timeTableId, studentId);


        btnMainMenu = (Button)findViewById(R.id.btnMainMenu);
        btnMainMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "Retrieve";
                RetrieveEventTask retrieveEventTask = new RetrieveEventTask(getApplicationContext());
                retrieveEventTask.execute(type, studentId);
                finish();
            }
        });

    }

    public void onBackPressed(){
        String type = "Retrieve";
        RetrieveEventTask retrieveEventTask = new RetrieveEventTask(getApplicationContext());
        retrieveEventTask.execute(type, studentId);
        finish();
    }


    public class GenerateQRForAttendance extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;


        GenerateQRForAttendance(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/generateQRforAttendance.php";

            if (type.equals("generateQR")) {
                try {
                    String timeTableIdGet = params[1];
                    String studentIdGet = params[2];

                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentIdGet, "UTF-8")+"&"+
                            URLEncoder.encode("timeTableId", "UTF-8")+"="+URLEncoder.encode(timeTableIdGet, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        result += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return result;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute(){

        }

        @Override
        protected void onPostExecute(String result) {
            String registrationId = "";

            try {
                JSONObject response = new JSONObject(result);
                registrationId = response.getString("RegistrationId");
            } catch (JSONException e) {
                e.printStackTrace();
            }


            image = (ImageView)findViewById(R.id.image);
            text2Qr = "TARUCEventApp://ATTENDANCE/REG" + registrationId;
            MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
            try{
                BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                image.setImageBitmap(bitmap);
            } catch (WriterException e) {
                e.printStackTrace();
            }

        }
    }
}


