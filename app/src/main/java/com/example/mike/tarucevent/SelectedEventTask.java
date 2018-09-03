package com.example.mike.tarucevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;

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

/**
 * Created by Mike on 10/17/2017.
 */

public class SelectedEventTask extends AsyncTask<String, Void, String> {
    Context context;
    android.app.AlertDialog alertDialog;
    SelectedEventTask(Context ctx) {
        context = ctx;
    }

    String studentId = "";
    String referBy = "None";
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String retrieveSelected_url = ipAddress + "/Android/retrieveSelectedEvent.php";

        if (type.equals("SelectedEvent")) {
            try {
                String eventId = params[1];
                studentId = params[2];
                URL url = new URL(retrieveSelected_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("eventId", "UTF-8")+"="+URLEncoder.encode(eventId, "UTF-8");
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
    protected void onPostExecute(final String result) {

                String eventId = "";
                String eventTitle = "";
                String eventDescription = "";
                String eventBrochure = "";
                String brochureType = "";
                String noOfParticipant = "";
                String createEventApprovalStatus = "";
                String activityType = "";
                String EBTicketDueDate = "";
                String eventLocation = "";

                try {
                    JSONObject response = new JSONObject(result);
                    eventId = response.getString("eventId");
                    eventTitle = response.getString("eventTitle");
                    eventDescription = response.getString("eventDescription");
                    eventBrochure = response.getString("eventBrochure");
                    brochureType = response.getString("brochureType");
                    noOfParticipant = response.getString("noOfParticipant");
                    createEventApprovalStatus = response.getString("createEventApprovalStatus");
                    activityType = response.getString("activityType");
                    EBTicketDueDate = response.getString("EBTicketDueDate");
                    eventLocation = response.getString("EventLocation");

                    Intent intent = new Intent(context, SelectedEvent.class);
                    intent.putExtra("eventId", eventId);
                    intent.putExtra("eventTitle", eventTitle);
                    intent.putExtra("eventDescription", eventDescription);
                    intent.putExtra("eventBrochure", eventBrochure);
                    intent.putExtra("brochureType", brochureType);
                    intent.putExtra("noOfParticipant", noOfParticipant);
                    intent.putExtra("createEventApprovalStatus", createEventApprovalStatus);
                    intent.putExtra("activityType", activityType);
                    intent.putExtra("EBTicketDueDate", EBTicketDueDate);
                    intent.putExtra("eventLocation", eventLocation);
                    intent.putExtra("studentId", studentId);
                    intent.putExtra("referBy", referBy);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }







    }
}
