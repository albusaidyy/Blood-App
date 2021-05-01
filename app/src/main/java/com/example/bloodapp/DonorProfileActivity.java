package com.example.bloodapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodapp.model.User;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

public class DonorProfileActivity extends AppCompatActivity {
    DatabaseHelper dDatabaseHelper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_profile);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Donor Profile");

        //getValues stored in SharedPreference
        SharedPreferences pref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = pref.getString("id", "noValue");
        String email = pref.getString("email", "dEmail");

        dDatabaseHelper = new DatabaseHelper(this);


        setContentView(R.layout.activity_donor_profile);

        //TextViews variable declaration and initialization
        TextView nameTv = findViewById(R.id.donor_name);
        TextView emailTv = findViewById(R.id.donor_email);
        TextView genderTv = findViewById(R.id.donor_gender);
        TextView bloodGroupTv = findViewById(R.id.donor_bloodgroup);
        TextView contactTv = findViewById(R.id.donor_contact);
        TextView addressTv = findViewById(R.id.donor_address);
        TextView statusTv = findViewById(R.id.donor_status);
        ImageButton edit = findViewById(R.id.donor_edit);


        //declare ArrayList<User> variable
        ArrayList<User> donorDataArrayList;

        //call getDonorData method
        donorDataArrayList = dDatabaseHelper.getDonorData(user_id);
        //assigning value to variables from the list of Data
        String name = (donorDataArrayList.get(0).getName());
        String gender = (donorDataArrayList.get(0).getGender());
        String bloodGroup = (donorDataArrayList.get(0).getBloodType());
        String contact = (donorDataArrayList.get(0).getContact());
        String address = (donorDataArrayList.get(0).getAddress());
        String status = (donorDataArrayList.get(0).getStatus());

        try {


            //set value from variables to TextViews
            nameTv.setText(name);
            emailTv.setText(email);
            genderTv.setText(String.valueOf(gender));
            bloodGroupTv.setText(bloodGroup);
            contactTv.setText("+254 " + contact);
            addressTv.setText(address);
            statusTv.setText(status);
            // status color based on value
            if (status.contentEquals("Not Available")) {
                statusTv.setBackgroundColor(getResources().getColor(R.color.orange));
            } else {
                statusTv.setBackgroundColor(getResources().getColor(R.color.green));
            }
        } catch (Exception e) {
            //send a message if an error occurs
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        //edit button click and send details to the next activity for Edit
        edit.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditDonorActivity.class);
            intent.putExtra("dName", name);
            intent.putExtra("dEmail", email);
            intent.putExtra("dGender", gender);
            intent.putExtra("dAddress", address);
            intent.putExtra("dContact", contact);
            intent.putExtra("dBloodGroup", bloodGroup);
            intent.putExtra("dStatus", status);
            startActivity(intent);
            finish();
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.donor_menu, menu);


        return true;
    }


    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //logout button click method to logout
        if (item.getItemId() == R.id.donor_logout) {
            try {
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("id", "");
                editor.putString("email", "");
                editor.putString("userType", "");
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                intent.putExtra("finish", true);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                //send a message if an error occurs
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return (super.onOptionsItemSelected(item));
    }
}