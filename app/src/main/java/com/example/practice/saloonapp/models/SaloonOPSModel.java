package com.example.practice.saloonapp.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SaloonOPSModel {

    @SerializedName("ListResult")
    private List<SaloonOPItem> opListResult;

    @SerializedName("IsSuccess")
    private boolean isSuccess;

    @SerializedName("AffectedRecords")
    private int affectedRecords;

    @SerializedName("EndUserMessage")
    private String endUserMessage;

    @SerializedName("ValidationErrors")
    private List<Object> validationErrors;

    @SerializedName("Exception")
    private Object exception;

    // Add getters and setters as needed

    public List<SaloonOPItem> getOpListResult() {
        return opListResult;
    }

    public void setOpListResult(List<SaloonOPItem> opListResult) {
        this.opListResult = opListResult;
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

    public List<Object> getValidationErrors() {
        return validationErrors;
    }

    public void setValidationErrors(List<Object> validationErrors) {
        this.validationErrors = validationErrors;
    }

    public Object getException() {
        return exception;
    }

    public void setException(Object exception) {
        this.exception = exception;
    }
}
