package com.example.practice.saloonapp.models;


import com.google.gson.annotations.SerializedName;

public class BranchList{

    @SerializedName("Id")
    public int id;
    @SerializedName("Name")
    public String name;
    @SerializedName("FilePath")
    public String filePath;
    @SerializedName("Address")
    public String address;
    @SerializedName("StartTime")
    public int startTime;
    @SerializedName("CloseTime")
    public int closeTime;
    @SerializedName("Room")
    public int room;
    @SerializedName("MobileNumber")
    public String mobileNumber;
    @SerializedName("IsActive")
    public boolean isActive;

    public BranchList(int id, String name, String filePath, String address, int startTime, int closeTime, int room, String mobileNumber, boolean isActive) {
        this.id = id;
        this.name = name;
        this.filePath = filePath;
        this.address = address;
        this.startTime = startTime;
        this.closeTime = closeTime;
        this.room = room;
        this.mobileNumber = mobileNumber;
        this.isActive = isActive;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStartTime() {
        return startTime;
    }

    public void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    public int getCloseTime() {
        return closeTime;
    }

    public void setCloseTime(int closeTime) {
        this.closeTime = closeTime;
    }

    public int getRoom() {
        return room;
    }

    public void setRoom(int room) {
        this.room = room;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
