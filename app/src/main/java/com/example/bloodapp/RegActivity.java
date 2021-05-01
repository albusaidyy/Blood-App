package com.example.bloodapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.example.bloodapp.model.BankStock;
import com.example.bloodapp.model.User;
import com.example.bloodapp.sqlite.DatabaseHelper;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegActivity extends AppCompatActivity {

    //seeker/donor editText View variables declaration
    private EditText nameReg, emailReg, passReg, confirmPassReg, addressReg, contactReg;

    //bank editText Views variables declaration
    private EditText bNameReg, bEmailReg, bPassReg, bConfirmPassReg, bAddressReg, bContactReg;

    //bank editText View variables declaration

    //userType layout variable declaration
    private LinearLayout userslayout, banklayout;

    //seeker/donor textView variables declaration
    private TextView Tvsignin, TvBankSignIn;

    //bank textView variables declaration
    private TextView TvBloodBank, TvChangeUser;

    //user/donor buttons variable declaration
    private Button BtnReg;

    //bank buttons variable declaration
    private Button BankBtnReg;

    //NestedScrollview declaration
    private NestedScrollView scrollView;

    // DatabaseHelper class objects
    private DatabaseHelper databaseHelper;

    // User class objects
    private User user;

    // BloodBag class objects
    private BankStock bankStock;

    //spinners variable declaration
    private Spinner gender_spinner, blood_spinner, user_spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Register");

        //initializing objects to be used
        databaseHelper = new DatabaseHelper(this);
        user = new User();
        bankStock = new BankStock();


        //seeker/donor view assignment
        nameReg = findViewById(R.id.name_reg);
        emailReg = findViewById(R.id.email_reg);
        addressReg = findViewById(R.id.address_reg);
        contactReg = findViewById(R.id.contact_reg);
        passReg = findViewById(R.id.password_reg);
        confirmPassReg = findViewById(R.id.confirmPassword_reg);
        Tvsignin = findViewById(R.id.Tv_signin);
        TvBloodBank = findViewById(R.id.Tv_BloodBank);
        BtnReg = findViewById(R.id.btn_reg);

        //bank view assignment
        bNameReg = findViewById(R.id.bank_name_reg);
        bEmailReg = findViewById(R.id.bank_email_reg);
        bAddressReg = findViewById(R.id.bank_address_reg);
        bContactReg = findViewById(R.id.bank_contact_reg);
        bPassReg = findViewById(R.id.bank_password_reg);
        bConfirmPassReg = findViewById(R.id.bank_confirmPassword_reg);
        TvBankSignIn = findViewById(R.id.Tv_bank_signin);
        TvChangeUser = findViewById(R.id.Tv_changeUser);
        BankBtnReg = findViewById(R.id.bank_btn_reg);


        //layout views assignment
        scrollView = findViewById(R.id.scrollview);
        userslayout = findViewById(R.id.userLay);
        banklayout = findViewById(R.id.bankLay);


        //assigning seeker/donor spinner
        gender_spinner = findViewById(R.id.gender_select_spinner);
        blood_spinner = findViewById(R.id.blood_select_spinner);
        user_spinner = findViewById(R.id.user_reg_select_spinner);


        //gender-group-Spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> gender_adapter = ArrayAdapter.createFromResource(this,
                R.array.gender_array_reg, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        gender_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        gender_spinner.setAdapter(gender_adapter);

        //blood-group-Spinner
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> blood_adapter = ArrayAdapter.createFromResource(this,
                R.array.blood_array, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        blood_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        blood_spinner.setAdapter(blood_adapter);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_array_reg, android.R.layout.simple_spinner_dropdown_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        user_spinner.setAdapter(adapter);


        //open login activity
        Tvsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                finish();
            }
        });

        //change activity views to seeker/bank
        TvChangeUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                banklayout.setVisibility(View.GONE);
                userslayout.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_UP);
                nameReg.requestFocus();
                nameReg.setFocusable(true);

            }
        });


        //change activity views to bank
        TvBloodBank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //change layout views to blood bank attributes
                userslayout.setVisibility(View.GONE);
                banklayout.setVisibility(View.VISIBLE);
                scrollView.fullScroll(View.FOCUS_UP);
                bNameReg.requestFocus();

            }
        });


        //bank textView to open login
        TvBankSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                finish();

            }
        });

        //seeker/donor input validation and registration
        BtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSeekerOrDonor();
            }

        });

        //bank input validation and registration
        BankBtnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createBankAndStock();
            }
        });
    }


    private void createSeekerOrDonor() {
        String name = nameReg.getText().toString().trim();
        String email = emailReg.getText().toString().trim();
        String password = passReg.getText().toString().trim();
        String password2 = confirmPassReg.getText().toString().trim();
        String address = addressReg.getText().toString().trim();
        String contact = contactReg.getText().toString().trim();
        String spinner_user = user_spinner.getSelectedItem().toString().trim();
        String spinner_gender = String.valueOf(gender_spinner.getSelectedItem());
        String spinner_blood_group = blood_spinner.getSelectedItem().toString().trim();


        //input validation
        if (name.isEmpty()) {
            //set Error and focus to name editText
            nameReg.setError("Required Field..");
            nameReg.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            //set Error and focus to email editText
            emailReg.setError("Required Field..");
            emailReg.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //set Error and focus to email editText
            emailReg.setError("Please enter a valid Email");
            emailReg.requestFocus();
            return;


        }

        if (databaseHelper.checkUser(email)) {
            //set Error and focus to email editText
            emailReg.setError("Email Already Exists..");
            emailReg.requestFocus();
            return;
        }

        if (address.isEmpty()) {
            addressReg.setError("Required Field...");
            addressReg.requestFocus();
            return;
        }

        if (contact.isEmpty()) {
            //set Error and focus to contact editText
            contactReg.setError("Required Field..");
            contactReg.requestFocus();
            return;

        }

        //check phone number validation
        String pattern = "^\\d{9}$";
        Matcher m;
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(contact);
        if (!m.find()) {
            contactReg.setError("Please enter a valid mobile number");
            contactReg.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            //set Error and focus to password editText
            passReg.setError("Required Field..");
            passReg.requestFocus();
            return;
        }

        if (password.length() < 6) {
            //set Error and focus to password editText
            passReg.setError("Password length at least 6 characters");
            passReg.requestFocus();
            return;

        }

        if (password2.isEmpty()) {
            //set Error and focus to password editText
            confirmPassReg.setError("Required Field..");
            confirmPassReg.requestFocus();
            return;
        }

        if (!password.equals(password2)) {
            //set Error and focus to confirmPassword editText
            confirmPassReg.setError("Password do not match..");
            confirmPassReg.requestFocus();
            return;
        }

        if (spinner_gender.equals("--------->")) {
            Toast.makeText(RegActivity.this, "Gender not Selected", Toast.LENGTH_SHORT).show();
            return;

        }

        if (spinner_blood_group.equals("--------->")) {
            Toast.makeText(RegActivity.this, "Blood Group not Selected", Toast.LENGTH_SHORT).show();
            return;

        }

        if (spinner_user.equals("--------->")) {
            Toast.makeText(RegActivity.this, "User Type not Selected", Toast.LENGTH_SHORT).show();
        } else {
            try {
                user.setEmail(email);
                user.setPassword(password);
                user.setUserType(spinner_user);

                user.setName(name);
                user.setContact(contact);
                user.setAddress(address);
                user.setGender(spinner_gender);
                user.setBloodType(spinner_blood_group);
                user.setStatus("Not Available");

                //call addUser method
                databaseHelper.addUser(user);

                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                // Toast to show success message that record saved successfully
                Toast.makeText(RegActivity.this, "Registration Successful. Please Log In", Toast.LENGTH_SHORT).show();
                finish();
            } catch (Exception e) {
                //send a message if an error occurs
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }

        }


    }

    private void createBankAndStock() {
        String name = bNameReg.getText().toString().trim();
        String email = bEmailReg.getText().toString().trim();
        String password = bPassReg.getText().toString().trim();
        String password2 = bConfirmPassReg.getText().toString().trim();
        String address = bAddressReg.getText().toString().trim();
        String contact = bContactReg.getText().toString().trim();

        //input validation
        if (name.isEmpty()) {
            //set Error and focus to email editText
            bNameReg.setError("Required Field..");
            bNameReg.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            //set Error and focus to email editText
            bEmailReg.setError("Required Field..");
            bEmailReg.requestFocus();
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //set Error and focus to email editText
            bEmailReg.setError("Please enter a valid Email");
            bEmailReg.requestFocus();
            return;


        }

        if (databaseHelper.checkUser(email)) {
            //set Error and focus to email editText
            bEmailReg.setError("Email Already Exists..");
            bEmailReg.requestFocus();
            return;

        }

        if (address.isEmpty()) {
            bAddressReg.setError("Required Field...");
            bAddressReg.requestFocus();
            return;

        }

        if (contact.isEmpty()) {
            //set Error and focus to email editText
            bContactReg.setError("Required Field..");
            bContactReg.requestFocus();
            return;

        }


        //check phone number validation
        String pattern = "^\\d{9}$";
        Matcher m;
        Pattern r = Pattern.compile(pattern);
        m = r.matcher(contact);
        if (!m.find()) {
            contactReg.setError("Please enter a valid mobile number");
            contactReg.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            //set Error and focus to password editText
            bPassReg.setError("Required Field..");
            bPassReg.requestFocus();
            return;

        }

        if (password.length() < 6) {
            //set Error and focus to password editText
            bPassReg.setError("Password length at least 6 characters");
            bPassReg.requestFocus();
            return;

        }

        if (password2.isEmpty()) {
            //set Error and focus to password editText
            bConfirmPassReg.setError("Required Field..");
            bConfirmPassReg.requestFocus();
            return;

        }

        if (!password.equals(password2)) {
            //set Error and focus to confirmPassword editText
            bConfirmPassReg.setError("Password do not match..");
            bConfirmPassReg.requestFocus();
        } else {
            try {

                bankStock.setEmail(email);
                bankStock.setPassword(password);
                bankStock.setUserType("Bank");


                bankStock.setName(name);
                bankStock.setContact(contact);
                bankStock.setAddress(address);


                bankStock.setBloodTypeAP(0);
                bankStock.setBloodTypeAN(0);
                bankStock.setBloodTypeBP(0);
                bankStock.setBloodTypeBN(0);
                bankStock.setBloodTypeABP(0);
                bankStock.setBloodTypeABN(0);
                bankStock.setBloodTypeOP(0);
                bankStock.setBloodTypeON(0);


                //call addBankAndStock method
                databaseHelper.addBankAndStock(bankStock);

                // Toast to show success message that record saved successfully
                startActivity(new Intent(RegActivity.this, LoginActivity.class));
                finish();
                Toast.makeText(RegActivity.this, "Registration Successful. Please Log In", Toast.LENGTH_SHORT).show();

            } catch (Exception e) {
                //send a message if an error occurs
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }


}



