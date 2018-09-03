package com.example.mike.tarucevent;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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
import java.util.regex.Pattern;

public class Profile extends AppCompatActivity {

    TextView txtId, txtName, txtContact, txtAddress;
    Button btnEdit, btnConfirm, btnCancel;
    EditText txtEditName, txtEditContact, txtEditAddress;

    String txtAfterName, txtAfterContact, txtAfterAddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("Profile");
        Intent intent = getIntent();
        final String studentId = intent.getStringExtra("studentId");
        final String studentName = intent.getStringExtra("studentName");
        final String contactNum = intent.getStringExtra("contactNum");
        final String address = intent.getStringExtra("address");

        txtId = (TextView)findViewById(R.id.txtId);
        txtName = (TextView)findViewById(R.id.txtName);
        txtContact = (TextView)findViewById(R.id.txtContact);
        txtAddress = (TextView)findViewById(R.id.txtAddress);



        txtId.setText(studentId);
        txtName.setText(studentName);
        txtContact.setText(contactNum);
        txtAddress.setText(address);

        final AlertDialog.Builder mBuilder = new AlertDialog.Builder(Profile.this);
        final View mView = getLayoutInflater().inflate(R.layout.dialog_edit_profile, null);
        mBuilder.setView(mView);
        final AlertDialog dialog = mBuilder.create();

        txtEditName = (EditText)mView.findViewById(R.id.txtNameEdit);
        txtEditContact = (EditText)mView.findViewById(R.id.txtContactEdit);
        txtEditAddress = (EditText)mView.findViewById(R.id.txtAddressEdit);

        btnEdit = (Button)findViewById(R.id.btnEdit);
        btnCancel = (Button)mView.findViewById(R.id.btnCancel);
        btnConfirm = (Button)mView.findViewById(R.id.btnConfirm);



        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtEditName.setText(txtName.getText().toString(), TextView.BufferType.EDITABLE);
                txtEditContact.setText(txtContact.getText().toString(), TextView.BufferType.EDITABLE);
                txtEditAddress.setText(txtAddress.getText().toString(), TextView.BufferType.EDITABLE);

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

        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String RegEx = "^[0-9]*$";
                String number = txtEditContact.getText().toString();
                if( number.matches(RegEx)) {
                    String type = "editProfile";

                    EditProfileTask editProfileTask = new EditProfileTask(Profile.this);
                    editProfileTask.execute(type, studentId, txtEditName.getText().toString(), txtEditContact.getText().toString(), txtEditAddress.getText().toString());

                    txtAfterName = txtEditName.getText().toString();
                    txtAfterContact = txtEditContact.getText().toString();
                    txtAfterAddress = txtEditAddress.getText().toString();

                    txtName.setText(txtAfterName);
                    txtContact.setText(txtAfterContact);
                    txtAddress.setText(txtAfterAddress);

                    dialog.cancel();
                } else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid phone number", Toast.LENGTH_LONG);
                    toast.show();
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

    public class EditProfileTask extends AsyncTask<String, Void, String> {
        Context context;
        android.app.AlertDialog alertDialog;

        EditProfileTask(Context ctx) {
            context = ctx;
        }
        Connectivity connectivity = new Connectivity();

        @Override
        protected String doInBackground(String... params) {
            String type = params[0];
            String ipAddress = "" + connectivity.getIP();
            String retrieve_url = ipAddress + "/Android/editProfile.php";

            if (type.equals("editProfile")) {
                try {
                    String studentId = params[1];
                    String nameEdit = params[2];
                    String contactEdit = params[3];
                    String addressEdit = params[4];

                    URL url = new URL(retrieve_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String post_data = URLEncoder.encode("studentId", "UTF-8")+"="+URLEncoder.encode(studentId, "UTF-8")+"&"+
                            URLEncoder.encode("nameEdit", "UTF-8")+"="+URLEncoder.encode(nameEdit, "UTF-8")+"&"+
                            URLEncoder.encode("contactEdit", "UTF-8")+"="+URLEncoder.encode(contactEdit, "UTF-8")+"&"+
                            URLEncoder.encode("addressEdit", "UTF-8")+"="+URLEncoder.encode(addressEdit, "UTF-8");
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



                Toast.makeText(context,"Edit Profile Success", Toast.LENGTH_SHORT).show();



        }
    }




}
