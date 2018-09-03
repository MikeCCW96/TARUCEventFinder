package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

public class EventRegistrationStatus extends AppCompatActivity {

    private String studentId = "";
    private ListView mListView;
    CustomAdapter customAdapter;

    ArrayList<String> waitingListStatusArray = new ArrayList<>();
    ArrayList<String> eventTitleArray = new ArrayList<>();
    ArrayList<String> eventStartDateArray = new ArrayList<>();
    ArrayList<String> eventEndDateArray = new ArrayList<>();
    ArrayList<String> eventStartTimeArray = new ArrayList<>();
    ArrayList<String> eventEndTimeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_registration_status);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Event Registration Status");

        Intent intent = getIntent();
        studentId = getIntent().getStringExtra("studentId");



        String type = "RetrieveEventRegistrationStatus";
        RetrieveEventRegistrationStatus retrieveEventRegistrationStatus = new RetrieveEventRegistrationStatus(this);
        retrieveEventRegistrationStatus.execute(type, studentId);

        mListView = (ListView) findViewById(R.id.listView);
        customAdapter = new CustomAdapter();


    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return eventTitleArray.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {


            convertView = getLayoutInflater().inflate(R.layout.customlayouteventstatus, null);
            TextView txtTitle = (TextView)convertView.findViewById(R.id.textViewEventTitle);
            TextView txtStartDate = (TextView)convertView.findViewById(R.id.textViewStartDate);
            TextView txtEndDate = (TextView)convertView.findViewById(R.id.textViewEndDate);
            TextView txtStartTime = (TextView)convertView.findViewById(R.id.textViewStartTime);
            TextView txtEndTime = (TextView)convertView.findViewById(R.id.textViewEndTime);
            TextView txtRegistrationStatus = (TextView)convertView.findViewById(R.id.textViewRegistrationStatus);

            txtTitle.setText(eventTitleArray.get(position));
            txtStartDate.setText(eventStartDateArray.get(position));
            txtEndDate.setText(eventEndDateArray.get(position));
            txtStartTime.setText(eventStartTimeArray.get(position));
            txtEndTime.setText(eventEndTimeArray.get(position));

            if (waitingListStatusArray.get(position).equals("")){
                txtRegistrationStatus.setText("Active");
                txtRegistrationStatus.setTextColor(Color.BLUE);
            } else if (waitingListStatusArray.get(position).equals("Waiting")){
                txtRegistrationStatus.setText("Waiting");
                txtRegistrationStatus.setTextColor(Color.RED);
            }

            return convertView;
        }
    }

    public class RetrieveEventRegistrationStatus extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;
        RetrieveEventRegistrationStatus(Context ctx) {
            context = ctx;
        }

        String studentId = "";
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String search_url = ipAddress + "/Android/retrieveEventRegistrationStatus.php";

            if (type.equals("RetrieveEventRegistrationStatus")) {
                try {
                    String studentId = params[1];
                    URL url = new URL(search_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8");
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

            ArrayList<String> waitingListStatusArrayAsync = new ArrayList<>();
            ArrayList<String> eventTitleArrayAsync = new ArrayList<>();
            ArrayList<String> eventStartDateArrayAsync = new ArrayList<>();
            ArrayList<String> eventEndDateArrayAsync = new ArrayList<>();
            ArrayList<String> eventStartTimeArrayAsync = new ArrayList<>();
            ArrayList<String> eventEndTimeArrayAsync = new ArrayList<>();

            Log.i("lol", result);

            String waitingListStatus = "";
            String eventTitle = "";
            String eventStartDate = "";
            String eventEndDate = "";
            String eventStartTime = "";
            String eventEndTime = "";


            try {
                JSONObject response = new JSONObject(result);
                waitingListStatus = response.getString("WaitingListStatus");
                eventTitle = response.getString("EventTitle");
                eventStartDate = response.getString("EventStartDate");
                eventEndDate = response.getString("EventEndDate");
                eventStartTime = response.getString("EventStartTime");
                eventEndTime = response.getString("EventEndTime");

                JSONArray jsonWaitingListStatus = new JSONArray(waitingListStatus);
                JSONArray jsonEventTitle = new JSONArray(eventTitle);
                JSONArray jsonEventStartDate = new JSONArray(eventStartDate);
                JSONArray jsonEventEndDate = new JSONArray(eventEndDate);
                JSONArray jsonEventStartTime = new JSONArray(eventStartTime);
                JSONArray jsonEventEndTime = new JSONArray(eventEndTime);

                for (int i = 0; i < jsonWaitingListStatus.length(); i++) {
                    JSONObject jsonObjectWaitingListStatus = jsonWaitingListStatus.optJSONObject(i);
                    JSONObject jsonObjectEventTitle = jsonEventTitle.optJSONObject(i);
                    JSONObject jsonObjectEventStartDate = jsonEventStartDate.optJSONObject(i);
                    JSONObject jsonObjectEventEndDate = jsonEventEndDate.optJSONObject(i);
                    JSONObject jsonObjectEventStartTime = jsonEventStartTime.optJSONObject(i);
                    JSONObject jsonObjectEventEndTime = jsonEventEndTime.optJSONObject(i);

                    if(jsonWaitingListStatus == null) {
                        continue;
                    }
                    String jsonValueWaitingListStatus = jsonObjectWaitingListStatus.optString("WaitingListStatus");
                    String jsonValueEventTitle = jsonObjectEventTitle.optString("EventTitle");
                    String jsonValueEventStartDate = jsonObjectEventStartDate.optString("EventStartDate");
                    String jsonValueEventEndDate = jsonObjectEventEndDate.optString("EventEndDate");
                    String jsonValueEventStartTime = jsonObjectEventStartTime.optString("EventStartTime");
                    String jsonValueEventEndTime = jsonObjectEventEndTime.optString("EventEndTime");

                    waitingListStatusArrayAsync.add(jsonValueWaitingListStatus);
                    eventTitleArrayAsync.add(jsonValueEventTitle);
                    eventStartDateArrayAsync.add(jsonValueEventStartDate);
                    eventEndDateArrayAsync.add(jsonValueEventEndDate);
                    eventStartTimeArrayAsync.add(jsonValueEventStartTime);
                    eventEndTimeArrayAsync.add(jsonValueEventEndTime);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            waitingListStatusArray.clear();
            waitingListStatusArray = (ArrayList<String>) waitingListStatusArrayAsync;
            eventTitleArray.clear();
            eventTitleArray = (ArrayList<String>) eventTitleArrayAsync;
            eventStartDateArray.clear();
            eventStartDateArray = (ArrayList<String>) eventStartDateArrayAsync;
            eventEndDateArray.clear();
            eventEndDateArray = (ArrayList<String>) eventEndDateArrayAsync;
            eventStartTimeArray.clear();
            eventStartTimeArray = (ArrayList<String>) eventStartTimeArrayAsync;
            eventEndTimeArray.clear();
            eventEndTimeArray = (ArrayList<String>) eventEndTimeArrayAsync;

            mListView.setAdapter(customAdapter);


        }
    }
}
