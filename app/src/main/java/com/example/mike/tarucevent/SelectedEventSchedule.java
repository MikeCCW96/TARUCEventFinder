package com.example.mike.tarucevent;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.Image;
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
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class SelectedEventSchedule extends AppCompatActivity {

    ArrayList<String> timeTableIdArray = new ArrayList<>();
    ArrayList<String> eventStartDateArray = new ArrayList<>();
    ArrayList<String> eventEndDateArray = new ArrayList<>();
    ArrayList<String> eventStartTimeArray = new ArrayList<>();
    ArrayList<String> eventEndTimeArray = new ArrayList<>();
    ArrayList<String> venueIdArray = new ArrayList<>();
    ArrayList<String> venueNameArray = new ArrayList<>();
    ArrayList<String> venueDescriptionArray = new ArrayList<>();
    String studentId = "";
    String studentId2 = "";
    String studentId3 = "";
    String studentId4 = "";
    String studentId5 = "";
    String eventId = "";
    String timeTableId = "";
    String referBy = "";
    private ListView mListView;
    private Button btnAttend, btnConfirm, btnCancel;
    private Spinner spinner;
    private EditText person1, person2, person3, person4, person5;
    private TextView dialogTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event_schedule);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Select Schedule");
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectedEventSchedule.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_attend, null);

        Intent intent = getIntent();
        timeTableIdArray = (ArrayList<String>) intent.getSerializableExtra("timeTableIdArray");
        eventStartDateArray = (ArrayList<String>) intent.getSerializableExtra("eventStartDateArray");
        eventEndDateArray = (ArrayList<String>) intent.getSerializableExtra("eventEndDateArray");
        eventStartTimeArray = (ArrayList<String>) intent.getSerializableExtra("eventStartTimeArray");
        eventEndTimeArray = (ArrayList<String>) intent.getSerializableExtra("eventEndTimeArray");
        venueIdArray = (ArrayList<String>) intent.getSerializableExtra("venueIdArray");
        venueNameArray = (ArrayList<String>) intent.getSerializableExtra("venueNameArray");
        venueDescriptionArray = (ArrayList<String>) intent.getSerializableExtra("venueDescriptionArray");
        studentId = intent.getStringExtra("studentId");
        eventId = intent.getStringExtra("eventId");
        referBy = intent.getStringExtra("referBy");

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();
        btnConfirm = (Button) mView.findViewById(R.id.btnConfirm);
        btnCancel= (Button) mView.findViewById(R.id.btnCancel);
        spinner = (Spinner) mView.findViewById(R.id.spinner);
        person1 = (EditText) mView.findViewById(R.id.person1);
        person2 = (EditText) mView.findViewById(R.id.person2);
        person3 = (EditText) mView.findViewById(R.id.person3);
        person4 = (EditText) mView.findViewById(R.id.person4);
        person5 = (EditText) mView.findViewById(R.id.person5);
        dialogTitle = (TextView) mView.findViewById(R.id.dialogTitle);

        person1.setFocusable(false);
        person1.setClickable(false);
        person1.setText(studentId);

        mListView = (ListView)findViewById(R.id.listView);
        CustomAdapter customAdapter = new CustomAdapter();
        mListView.setAdapter(customAdapter);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView getIdText = (TextView)view.findViewById(R.id.textViewVenueName);
                TextView getTimeTableIdText = (TextView) view.findViewById(R.id.TextViewTimeTableId);
                timeTableId = getTimeTableIdText.getText().toString();


                if (referBy.equals("None")){
                    dialogTitle.setText("");
                } else {
                    dialogTitle.setText("Referred by : " + referBy);
                }

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String numberSelected = spinner.getSelectedItem().toString();

                if (numberSelected.equals("1")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.INVISIBLE);
                    person3.setVisibility(mView.INVISIBLE);
                    person4.setVisibility(mView.INVISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                    person2.setText("");
                    person3.setText("");
                    person4.setText("");
                    person5.setText("");
                } else if (numberSelected.equals("2")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.INVISIBLE);
                    person4.setVisibility(mView.INVISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                    person3.setText("");
                    person4.setText("");
                    person5.setText("");
                } else if (numberSelected.equals("3")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.VISIBLE);
                    person4.setVisibility(mView.INVISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                    person4.setText("");
                    person5.setText("");
                } else if (numberSelected.equals("4")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.VISIBLE);
                    person4.setVisibility(mView.VISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                    person5.setText("");
                } else if (numberSelected.equals("5")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.VISIBLE);
                    person4.setVisibility(mView.VISIBLE);
                    person5.setVisibility(mView.VISIBLE);
                }


            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String numberSelected = spinner.getSelectedItem().toString();
                if (numberSelected.equals("1")){
                    new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String type = "registerEvent";
                                    RegisterTask registerTask = new RegisterTask(getApplicationContext());
                                    registerTask.execute(type, timeTableId, studentId, "", "", "", "", referBy);
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();


                } else if (numberSelected.equals("2")){
                    if (person2.getText().toString().equals("") || person2.getText().toString().length() < 7 || person2.getText().toString().length() > 7){
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Invalid Student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (person2.getText().toString().equals(person1.getText().toString())) {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Cannot have multiple same student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Confirmation")
                                .setMessage("Are you sure?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String type = "registerEvent";
                                        RegisterTask registerTask = new RegisterTask(getApplicationContext());
                                        studentId2 = person2.getText().toString();
                                        registerTask.execute(type, timeTableId, studentId, studentId2, "", "", "", referBy);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                } else if (numberSelected.equals("3")){
                    if (person2.getText().toString().equals("") || person2.getText().toString().length() < 7 || person2.getText().toString().length() > 7 || person3.getText().toString().equals("") || person3.getText().toString().length() < 7 || person3.getText().toString().length() > 7){
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Invalid Student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (person2.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person2.getText().toString())) {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Cannot have multiple same student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Confirmation")
                                .setMessage("Are you sure?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String type = "registerEvent";
                                        RegisterTask registerTask = new RegisterTask(getApplicationContext());
                                        studentId2 = person2.getText().toString();
                                        studentId3 = person3.getText().toString();
                                        registerTask.execute(type, timeTableId, studentId, studentId2, studentId3, "", "", referBy);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }
                } else if (numberSelected.equals("4")){
                    if (person2.getText().toString().equals("") || person2.getText().toString().length() < 7 || person2.getText().toString().length() > 7 || person3.getText().toString().equals("") || person3.getText().toString().length() < 7 || person3.getText().toString().length() > 7 || person4.getText().toString().equals("") || person4.getText().toString().length() < 7 || person4.getText().toString().length() > 7){
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Invalid Student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (person2.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person2.getText().toString()) || person4.getText().toString().equals(person1.getText().toString()) || person4.getText().toString().equals(person2.getText().toString()) || person4.getText().toString().equals(person3.getText().toString())) {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Cannot have multiple same student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Confirmation")
                                .setMessage("Are you sure?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String type = "registerEvent";
                                        RegisterTask registerTask = new RegisterTask(getApplicationContext());
                                        studentId2 = person2.getText().toString();
                                        studentId3 = person3.getText().toString();
                                        studentId4 = person4.getText().toString();
                                        registerTask.execute(type, timeTableId, studentId, studentId2, studentId3, studentId4, "", referBy);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }

                } else if (numberSelected.equals("5")){
                    if (person2.getText().toString().equals("") || person2.getText().toString().length() < 7 || person2.getText().toString().length() > 7 || person3.getText().toString().equals("") || person3.getText().toString().length() < 7 || person3.getText().toString().length() > 7 || person4.getText().toString().equals("") || person4.getText().toString().length() < 7 || person4.getText().toString().length() > 7 || person5.getText().toString().equals("") || person5.getText().toString().length() < 7 || person5.getText().toString().length() > 7){
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Invalid Student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else if (person2.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person1.getText().toString()) || person3.getText().toString().equals(person2.getText().toString()) || person4.getText().toString().equals(person1.getText().toString()) || person4.getText().toString().equals(person2.getText().toString()) || person4.getText().toString().equals(person3.getText().toString()) || person5.getText().toString().equals(person1.getText().toString()) || person5.getText().toString().equals(person2.getText().toString()) || person5.getText().toString().equals(person3.getText().toString()) || person5.getText().toString().equals(person4.getText().toString())) {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Error")
                                .setMessage("Cannot have multiple same student ID")
                                .setCancelable(false)
                                .setPositiveButton("OK", null)
                                .show();
                    } else {
                        new android.app.AlertDialog.Builder(SelectedEventSchedule.this)
                                .setIcon(R.drawable.ic_error_outline_black_24dp)
                                .setTitle("Confirmation")
                                .setMessage("Are you sure?")
                                .setCancelable(false)
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        String type = "registerEvent";
                                        RegisterTask registerTask = new RegisterTask(getApplicationContext());
                                        studentId2 = person2.getText().toString();
                                        studentId3 = person3.getText().toString();
                                        studentId4 = person4.getText().toString();
                                        studentId5 = person5.getText().toString();
                                        registerTask.execute(type, timeTableId, studentId, studentId2, studentId3, studentId4, studentId5, referBy);
                                    }
                                })
                                .setNegativeButton("No", null)
                                .show();

                    }

                }
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

            txtStartDate.setText(eventStartDateArray.get(position));
            txtEndDate.setText(eventEndDateArray.get(position));
            txtStartTime.setText(eventStartTimeArray.get(position));
            txtEndTime.setText(eventEndTimeArray.get(position));
            txtVenueName.setText(venueNameArray.get(position));
            txtVenueDescription.setText(venueDescriptionArray.get(position));
            txtTimeTableId.setText(timeTableIdArray.get(position));

            return convertView;
        }
    }
}

