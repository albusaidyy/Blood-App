package com.example.bloodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodapp.sqlite.DatabaseHelper;

public class EditDonorActivity extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(EditDonorActivity.this, "Click on SAVE", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Donor Status");
        //getValues stored in SharedPreference
        SharedPreferences pref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = pref.getString("id", "noValue");
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        setContentView(R.layout.activity_edit_donor);


        Button saveDonorStatus = findViewById(R.id.save_donor_status);


        //getting data from last screen
        Intent intent = getIntent();
        String name = intent.getStringExtra("dName");
        String email = intent.getStringExtra("dEmail");
        String gender = intent.getStringExtra("dGender");
        String address = intent.getStringExtra("dAddress");
        String contact = intent.getStringExtra("dContact");
        String bloodGroup = intent.getStringExtra("dBloodGroup");
        String status = intent.getStringExtra("dStatus").trim();


        Spinner spinner = findViewById(R.id.availability_select_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.status_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        //populate the spinner based on data of ststus received
        if (status.equals("Not Available")) {
            spinner.setSelection(adapter.getPosition("Not Available"));
        } else {
            spinner.setSelection(adapter.getPosition("Available"));
        }


        //save button click and call updateDonorStatus method
        saveDonorStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String newStatus = spinner.getSelectedItem().toString().trim();
                    databaseHelper.updateDonorStatus(user_id, newStatus);
                    Toast.makeText(EditDonorActivity.this, "Donor Status Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditDonorActivity.this, DonorProfileActivity.class));
                    finish();
                } catch (Exception e) {
                    //send a message if an error occurs
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}