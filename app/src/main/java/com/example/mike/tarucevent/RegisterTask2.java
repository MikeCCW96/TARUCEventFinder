package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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

/**
 * Created by Mike on 10/19/2017.
 */

public class RegisterTask2 extends AsyncTask<String, Void, String> {
    Context context;
    android.app.AlertDialog alertDialog;
    String timeTableId = "";
    String studentId = "";
    String studentId2 = "";
    String studentId3 = "";
    String studentId4 = "";
    String studentId5 = "";
    String referBy = "";


    RegisterTask2(Context ctx) {
        context = ctx;
    }
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String retrieve_url = ipAddress + "/Android/registerEvent.php";

        if (type.equals("registerEvent2")) {
            try {
                timeTableId = params[1];
                studentId = params[2];
                studentId2 = params[3];
                studentId3 = params[4];
                studentId4 = params[5];
                studentId5 = params[6];
                referBy = params[7];
                URL url = new URL(retrieve_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("timeTableID", "UTF-8")+"="+URLEncoder.encode(timeTableId, "UTF-8")+"&"+
                        URLEncoder.encode("studentID", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8")+"&"+
                        URLEncoder.encode("studentID2", "UTF-8")+"="+URLEncoder.encode(studentId2, "UTF-8")+"&"+
                        URLEncoder.encode("studentID3", "UTF-8")+"="+URLEncoder.encode(studentId3, "UTF-8")+"&"+
                        URLEncoder.encode("studentID4", "UTF-8")+"="+URLEncoder.encode(studentId4, "UTF-8")+"&"+
                        URLEncoder.encode("studentID5", "UTF-8")+"="+URLEncoder.encode(studentId5, "UTF-8")+"&"+
                        URLEncoder.encode("referBy", "UTF-8")+"="+URLEncoder.encode(referBy, "UTF-8");
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

        Log.i("message", result);

        Intent intent = new Intent(context, RegisteredEvent.class);
        intent.putExtra("studentId", studentId);
        intent.putExtra("timeTableId", timeTableId);
            if(result.equals("Registered Successful! You're in waiting list. Congratulation you earn an early bird gift!")){
                intent.putExtra("registerStatus", "Registered Successful! You're in waiting list. Congratulation you earn an early bird gift!");
            } else if (result.equals("Registered Successful! You're in waiting list.")){
                intent.putExtra("registerStatus", "Registered Successful! You're in waiting list.");
            } else if (result.equals("Registered Successful!")){
                intent.putExtra("registerStatus", "Registered Successful!");
            } else if (result.equals("Registered Successful! Congratulation you earn an early bird gift!")){
                intent.putExtra("registerStatus", "Registered Successful! Congratulation you earn an early bird gift!");
            }
            intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);

    }
}