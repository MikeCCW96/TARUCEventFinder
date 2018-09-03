package com.example.mike.tarucevent;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
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

public class updateSoftSkillPoint extends AppCompatActivity {
    Integer intEventCS, intEventCTPS, intEventTS, intEventLL, intEventKK, intEventEM, intEventLS;
    Integer intCurrentCS, intCurrentCTPS, intCurrentTS, intCurrentLL, intCurrentKK, intCurrentEM, intCurrentLS;
    Integer intUpdateCS, intUpdateCTPS, intUpdateTS, intUpdateLL, intUpdateKK, intUpdateEM, intUpdateLS;

    TextView regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ssp);

        regId = (TextView) findViewById(R.id.tvRegId);
    }

    public void btnUpdate(View v) {
        String registrationId = regId.getText().toString();
        String type = "retrieveCurrentAndEventSSP";

        RetrieveCurrentAndEventSSPTask retrieveCurrentAndEventSSPTask = new RetrieveCurrentAndEventSSPTask(this);
        retrieveCurrentAndEventSSPTask.execute(type, registrationId);
    }

    public void btnRetrieve(View v) {
        Intent intent = new Intent(this, retrieveSoftSkillPoint.class);
        startActivity(intent);
    }

    private void updateSSP(String json) throws JSONException {
        //creating a json array from the json string
        JSONArray jsonArray = new JSONArray(json);

        //creating a string array for table
        String[] softSkillPoint = new String[jsonArray.length()];

        //looping through all the elements in json array
        for (int i = 0; i < jsonArray.length(); i++) {

            //getting json object from the json array
            JSONObject obj = jsonArray.getJSONObject(i);

            //getting the name from the json object and putting it inside string array
            softSkillPoint[i] = obj.getString("SoftSkillPoint");
        }

        //Separate the merged array that retrieve from php
        String[] currentSoftSkillPoint = softSkillPoint[0].split(",");
        String[] eventSoftSkillPoint = softSkillPoint[1].split(",");

        //Separate the value from array into different variable
        intCurrentCS = Integer.parseInt(currentSoftSkillPoint[0].substring(1));
        intCurrentCTPS = Integer.parseInt(currentSoftSkillPoint[1]);
        intCurrentTS = Integer.parseInt(currentSoftSkillPoint[2]);
        intCurrentLL = Integer.parseInt(currentSoftSkillPoint[3]);
        intCurrentKK = Integer.parseInt(currentSoftSkillPoint[4]);
        intCurrentEM = Integer.parseInt(currentSoftSkillPoint[5]);
        intCurrentLS = Integer.parseInt(currentSoftSkillPoint[6].substring(0, currentSoftSkillPoint[6].length() - 1));

        //Separate the value from array into different variable
        intEventCS = Integer.parseInt(eventSoftSkillPoint[0].substring(1));
        intEventCTPS = Integer.parseInt(eventSoftSkillPoint[1]);
        intEventTS = Integer.parseInt(eventSoftSkillPoint[2]);
        intEventLL = Integer.parseInt(eventSoftSkillPoint[3]);
        intEventKK = Integer.parseInt(eventSoftSkillPoint[4]);
        intEventEM = Integer.parseInt(eventSoftSkillPoint[5]);
        intEventLS = Integer.parseInt(eventSoftSkillPoint[6].substring(0, eventSoftSkillPoint[6].length() - 1));

        //Add the current SSP and event SSP
        intUpdateCS = intCurrentCS + intEventCS;
        intUpdateCTPS = intCurrentCTPS + intEventCTPS;
        intUpdateTS = intCurrentTS + intEventTS;
        intUpdateLL = intCurrentLL + intEventLL;
        intUpdateKK = intCurrentKK + intEventKK;
        intUpdateEM = intCurrentEM + intEventEM;
        intUpdateLS = intCurrentLS + intEventLS;

        //Update the new SSP into database
        String registrationId = regId.getText().toString();
        String SoftSkillPoint = "{" + intUpdateCS.toString() + "," + intUpdateCTPS.toString() + "," +  intUpdateTS.toString() + "," + intUpdateLL.toString() + "," + intUpdateKK.toString() + "," + intUpdateEM.toString() + "," + intUpdateLS.toString() + "}";;
        String type = "updateSoftSkillPoint";
        UpdateSSPTask updateSSPTask = new UpdateSSPTask(this);
        updateSSPTask.execute(type, registrationId, SoftSkillPoint);

    }

    public class RetrieveCurrentAndEventSSPTask extends AsyncTask<String, Void, String> {
        Context context;

        String registrationId;

        RetrieveCurrentAndEventSSPTask(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String retrieveCurrentAndEventSSP_url = "http://192.168.0.123/Testing/retrieveCurrentAndEventSSP.php";

            if (type.equals("retrieveCurrentAndEventSSP")) {
                try {
                    registrationId = params[1];
                    URL url = new URL(retrieveCurrentAndEventSSP_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("registrationId", "UTF-8") + "=" + URLEncoder.encode(registrationId, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));

                    StringBuilder sb = new StringBuilder();
                    String json;

                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }

                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();

                    return sb.toString().trim();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                updateSSP(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }

    class UpdateSSPTask extends AsyncTask<String, Void, String> {
        Context context;

        String registrationId, SoftSkillPoint;

        UpdateSSPTask(Context ctx) {
            context = ctx;
        }

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String updateSSP_url = "http://192.168.0.123/Testing/updateSoftSkillPoint.php";

            if (type.equals("updateSoftSkillPoint")) {
                try {
                    registrationId = params[1];
                    SoftSkillPoint = params[2];
                    URL url = new URL(updateSSP_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("registrationId", "UTF-8")+"="+URLEncoder.encode(registrationId, "UTF-8")+"&"+
                            URLEncoder.encode("SoftSkillPoint", "UTF-8")+"="+URLEncoder.encode(SoftSkillPoint, "UTF-8");
                    bufferedWriter.write(post_data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();
                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String result="";
                    String line="";
                    while ((line = bufferedReader.readLine()) != null){
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
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (result != null & result.equals("Update SSP successful")) {
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            } else if (result != null & result.equals("Update SSP failed")){
                Toast.makeText(context, result, Toast.LENGTH_LONG).show();
            }
        }


        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }
}