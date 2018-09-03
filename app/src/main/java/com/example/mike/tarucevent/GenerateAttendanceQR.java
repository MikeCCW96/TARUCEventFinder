package com.example.mike.tarucevent;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.ArrayList;

public class GenerateAttendanceQR extends AppCompatActivity {

    ArrayList<String> timeTableIdArray = new ArrayList<>();
    ArrayList<String> eventStartDateArray = new ArrayList<>();
    ArrayList<String> eventEndDateArray = new ArrayList<>();
    ArrayList<String> eventStartTimeArray = new ArrayList<>();
    ArrayList<String> eventEndTimeArray = new ArrayList<>();
    ArrayList<String> venueIdArray = new ArrayList<>();
    ArrayList<String> venueNameArray = new ArrayList<>();
    ArrayList<String> venueDescriptionArray = new ArrayList<>();
    ArrayList<String> waitingListStatusArray = new ArrayList<>();
    String studentId = "";
    String eventId = "";
    String text2Qr = "";
    String getStatus = "";
    private ImageView image;
    private ListView mListView;
    String timeTableId, getId;
    Button btnCancel;
    String regId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_attendance_qr);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select Schedule");
        getStatus = "";

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(GenerateAttendanceQR.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_qr_attend, null);

        Intent intent = getIntent();
        timeTableIdArray = (ArrayList<String>) intent.getSerializableExtra("timeTableIdArray");
        eventStartDateArray = (ArrayList<String>) intent.getSerializableExtra("eventStartDateArray");
        eventEndDateArray = (ArrayList<String>) intent.getSerializableExtra("eventEndDateArray");
        eventStartTimeArray = (ArrayList<String>) intent.getSerializableExtra("eventStartTimeArray");
        eventEndTimeArray = (ArrayList<String>) intent.getSerializableExtra("eventEndTimeArray");
        venueIdArray = (ArrayList<String>) intent.getSerializableExtra("venueIdArray");
        venueNameArray = (ArrayList<String>) intent.getSerializableExtra("venueNameArray");
        venueDescriptionArray = (ArrayList<String>) intent.getSerializableExtra("venueDescriptionArray");
        waitingListStatusArray = (ArrayList<String>) intent.getSerializableExtra("waitingListStatusArray");


        studentId = intent.getStringExtra("studentId");
        eventId = intent.getStringExtra("eventId");

        image = (ImageView)mView.findViewById(R.id.image);

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnCancel = (Button) mView.findViewById(R.id.btnCancel);

        mListView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getIdText = (TextView)view.findViewById(R.id.TextViewTimeTableId);
                TextView getStatusText = (TextView)view.findViewById(R.id.TextViewWaitingListStatus);
                getId = getIdText.getText().toString();
                getStatus = getStatusText.getText().toString();

                if(getStatus.equals("Waiting")){
                    new android.app.AlertDialog.Builder(GenerateAttendanceQR.this)
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setTitle("Sorry")
                            .setMessage("Unfortunately, you're still in waiting list.")
                            .setCancelable(false)
                            .setPositiveButton("OK", null)
                            .show();
                }else {
                    String type = "generateQR";
                    GenerateQRForAttendance generateQRForAttendance = new GenerateQRForAttendance(GenerateAttendanceQR.this);
                    generateQRForAttendance.execute(type, getId, studentId);
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    dialog.show();
                }


            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new android.app.AlertDialog.Builder(GenerateAttendanceQR.this)
                        .setIcon(R.drawable.ic_error_outline_black_24dp)
                        .setTitle("Cancel")
                        .setMessage("Are you sure?")
                        .setCancelable(false)
                        .setNegativeButton("No", null)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String type = "Cancel";
                                String timeTableId = getId;
                                CheckWaitingListTask checkWaitingListTask = new CheckWaitingListTask(GenerateAttendanceQR.this);
                                checkWaitingListTask.execute(type, regId, timeTableId);

                            }
                        })
                        .show();
            }
        });
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
            return timeTableIdArray.size();
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
            convertView = getLayoutInflater().inflate(R.layout.customlayoutschedule, null);
            TextView txtStartDate = (TextView)convertView.findViewById(R.id.textViewStartDate);
            TextView txtEndDate = (TextView)convertView.findViewById(R.id.textViewEndDate);
            TextView txtStartTime = (TextView)convertView.findViewById(R.id.textViewStartTime);
            TextView txtEndTime = (TextView)convertView.findViewById(R.id.textViewEndTime);
            TextView txtVenueName = (TextView)convertView.findViewById(R.id.textViewVenueName);
            TextView txtVenueDescription = (TextView)convertView.findViewById(R.id.textViewVenueDescription);
            TextView txtTimeTableId = (TextView)convertView.findViewById(R.id.TextViewTimeTableId);
            TextView txtWaitingListStatus = (TextView)convertView.findViewById(R.id.TextViewWaitingListStatus);


            txtStartDate.setText(eventStartDateArray.get(position));
            txtEndDate.setText(eventEndDateArray.get(position));
            txtStartTime.setText(eventStartTimeArray.get(position));
            txtEndTime.setText(eventEndTimeArray.get(position));
            txtVenueName.setText(venueNameArray.get(position));
            txtVenueDescription.setText(venueDescriptionArray.get(position));
            txtTimeTableId.setText(timeTableIdArray.get(position));
            txtWaitingListStatus.setText(waitingListStatusArray.get(position));

            return convertView;
        }
    }

    public class CheckWaitingListTask extends AsyncTask<String, Void, String>{

        Context context;
        CheckWaitingListTask(Context ctx){
            context = ctx;
        }
        android.app.AlertDialog alertDialog;


        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            Connectivity connectivity = new Connectivity();
            String ipAddress = "" + connectivity.getIP();
            String cancel_url = ipAddress + "/Android/waitingList.php";

            if(type.equals("Cancel")){
                try {
                    String regIdGet = params[1];
                    String timeTableIdGet = params[2];
                    Log.i("regId", regIdGet);
                    Log.i("timeTable", timeTableIdGet);
                    URL url = new URL(cancel_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("regId", "UTF-8")+"="+URLEncoder.encode(regIdGet, "UTF-8")+"&"+
                            URLEncoder.encode("timeTableId", "UTF-8")+"="+URLEncoder.encode(timeTableIdGet, "UTF-8");
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
        protected void onPreExecute(){
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.equals("attended")){
                Toast.makeText(getApplicationContext(), "Attendance already taken, unable to cancel", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                finish();
            }


        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }
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

            try {
                JSONObject response = new JSONObject(result);
                regId = response.getString("RegistrationId");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            if (getStatus.equals("Waiting")){
                Toast.makeText(GenerateAttendanceQR.this, "Still Waiting", Toast.LENGTH_SHORT).show();
            } else {
                text2Qr = "TARUCEventApp://ATTENDANCE/REG" + regId;
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
}




