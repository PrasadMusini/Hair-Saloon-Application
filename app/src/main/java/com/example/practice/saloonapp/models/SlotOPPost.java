package com.example.practice.saloonapp.models;

import com.google.gson.annotations.SerializedName;

public class SlotOPPost {

    @SerializedName("Id")
    public String id;
    @SerializedName("BranchId")
    public int branchId;
    @SerializedName("Date")
    public String date;
    @SerializedName("SlotTime")
    public String slotTime;
    @SerializedName("CustomerName")
    public String customerName;
    @SerializedName("PhoneNumber")
    public String phoneNumber;
    @SerializedName("Email")
    public String email;
    @SerializedName("GenderTypeId")
    public int genderTypeId;
    @SerializedName("StatusTypeId")
    public int statusTypeId;
    @SerializedName("PurposeOfVisitId")
    public int purposeOfVisitId;
    @SerializedName("PurposeOfVisit")
    public String purposeOfVisit;
    @SerializedName("IsActive")
    public boolean isActive;
    @SerializedName("CreatedDate")
    public String createdDate;
    @SerializedName("UpdatedDate")
    public String updatedDate;
    @SerializedName("UpdatedByUserId")
    public String updatedByUserId;

    public SlotOPPost(String id, int branchId, String date, String slotTime, String customerName, String phoneNumber, String email, int genderTypeId, int statusTypeId,
                      int purposeOfVisitId, String purposeOfVisit, boolean isActive, String createdDate, String updatedDate, String updatedByUserId) {
        this.id = id;
        this.branchId = branchId;
        this.date = date;
        this.slotTime = slotTime;
        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.genderTypeId = genderTypeId;
        this.statusTypeId = statusTypeId;
        this.purposeOfVisitId = purposeOfVisitId;
        this.purposeOfVisit = purposeOfVisit;
        this.isActive = isActive;
        this.createdDate = createdDate;
        this.updatedDate = updatedDate;
        this.updatedByUserId = updatedByUserId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getBranchId() {
        return branchId;
    }

    public void setBranchId(int branchId) {
        this.branchId = branchId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSlotTime() {
        return slotTime;
    }

    public void setSlotTime(String slotTime) {
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

    public int getStatusTypeId() {
        return statusTypeId;
    }

    public void setStatusTypeId(int statusTypeId) {
        this.statusTypeId = statusTypeId;
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

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getUpdatedDate() {
        return updatedDate;
    }

    public void setUpdatedDate(String updatedDate) {
        this.updatedDate = updatedDate;
    }

    public String getUpdatedByUserId() {
        return updatedByUserId;
    }

    public void setUpdatedByUserId(String updatedByUserId) {
        this.updatedByUserId = updatedByUserId;
    }
}
