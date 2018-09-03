package com.example.mike.tarucevent;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;
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
import java.util.ArrayList;

public class JoinedEvent extends AppCompatActivity {

    Button btnAllJoined, btnMonthlyJoined, btnYearlyJoined;
    ImageButton btnFilter, btnSearch;
    ArrayList<String> IdArray = new ArrayList<>();
    ArrayList<String> titleArray = new ArrayList<>();
    ArrayList<String> descriptionArray = new ArrayList<>();
    ArrayList<String> timeTableIdArray = new ArrayList<>();
    private ListView mListView;
    String studentId = "";
    CustomAdapter customAdapter;
    Spinner spinnerMonthlyMonth, spinnerMonthlyYear, spinnerYearlyYear;
    EditText searchText;
    private String registerTime = "";
    private String eventStartDate = "";
    private String eventName = "";
    private AlertDialog dialog;

    String all = "0";
    String monthly = "0";
    String yearly = "0";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joined_event);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mListView = (ListView) findViewById(R.id.listView);



        setTitle("Joined Events");

        Intent intent = getIntent();
        studentId = getIntent().getStringExtra("studentId");



        String type = "Retrieve";
        RetrieveJoinedEventAllTask retrieveJoinedEventAllTask = new RetrieveJoinedEventAllTask(JoinedEvent.this);
        retrieveJoinedEventAllTask.execute(type, studentId);
        customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);

        btnAllJoined = (Button) findViewById(R.id.btnAllJoined);
        btnMonthlyJoined = (Button) findViewById(R.id.btnMonthlyJoined);
        btnYearlyJoined = (Button) findViewById(R.id.btnYearlyJoined);

        spinnerMonthlyMonth = (Spinner) findViewById(R.id.spinnerMonthlyMonth);
        spinnerMonthlyYear = (Spinner) findViewById(R.id.spinnerMonthlyYear);
        spinnerYearlyYear = (Spinner) findViewById(R.id.spinnerYearlyYear);

        searchText = (EditText) findViewById(R.id.searchText);
        btnSearch = (ImageButton) findViewById(R.id.btnSearch);
        btnFilter = (ImageButton) findViewById(R.id.btnFilter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //TextView getTitleText = (TextView)view.findViewById(R.id.textViewTitle);

                TextView getTimeTableIdText = (TextView)view.findViewById(R.id.textViewTimeTableId);
                String timeTableId = getTimeTableIdText.getText().toString();
                TextView getEventName = (TextView)view.findViewById(R.id.textViewStartDate);
                eventName = getEventName.getText().toString();
                //Toast.makeText(getApplicationContext(),timeTableId,Toast.LENGTH_SHORT).show();



                String type = "Retrieve";
                RetrieveRegisteredTimeDate retrieveRegisteredTimeDate = new RetrieveRegisteredTimeDate(getApplicationContext());
                retrieveRegisteredTimeDate.execute(type,studentId,timeTableId);






            }
        });


        btnAllJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllJoined.setBackgroundResource(R.drawable.button);
                btnAllJoined.setTextColor(Color.WHITE);
                btnMonthlyJoined.setBackgroundResource(R.color.transparent);
                btnMonthlyJoined.setTextColor(Color.BLACK);
                btnYearlyJoined.setBackgroundResource(R.color.transparent);
                btnYearlyJoined.setTextColor(Color.BLACK);
                searchText.setVisibility(View.INVISIBLE);
                btnSearch.setVisibility(View.INVISIBLE);
                btnFilter.setVisibility(View.INVISIBLE);
                spinnerMonthlyMonth.setVisibility(View.INVISIBLE);
                spinnerMonthlyYear.setVisibility(View.INVISIBLE);
                spinnerYearlyYear.setVisibility(View.INVISIBLE);
                String type = "Retrieve";
                RetrieveJoinedEventAllTask retrieveJoinedEventAllTask = new RetrieveJoinedEventAllTask(JoinedEvent.this);
                retrieveJoinedEventAllTask.execute(type, studentId);
            }
        });

        btnMonthlyJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllJoined.setBackgroundResource(R.color.transparent);
                btnAllJoined.setTextColor(Color.BLACK);
                btnMonthlyJoined.setBackgroundResource(R.drawable.button);
                btnMonthlyJoined.setTextColor(Color.WHITE);
                btnYearlyJoined.setBackgroundResource(R.color.transparent);
                btnYearlyJoined.setTextColor(Color.BLACK);
                searchText.setVisibility(View.INVISIBLE);
                btnSearch.setVisibility(View.INVISIBLE);
                btnFilter.setVisibility(View.INVISIBLE);
                spinnerMonthlyMonth.setVisibility(View.VISIBLE);
                spinnerMonthlyYear.setVisibility(View.VISIBLE);
                spinnerYearlyYear.setVisibility(View.INVISIBLE);

                String monthlyGetBefore = spinnerMonthlyMonth.getSelectedItem().toString();
                String monthlyGet = "";
                if (monthlyGetBefore.equals("January")) {
                    monthlyGet = "1";
                } else if (monthlyGetBefore.equals("February")) {
                    monthlyGet = "2";
                } else if (monthlyGetBefore.equals("March")) {
                    monthlyGet = "3";
                } else if (monthlyGetBefore.equals("April")) {
                    monthlyGet = "4";
                } else if (monthlyGetBefore.equals("May")) {
                    monthlyGet = "5";
                } else if (monthlyGetBefore.equals("June")) {
                    monthlyGet = "6";
                } else if (monthlyGetBefore.equals("July")) {
                    monthlyGet = "7";
                } else if (monthlyGetBefore.equals("August")) {
                    monthlyGet = "8";
                } else if (monthlyGetBefore.equals("September")) {
                    monthlyGet = "9";
                } else if (monthlyGetBefore.equals("October")) {
                    monthlyGet = "10";
                } else if (monthlyGetBefore.equals("November")) {
                    monthlyGet = "11";
                } else if (monthlyGetBefore.equals("December")) {
                    monthlyGet = "12";
                }
                String yearlyGet = spinnerMonthlyYear.getSelectedItem().toString();
                String type = "Retrieve";
                RetrieveJoinedEventMonthlyTask retrieveJoinedEventMonthlyTask = new RetrieveJoinedEventMonthlyTask(JoinedEvent.this);
                retrieveJoinedEventMonthlyTask.execute(type, studentId, monthlyGet, yearlyGet);

            }
        });

        spinnerMonthlyMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String monthlyGetBefore = spinnerMonthlyMonth.getSelectedItem().toString();
                String monthlyGet = "";
                if (monthlyGetBefore.equals("January")) {
                    monthlyGet = "1";
                } else if (monthlyGetBefore.equals("February")) {
                    monthlyGet = "2";
                } else if (monthlyGetBefore.equals("March")) {
                    monthlyGet = "3";
                } else if (monthlyGetBefore.equals("April")) {
                    monthlyGet = "4";
                } else if (monthlyGetBefore.equals("May")) {
                    monthlyGet = "5";
                } else if (monthlyGetBefore.equals("June")) {
                    monthlyGet = "6";
                } else if (monthlyGetBefore.equals("July")) {
                    monthlyGet = "7";
                } else if (monthlyGetBefore.equals("August")) {
                    monthlyGet = "8";
                } else if (monthlyGetBefore.equals("September")) {
                    monthlyGet = "9";
                } else if (monthlyGetBefore.equals("October")) {
                    monthlyGet = "10";
                } else if (monthlyGetBefore.equals("November")) {
                    monthlyGet = "11";
                } else if (monthlyGetBefore.equals("December")) {
                    monthlyGet = "12";
                }
                String yearlyGet = spinnerMonthlyYear.getSelectedItem().toString();
                String type = "Retrieve";
                RetrieveJoinedEventMonthlyTask retrieveJoinedEventMonthlyTask = new RetrieveJoinedEventMonthlyTask(JoinedEvent.this);
                retrieveJoinedEventMonthlyTask.execute(type, studentId, monthlyGet, yearlyGet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerMonthlyYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String monthlyGetBefore = spinnerMonthlyMonth.getSelectedItem().toString();
                String monthlyGet = "";
                if (monthlyGetBefore.equals("January")) {
                    monthlyGet = "1";
                } else if (monthlyGetBefore.equals("February")) {
                    monthlyGet = "2";
                } else if (monthlyGetBefore.equals("March")) {
                    monthlyGet = "3";
                } else if (monthlyGetBefore.equals("April")) {
                    monthlyGet = "4";
                } else if (monthlyGetBefore.equals("May")) {
                    monthlyGet = "5";
                } else if (monthlyGetBefore.equals("June")) {
                    monthlyGet = "6";
                } else if (monthlyGetBefore.equals("July")) {
                    monthlyGet = "7";
                } else if (monthlyGetBefore.equals("August")) {
                    monthlyGet = "8";
                } else if (monthlyGetBefore.equals("September")) {
                    monthlyGet = "9";
                } else if (monthlyGetBefore.equals("October")) {
                    monthlyGet = "10";
                } else if (monthlyGetBefore.equals("November")) {
                    monthlyGet = "11";
                } else if (monthlyGetBefore.equals("December")) {
                    monthlyGet = "12";
                }
                String yearlyGet = spinnerMonthlyYear.getSelectedItem().toString();
                String type = "Retrieve";
                RetrieveJoinedEventMonthlyTask retrieveJoinedEventMonthlyTask = new RetrieveJoinedEventMonthlyTask(JoinedEvent.this);
                retrieveJoinedEventMonthlyTask.execute(type, studentId, monthlyGet, yearlyGet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnYearlyJoined.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnAllJoined.setBackgroundResource(R.color.transparent);
                btnAllJoined.setTextColor(Color.BLACK);
                btnMonthlyJoined.setBackgroundResource(R.color.transparent);
                btnMonthlyJoined.setTextColor(Color.BLACK);
                btnYearlyJoined.setBackgroundResource(R.drawable.button);
                btnYearlyJoined.setTextColor(Color.WHITE);
                searchText.setVisibility(View.INVISIBLE);
                btnSearch.setVisibility(View.INVISIBLE);
                btnFilter.setVisibility(View.INVISIBLE);
                spinnerMonthlyMonth.setVisibility(View.INVISIBLE);
                spinnerMonthlyYear.setVisibility(View.INVISIBLE);
                spinnerYearlyYear.setVisibility(View.VISIBLE);

                String yearlyGet = spinnerYearlyYear.getSelectedItem().toString();

                String type = "Retrieve";
                RetrieveJoinedEventYearlyTask retrieveJoinedEventYearlyTaskTask = new RetrieveJoinedEventYearlyTask(JoinedEvent.this);
                retrieveJoinedEventYearlyTaskTask.execute(type, studentId, yearlyGet);
            }
        });

        spinnerYearlyYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String yearlyGet = spinnerYearlyYear.getSelectedItem().toString();

                String type = "Retrieve";
                RetrieveJoinedEventYearlyTask retrieveJoinedEventYearlyTaskTask = new RetrieveJoinedEventYearlyTask(JoinedEvent.this);
                retrieveJoinedEventYearlyTaskTask.execute(type, studentId, yearlyGet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    class CustomAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return IdArray.size();
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

            convertView = getLayoutInflater().inflate(R.layout.customlayout, null);

            TextView txtTitle = (TextView) convertView.findViewById(R.id.textViewStartDate);
            TextView txtDescription = (TextView) convertView.findViewById(R.id.textViewStartTime);
            TextView txtId = (TextView) convertView.findViewById(R.id.textViewId);
            TextView txtTimeTableId = (TextView) convertView.findViewById(R.id.textViewTimeTableId);


            txtTitle.setText(titleArray.get(position));
            txtDescription.setText(descriptionArray.get(position));
            txtId.setText(IdArray.get(position));
            txtTimeTableId.setText(timeTableIdArray.get(position));

            return convertView;
        }
    }



    public class RetrieveRegisteredTimeDate extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveRegisteredTimeDate(Context ctx) {
            context = ctx;
        }

        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrieveRegisteredTimeDate.php";

            if (type.equals("Retrieve")) {
                try {
                    String studentId = params[1];
                    String timeTableId = params[2];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8") + "=" + URLEncoder.encode(studentId, "UTF-8") + "&" +
                            URLEncoder.encode("timeTableId", "UTF-8") + "=" + URLEncoder.encode(timeTableId, "UTF-8");
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

            try {
                JSONObject response = new JSONObject(result);
                eventStartDate = response.getString("EventStartDate");
                registerTime = response.getString("RegisterTime");


                final AlertDialog.Builder mBuilder = new AlertDialog.Builder(JoinedEvent.this);
                final View mView = getLayoutInflater().inflate(R.layout.dialog_joined_time, null);
                mBuilder.setView(mView);
                dialog = mBuilder.create();

                TextView txtEventDateTime = (TextView)mView.findViewById(R.id.txtEventDateTime);
                TextView txtRegisteredDateTime = (TextView)mView.findViewById(R.id.txtRegisteredDateTime);
                TextView txtEventName = (TextView)mView.findViewById(R.id.txtTitle);

                txtEventDateTime.setText(eventStartDate);
                txtRegisteredDateTime.setText(registerTime);
                txtEventName.setText(eventName);


                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            } catch (JSONException e) {
                e.printStackTrace();
            }



        }
    }






    public class RetrieveJoinedEventAllTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveJoinedEventAllTask(Context ctx) {
            context = ctx;
        }

        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrievePastEvents.php";

            if (type.equals("Retrieve")) {
                try {
                    studentId = params[1];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8") + "=" + URLEncoder.encode(studentId, "UTF-8") + "&" +
                            URLEncoder.encode("all", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + "&" +
                            URLEncoder.encode("monthly", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("yearly", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthlyMonth", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthlyYear", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("yearlyYear", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
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
            if (result.equals("fail")) {
                IdArray.clear();
                titleArray.clear();
                descriptionArray.clear();
                timeTableIdArray.clear();
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            } else {
                ArrayList<String> IdArrayAsync = new ArrayList<>();
                ArrayList<String> titleArrayAsync = new ArrayList<>();
                ArrayList<String> descriptionArrayAsync = new ArrayList<>();
                ArrayList<String> timeTableIdArrayAsync = new ArrayList<>();
                String eventId = "";
                String eventTitle = "";
                String eventDescription = "";
                String timeTableId = "";
                try {
                    JSONObject response = new JSONObject(result);
                    eventId = response.getString("EventId");
                    eventTitle = response.getString("EventTitle");
                    eventDescription = response.getString("EventDescription");
                    timeTableId = response.getString("TimeTableId");

                    JSONArray jsonArrayId = new JSONArray(eventId);
                    JSONArray jsonArrayTitle = new JSONArray(eventTitle);
                    JSONArray jsonArrayDescription = new JSONArray(eventDescription);
                    JSONArray jsonArrayTimeTableId = new JSONArray(timeTableId);

                    for (int i = 0; i < jsonArrayId.length(); i++) {
                        JSONObject jsonObjectId = jsonArrayId.optJSONObject(i);
                        JSONObject jsonObjectTitle = jsonArrayTitle.optJSONObject(i);
                        JSONObject jsonObjectDescription = jsonArrayDescription.optJSONObject(i);
                        JSONObject jsonObjectTimeTableId = jsonArrayTimeTableId.optJSONObject(i);
                        if (jsonObjectId == null) {
                            continue;
                        }
                        String jsonValueId = jsonObjectId.optString("EventId");
                        String jsonValueTitle = jsonObjectTitle.optString("EventTitle");
                        String jsonValueDescription = jsonObjectDescription.optString("EventDescription");
                        String jsonValueTimeTableId = jsonObjectTimeTableId.optString("TimeTableId");
                        IdArrayAsync.add(jsonValueId);
                        titleArrayAsync.add(jsonValueTitle);
                        descriptionArrayAsync.add(jsonValueDescription);
                        timeTableIdArrayAsync.add(jsonValueTimeTableId);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IdArray.clear();
                IdArray = (ArrayList<String>) IdArrayAsync;
                titleArray.clear();
                titleArray = (ArrayList<String>) titleArrayAsync;
                descriptionArray.clear();
                descriptionArray = (ArrayList<String>) descriptionArrayAsync;
                timeTableIdArray.clear();
                timeTableIdArray = (ArrayList<String>) timeTableIdArrayAsync;
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            }


        }
    }

    public class RetrieveJoinedEventMonthlyTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveJoinedEventMonthlyTask(Context ctx) {
            context = ctx;
        }

        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrievePastEvents.php";

            if (type.equals("Retrieve")) {
                try {
                    studentId = params[1];
                    String monthlyMonthGet = params[2];
                    String monthlyYearGet = params[3];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8") + "=" + URLEncoder.encode(studentId, "UTF-8") + "&" +
                            URLEncoder.encode("all", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthly", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + "&" +
                            URLEncoder.encode("yearly", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthlyMonth", "UTF-8") + "=" + URLEncoder.encode(monthlyMonthGet, "UTF-8") + "&" +
                            URLEncoder.encode("monthlyYear", "UTF-8") + "=" + URLEncoder.encode(monthlyYearGet, "UTF-8") + "&" +
                            URLEncoder.encode("yearlyYear", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8");
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
            if (result.equals("fail")) {
                IdArray.clear();
                titleArray.clear();
                descriptionArray.clear();
                timeTableIdArray.clear();
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            } else {
                ArrayList<String> IdArrayAsync = new ArrayList<>();
                ArrayList<String> titleArrayAsync = new ArrayList<>();
                ArrayList<String> descriptionArrayAsync = new ArrayList<>();
                ArrayList<String> timeTableIdArrayAsync = new ArrayList<>();
                String eventId = "";
                String eventTitle = "";
                String eventDescription = "";
                String timeTableId = "";
                try {
                    JSONObject response = new JSONObject(result);
                    eventId = response.getString("EventId");
                    eventTitle = response.getString("EventTitle");
                    eventDescription = response.getString("EventDescription");
                    timeTableId = response.getString("TimeTableId");

                    JSONArray jsonArrayId = new JSONArray(eventId);
                    JSONArray jsonArrayTitle = new JSONArray(eventTitle);
                    JSONArray jsonArrayDescription = new JSONArray(eventDescription);
                    JSONArray jsonArrayTimeTableId = new JSONArray(timeTableId);

                    for (int i = 0; i < jsonArrayId.length(); i++) {
                        JSONObject jsonObjectId = jsonArrayId.optJSONObject(i);
                        JSONObject jsonObjectTitle = jsonArrayTitle.optJSONObject(i);
                        JSONObject jsonObjectDescription = jsonArrayDescription.optJSONObject(i);
                        JSONObject jsonObjectTimeTableId = jsonArrayTimeTableId.optJSONObject(i);
                        if (jsonObjectId == null) {
                            continue;
                        }
                        String jsonValueId = jsonObjectId.optString("EventId");
                        String jsonValueTitle = jsonObjectTitle.optString("EventTitle");
                        String jsonValueDescription = jsonObjectDescription.optString("EventDescription");
                        String jsonValueTimeTableId = jsonObjectTimeTableId.optString("TimeTableId");
                        IdArrayAsync.add(jsonValueId);
                        titleArrayAsync.add(jsonValueTitle);
                        descriptionArrayAsync.add(jsonValueDescription);
                        timeTableIdArrayAsync.add(jsonValueTimeTableId);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IdArray.clear();
                IdArray = (ArrayList<String>) IdArrayAsync;
                titleArray.clear();
                titleArray = (ArrayList<String>) titleArrayAsync;
                descriptionArray.clear();
                descriptionArray = (ArrayList<String>) descriptionArrayAsync;
                timeTableIdArray.clear();
                timeTableIdArray = (ArrayList<String>) timeTableIdArrayAsync;
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            }


        }
    }

    public class RetrieveJoinedEventYearlyTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        RetrieveJoinedEventYearlyTask(Context ctx) {
            context = ctx;
        }

        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/retrievePastEvents.php";

            if (type.equals("Retrieve")) {
                try {
                    studentId = params[1];
                    String yearlyYearGet = params[2];
                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8") + "=" + URLEncoder.encode(studentId, "UTF-8") + "&" +
                            URLEncoder.encode("all", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthly", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("yearly", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8") + "&" +
                            URLEncoder.encode("monthlyMonth", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("monthlyYear", "UTF-8") + "=" + URLEncoder.encode("0", "UTF-8") + "&" +
                            URLEncoder.encode("yearlyYear", "UTF-8") + "=" + URLEncoder.encode(yearlyYearGet, "UTF-8");
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
        protected void onPostExecute(final String result) {
            if (result.equals("fail")) {
                IdArray.clear();
                titleArray.clear();
                descriptionArray.clear();
                timeTableIdArray.clear();
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            } else {
                ArrayList<String> IdArrayAsync = new ArrayList<>();
                ArrayList<String> titleArrayAsync = new ArrayList<>();
                ArrayList<String> descriptionArrayAsync = new ArrayList<>();
                ArrayList<String> timeTableIdArrayAsync = new ArrayList<>();
                String eventId = "";
                String eventTitle = "";
                String eventDescription = "";
                String timeTableId = "";
                try {
                    JSONObject response = new JSONObject(result);
                    eventId = response.getString("EventId");
                    eventTitle = response.getString("EventTitle");
                    eventDescription = response.getString("EventDescription");
                    timeTableId = response.getString("TimeTableId");

                    JSONArray jsonArrayId = new JSONArray(eventId);
                    JSONArray jsonArrayTitle = new JSONArray(eventTitle);
                    JSONArray jsonArrayDescription = new JSONArray(eventDescription);
                    JSONArray jsonArrayTimeTableId = new JSONArray(timeTableId);

                    for (int i = 0; i < jsonArrayId.length(); i++) {
                        JSONObject jsonObjectId = jsonArrayId.optJSONObject(i);
                        JSONObject jsonObjectTitle = jsonArrayTitle.optJSONObject(i);
                        JSONObject jsonObjectDescription = jsonArrayDescription.optJSONObject(i);
                        JSONObject jsonObjectTimeTableId = jsonArrayTimeTableId.optJSONObject(i);
                        if (jsonObjectId == null) {
                            continue;
                        }
                        String jsonValueId = jsonObjectId.optString("EventId");
                        String jsonValueTitle = jsonObjectTitle.optString("EventTitle");
                        String jsonValueDescription = jsonObjectDescription.optString("EventDescription");
                        String jsonValueTimeTableId = jsonObjectTimeTableId.optString("TimeTableId");
                        IdArrayAsync.add(jsonValueId);
                        titleArrayAsync.add(jsonValueTitle);
                        descriptionArrayAsync.add(jsonValueDescription);
                        timeTableIdArrayAsync.add(jsonValueTimeTableId);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                IdArray.clear();
                IdArray = (ArrayList<String>) IdArrayAsync;
                titleArray.clear();
                titleArray = (ArrayList<String>) titleArrayAsync;
                descriptionArray.clear();
                descriptionArray = (ArrayList<String>) descriptionArrayAsync;
                timeTableIdArray.clear();
                timeTableIdArray = (ArrayList<String>) timeTableIdArrayAsync;
                customAdapter.notifyDataSetChanged();
                mListView.setAdapter(customAdapter);
            }


        }
    }
}

