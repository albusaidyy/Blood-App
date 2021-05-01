package com.example.bloodapp.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.bloodapp.model.BankStock;
import com.example.bloodapp.model.User;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    // User table name
    public static final String TABLE_USER = "user";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "BloodApp.db";
    //Seeker table name
    private static final String TABLE_SEEKER = "seeker";

    //Donor table name
    private static final String TABLE_DONOR = "donor";

    //Bank table name
    private static final String TABLE_BANK = "bank";

    //BloodBag table name
    private static final String TABLE_BANK_STOCK = "bank_stock";

    // User Table Columns names
    private static final String COLUMN_USER_ID = "user_id";
    private static final String COLUMN_USER_EMAIL = "user_email";
    private static final String COLUMN_USER_PASSWORD = "user_password";
    private static final String COLUMN_USER_TYPE = "user_type";

    //Seeker Table Column name
    private static final String COLUMN_SEEKER_ID = "seeker_id";
    private static final String COLUMN_SEEKER_NAME = "seeker_name";
    private static final String COLUMN_SEEKER_ADDRESS = "seeker_address";
    private static final String COLUMN_SEEKER_CONTACT = "seeker_contact";
    private static final String COLUMN_SEEKER_GENDER = "seeker_gender";
    private static final String COLUMN_SEEKER_BLOOD_TYPE = "seeker_bloodType";
    private static final String COLUMN_SEEKER_STATUS = "seeker_status";
    private static final String COLUMN_SEEKER_USER_ID = "seeker_user_id";


    //Donor Table columns
    private static final String COLUMN_DONOR_ID = "donor_id";
    private static final String COLUMN_DONOR_NAME = "donor_name";
    private static final String COLUMN_DONOR_ADDRESS = "donor_address";
    private static final String COLUMN_DONOR_CONTACT = "donor_contact";
    private static final String COLUMN_DONOR_GENDER = "donor_gender";
    private static final String COLUMN_DONOR_BLOOD_TYPE = "donor_bloodType";
    private static final String COLUMN_DONOR_STATUS = "donor_status";
    private static final String COLUMN_DONOR_USER_ID = "donor_user_id";


    //Bank Table Columns
    private static final String COLUMN_BANK_ID = "bank_id";
    private static final String COLUMN_BANK_NAME = "bank_name";
    private static final String COLUMN_BANK_ADDRESS = "bank_address";
    private static final String COLUMN_BANK_CONTACT = "bank_contact";
    private static final String COLUMN_BANK_USER_ID = "bank_user_id";


    //BankStock Table Columns
    private static final String COLUMN_STOCK_ID = "stock_id";
    private static final String COLUMN_BAG_TYPE_AP = "blood_type_AP";
    private static final String COLUMN_BAG_TYPE_AN = "blood_type_AN";

    private static final String COLUMN_BAG_TYPE_BP = "blood_type_BP";
    private static final String COLUMN_BAG_TYPE_BN = "blood_type_BN";

    private static final String COLUMN_BAG_TYPE_ABP = "blood_type_ABP";
    private static final String COLUMN_BAG_TYPE_ABN = "blood_type_ABN";

    private static final String COLUMN_BAG_TYPE_OP = "blood_type_OP";
    private static final String COLUMN_BAG_TYPE_ON = "blood_type_ON";

    private static final String COLUMN_BANK_STOCK_ID = "stock_bank_id";


    // create user table sql query
    private final String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_USER_EMAIL + " VARCHAR NOT NULL,"
            + COLUMN_USER_PASSWORD + " VARCHAR NOT NULL,"
            + COLUMN_USER_TYPE + " VARCHAR NOT NULL" + ")";

    //create seeker table sql query
    private final String CREATE_SEEKER_TABLE = "CREATE TABLE " + TABLE_SEEKER + "("
            + COLUMN_SEEKER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_SEEKER_NAME + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_ADDRESS + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_CONTACT + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_GENDER + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_BLOOD_TYPE + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_STATUS + " VARCHAR NOT NULL,"
            + COLUMN_SEEKER_USER_ID + " INTEGER NOT NULL,"
            + "FOREIGN KEY (" + COLUMN_SEEKER_USER_ID + ") REFERENCES " + TABLE_USER + " (" + COLUMN_USER_ID + ") )";


    //create Donor table sql query
    private final String CREATE_DONOR_TABLE = "CREATE TABLE " + TABLE_DONOR + "("
            + COLUMN_DONOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_DONOR_NAME + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_ADDRESS + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_CONTACT + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_GENDER + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_BLOOD_TYPE + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_STATUS + " VARCHAR NOT NULL,"
            + COLUMN_DONOR_USER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_DONOR_USER_ID + ") REFERENCES " + TABLE_USER + " (" + COLUMN_USER_ID + ") )";


    //create Bank table sql query
    private final String CREATE_BANK_TABLE = "CREATE TABLE " + TABLE_BANK + "("
            + COLUMN_BANK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_BANK_NAME + " VARCHAR NOT NULL,"
            + COLUMN_BANK_ADDRESS + " VARCHAR NOT NULL,"
            + COLUMN_BANK_CONTACT + " VARCHAR NOT NULL,"
            + COLUMN_BANK_USER_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_BANK_USER_ID + ") REFERENCES " + TABLE_USER + " (" + COLUMN_USER_ID + ") )";


    //create BankStock table sql query
    private final String CREATE_BANK_STOCK_TABLE = "CREATE TABLE " + TABLE_BANK_STOCK + "("
            + COLUMN_STOCK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
            + COLUMN_BAG_TYPE_AP + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_AN + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_BP + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_BN + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_ABP + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_ABN + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_OP + " INTEGER NOT NULL,"
            + COLUMN_BAG_TYPE_ON + " INTEGER NOT NULL,"
            + COLUMN_BANK_STOCK_ID + " INTEGER NOT NULL, "
            + "FOREIGN KEY (" + COLUMN_BANK_STOCK_ID + ") REFERENCES " + TABLE_BANK + " (" + COLUMN_BANK_ID + ") )";


    //constructor
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //Hash password
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
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = "0" + h;
                hexString.append(h);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL(CREATE_SEEKER_TABLE);
        db.execSQL(CREATE_DONOR_TABLE);
        db.execSQL(CREATE_BANK_TABLE);
        db.execSQL(CREATE_BANK_STOCK_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Drop User Table if exist
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_USER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_SEEKER + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_DONOR + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_BANK + "'");
        db.execSQL("DROP TABLE IF EXISTS '" + TABLE_BANK_STOCK + "'");


        // Create tables again
        onCreate(db);
    }

    //This method adds a seeker/donor to database i.e  register
    public void addUser(User user) {
        //call hash method on the password
        String hashPass = md5(user.getPassword());

        SQLiteDatabase db = this.getWritableDatabase();

        //adding users in users table
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, hashPass);
        values.put(COLUMN_USER_TYPE, user.getUserType());
        // Inserting Row
        Long id = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);


        //send data to the table of a user type i.e seeker/donor
        switch (user.getUserType()) {
            case "Seeker":
                //adding seekersValues in seeker table
                ContentValues valuesSeeker = new ContentValues();
                valuesSeeker.put(COLUMN_SEEKER_NAME, user.getName());
                valuesSeeker.put(COLUMN_SEEKER_ADDRESS, user.getAddress());
                valuesSeeker.put(COLUMN_SEEKER_CONTACT, user.getContact());
                valuesSeeker.put(COLUMN_SEEKER_GENDER, user.getGender());
                valuesSeeker.put(COLUMN_SEEKER_BLOOD_TYPE, user.getBloodType());
                valuesSeeker.put(COLUMN_SEEKER_STATUS, user.getStatus());
                valuesSeeker.put(COLUMN_SEEKER_USER_ID, id);

                db.insert(TABLE_SEEKER, null, valuesSeeker);
                break;

            case "Donor":
                //adding donorsValues in donor table
                ContentValues valuesDonor = new ContentValues();
                valuesDonor.put(COLUMN_DONOR_NAME, user.getName());
                valuesDonor.put(COLUMN_DONOR_ADDRESS, user.getAddress());
                valuesDonor.put(COLUMN_DONOR_CONTACT, user.getContact());
                valuesDonor.put(COLUMN_DONOR_GENDER, user.getGender());
                valuesDonor.put(COLUMN_DONOR_BLOOD_TYPE, user.getBloodType());
                valuesDonor.put(COLUMN_DONOR_STATUS, user.getStatus());
                valuesDonor.put(COLUMN_DONOR_USER_ID, id);

                db.insert(TABLE_DONOR, null, valuesDonor);
                break;

            default:
                break;
        }

    }

    // This method to check user exist or not
    public boolean checkUser(String email) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?";
        // selection argument
        String[] selectionArgs = {email};
        // query user table with condition
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@abc.com';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            cursor.moveToFirst();
            return true;
        }
        return false;
    }

    // This method to check user exist and password match
    public boolean checkUser(String email, String password) {
        // array of columns to fetch
        String[] columns = {
                COLUMN_USER_ID
        };
        SQLiteDatabase db = this.getReadableDatabase();
        // selection criteria
        String selection = COLUMN_USER_EMAIL + " = ?" + " AND " + COLUMN_USER_PASSWORD + " = ?";
        // selection arguments
        String[] selectionArgs = {email, password};
        // query user table with conditions
        /*
         * Here query function is used to fetch records from user table this function works like we use sql query.
         * SQL query equivalent to this query function is
         * SELECT user_id FROM user WHERE user_email = 'jack@abc.com' AND user_password = 'haspass';
         */
        Cursor cursor = db.query(TABLE_USER, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                       //filter by row groups
                null);                      //The sort order
        int cursorCount = cursor.getCount();
        return cursorCount > 0;
    }

    //This method is used to get User Id when Logging in
    public int GetUserID(String tableName, String email) {
        String where = COLUMN_USER_EMAIL + " LIKE '%" + email + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true, tableName, null,
                where, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            return c.getInt(0);

        }
        return 0;
    }

    //This method is used to get the User Type
    public String GetUserType(String tableName, String email) {
        String where = COLUMN_USER_EMAIL + " LIKE '%" + email + "%'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(true, tableName, null,
                where, null, null, null, null, null);
        if (c != null && c.getCount() > 0) {
            c.moveToFirst();
            return String.valueOf(c.getString(3));
        }
        return null;
    }


    //This method is to fetch all user and return the list of user records
    public ArrayList<User> getAllDonors() {
        ArrayList<User> userModelArrayList = new ArrayList<>();
        // query the user table
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_TYPE + " = " + " 'Donor' ";

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                User user = new User();
                user.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                user.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                user.setPassword(cursor.getString(cursor.getColumnIndex(COLUMN_USER_PASSWORD)));
                user.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE)));


                //getting donor where id = donor_id from TABLE_DONOR
                String selectDonorQuery = "SELECT  * FROM " + TABLE_DONOR + " WHERE " + COLUMN_DONOR_USER_ID + " = " + user.getId();
                Cursor donorCursor = db.rawQuery(selectDonorQuery, null);

                if (donorCursor.moveToFirst()) {
                    do {
                        user.setName(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_NAME)));
                        user.setContact(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_CONTACT)));
                        user.setAddress(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_ADDRESS)));
                        user.setGender(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_GENDER)));
                        user.setBloodType(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_BLOOD_TYPE)));
                        user.setStatus(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_STATUS)));


                    } while (donorCursor.moveToNext());
                }

                // Adding user record to list
                userModelArrayList.add(user);
            } while (cursor.moveToNext());
        }
        // return user list
        return userModelArrayList;

    }

    //This method searches for donor attributes matching with the entered String keyword
    public ArrayList<User> searchDonor(String keyword) {

        ArrayList<User> userModelArrayList = new ArrayList<>();
        try {
            // query the user table
            SQLiteDatabase db = this.getReadableDatabase();

            //getting donor where id = donor_id from TABLE_DONOR
            String selectBankQuery = "SELECT  * FROM " + TABLE_DONOR + " WHERE " + COLUMN_DONOR_NAME + " like ? OR " +
                    COLUMN_DONOR_ADDRESS + " like ? OR " + COLUMN_DONOR_BLOOD_TYPE + " like ? ";

            Cursor searchCursor = db.rawQuery(selectBankQuery, new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});

            if (searchCursor.moveToFirst()) {
                do {
                    User userSearch = new User();
                    userSearch.setName(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_NAME)));
                    userSearch.setContact(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_CONTACT)));
                    userSearch.setAddress(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_ADDRESS)));
                    userSearch.setGender(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_GENDER)));
                    userSearch.setBloodType(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_BLOOD_TYPE)));
                    userSearch.setStatus(searchCursor.getString(searchCursor.getColumnIndex(COLUMN_DONOR_STATUS)));

                    // Adding donor record to list
                    userModelArrayList.add(userSearch);

                } while (searchCursor.moveToNext());

            }


        } catch (Exception e) {

            userModelArrayList = null;

        }

        return userModelArrayList;
    }


    //This method gets the Donor data using id
    public ArrayList<User> getDonorData(String id) {
        ArrayList<User> donorDataArrayList = new ArrayList<>();

        // query the user table
        SQLiteDatabase db = this.getReadableDatabase();


        //getting donor where id = donor-User_id from TABLE_DONOR
        String selectDonorQuery = "SELECT  * FROM " + TABLE_DONOR + " WHERE " + COLUMN_DONOR_USER_ID + " = " + id;
        Cursor donorCursor = db.rawQuery(selectDonorQuery, null);


        if (donorCursor.moveToFirst()) {

            do {
                User duser = new User();
                duser.setName(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_NAME)));
                duser.setContact(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_CONTACT)));
                duser.setAddress(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_ADDRESS)));
                duser.setGender(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_GENDER)));
                duser.setBloodType(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_BLOOD_TYPE)));
                duser.setStatus(donorCursor.getString(donorCursor.getColumnIndex(COLUMN_DONOR_STATUS)));
                // Adding donor record to list
                donorDataArrayList.add(duser);

            } while (donorCursor.moveToNext());

        }

        return donorDataArrayList;

    }

    //This method updates the status of a Donor
    public void updateDonorStatus(String id, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DONOR_STATUS, status);
        // updating row
        db.update(TABLE_DONOR, values, COLUMN_DONOR_USER_ID + " = ?",
                new String[]{String.valueOf(id)});
    }


    //This method add a bank and default stock during registration
    public void addBankAndStock(BankStock bankStock) {

        //hash the password
        String hashPass = md5(bankStock.getPassword());
        SQLiteDatabase db = this.getWritableDatabase();

        //adding users in users table
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, bankStock.getEmail());
        values.put(COLUMN_USER_PASSWORD, hashPass);
        values.put(COLUMN_USER_TYPE, bankStock.getUserType());
        // Inserting Row
        Long id = db.insertWithOnConflict(TABLE_USER, null, values, SQLiteDatabase.CONFLICT_IGNORE);

        //insert into table bank
        ContentValues valuesBank = new ContentValues();
        valuesBank.put(COLUMN_BANK_NAME, bankStock.getName());
        valuesBank.put(COLUMN_BANK_ADDRESS, bankStock.getAddress());
        valuesBank.put(COLUMN_BANK_CONTACT, bankStock.getContact());
        valuesBank.put(COLUMN_BANK_USER_ID, id);


        Long bId = db.insertWithOnConflict(TABLE_BANK, null, valuesBank, SQLiteDatabase.CONFLICT_IGNORE);

        //insert into table bankStock
        ContentValues valuesBankStock = new ContentValues();

        valuesBankStock.put(COLUMN_BAG_TYPE_AP, bankStock.getBloodTypeAP());
        valuesBankStock.put(COLUMN_BAG_TYPE_AN, bankStock.getBloodTypeAN());

        valuesBankStock.put(COLUMN_BAG_TYPE_BP, bankStock.getBloodTypeBP());
        valuesBankStock.put(COLUMN_BAG_TYPE_BN, bankStock.getBloodTypeBN());

        valuesBankStock.put(COLUMN_BAG_TYPE_ABP, bankStock.getBloodTypeABP());
        valuesBankStock.put(COLUMN_BAG_TYPE_ABN, bankStock.getBloodTypeABN());

        valuesBankStock.put(COLUMN_BAG_TYPE_OP, bankStock.getBloodTypeOP());
        valuesBankStock.put(COLUMN_BAG_TYPE_ON, bankStock.getBloodTypeON());

        valuesBankStock.put(COLUMN_BANK_STOCK_ID, bId);
        db.insert(TABLE_BANK_STOCK, null, valuesBankStock);
    }

    //This method is used to get All bank list
    public ArrayList<BankStock> getAllBanks() {

        ArrayList<BankStock> userBankStockArrayList = new ArrayList<>();

        // query the user table
        String selectQuery = "SELECT  * FROM " + TABLE_USER + " WHERE " + COLUMN_USER_TYPE + " = " + " 'Bank' ";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Traversing through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                BankStock bankStock = new BankStock();
                bankStock.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_USER_ID)));
                bankStock.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                bankStock.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE)));

                //getting bank where id = bank_user_id from TABLE_BANK
                String selectBankQuery = "SELECT  * FROM " + TABLE_BANK + " WHERE " + COLUMN_BANK_USER_ID + " = " + bankStock.getId();
                Cursor bankCursor = db.rawQuery(selectBankQuery, null);

                if (bankCursor.moveToFirst()) {
                    do {
                        bankStock.setbId(bankCursor.getInt(bankCursor.getColumnIndex(COLUMN_BANK_ID)));
                        bankStock.setName(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_NAME)));
                        bankStock.setAddress(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_ADDRESS)));
                        bankStock.setContact(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_CONTACT)));

                        //getting bank stock data where stock_bank_id = bank_id fro TABLE BANk
                        String selectBagQuery = "SELECT  * FROM " + TABLE_BANK_STOCK + " WHERE " + COLUMN_BANK_STOCK_ID + " = " + bankStock.getbId();
                        Cursor stockCursor = db.rawQuery(selectBagQuery, null);

                        if (stockCursor.moveToFirst()) {
                            do {
                                bankStock.setBloodTypeAP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AP)));
                                bankStock.setBloodTypeAN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AN)));

                                bankStock.setBloodTypeBP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BP)));
                                bankStock.setBloodTypeBN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BN)));

                                bankStock.setBloodTypeABP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABP)));
                                bankStock.setBloodTypeABN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABN)));

                                bankStock.setBloodTypeOP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_OP)));
                                bankStock.setBloodTypeON(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ON)));


                            } while (stockCursor.moveToNext());
                        }

                    } while (bankCursor.moveToNext());
                }

                // Adding user record to list
                userBankStockArrayList.add(bankStock);
            } while (cursor.moveToNext());
        }
        // return user list
        return userBankStockArrayList;


    }

    //This method searches for bank attributes matching with the entered String keyword
    public ArrayList<BankStock> searchBanks(String keyword) {

        ArrayList<BankStock> bankAndStockArrayList = new ArrayList<>();
        try {

            // query the user table
            String selectQuery = "SELECT  * FROM " + TABLE_USER + " INNER JOIN " + TABLE_BANK +
                    " ON " + COLUMN_USER_ID + " = " + COLUMN_BANK_USER_ID + " WHERE " +
                    COLUMN_USER_TYPE + "  = " + " 'Bank' AND " + COLUMN_USER_EMAIL +
                    " like ? OR " + COLUMN_BANK_NAME + " like ? OR " + COLUMN_BANK_ADDRESS + " like ? ";

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, new String[]{"%" + keyword + "%", "%" + keyword + "%", "%" + keyword + "%"});

            // Traversing through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    BankStock bankStock = new BankStock();
                    bankStock.setEmail(cursor.getString(cursor.getColumnIndex(COLUMN_USER_EMAIL)));
                    bankStock.setUserType(cursor.getString(cursor.getColumnIndex(COLUMN_USER_TYPE)));


                    bankStock.setName(cursor.getString(cursor.getColumnIndex(COLUMN_BANK_NAME)));
                    bankStock.setAddress(cursor.getString(cursor.getColumnIndex(COLUMN_BANK_ADDRESS)));
                    bankStock.setContact(cursor.getString(cursor.getColumnIndex(COLUMN_BANK_CONTACT)));
                    bankStock.setId(cursor.getInt(cursor.getColumnIndex(COLUMN_BANK_ID)));


                    //getting bank stock data where stock_bank_id = bank_id fro TABLE BANk
                    String selectBagQuery = "SELECT  * FROM " + TABLE_BANK_STOCK + " WHERE " + COLUMN_BANK_STOCK_ID + " = " + bankStock.getId();
                    Cursor stockCursor = db.rawQuery(selectBagQuery, null);

                    if (stockCursor.moveToFirst()) {
                        do {
                            bankStock.setBloodTypeAP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AP)));
                            bankStock.setBloodTypeAN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AN)));

                            bankStock.setBloodTypeBP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BP)));
                            bankStock.setBloodTypeBN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BN)));

                            bankStock.setBloodTypeABP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABP)));
                            bankStock.setBloodTypeABN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABN)));

                            bankStock.setBloodTypeOP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_OP)));
                            bankStock.setBloodTypeON(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ON)));


                        } while (stockCursor.moveToNext());
                    }


                    // Adding bankAndStock record to list
                    bankAndStockArrayList.add(bankStock);
                } while (cursor.moveToNext());
            }

        } catch (Exception e) {

            bankAndStockArrayList = null;

        }
        // return user list
        return bankAndStockArrayList;


    }

    //This method is used to get BankStock data
    public ArrayList<BankStock> getBankData(String id) {

        ArrayList<BankStock> bankDataArrayList = new ArrayList<>();
        // query the user table
        SQLiteDatabase db = this.getReadableDatabase();


        //getting bank details  where id = bank_user_id from TABLE_BANK
        String selectBankQuery = "SELECT  * FROM " + TABLE_BANK + " WHERE " + COLUMN_BANK_USER_ID + " = " + id;
        Cursor bankCursor = db.rawQuery(selectBankQuery, null);

        if (bankCursor.moveToFirst()) {
            do {
                BankStock bankStock = new BankStock();
                bankStock.setbId(bankCursor.getInt(bankCursor.getColumnIndex(COLUMN_BANK_ID)));
                bankStock.setName(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_NAME)));
                bankStock.setAddress(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_ADDRESS)));
                bankStock.setContact(bankCursor.getString(bankCursor.getColumnIndex(COLUMN_BANK_CONTACT)));

                //getting bank where id = bank_id from TABLE_BANK
                String selectBagQuery = "SELECT  * FROM " + TABLE_BANK_STOCK + " WHERE " + COLUMN_BANK_STOCK_ID + " = " + bankStock.getbId();
                Cursor stockCursor = db.rawQuery(selectBagQuery, null);

                if (stockCursor.moveToFirst()) {
                    do {
                        bankStock.setBloodTypeAP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AP)));
                        bankStock.setBloodTypeAN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_AN)));

                        bankStock.setBloodTypeBP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BP)));
                        bankStock.setBloodTypeBN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_BN)));

                        bankStock.setBloodTypeABP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABP)));
                        bankStock.setBloodTypeABN(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ABN)));

                        bankStock.setBloodTypeOP(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_OP)));
                        bankStock.setBloodTypeON(stockCursor.getInt(stockCursor.getColumnIndex(COLUMN_BAG_TYPE_ON)));


                    } while (stockCursor.moveToNext());
                }


                // Adding user record to list
                bankDataArrayList.add(bankStock);

            } while (bankCursor.moveToNext());


        }

        // return user list
        return bankDataArrayList;

    }

    public void updateStock(BankStock bankStock, String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BAG_TYPE_AP, bankStock.getBloodTypeAP());
        values.put(COLUMN_BAG_TYPE_AN, bankStock.getBloodTypeAN());
        values.put(COLUMN_BAG_TYPE_BP, bankStock.getBloodTypeBP());
        values.put(COLUMN_BAG_TYPE_BN, bankStock.getBloodTypeBN());
        values.put(COLUMN_BAG_TYPE_ABP, bankStock.getBloodTypeABP());
        values.put(COLUMN_BAG_TYPE_ABN, bankStock.getBloodTypeABN());
        values.put(COLUMN_BAG_TYPE_OP, bankStock.getBloodTypeOP());
        values.put(COLUMN_BAG_TYPE_ON, bankStock.getBloodTypeON());

        // updating row
        db.update(TABLE_BANK_STOCK, values, COLUMN_BANK_STOCK_ID + " = ?",
                new String[]{String.valueOf(id)});
    }

    // This method to update user record
    public void updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USER_EMAIL, user.getEmail());
        values.put(COLUMN_USER_PASSWORD, user.getPassword());
        // updating row
        db.update(TABLE_USER, values, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }

    //This method is to delete user record
    public void deleteUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        // delete user record by id
        db.delete(TABLE_USER, COLUMN_USER_ID + " = ?",
                new String[]{String.valueOf(user.getId())});
    }
}
