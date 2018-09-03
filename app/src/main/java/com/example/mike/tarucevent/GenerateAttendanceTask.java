package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

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
import java.util.ArrayList;

/**
 * Created by Mike on 10/24/2017.
 */

public class GenerateAttendanceTask extends AsyncTask<String, Void, String> {
    Context context;
    android.app.AlertDialog alertDialog;
    GenerateAttendanceTask(Context ctx) {
        context = ctx;
    }

    String studentId = "";
    String eventId = "";
    Connectivity connectivity = new Connectivity();

    @Override
    protected String doInBackground(String... params) {
        String type = params[0];
        String ipAddress = "" + connectivity.getIP();
        String search_url = ipAddress + "/Android/retrieveSelectedEventScheduleGenerate.php";

        if (type.equals("SelectedEventScheduleGenerate")) {
            try {
                eventId = params[1];
                studentId = params[2];
                URL url = new URL(search_url);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8")+"&"+
                                    URLEncoder.encode("eventId", "UTF-8")+"="+URLEncoder.encode(eventId, "UTF-8");
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

        ArrayList<String> timeTableIdArray = new ArrayList<>();
        ArrayList<String> eventStartDateArray = new ArrayList<>();
        ArrayList<String> eventEndDateArray = new ArrayList<>();
        ArrayList<String> eventStartTimeArray = new ArrayList<>();
        ArrayList<String> eventEndTimeArray = new ArrayList<>();
        ArrayList<String> venueIdArray = new ArrayList<>();
        ArrayList<String> venueNameArray = new ArrayList<>();
        ArrayList<String> venueDescriptionArray = new ArrayList<>();
        ArrayList<String> waitingListStatusArray = new ArrayList<>();

        String timeTableId = "";
        String eventStartDate = "";
        String eventEndDate = "";
        String eventStartTime = "";
        String eventEndTime = "";
        String venueId = "";
        String venueName = "";
        String venueDescription = "";
        String waitingListStatus = "";

        try {
            JSONObject response = new JSONObject(result);
            timeTableId = response.getString("timeTableId");
            eventStartDate = response.getString("eventStartDate");
            eventEndDate = response.getString("eventEndDate");
            eventStartTime = response.getString("eventStartTime");
            eventEndTime = response.getString("eventEndTime");
            venueId = response.getString("venueId");
            venueName = response.getString("venueName");
            venueDescription = response.getString("venueDescription");
            waitingListStatus = response.getString("waitingListStatus");

            JSONArray jsonTimeTableId = new JSONArray(timeTableId);
            JSONArray jsonEventStartDate = new JSONArray(eventStartDate);
            JSONArray jsonEventEndDate = new JSONArray(eventEndDate);
            JSONArray jsonEventStartTime = new JSONArray(eventStartTime);
            JSONArray jsonEventEndTime = new JSONArray(eventEndTime);
            JSONArray jsonVenueId = new JSONArray(venueId);
            JSONArray jsonVenueName = new JSONArray(venueName);
            JSONArray jsonVenueDescription = new JSONArray(venueDescription);
            JSONArray jsonWaitingListStatus = new JSONArray(waitingListStatus);


            for (int i = 0; i < jsonTimeTableId.length(); i++) {
                JSONObject jsonObjectTimeTableId = jsonTimeTableId.optJSONObject(i);
                JSONObject jsonObjectEventStartDate = jsonEventStartDate.optJSONObject(i);
                JSONObject jsonObjectEventEndDate = jsonEventEndDate.optJSONObject(i);
                JSONObject jsonObjectEventStartTime = jsonEventStartTime.optJSONObject(i);
                JSONObject jsonObjectEventEndTime = jsonEventEndTime.optJSONObject(i);
                JSONObject jsonObjectVenueId = jsonVenueId.optJSONObject(i);
                JSONObject jsonObjectVenueName = jsonVenueName.optJSONObject(i);
                JSONObject jsonObjectVenueDescription = jsonVenueDescription.optJSONObject(i);
                JSONObject jsonObjectWaitingListStatus = jsonWaitingListStatus.optJSONObject(i);
                if(jsonObjectTimeTableId == null) {
                    continue;
                }
                String jsonValueTimeTableId = jsonObjectTimeTableId.optString("timeTableId");
                String jsonValueEventStartDate = jsonObjectEventStartDate.optString("eventStartDate");
                String jsonValueEventEndDate = jsonObjectEventEndDate.optString("eventEndDate");
                String jsonValueEventStartTime = jsonObjectEventStartTime.optString("eventStartTime");
                String jsonValueEventEndTime = jsonObjectEventEndTime.optString("eventEndTime");
                String jsonValueVenueId = jsonObjectVenueId.optString("venueId");
                String jsonValueVenueName = jsonObjectVenueName.optString("venueName");
                String jsonValueVenueDescription = jsonObjectVenueDescription.optString("venueDescription");
                String jsonValueWaitingListStatus = jsonObjectWaitingListStatus.optString("waitingListStatus");

                timeTableIdArray.add(jsonValueTimeTableId);
                eventStartDateArray.add(jsonValueEventStartDate);
                eventEndDateArray.add(jsonValueEventEndDate);
                eventStartTimeArray.add(jsonValueEventStartTime);
                eventEndTimeArray.add(jsonValueEventEndTime);
                venueIdArray.add(jsonValueVenueId);
                venueNameArray.add(jsonValueVenueName);
                venueDescriptionArray.add(jsonValueVenueDescription);
                waitingListStatusArray.add(jsonValueWaitingListStatus);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (String member : eventEndDateArray){
            Log.i("Member name: ", member);
        }

        Intent intent = new Intent(context, GenerateAttendanceQR.class);
        intent.putExtra("timeTableIdArray", timeTableIdArray);
        intent.putExtra("eventStartDateArray", eventStartDateArray);
        intent.putExtra("eventEndDateArray", eventEndDateArray);
        intent.putExtra("eventStartTimeArray", eventStartTimeArray);
        intent.putExtra("eventEndTimeArray", eventEndTimeArray);
        intent.putExtra("venueIdArray", venueIdArray);
        intent.putExtra("venueNameArray", venueNameArray);
        intent.putExtra("venueDescriptionArray", venueDescriptionArray);
        intent.putExtra("waitingListStatusArray", waitingListStatusArray);
        intent.putExtra("eventId", eventId);
        intent.putExtra("studentId", studentId);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(intent);

    }
}
