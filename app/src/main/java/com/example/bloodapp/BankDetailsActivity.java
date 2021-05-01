package com.example.bloodapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class BankDetailsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bank Details");
        setContentView(R.layout.activity_bank_details);

        //TextViews variable declaration and initialization
        TextView nameTv = findViewById(R.id.bank_name);
        TextView emailTv = findViewById(R.id.bank_email);
        TextView bloodAPTv = findViewById(R.id.bank_bloodAP);
        TextView bloodANTv = findViewById(R.id.bank_bloodAN);

        TextView bloodBPTv = findViewById(R.id.bank_bloodBP);
        TextView bloodBNTv = findViewById(R.id.bank_bloodBN);

        TextView bloodABPTv = findViewById(R.id.bank_bloodABP);
        TextView bloodABNTv = findViewById(R.id.bank_bloodABN);

        TextView bloodOPTv = findViewById(R.id.bank_bloodOP);
        TextView bloodONTv = findViewById(R.id.bank_bloodON);

        TextView contactTv = findViewById(R.id.bank_contact);
        TextView addressTv = findViewById(R.id.bank_address);
        ImageButton call = findViewById(R.id.request_btn_bank);

        //getting details send by the single cardview of a donor from previous activity
        Intent intent = getIntent();

        //set the details received to their textViews
        String bank_id = intent.getStringExtra("bId");
        nameTv.setText(intent.getStringExtra("bName"));
        emailTv.setText(intent.getStringExtra("bEmail"));
        String mobile = ("+254 " + intent.getStringExtra("bContact"));
        contactTv.setText(mobile);
        addressTv.setText(intent.getStringExtra("bAddress"));

        bloodAPTv.setText(intent.getStringExtra("bBloodAP"));
        bloodANTv.setText(intent.getStringExtra("bBloodAN"));

        bloodBPTv.setText(intent.getStringExtra("bBloodBP"));
        bloodBNTv.setText(intent.getStringExtra("bBloodBN"));

        bloodABPTv.setText(intent.getStringExtra("bBloodABP"));
        bloodABNTv.setText(intent.getStringExtra("bBloodABN"));

        bloodOPTv.setText(intent.getStringExtra("bBloodOP"));
        bloodONTv.setText(intent.getStringExtra("bBloodON"));


        //call button and send contact to dial pad
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