package com.example.bloodapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bloodapp.model.BankStock;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.ArrayList;

public class EditBankAcitivity extends AppCompatActivity {
    //objects
    DatabaseHelper database_helper;
    BankStock bankStock;


    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        Toast.makeText(EditBankAcitivity.this, "Click on SAVE", Toast.LENGTH_SHORT).show();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Edit Bank Stock");
        //getValues stored in SharedPreference
        SharedPreferences pref = this.getSharedPreferences("user", Context.MODE_PRIVATE);
        String user_id = pref.getString("id", "noValue");
        String email = pref.getString("email", "dEmail");
        setContentView(R.layout.activity_edit_bank);

        Intent intent = getIntent();
        String bId = intent.getStringExtra("bId");


        EditText bloodAP = findViewById(R.id.edit_bank_bloodTypeAP);
        EditText bloodAN = findViewById(R.id.edit_bank_bloodTypeAN);

        EditText bloodBP = findViewById(R.id.edit_bank_bloodTypeBP);
        EditText bloodBN = findViewById(R.id.edit_bank_bloodTypeBN);

        EditText bloodABP = findViewById(R.id.edit_bank_bloodTypeABP);
        EditText bloodABN = findViewById(R.id.edit_bank_bloodTypeABN);

        EditText bloodOP = findViewById(R.id.edit_bank_bloodTypeOP);
        EditText bloodON = findViewById(R.id.edit_bank_bloodTypeON);

        Button save = findViewById(R.id.save_bank_stock);


        //get bank data from DatabaseHelper class method of-getBankData
        database_helper = new DatabaseHelper(this);
        //create bankStock object
        bankStock = new BankStock();
        ArrayList<BankStock> data;
        data = database_helper.getBankData(user_id);


        bloodAP.setText(String.valueOf(data.get(0).getBloodTypeAP()));
        bloodAN.setText(String.valueOf(data.get(0).getBloodTypeAN()));
        bloodBP.setText(String.valueOf(data.get(0).getBloodTypeBP()));
        bloodBN.setText(String.valueOf(data.get(0).getBloodTypeBN()));
        bloodABP.setText(String.valueOf(data.get(0).getBloodTypeABP()));
        bloodABN.setText(String.valueOf(data.get(0).getBloodTypeABN()));
        bloodOP.setText(String.valueOf(data.get(0).getBloodTypeOP()));
        bloodON.setText(String.valueOf(data.get(0).getBloodTypeON()));

        save.setOnClickListener(view -> {
            String AP = bloodAP.getText().toString().trim();
            String AN = bloodAN.getText().toString().trim();
            String BP = bloodBP.getText().toString().trim();
            String BN = bloodBN.getText().toString().trim();
            String ABP = bloodABP.getText().toString().trim();
            String ABN = bloodABN.getText().toString().trim();
            String OP = bloodOP.getText().toString().trim();
            String ON = bloodON.getText().toString().trim();


            if (AP.isEmpty()) {
                //set Error and focus to email editText
                bloodAP.setError("Required Field..");
                bloodAP.requestFocus();
                return;
            }


            //check bag number!>3 digits validation

            if (AP.length() > 3) {
                bloodAP.setError("Please enter a value less than 999");
                bloodAP.requestFocus();
                return;
            }

            if (AN.isEmpty()) {
                //set Error and focus to email editText
                bloodAN.setError("Required Field..");
                bloodAN.requestFocus();
                return;
            }

            if (AN.length() > 3) {
                bloodAN.setError("Please enter a value less than 999");
                bloodAN.requestFocus();
                return;
            }

            if (BP.isEmpty()) {
                //set Error and focus to email editText
                bloodBP.setError("Required Field..");
                bloodBP.requestFocus();
                return;
            }

            if (BP.length() > 3) {
                bloodBP.setError("Please enter a value less than 999");
                bloodBP.requestFocus();
                return;
            }

            if (BN.isEmpty()) {
                //set Error and focus to email editText
                bloodBN.setError("Required Field..");
                bloodBN.requestFocus();
                return;
            }
            if (BN.length() > 3) {
                bloodBN.setError("Please enter a value less than 999");
                bloodBN.requestFocus();
                return;
            }

            if (ABP.isEmpty()) {
                //set Error and focus to email editText
                bloodABP.setError("Required Field..");
                bloodABP.requestFocus();
                return;
            }
            if (ABP.length() > 3) {
                bloodABP.setError("Please enter a value less than 999");
                bloodABP.requestFocus();
                return;
            }


            if (ABN.isEmpty()) {
                //set Error and focus to email editText
                bloodABN.setError("Required Field..");
                bloodABN.requestFocus();
                return;
            }
            if (ABN.length() > 3) {
                bloodABN.setError("Please enter a value less than 999");
                bloodABN.requestFocus();
                return;
            }


            if (OP.isEmpty()) {
                //set Error and focus to email editText
                bloodOP.setError("Required Field..");
                bloodOP.requestFocus();
                return;
            }
            if (OP.length() > 3) {
                bloodOP.setError("Please enter a value less than 999");
                bloodOP.requestFocus();
                return;
            }


            if (ON.isEmpty()) {
                //set Error and focus to email editText
                bloodON.setError("Required Field..");
                bloodON.requestFocus();
                return;
            }
            if (ON.length() > 3) {
                bloodON.setError("Please enter a value less than 999");
                bloodON.requestFocus();
            } else {


                bankStock.setBloodTypeAP(Integer.parseInt(AP));
                bankStock.setBloodTypeAN(Integer.parseInt(AN));
                bankStock.setBloodTypeBP(Integer.parseInt(BP));
                bankStock.setBloodTypeBN(Integer.parseInt(BN));
                bankStock.setBloodTypeABP(Integer.parseInt(ABP));
                bankStock.setBloodTypeABN(Integer.parseInt(ABN));
                bankStock.setBloodTypeOP(Integer.parseInt(OP));
                bankStock.setBloodTypeON(Integer.parseInt(ON));
                try {
                    //call updateStock method
                    database_helper.updateStock(bankStock, bId);
                    Toast.makeText(EditBankAcitivity.this, "Bank Stock Updated", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(EditBankAcitivity.this, BankProfileActivity.class));
                    finish();
                } catch (Exception e) {
                    //send a message if an error occurs
                    Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }


        });
    }
}