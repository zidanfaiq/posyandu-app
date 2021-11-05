package com.zidanfaiq.posyandu.api;

import com.zidanfaiq.posyandu.model.AnakResponse;
import com.zidanfaiq.posyandu.model.LoginResponse;
import com.zidanfaiq.posyandu.model.RegisterResponse;
import com.zidanfaiq.posyandu.model.RiwayatResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {
    @FormUrlEncoded
    @POST("login.php")
    Call<LoginResponse> loginResponse(
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("register.php")
    Call<RegisterResponse> registerResponse(
            @Field("nama_ibu") String nama_ibu,
            @Field("nik_ibu") String nik_ibu,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("alamat") String alamat,
            @Field("posyandu") String posyandu,
            @Field("telepon") String telepon,
            @Field("email") String email,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_anak.php")
    Call<AnakResponse> anakResponse(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("insert_anak.php")
    Call<AnakResponse> insertResponse(
            @Field("nama_anak") String nama_anak,
            @Field("anak_ke") String anak_ke,
            @Field("no_akte") String no_akte,
            @Field("nik_anak") String nik_anak,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("gol_darah") String gol_darah,
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("delete_anak.php")
    Call<AnakResponse> deleteResponse(
            @Field("id_anak") String id_anak
    );

    @FormUrlEncoded
    @POST("get_id_anak.php")
    Call<AnakResponse> getResponse(
            @Field("id_anak") String id_anak
    );

    @FormUrlEncoded
    @POST("update_anak.php")
    Call<AnakResponse> updateResponse(
            @Field("id_anak") String id_anak,
            @Field("nama_anak") String nama_anak,
            @Field("anak_ke") String anak_ke,
            @Field("no_akte") String no_akte,
            @Field("nik_anak") String nik_anak,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("jenis_kelamin") String jenis_kelamin,
            @Field("gol_darah") String gol_darah
    );

    @FormUrlEncoded
    @POST("get_pemeriksaan.php")
    Call<RiwayatResponse> riwayatResponse(
            @Field("id_anak") String id_anak
    );

    @FormUrlEncoded
    @POST("insert_pemeriksaan.php")
    Call<RiwayatResponse> pemeriksaanResponse(
            @Field("tgl_pemeriksaan") String tgl_pemeriksaan,
            @Field("tinggi_badan") String tinggi_badan,
            @Field("berat_badan") String berat_badan,
            @Field("imunisasi") String imunisasi,
            @Field("vitamin") String vitamin,
            @Field("status_gizi") String status_gizi,
            @Field("penyuluhan") String penyuluhan,
            @Field("id_anak") String id_anak
    );

    @FormUrlEncoded
    @POST("delete_pemeriksaan.php")
    Call<RiwayatResponse> hapusResponse(
            @Field("id_pemeriksaan") String id_pemeriksaan
    );

    @FormUrlEncoded
    @POST("get_id_pemeriksaan.php")
    Call<RiwayatResponse> getUbahResponse(
            @Field("id_pemeriksaan") String id_pemeriksaan
    );

    @FormUrlEncoded
    @POST("update_pemeriksaan.php")
    Call<RiwayatResponse> ubahResponse(
            @Field("id_pemeriksaan") String id_pemeriksaan,
            @Field("tgl_pemeriksaan") String tgl_pemeriksaan,
            @Field("tinggi_badan") String tinggi_badan,
            @Field("berat_badan") String berat_badan,
            @Field("imunisasi") String imunisasi,
            @Field("vitamin") String vitamin,
            @Field("status_gizi") String status_gizi,
            @Field("penyuluhan") String penyuluhan
    );

    @FormUrlEncoded
    @POST("update_profil.php")
    Call<LoginResponse> profilResponse(
            @Field("user_id") String user_id,
            @Field("nama_ibu") String nama_ibu,
            @Field("nik_ibu") String nik_ibu,
            @Field("tempat_lahir") String tempat_lahir,
            @Field("tgl_lahir") String tgl_lahir,
            @Field("alamat") String alamat,
            @Field("posyandu") String posyandu,
            @Field("telepon") String telepon,
            @Field("email") String email
    );

    @FormUrlEncoded
    @POST("change_password.php")
    Call<LoginResponse> passwordResponse(
            @Field("user_id") String user_id,
            @Field("password") String password
    );

    @FormUrlEncoded
    @POST("get_all_pemeriksaan.php")
    Call<RiwayatResponse> allRiwayatResponse(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("get_akun.php")
    Call<LoginResponse> akunResponse(
            @Field("user_id") String user_id
    );

    @FormUrlEncoded
    @POST("feedback.php")
    Call<LoginResponse> feedbackResponse(
            @Field("kategori") String kategori,
            @Field("pesan") String pesan,
            @Field("user_id") String user_id
    );
}