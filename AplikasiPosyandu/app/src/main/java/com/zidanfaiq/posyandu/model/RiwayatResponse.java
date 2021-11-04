package com.zidanfaiq.posyandu.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RiwayatResponse {
    @SerializedName("data")
    private List<RiwayatData> riwayatData;

    @SerializedName("message")
    private String message;

    @SerializedName("status")
    private boolean status;

    public List<RiwayatData> getRiwayatData() {
        return riwayatData;
    }

    public void setRiwayatData(List<RiwayatData> riwayatData) {
        this.riwayatData = riwayatData;
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
