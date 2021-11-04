package com.zidanfaiq.posyandu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AnakResponse {
    @SerializedName("data")
    private List<AnakData> anakData;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<AnakData> getAnakData() {
        return anakData;
    }

    public void setAnakData(List<AnakData> anakData) {
        this.anakData = anakData;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
