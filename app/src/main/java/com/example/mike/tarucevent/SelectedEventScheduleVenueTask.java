package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;

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
 * Created by Mike on 10/18/2017.
 */

public class SelectedEventScheduleVenueTask extends AsyncTask<String, Void, String> {
    Context context;
    android.app.AlertDialog alertDialog;
    SelectedEventScheduleVenueTask(Context ctx) {
        context = ctx;
    }

    String studentId = "";
    String eventId = "";
    String timeTableId = "";
    String eventStartDate = "";
    String eventEndDate = "";
    String eventStartTime = "";
    String eventEndTime = "";
    String venueId = "";
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String retrieveSelected_url = ipAddress + "/Android/retrieveVenue.php";

        if (type.equals("retrieveVenue")) {
            try {
                eventId = params[1];
                studentId = params[2];
                timeTableId = params[3];
                eventStartDate = params[4];
                eventEndDate = params[5];
                eventStartTime = params[6];
                eventEndTime = params[7];
                venueId = params[8];
                URL url = new URL(retrieveSelected_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("venueId", "UTF-8")+"="+URLEncoder.encode(venueId, "UTF-8");
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
    protected void onPostExecute(String result) {
        String venueId = "";
        String venueName = "";
        String venueDescription = "";


        try {
            JSONObject response = new JSONObject(result);
            venueId = response.getString("venueId");
            venueName = response.getString("venueName");
            venueDescription = response.getString("venueDescription");

            Intent intent = new Intent(context, SelectedEventSchedule.class);
            intent.putExtra("eventId", eventId);
            intent.putExtra("venueId", venueId);
            intent.putExtra("venueName", venueName);
            intent.putExtra("venueDescription", venueDescription);
            intent.putExtra("studentId", studentId);
            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
