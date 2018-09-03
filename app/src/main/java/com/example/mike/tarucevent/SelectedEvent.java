package com.example.mike.tarucevent;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.support.v7.widget.Toolbar;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SelectedEvent extends AppCompatActivity implements OnMapReadyCallback {
    private Button btnAttend, btnConfirm, btnCancel, btnAttendance, btnViewBrochure;
    private ImageButton btnShare;
    private Spinner spinner;
    private EditText person1, person2, person3, person4, person5;
    private TextView dialogTitle;
    private String studentId = "";
    private String eventId = "";
    private String eventLocation = "";
    private String referBy = "";
    private String eventBrochure = "";
    private String brochureType = "";
    private GoogleMap mMap;
    private AlertDialog dialog2;
    ImageView image;
    String text2Qr;

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // API 5+ solution
                onBackPressed();
                return true;

            case R.id.Search:
                text2Qr = "TARUCEventApp://SHARE/{"+ "\"studentId\""+":" + "\"" + studentId + "\"" + "," + "\"eventId\""+":" + "\"" + eventId + "\"" + "}";

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{

                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_event);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        final AlertDialog.Builder mBuilder2 = new AlertDialog.Builder(SelectedEvent.this);
        final View mView2 = getLayoutInflater().inflate(R.layout.dialog_qr_share, null);
        image = (ImageView)mView2.findViewById(R.id.image) ;

        mBuilder2.setView(mView2);
        dialog2 = mBuilder2.create();

        //map thing


        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        //map thing

        Intent intent = getIntent();
        final String eventTitle = intent.getStringExtra("eventTitle");
        final String eventDescription = intent.getStringExtra("eventDescription");
        final String activityType = intent.getStringExtra("activityType");
        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(SelectedEvent.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_attend, null);

        setTitle(eventTitle);

        studentId = intent.getStringExtra("studentId");
        eventId = intent.getStringExtra("eventId");
        eventLocation = intent.getStringExtra("eventLocation");
        referBy = intent.getStringExtra("referBy");
        eventBrochure = intent.getStringExtra("eventBrochure");
        brochureType = intent.getStringExtra("brochureType");
        TextView txtData = (TextView)findViewById(R.id.txtData);
        TextView txtDescription = (TextView)findViewById(R.id.txtDescription);
        TextView txtType = (TextView)findViewById(R.id.txtActivityType);


        if (eventTitle.indexOf("TARUCEventApp://") > -1){
            String remove = "TARUCEventApp://";
            txtData = (TextView)findViewById(R.id.txtData);
            txtData.setText(removeWords(eventTitle, remove));

        } else {
            txtData = (TextView)findViewById(R.id.txtData);
            txtData.setText(eventTitle);
        }

        txtDescription.setText(eventDescription);
        txtType.setText(activityType);


        btnShare = (ImageButton) findViewById(R.id.btnShare);
        btnAttend = (Button) findViewById(R.id.btnAttend);
        btnAttendance = (Button) findViewById(R.id.btnAttendance);
        btnConfirm = (Button) mView.findViewById(R.id.btnConfirm);
        btnCancel= (Button) mView.findViewById(R.id.btnCancel);
        btnViewBrochure = (Button) findViewById(R.id.btnViewBrochure);
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

        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        btnViewBrochure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (SelectedEvent.this, ViewBrochure.class);
                intent.putExtra("eventBrochure", eventBrochure);
                intent.putExtra("brochureType", brochureType);
                startActivity(intent);
            }
        });




        btnAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = "SelectedEventScheduleGenerate";
                GenerateAttendanceTask generateAttendanceTask = new GenerateAttendanceTask(getApplicationContext());
                generateAttendanceTask.execute(type, eventId, studentId);
            }
        });

        btnAttend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogTitle.setText(eventTitle);
                //dialog.show();
                String type = "SelectedEventSchedule";
                SelectedEventScheduleTask selectedEventScheduleTask = new SelectedEventScheduleTask(getApplicationContext());
                selectedEventScheduleTask.execute(type, eventId, studentId, referBy);

            }

        });

        btnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text2Qr = "TARUCEventApp://SHARE/{"+ "\"studentId\""+":" + "\"" + studentId + "\"" + "," + "\"eventId\""+":" + "\"" + eventId + "}";

                MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                try{

                    BitMatrix bitMatrix = multiFormatWriter.encode(text2Qr, BarcodeFormat.QR_CODE,200,200);
                    BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                    Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                    image.setImageBitmap(bitmap);
                } catch (WriterException e) {
                    e.printStackTrace();
                }
                dialog2.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog2.show();
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
                } else if (numberSelected.equals("2")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.INVISIBLE);
                    person4.setVisibility(mView.INVISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                } else if (numberSelected.equals("3")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.VISIBLE);
                    person4.setVisibility(mView.INVISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
                } else if (numberSelected.equals("4")){
                    person1.setVisibility(mView.VISIBLE);
                    person2.setVisibility(mView.VISIBLE);
                    person3.setVisibility(mView.VISIBLE);
                    person4.setVisibility(mView.VISIBLE);
                    person5.setVisibility(mView.INVISIBLE);
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
                    Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
                } else if (numberSelected.equals("2")){
                    if (person2.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
                    }
                } else if (numberSelected.equals("3")){
                    if (person2.getText().toString().equals("") || person3.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
                    }
                } else if (numberSelected.equals("4")){
                    if (person2.getText().toString().equals("") || person3.getText().toString().equals("") || person4.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
                    }

                } else if (numberSelected.equals("5")){
                    if (person2.getText().toString().equals("") || person3.getText().toString().equals("") || person4.getText().toString().equals("") || person5.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"fail",Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(),"yay",Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });

    }



    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.getUiSettings().setScrollGesturesEnabled(false);
        LatLng location = new LatLng(3.2162353,101.7277986);

        if (eventLocation.equals("Canteen2")){
            location = new LatLng(3.2162353,101.7277986);
            mMap.addMarker(new MarkerOptions().position(location).title("Canteen 2"));
        } else if (eventLocation.equals("Canteen1")) {
            location = new LatLng(3.216090, 101.725451);
            mMap.addMarker(new MarkerOptions().position(location).title("Canteen 1"));
        } else if (eventLocation.equals("Hall")){
            location = new LatLng(3.216421, 101.729375);
            mMap.addMarker(new MarkerOptions().position(location).title("Hall"));
        }


        float zoomLevel = 19.0f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoomLevel));

    }

    public static String removeWords(String word ,String remove) {
        return word.replace(remove,"");
    }



}
