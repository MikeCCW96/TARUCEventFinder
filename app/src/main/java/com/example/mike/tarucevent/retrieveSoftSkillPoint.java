package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TableLayout;
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

public class retrieveSoftSkillPoint extends AppCompatActivity {
    TextView studentId, CS, CTPS, TS, LL, KK, EM, LS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_ssp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Soft Skill Points");

        //TextView that used in table layout
        CS = (TextView) findViewById(R.id.tvSSP1);
        CTPS = (TextView) findViewById(R.id.tvSSP2);
        TS = (TextView) findViewById(R.id.tvSSP3);
        LL = (TextView) findViewById(R.id.tvSSP4);
        KK = (TextView) findViewById(R.id.tvSSP5);
        EM = (TextView) findViewById(R.id.tvSSP6);
        LS = (TextView) findViewById(R.id.tvSSP7);

        Intent intent = getIntent();
        String StudentId = intent.getStringExtra("studentId");

        studentId = (TextView) findViewById(R.id.txtStudentId);
        studentId.setText(StudentId);
        String type = "retrieveSoftSkillPoint";

        RetrieveSoftSkillPointTask retrieveSoftSkillPointTask = new RetrieveSoftSkillPointTask(this);
        retrieveSoftSkillPointTask.execute(type, StudentId);

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

    private void loadIntoTable(String json) throws JSONException {
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

        String[] softSkillPoint2 = softSkillPoint[0].split(",");

        CS.setText(softSkillPoint2[0].substring(1));
        CTPS.setText(softSkillPoint2[1]);
        TS.setText(softSkillPoint2[2]);
        LL.setText(softSkillPoint2[3]);
        KK.setText(softSkillPoint2[4]);
        EM.setText(softSkillPoint2[5]);
        LS.setText(softSkillPoint2[6].substring(0, softSkillPoint2[6].length() - 1));
    }

    public class RetrieveSoftSkillPointTask extends AsyncTask<String, Void, String>{
        Context context;

        String studentId;

        RetrieveSoftSkillPointTask(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieveCurrentAndEventSSP_url = ipAddress + "/Android/retrieveSoftSkillPoint.php";

            if (type.equals("retrieveSoftSkillPoint")) {
                try {
                    studentId = params[1];
                    URL url = new URL(retrieveCurrentAndEventSSP_url);
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

                    StringBuilder sb = new StringBuilder();
                    String json;

                    while((json = bufferedReader.readLine()) != null){
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
                loadIntoTable(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }



        @Override
        protected void onProgressUpdate(Void... values) {

            super.onProgressUpdate(values);
        }
    }
}

