package com.example.bloodapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.example.bloodapp.sqlite.DatabaseHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class LoginActivity extends AppCompatActivity {

    //views variables declaration
    private EditText emailLogIn, passLogIn;
    private Button bntlogin;
    private TextView Tvsignup;


    // DatabaseHelper class objects
    private DatabaseHelper databaseHelper;

    //convert entered String in Password EditText to md5 encryption
    public static String md5(final String pass) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest
                    .getInstance(MD5);
            digest.update(pass.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                StringBuilder h = new StringBuilder(Integer.toHexString(0xFF & aMessageDigest));
                while (h.length() < 2)
                    h.insert(0, "0");
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    //check userLogin When Start
    @Override
    protected void onStart() {
        super.onStart();

        //check if a user was loggedIn before
        SharedPreferences prefs = getSharedPreferences("user", MODE_PRIVATE);
        boolean isLoggedIn = prefs.getBoolean("isLoggedIn", false);
        String user_type = prefs.getString("userType", "userValue");

        //if user is Logged in before direct to the page based on userType
        if (isLoggedIn) {
            if (user_type.equals("Seeker")) {
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();
                return;
            }
            if (user_type.equals("Donor")) {
                startActivity(new Intent(getApplicationContext(), DonorProfileActivity.class));
                finish();
                return;
            }
            if (user_type.equals("Bank")) {
                startActivity(new Intent(getApplicationContext(), BankProfileActivity.class));
                finish();
            }

        }
    }

    @Override
    public void onBackPressed() {
        moveTaskToBack(true);
        LoginActivity.this.finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //set ActionBar Title
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("LogIn");
        //Disables nightMode on launch
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        setContentView(R.layout.activity_login);

        //initializing objects to be used
        databaseHelper = new DatabaseHelper(this);
        //views assignment
        emailLogIn = findViewById(R.id.ed_email_login);
        passLogIn = findViewById(R.id.ed_pass_login);
        bntlogin = findViewById(R.id.btn_login);
        Tvsignup = findViewById(R.id.Tv_signUp);

        //Button Login Click
        bntlogin.setOnClickListener(view -> {
            //validate user
            checkUserandLogin();
        });

        //SignUp TextView click
        Tvsignup.setOnClickListener(view -> {
            startActivity(new Intent(LoginActivity.this, RegActivity.class));

        });


    }

    //validate input fields and direct user based on userType stored in database
    private void checkUserandLogin() {

        String email = emailLogIn.getText().toString().trim();
        String password = passLogIn.getText().toString().trim();

        String hashPass = md5(password);


        if (email.isEmpty()) {
            //set Error and focus to email editText
            emailLogIn.setError("Required Field..");
            return;

        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            //set Error and focus to email editText
            emailLogIn.setError("Please enter a valid Email");
            emailLogIn.requestFocus();
            return;


        }

        if (password.isEmpty()) {
            //set Error and focus to password editText
            passLogIn.setError("Required Field..");
            passLogIn.requestFocus();
            return;
        }


        if (!databaseHelper.checkUser(email, hashPass)) {
            //set Error and focus to email editText
            Toast.makeText(LoginActivity.this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
        } else {
            try {
                //open home activity if login is successful
                //Store User details when successful login, can be used later for update details/logout
                SharedPreferences.Editor editor = getSharedPreferences("user", MODE_PRIVATE).edit();
                editor.putString("email", email);
                editor.putBoolean("isLoggedIn", true);
                String ID = String.valueOf(databaseHelper.GetUserID(DatabaseHelper.TABLE_USER, email));
                String spinner_user = databaseHelper.GetUserType(DatabaseHelper.TABLE_USER, email);
                editor.putString("id", ID);
                editor.putString("userType", spinner_user);
                editor.apply();

                //Switch to direct user based 0on his/her userType
                switch (spinner_user) {
                    case "Seeker":
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "Donor":
                        startActivity(new Intent(LoginActivity.this, DonorProfileActivity.class));
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    case "Bank":
                        startActivity(new Intent(LoginActivity.this, BankProfileActivity.class));
                        Toast.makeText(LoginActivity.this, "Welcome", Toast.LENGTH_SHORT).show();
                        finish();
                        break;
                    default:
                        break;
                }
            } catch (Exception e) {
                //send a message if an error occurs
                Toast.makeText(getApplicationContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }


    }

}