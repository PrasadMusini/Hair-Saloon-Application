package com.example.practice.saloonapp.models;

import com.google.gson.annotations.SerializedName;

public class SaloonOPItem {

    @SerializedName("Id")
    private int id;

    @SerializedName("BranchId")
    private int branchId;

    @SerializedName("Name")
    private String name;

    @SerializedName("Date")
    private String date;

    @SerializedName("SlotTime")
    private int slotTime;

    @SerializedName("CustomerName")
    private String customerName;

    @SerializedName("PhoneNumber")
    private String phoneNumber;

    @SerializedName("Email")
    private String email;

    @SerializedName("GenderTypeId")
    private int genderTypeId;

    @SerializedName("Gender")
    private String gender;

    @SerializedName("StatusTypeId")
    private int statusTypeId;

    @SerializedName("Status")
    private String status;

    @SerializedName("PurposeOfVisitId")
    private int purposeOfVisitId;

    @SerializedName("PurposeOfVisit")
    private String purposeOfVisit;

    @SerializedName("IsActive")
    private boolean isActive;

    @SerializedName("SlotDuration")
    private String slotDuration;

    public SaloonOPItem(int id, int branchId, String name, String date, int slotTime, String customerName, String phoneNumber, String email, int genderTypeId, String gender, int statusTypeId, String status, int purposeOfVisitId, String purposeOfVisit, boolean isActive, String slotDuration) {
        this.id = id;
        this.branchId = branchId;
        this.name = name;
        this.date = date;
        this.slotTime = slotTime;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderTypeId = genderTypeId;
        this.gender = gender;
        this.statusTypeId = statusTypeId;
        this.status = status;
        this.purposeOfVisitId = purposeOfVisitId;
        this.purposeOfVisit = purposeOfVisit;
        this.isActive = isActive;
        this.slotDuration = slotDuration;
    }

    // Add getters and setters as needed

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(int slotTime) {
        this.slotTime = slotTime;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getGenderTypeId() {
        return genderTypeId;
    }

    public void setGenderTypeId(int genderTypeId) {
        this.genderTypeId = genderTypeId;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        this.statusTypeId = statusTypeId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getPurposeOfVisitId() {
        return purposeOfVisitId;
    }

    public void setPurposeOfVisitId(int purposeOfVisitId) {
        this.purposeOfVisitId = purposeOfVisitId;
    }

    public String getPurposeOfVisit() {
        return purposeOfVisit;
    }

    public void setPurposeOfVisit(String purposeOfVisit) {
        this.purposeOfVisit = purposeOfVisit;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getSlotDuration() {
        return slotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        this.slotDuration = slotDuration;
    }
}