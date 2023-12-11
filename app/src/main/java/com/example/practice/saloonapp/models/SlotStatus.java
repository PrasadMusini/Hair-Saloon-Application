package com.example.practice.saloonapp.models;

public class SlotStatus {

    private int Id;
    private int StatusTypeId;
    private String BranchName;
    private String Date;
    private int SlotTime;
    private String CustomerName;
    private String PhoneNumber;
    private String Email;
    private String Address;
    private String SlotDuration;

    public SlotStatus(int id, int statusTypeId, String branchName, String date, int slotTime, String customerName, String phoneNumber, String email, String address, String slotDuration) {
        Id = id;
        StatusTypeId = statusTypeId;
        BranchName = branchName;
        Date = date;
        SlotTime = slotTime;
        CustomerName = customerName;
        PhoneNumber = phoneNumber;
        Email = email;
        Address = address;
        SlotDuration = slotDuration;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public int getStatusTypeId() {
        return StatusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        StatusTypeId = statusTypeId;
    }

    public String getBranchName() {
        return BranchName;
    }

    public void setBranchName(String branchName) {
        BranchName = branchName;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public int getSlotTime() {
        return SlotTime;
    }

    public void setSlotTime(int slotTime) {
        SlotTime = slotTime;
    }

    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSlotDuration() {
        return SlotDuration;
    }

    public void setSlotDuration(String slotDuration) {
        SlotDuration = slotDuration;
    }
}



