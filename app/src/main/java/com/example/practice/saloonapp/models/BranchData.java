package com.example.practice.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class BranchData {

        @SerializedName("ListResult")
        public ArrayList<BranchList> listResult;
        @SerializedName("IsSuccess")
        public boolean isSuccess;
        @SerializedName("AffectedRecords")
        public int affectedRecords;
        @SerializedName("EndUserMessage")
        public String endUserMessage;
        @SerializedName("ValidationErrors")
        public ArrayList<Object> validationErrors;
        @SerializedName("Exception")
        public Object exception;
        public Object links;


    public BranchData(ArrayList<BranchList> listResult, boolean isSuccess, int affectedRecords, String endUserMessage, ArrayList<Object> validationErrors, Object exception, Object links) {
        this.listResult = listResult;
        this.isSuccess = isSuccess;
        this.affectedRecords = affectedRecords;
        this.endUserMessage = endUserMessage;
        this.validationErrors = validationErrors;
        this.exception = exception;
        this.links = links;
    }

    public ArrayList<BranchList> getListResult() {
        return listResult;
    }

    public void setListResult(ArrayList<BranchList> listResult) {
        this.listResult = listResult;
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

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }

    public Object getLinks() {
        return links;
    }

    public void setLinks(Object links) {
        this.links = links;
    }


}
