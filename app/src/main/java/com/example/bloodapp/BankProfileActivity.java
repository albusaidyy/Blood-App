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

import com.example.bloodapp.model.BankStock;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

public class BankProfileActivity extends AppCompatActivity {

    DatabaseHelper database_helper;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Bank Profile");

        //getValues stored in SharedPreference
        SharedPreferences pref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = pref.getString("id", "noValue");
        String email = pref.getString("email", "dEmail");
        setContentView(R.layout.activity_bank_profile);

        //TextViews variable declaration and initialization
        TextView nameTv = findViewById(R.id.my_bank_name);
        TextView emailTv = findViewById(R.id.my_bank_email);
        TextView contactTv = findViewById(R.id.my_bank_contact);
        TextView address = findViewById(R.id.my_bank_address);

        TextView bloodTypeTvAP = findViewById(R.id.my_bank_bloodTypeAP);
        TextView bloodTypeTvAN = findViewById(R.id.my_bank_bloodTypeAN);

        TextView bloodTypeBPTv = findViewById(R.id.my_bank_bloodTypeBP);
        TextView bloodTypeBNTv = findViewById(R.id.my_bank_bloodTypeBN);

        TextView bloodTypeABPTv = findViewById(R.id.my_bank_bloodTypeABP);
        TextView bloodTypeABNTv = findViewById(R.id.my_bank_bloodTypeABN);

        TextView bloodTypeOPTv = findViewById(R.id.my_bank_bloodTypeOP);
        TextView bloodTypeONTv = findViewById(R.id.my_bank_bloodTypeON);

        ImageButton edit = findViewById(R.id.edit_btn_bank);


        //get bank data from DatabaseHelper class method of getBankData and set to Views
        database_helper = new DatabaseHelper(this);
        ArrayList<BankStock> data;
        int bId = 0;
        try {
            data = database_helper.getBankData(user_id);
            bId = data.get(0).getbId();
            nameTv.setText(data.get(0).getName());
            emailTv.setText(email);
            contactTv.setText("+254" + data.get(0).getContact());
            address.setText(data.get(0).getAddress());

            //set data to views
            bloodTypeTvAP.setText(String.valueOf(data.get(0).getBloodTypeAP()));
            bloodTypeTvAN.setText(String.valueOf(data.get(0).getBloodTypeAN()));
            bloodTypeBPTv.setText(String.valueOf(data.get(0).getBloodTypeBP()));
            bloodTypeBNTv.setText(String.valueOf(data.get(0).getBloodTypeBN()));
            bloodTypeABPTv.setText(String.valueOf(data.get(0).getBloodTypeABP()));
            bloodTypeABNTv.setText(String.valueOf(data.get(0).getBloodTypeABN()));
            bloodTypeOPTv.setText(String.valueOf(data.get(0).getBloodTypeOP()));
            bloodTypeONTv.setText(String.valueOf(data.get(0).getBloodTypeON()));
        } catch (Exception e) {
            //send a message if an error occurs
            Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        //edit button click and open editBank Activity sending Id with it
        int finalBId = bId;
        edit.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), EditBankAcitivity.class);
            intent.putExtra("bId", String.valueOf(finalBId));
            startActivity(intent);
            finish();
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bank_menu, menu);


        return true;
    }


    //assign menu items
    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.bank_logout) {
            try {

                //clear the SharedPreferences when loggedOut
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("id", "");
                editor.putString("email", "");
                editor.putString("userType", "");
                editor.putBoolean("isLoggedIn", false);
                editor.apply();
                //open Login Activity when done
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