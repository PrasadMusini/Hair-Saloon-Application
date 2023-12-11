package com.example.practice.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.Date;

public class SlotsOPSModel {

    @SerializedName("ListResult")
    public ArrayList<SlotsListItems> slotsListResult;
    @SerializedName("IsSuccess")
    public boolean isSuccess;
    @SerializedName("AffectedRecords")
    public int affectedRecords;
    @SerializedName("EndUserMessage")
    public String endUserMessage;
    @SerializedName("ValidationErrors")
    public ArrayList<Object> validationErrors;
    @SerializedName("Exception")
    public Object slotException;
    public Object links;


    public SlotsOPSModel(ArrayList<SlotsListItems> slotsListResult, boolean isSuccess, int affectedRecords, String endUserMessage, ArrayList<Object> validationErrors, Object slotException, Object links) {
        this.slotsListResult = slotsListResult;
        this.isSuccess = isSuccess;
        this.affectedRecords = affectedRecords;
        this.endUserMessage = endUserMessage;
        this.validationErrors = validationErrors;
        this.slotException = slotException;
        this.links = links;
    }

    public ArrayList<SlotsListItems> getSlotsListResult() {
        return slotsListResult;
    }

    public void setSlotsListResult(ArrayList<SlotsListItems> slotsListResult) {
        this.slotsListResult = slotsListResult;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getAffectedRecords() {
        return affectedRecords;
    }

    public void setAffectedRecords(int affectedRecords) {
        this.affectedRecords = affectedRecords;
    }

    public String getEndUserMessage() {
        return endUserMessage;
    }

    public void setEndUserMessage(String endUserMessage) {
        this.endUserMessage = endUserMessage;
    }

    public ArrayList<Object> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(ArrayList<Object> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Object getSlotException() {
        return slotException;
    }

    public void setSlotException(Object slotException) {
        this.slotException = slotException;
    }

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }

    public class SlotsListItems {
        @SerializedName("BranchId")
        public int branchId;
        @SerializedName("Name")
        public String name;
        @SerializedName("Dates")
        public String dates;
        @SerializedName("Room")
        public int room;
        @SerializedName("Slot")
        public String slot;
        @SerializedName("AvailableSlots")
        public int availableSlots;
        @SerializedName("SlotTimeSpan")
        public String slotTimeSpan;

        public SlotsListItems(int branchId, String name, String dates, int room, String slot, int availableSlots, String slotTimeSpan) {
            this.branchId = branchId;
            this.name = name;
            this.dates = dates;
            this.room = room;
            this.slot = slot;
            this.availableSlots = availableSlots;
            this.slotTimeSpan = slotTimeSpan;
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

        public String getDates() {
            return dates;
        }

        public void setDates(String dates) {
            this.dates = dates;
        }

        public int getRoom() {
            return room;
        }

        public void setRoom(int room) {
            this.room = room;
        }

        public String getSlot() {
            return slot;
        }

        public void setSlot(String slot) {
            this.slot = slot;
        }

        public int getAvailableSlots() {
            return availableSlots;
        }

        public void setAvailableSlots(int availableSlots) {
            this.availableSlots = availableSlots;
        }

        public String getSlotTimeSpan() {
            return slotTimeSpan;
        }

        public void setSlotTimeSpan(String slotTimeSpan) {
            this.slotTimeSpan = slotTimeSpan;
        }
    }

}
