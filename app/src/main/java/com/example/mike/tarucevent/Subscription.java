package com.example.mike.tarucevent;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class Subscription extends AppCompatActivity {
    ArrayList<String> activityArray = new ArrayList<>();
    Button btnMeeting, btnAnnual, btnTraining, btnClass, btnGathering, btnVisit, btnTrip, btnCamp, btnPerformance, btnNite, btnTalk,
                        btnWorkshop, btnSeminar, btnConference, btnExhibition, btnFundRaising, btnCompetition, btnSportsCarnival, btnTreasureHunt, btnOthers,
                        btnCancel, btnOK;

    Integer intMeeting, intAnnual, intTraining, intClass, intGathering, intVisit, intTrip, intCamp, intPerformance, intNite, intTalk,
                        intWorkshop, intSeminar, intConference, intExhibition, intFundraising, intCompetition, intSportsCarnival, intTreasureHunt, intOthers;


    Integer userEdited = 0;

    String studentId = "";

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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setTitle("Subscription");
        setContentView(R.layout.activity_subscription);

        intMeeting = 0;
        intAnnual = 0;
        intTraining = 0;
        intClass = 0;
        intGathering = 0;
        intVisit = 0;
        intTrip = 0;
        intCamp = 0;
        intPerformance = 0;
        intNite = 0;
        intTalk = 0;
        intWorkshop = 0;
        intSeminar = 0;
        intConference = 0;
        intExhibition = 0;
        intFundraising = 0;
        intCompetition = 0;
        intSportsCarnival = 0;
        intTreasureHunt = 0;
        intOthers = 0;

        btnMeeting = (Button)findViewById(R.id.btnMeeting);
        btnAnnual = (Button)findViewById(R.id.btnAnnual);
        btnTraining = (Button)findViewById(R.id.btnTraining);
        btnClass = (Button)findViewById(R.id.btnClass);
        btnGathering = (Button)findViewById(R.id.btnGathering);
        btnVisit = (Button)findViewById(R.id.btnVisit);
        btnTrip = (Button)findViewById(R.id.btnTrip);
        btnCamp = (Button)findViewById(R.id.btnCamp);
        btnPerformance = (Button)findViewById(R.id.btnPerformance);
        btnNite = (Button)findViewById(R.id.btnNite);
        btnTalk = (Button)findViewById(R.id.btnTalk);
        btnWorkshop = (Button)findViewById(R.id.btnWorkshop);
        btnSeminar = (Button)findViewById(R.id.btnSeminar);
        btnConference = (Button)findViewById(R.id.btnConference);
        btnExhibition = (Button)findViewById(R.id.btnExhibition);
        btnFundRaising = (Button)findViewById(R.id.btnFundRaising);
        btnCompetition = (Button)findViewById(R.id.btnCompetition);
        btnSportsCarnival = (Button)findViewById(R.id.btnSportsCarnival);
        btnTreasureHunt = (Button)findViewById(R.id.btnTreasureHunt);
        btnOthers = (Button)findViewById(R.id.btnOthers);

        btnCancel = (Button)findViewById(R.id.btnCancel);
        btnOK = (Button)findViewById(R.id.btnOK);

        btnMeeting.setBackgroundResource(R.drawable.buttonred);
        btnAnnual.setBackgroundResource(R.drawable.buttonred);
        btnTraining.setBackgroundResource(R.drawable.buttonred);
        btnClass.setBackgroundResource(R.drawable.buttonred);
        btnGathering.setBackgroundResource(R.drawable.buttonred);
        btnVisit.setBackgroundResource(R.drawable.buttonred);
        btnTrip.setBackgroundResource(R.drawable.buttonred);
        btnCamp.setBackgroundResource(R.drawable.buttonred);
        btnPerformance.setBackgroundResource(R.drawable.buttonred);
        btnNite.setBackgroundResource(R.drawable.buttonred);
        btnTalk.setBackgroundResource(R.drawable.buttonred);
        btnWorkshop.setBackgroundResource(R.drawable.buttonred);
        btnSeminar.setBackgroundResource(R.drawable.buttonred);
        btnConference.setBackgroundResource(R.drawable.buttonred);
        btnExhibition.setBackgroundResource(R.drawable.buttonred);
        btnFundRaising.setBackgroundResource(R.drawable.buttonred);
        btnCompetition.setBackgroundResource(R.drawable.buttonred);
        btnSportsCarnival.setBackgroundResource(R.drawable.buttonred);
        btnTreasureHunt.setBackgroundResource(R.drawable.buttonred);
        btnOthers.setBackgroundResource(R.drawable.buttonred);

        Intent intent = getIntent();
        activityArray = (ArrayList<String>) intent.getSerializableExtra("activityArray");
        studentId = intent.getStringExtra("studentId");

        for (int i = 0; i < activityArray.size(); i++){
            String getData = activityArray.get(i);

            if (getData.equals("Meeting")){
                intMeeting = 1;
                btnMeeting.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Annual General Meeting")){
                intAnnual = 1;
                btnAnnual.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Training/Practice")){
                intTraining = 1;
                btnTraining.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Class")){
                intClass = 1;
                btnClass.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Gathering")){
                intGathering = 1;
                btnGathering.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Visit")){
                intVisit = 1;
                btnVisit.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Trip")){
                intTrip = 1;
                btnTrip.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Camp")){
                intCamp = 1;
                btnCamp.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Performance")){
                intPerformance = 1;
                btnPerformance.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Nite")){
                intNite = 1;
                btnNite.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Talk")){
                intTalk = 1;
                btnTalk.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Workshop")){
                intWorkshop = 1;
                btnWorkshop.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Seminar")){
                intSeminar = 1;
                btnSeminar.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Conference")){
                intConference = 1;
                btnConference.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Exhibition")){
                intExhibition = 1;
                btnExhibition.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Fund Raising")){
                intFundraising = 1;
                btnFundRaising.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Competition")){
                intCompetition = 1;
                btnCompetition.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Sports Carnival")){
                intSportsCarnival = 1;
                btnSportsCarnival.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Treasure Hunt")){
                intTreasureHunt = 1;
                btnTreasureHunt.setBackgroundResource(R.drawable.button);
            } else if (getData.equals("Others")){
                intOthers = 1;
                btnOthers.setBackgroundResource(R.drawable.button);
            }
        }

        btnMeeting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intMeeting == 1){
                    btnMeeting.setBackgroundResource(R.drawable.buttonred);
                    intMeeting = 0;
                } else {
                    btnMeeting.setBackgroundResource(R.drawable.button);
                    intMeeting = 1;
                }
            }
        });

        btnAnnual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intAnnual == 1){
                    btnAnnual.setBackgroundResource(R.drawable.buttonred);
                    intAnnual = 0;
                } else {
                    btnAnnual.setBackgroundResource(R.drawable.button);
                    intAnnual = 1;
                }
            }
        });

        btnTraining.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intTraining == 1){
                    btnTraining.setBackgroundResource(R.drawable.buttonred);
                    intTraining = 0;
                } else {
                    btnTraining.setBackgroundResource(R.drawable.button);
                    intTraining = 1;
                }
            }
        });

        btnClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intClass == 1){
                    btnClass.setBackgroundResource(R.drawable.buttonred);
                    intClass = 0;
                } else {
                    btnClass.setBackgroundResource(R.drawable.button);
                    intClass = 1;
                }
            }
        });

        btnGathering.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intGathering == 1){
                    btnGathering.setBackgroundResource(R.drawable.buttonred);
                    intGathering = 0;
                } else {
                    btnGathering.setBackgroundResource(R.drawable.button);
                    intGathering = 1;
                }
            }
        });

        btnVisit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intVisit == 1){
                    btnVisit.setBackgroundResource(R.drawable.buttonred);
                    intVisit = 0;
                } else {
                    btnVisit.setBackgroundResource(R.drawable.button);
                    intVisit = 1;
                }
            }
        });

        btnTrip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intTrip == 1){
                    btnTrip.setBackgroundResource(R.drawable.buttonred);
                    intTrip = 0;
                } else {
                    btnTrip.setBackgroundResource(R.drawable.button);
                    intTrip = 1;
                }
            }
        });

        btnCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intCamp == 1){
                    btnCamp.setBackgroundResource(R.drawable.buttonred);
                    intCamp = 0;
                } else {
                    btnCamp.setBackgroundResource(R.drawable.button);
                    intCamp = 1;
                }
            }
        });

        btnPerformance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intPerformance == 1){
                    btnPerformance.setBackgroundResource(R.drawable.buttonred);
                    intPerformance = 0;
                } else {
                    btnPerformance.setBackgroundResource(R.drawable.button);
                    intPerformance = 1;
                }
            }
        });

        btnNite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intNite == 1){
                    btnNite.setBackgroundResource(R.drawable.buttonred);
                    intNite = 0;
                } else {
                    btnNite.setBackgroundResource(R.drawable.button);
                    intNite = 1;
                }
            }
        });

        btnTalk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intTalk == 1){
                    btnTalk.setBackgroundResource(R.drawable.buttonred);
                    intTalk = 0;
                } else {
                    btnTalk.setBackgroundResource(R.drawable.button);
                    intTalk = 1;
                }
            }
        });

        btnWorkshop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intWorkshop == 1){
                    btnWorkshop.setBackgroundResource(R.drawable.buttonred);
                    intWorkshop = 0;
                } else {
                    btnWorkshop.setBackgroundResource(R.drawable.button);
                    intWorkshop = 1;
                }
            }
        });

        btnSeminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intSeminar == 1){
                    btnSeminar.setBackgroundResource(R.drawable.buttonred);
                    intSeminar = 0;
                } else {
                    btnSeminar.setBackgroundResource(R.drawable.button);
                    intSeminar = 1;
                }
            }
        });

        btnConference.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intConference == 1){
                    btnConference.setBackgroundResource(R.drawable.buttonred);
                    intConference = 0;
                } else {
                    btnConference.setBackgroundResource(R.drawable.button);
                    intConference = 1;
                }
            }
        });

        btnExhibition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intExhibition == 1){
                    btnExhibition.setBackgroundResource(R.drawable.buttonred);
                    intExhibition = 0;
                } else {
                    btnExhibition.setBackgroundResource(R.drawable.button);
                    intExhibition = 1;
                }
            }
        });

        btnFundRaising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intFundraising == 1){
                    btnFundRaising.setBackgroundResource(R.drawable.buttonred);
                    intFundraising = 0;
                } else {
                    btnFundRaising.setBackgroundResource(R.drawable.button);
                    intFundraising = 1;
                }
            }
        });

        btnCompetition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intCompetition == 1){
                    btnCompetition.setBackgroundResource(R.drawable.buttonred);
                    intCompetition = 0;
                } else {
                    btnCompetition.setBackgroundResource(R.drawable.button);
                    intCompetition = 1;
                }
            }
        });

        btnSportsCarnival.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intSportsCarnival == 1){
                    btnSportsCarnival.setBackgroundResource(R.drawable.buttonred);
                    intSportsCarnival = 0;
                } else {
                    btnSportsCarnival.setBackgroundResource(R.drawable.button);
                    intSportsCarnival = 1;
                }
            }
        });

        btnTreasureHunt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intTreasureHunt == 1){
                    btnTreasureHunt.setBackgroundResource(R.drawable.buttonred);
                    intTreasureHunt = 0;
                } else {
                    btnTreasureHunt.setBackgroundResource(R.drawable.button);
                    intTreasureHunt = 1;
                }
            }
        });

        btnOthers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userEdited = 1;
                if (intOthers == 1){
                    btnOthers.setBackgroundResource(R.drawable.buttonred);
                    intOthers = 0;
                } else {
                    btnOthers.setBackgroundResource(R.drawable.button);
                    intOthers = 1;
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEdited == 1){
                    new AlertDialog.Builder(Subscription.this)
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setTitle("Warning")
                            .setMessage("Any unsaved changed will be lost\nAre you sure you want to exit?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    finish();
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    finish();
                }
            }
        });

        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userEdited == 1){
                    new AlertDialog.Builder(Subscription.this)
                            .setIcon(R.drawable.ic_error_outline_black_24dp)
                            .setTitle("Confirmation")
                            .setMessage("Are you sure?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    String type = "subscribe";
                                    SubscriptionTask subscriptionTask = new SubscriptionTask(getApplicationContext());
                                    String strMeeting = intMeeting.toString();
                                    String strAnnual = intAnnual.toString();
                                    String strTraining = intTraining.toString();
                                    String strClass = intClass.toString();
                                    String strGathering = intGathering.toString();
                                    String strVisit = intVisit.toString();
                                    String strTrip = intTrip.toString();
                                    String strCamp = intCamp.toString();
                                    String strPerformance = intPerformance.toString();
                                    String strNite = intNite.toString();
                                    String strTalk = intTalk.toString();
                                    String strWorkshop = intWorkshop.toString();
                                    String strSeminar = intSeminar.toString();
                                    String strConference = intConference.toString();
                                    String strExhibition = intExhibition.toString();
                                    String strFundRaising = intFundraising.toString();
                                    String strCompetition = intCompetition.toString();
                                    String strSportsCarnival = intSportsCarnival.toString();
                                    String strTreasureHunt = intTreasureHunt.toString();
                                    String strOthers = intOthers.toString();
                                    subscriptionTask.execute(type, studentId, strMeeting, strAnnual, strTraining, strClass, strGathering, strVisit, strTrip, strCamp,strPerformance,
                                            strNite, strTalk, strWorkshop, strSeminar, strConference, strExhibition, strFundRaising, strCompetition, strSportsCarnival, strTreasureHunt, strOthers);

                                    finish();


                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                } else {
                    finish();
                }
            }
        });

    }

    public void onBackPressed(){
        if(userEdited == 1){
            new AlertDialog.Builder(this)
                    .setIcon(R.drawable.ic_error_outline_black_24dp)
                    .setTitle("Warning")
                    .setMessage("Any unsaved changed will be lost\nAre you sure you want to exit?")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        } else {
            finish();
        }
    }
}
