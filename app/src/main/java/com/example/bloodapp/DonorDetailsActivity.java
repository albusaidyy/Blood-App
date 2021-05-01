package com.example.bloodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class DonorDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_donor_details);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Donor Details");


        //TextViews variable declaration and initialization
        TextView nameTv = findViewById(R.id.user_donor_name);
        TextView emailTv = findViewById(R.id.user_donor_email);
        TextView genderTv = findViewById(R.id.user_donor_gender);
        TextView bloodGroupTv = findViewById(R.id.user_donor_bloodgroup);
        TextView contactTv = findViewById(R.id.user_donor_contact);
        TextView addressTv = findViewById(R.id.user_donor_address);
        TextView statusTv = findViewById(R.id.user_donor_status);
        ImageButton call = findViewById(R.id.user_request_btn_donor);

        //getting details send by the single cardView of a donor from previous activity
        Intent intent = getIntent();

        //set the details received to their textViews
        nameTv.setText(intent.getStringExtra("dName"));
        emailTv.setText(intent.getStringExtra("dEmail"));
        genderTv.setText(intent.getStringExtra("dGender"));
        bloodGroupTv.setText(intent.getStringExtra("dBloodGroup"));
        String mobile = ("+254 " + intent.getStringExtra("dContact"));
        contactTv.setText(mobile);
        addressTv.setText(intent.getStringExtra("dAddress"));
        String status = intent.getStringExtra("dStatus");
        statusTv.setText(status);
        //set background of status TextView based on the data
        if (status.contentEquals("Not Available")) {
            statusTv.setBackgroundColor(getResources().getColor(R.color.orange));
        } else {
            statusTv.setBackgroundColor(getResources().getColor(R.color.green));

        }


        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:" + mobile));
                startActivity(intent);
            }
        });


    }
}