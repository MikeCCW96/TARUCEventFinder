package com.example.mike.tarucevent;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StaffTakeAttendance extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_take_attendance);

        Button btnTakeAttendance = (Button)findViewById(R.id.btnTakeAttendance);
        btnTakeAttendance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StaffTakeAttendance.this, QRScanTakeAttendance.class);
                startActivity(intent);
            }
        });
    }
}
