package com.example.bloodapp.model;


public class BankStock extends User {

    private int bId;

    private int bloodTypeAP;
    private int bloodTypeAN;

    private int bloodTypeBP;
    private int bloodTypeBN;

    private int bloodTypeABP;
    private int bloodTypeABN;

    private int bloodTypeOP;
    private int bloodTypeON;


    public BankStock() {

    }

    public BankStock(int id, String email, String password, String userType, String name, String address, String contact, String gender, String bloodType, String status) {
        super(id, email, password, userType, name, address, contact, gender, bloodType, status);
    }

    public int getbId() {
        return bId;
    }

    public void setbId(int bId) {
        this.bId = bId;
    }

    public int getBloodTypeAP() {
        return bloodTypeAP;
    }

    public void setBloodTypeAP(int bloodTypeAP) {
        this.bloodTypeAP = bloodTypeAP;
    }

    public int getBloodTypeAN() {
        return bloodTypeAN;
    }

    public void setBloodTypeAN(int bloodTypeAN) {
        this.bloodTypeAN = bloodTypeAN;
    }

    public int getBloodTypeBP() {
        return bloodTypeBP;
    }

    public void setBloodTypeBP(int bloodTypeBP) {
        this.bloodTypeBP = bloodTypeBP;
    }

    public int getBloodTypeBN() {
        return bloodTypeBN;
    }

    public void setBloodTypeBN(int bloodTypeBN) {
        this.bloodTypeBN = bloodTypeBN;
    }

    public int getBloodTypeABP() {
        return bloodTypeABP;
    }

    public void setBloodTypeABP(int bloodTypeABP) {
        this.bloodTypeABP = bloodTypeABP;
    }

    public int getBloodTypeABN() {
        return bloodTypeABN;
    }

    public void setBloodTypeABN(int bloodTypeABN) {
        this.bloodTypeABN = bloodTypeABN;
    }

    public int getBloodTypeOP() {
        return bloodTypeOP;
    }

    public void setBloodTypeOP(int bloodTypeOP) {
        this.bloodTypeOP = bloodTypeOP;
    }

    public int getBloodTypeON() {
        return bloodTypeON;
    }

    public void setBloodTypeON(int bloodTypeON) {
        this.bloodTypeON = bloodTypeON;
    }
}