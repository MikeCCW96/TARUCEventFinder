package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

/**
 * Created by Jefferson on 16-Nov-17.
 */

public class EarlyBird extends AppCompatActivity {

    private String studentId = "";
    private ListView mListView;
    CustomAdapter customAdapter;

    ArrayList<String> redeemStatusArray = new ArrayList<>();
    ArrayList<String> eventTitleArray = new ArrayList<>();
    ArrayList<String> eventStartDateArray = new ArrayList<>();
    ArrayList<String> eventEndDateArray = new ArrayList<>();
    ArrayList<String> eventStartTimeArray = new ArrayList<>();
    ArrayList<String> eventEndTimeArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_earlybird);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Early Bird Gifts");

        Intent intent = getIntent();
        studentId = getIntent().getStringExtra("studentId");

        String type = "retrieveEarlyBird";
        RetrieveEarlyBird retrieveEarlyBird = new RetrieveEarlyBird(this);
        retrieveEarlyBird.execute(type, studentId);

        mListView = (ListView) findViewById(R.id.lvEarlyBird);
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


            convertView = getLayoutInflater().inflate(R.layout.customlayoutearlybird, null);
            TextView txtTitle = (TextView)convertView.findViewById(R.id.tvEventTitle2);
            TextView txtStartDate = (TextView)convertView.findViewById(R.id.tvStartDate2);
            TextView txtEndDate = (TextView)convertView.findViewById(R.id.tvEndDate2);
            TextView txtStartTime = (TextView)convertView.findViewById(R.id.tvStartTime2);
            TextView txtEndTime = (TextView)convertView.findViewById(R.id.tvEndTime2);
            TextView txtRedeemStatus = (TextView)convertView.findViewById(R.id.tvRedeemStatus);

            txtTitle.setText(eventTitleArray.get(position));
            txtStartDate.setText(eventStartDateArray.get(position));
            txtEndDate.setText(eventEndDateArray.get(position));
            txtStartTime.setText(eventStartTimeArray.get(position));
            txtEndTime.setText(eventEndTimeArray.get(position));

            txtRedeemStatus.setText("Please redeem your early bird gifts at the event!");

            return convertView;
        }
    }

    public class RetrieveEarlyBird extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;
        RetrieveEarlyBird(Context ctx) {
            context = ctx;
        }

        String studentId = "";
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String search_url = ipAddress + "/Android/retrieveEarlyBird.php";

            if (type.equals("retrieveEarlyBird")) {
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

            ArrayList<String> redeemStatusArrayAsync = new ArrayList<>();
            ArrayList<String> eventTitleArrayAsync = new ArrayList<>();
            ArrayList<String> eventStartDateArrayAsync = new ArrayList<>();
            ArrayList<String> eventEndDateArrayAsync = new ArrayList<>();
            ArrayList<String> eventStartTimeArrayAsync = new ArrayList<>();
            ArrayList<String> eventEndTimeArrayAsync = new ArrayList<>();

            String redeemStatus = "";
            String eventTitle = "";
            String eventStartDate = "";
            String eventEndDate = "";
            String eventStartTime = "";
            String eventEndTime = "";


            try {
                JSONObject response = new JSONObject(result);
                redeemStatus = response.getString("RedeemStatus");
                eventTitle = response.getString("EventTitle");
                eventStartDate = response.getString("EventStartDate");
                eventEndDate = response.getString("EventEndDate");
                eventStartTime = response.getString("EventStartTime");
                eventEndTime = response.getString("EventEndTime");

                JSONArray jsonRedeemStatus = new JSONArray(redeemStatus);
                JSONArray jsonEventTitle = new JSONArray(eventTitle);
                JSONArray jsonEventStartDate = new JSONArray(eventStartDate);
                JSONArray jsonEventEndDate = new JSONArray(eventEndDate);
                JSONArray jsonEventStartTime = new JSONArray(eventStartTime);
                JSONArray jsonEventEndTime = new JSONArray(eventEndTime);

                for (int i = 0; i < jsonRedeemStatus.length(); i++) {
                    JSONObject jsonObjectRedeemStatus = jsonRedeemStatus.optJSONObject(i);
                    JSONObject jsonObjectEventTitle = jsonEventTitle.optJSONObject(i);
                    JSONObject jsonObjectEventStartDate = jsonEventStartDate.optJSONObject(i);
                    JSONObject jsonObjectEventEndDate = jsonEventEndDate.optJSONObject(i);
                    JSONObject jsonObjectEventStartTime = jsonEventStartTime.optJSONObject(i);
                    JSONObject jsonObjectEventEndTime = jsonEventEndTime.optJSONObject(i);

                    if(jsonRedeemStatus == null) {
                        continue;
                    }
                    String jsonValueRedeemStatus = jsonObjectRedeemStatus.optString("RedeemStatus");
                    String jsonValueEventTitle = jsonObjectEventTitle.optString("EventTitle");
                    String jsonValueEventStartDate = jsonObjectEventStartDate.optString("EventStartDate");
                    String jsonValueEventEndDate = jsonObjectEventEndDate.optString("EventEndDate");
                    String jsonValueEventStartTime = jsonObjectEventStartTime.optString("EventStartTime");
                    String jsonValueEventEndTime = jsonObjectEventEndTime.optString("EventEndTime");

                    redeemStatusArrayAsync.add(jsonValueRedeemStatus);
                    eventTitleArrayAsync.add(jsonValueEventTitle);
                    eventStartDateArrayAsync.add(jsonValueEventStartDate);
                    eventEndDateArrayAsync.add(jsonValueEventEndDate);
                    eventStartTimeArrayAsync.add(jsonValueEventStartTime);
                    eventEndTimeArrayAsync.add(jsonValueEventEndTime);


                }

            } catch (JSONException e) {
                e.printStackTrace();
            }


            redeemStatusArray.clear();
            redeemStatusArray = (ArrayList<String>) redeemStatusArrayAsync;
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
