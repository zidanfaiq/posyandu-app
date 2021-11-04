package com.zidanfaiq.posyandu.model;

import com.google.gson.annotations.SerializedName;

public class AnakData {
    @SerializedName("id_anak")
    private String id_anak;

    @SerializedName("nama_anak")
    private String nama_anak;

    @SerializedName("anak_ke")
    private String anak_ke;

    @SerializedName("no_akte")
    private String no_akte;

    @SerializedName("nik_anak")
    private String nik_anak;

    @SerializedName("tempat_lahir")
    private String tempat_lahir;

    @SerializedName("tgl_lahir")
    private String tgl_lahir;

    @SerializedName("jenis_kelamin")
    private String jenis_kelamin;

    @SerializedName("gol_darah")
    private String gol_darah;

    @SerializedName("user_id")
    private String user_id;

    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }

    public String getNama_anak() {
        return nama_anak;
    }

    public void setNama_anak(String nama_anak) {
        this.nama_anak = nama_anak;
    }

    public String getAnak_ke() {
        return anak_ke;
    }

    public void setAnak_ke(String anak_ke) {
        this.anak_ke = anak_ke;
    }

    public String getNo_akte() {
        return no_akte;
    }

    public void setNo_akte(String no_akte) {
        this.no_akte = no_akte;
    }

    public String getNik_anak() {
        return nik_anak;
    }

    public void setNik_anak(String nik_anak) {
        this.nik_anak = nik_anak;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTgl_lahir() {
        return tgl_lahir;
    }

    public void setTgl_lahir(String tgl_lahir) {
        this.tgl_lahir = tgl_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getGol_darah() {
        return gol_darah;
    }

    public void setGol_darah(String gol_darah) {
        this.gol_darah = gol_darah;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

}
