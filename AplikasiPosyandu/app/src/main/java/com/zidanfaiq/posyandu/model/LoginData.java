package com.zidanfaiq.posyandu.model;

import com.google.gson.annotations.SerializedName;

public class LoginData {
    @SerializedName("user_id")
    private String userId;

    @SerializedName("nama_ibu")
    private String nama_ibu;

    @SerializedName("nik_ibu")
    private String nik_ibu;

    @SerializedName("tempat_lahir")
    private String tempat_lahir;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("alamat")
    private String alamat;

    @SerializedName("posyandu")
    private String posyandu;

    @SerializedName("telepon")
    private String telepon;

    @SerializedName("email")
    private String email;

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setNamaIbu(String nama_ibu){
        this.nama_ibu = nama_ibu;
    }

    public String getNamaIbu(){
        return nama_ibu;
    }

    public void setNIKIbu(String nik_ibu){
        this.nik_ibu = nik_ibu;
    }

    public String getNIKIbu(){
        return nik_ibu;
    }

    public void setTempatLahir(String tempat_lahir){
        this.tempat_lahir = tempat_lahir;
    }

    public String getTempatlahir(){
        return tempat_lahir;
    }

    public void setTglLahir(String tgl_lahir){
        this.tgl_lahir = tgl_lahir;
    }

    public String getTglLahir(){
        return tgl_lahir;
    }

    public void setAlamat(String alamat){
        this.alamat = alamat;
    }

    public String getAlamat(){
        return alamat;
    }

    public String getPosyandu() {
        return posyandu;
    }

    public void setPosyandu(String posyandu) {
        this.posyandu = posyandu;
    }

    public void setTelepon(String telepon){
        this.telepon = telepon;
    }

    public String getTelepon(){
        return telepon;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return email;
    }
}