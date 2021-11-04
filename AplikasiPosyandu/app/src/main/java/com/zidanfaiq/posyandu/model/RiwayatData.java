package com.zidanfaiq.posyandu.model;

import com.google.gson.annotations.SerializedName;

public class RiwayatData {
    @SerializedName("nama_anak")
    private String nama_anak;

    @SerializedName("id_pemeriksaan")
    private String id_pemeriksaan;

    @SerializedName("tgl_pemeriksaan")
    private String tgl_pemeriksaan;

    @SerializedName("tinggi_badan")
    private String tinggi_badan;

    @SerializedName("berat_badan")
    private String berat_badan;

    @SerializedName("imunisasi")
    private String imunisasi;

    @SerializedName("vitamin")
    private String vitamin;

    @SerializedName("status_gizi")
    private String status_gizi;

    @SerializedName("penyuluhan")
    private String penyuluhan;

    @SerializedName("id_anak")
    private String id_anak;

    public String getNama_anak() {
        return nama_anak;
    }

    public void setNama_anak(String nama_anak) {
        this.nama_anak = nama_anak;
    }

    public String getId_pemeriksaan() {
        return id_pemeriksaan;
    }

    public void setId_pemeriksaan(String id_pemeriksaan) {
        this.id_pemeriksaan = id_pemeriksaan;
    }

    public String getTgl_pemeriksaan() {
        return tgl_pemeriksaan;
    }

    public void setTgl_pemeriksaan(String tgl_pemeriksaan) {
        this.tgl_pemeriksaan = tgl_pemeriksaan;
    }

    public String getTinggi_badan() {
        return tinggi_badan;
    }

    public void setTinggi_badan(String tinggi_badan) {
        this.tinggi_badan = tinggi_badan;
    }

    public String getBerat_badan() {
        return berat_badan;
    }

    public void setBerat_badan(String berat_badan) {
        this.berat_badan = berat_badan;
    }

    public String getImunisasi() {
        return imunisasi;
    }

    public void setImunisasi(String imunisasi) {
        this.imunisasi = imunisasi;
    }

    public String getVitamin() {
        return vitamin;
    }

    public void setVitamin(String vitamin) {
        this.vitamin = vitamin;
    }

    public String getStatus_gizi() {
        return status_gizi;
    }

    public void setStatus_gizi(String status_gizi) {
        this.status_gizi = status_gizi;
    }

    public String getPenyuluhan() {
        return penyuluhan;
    }

    public void setPenyuluhan(String penyuluhan) {
        this.penyuluhan = penyuluhan;
    }

    public String getId_anak() {
        return id_anak;
    }

    public void setId_anak(String id_anak) {
        this.id_anak = id_anak;
    }
}
