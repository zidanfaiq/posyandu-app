package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.LoginData;
import com.zidanfaiq.posyandu.model.LoginResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateProfilActivity extends AppCompatActivity {

    TextView etNamaIbu, etNIK, etTempatLahir, etTglLahir, etAlamat, etPosyandu, etTelepon, etEmail;
    SessionManager sessionManager;
    String UserID, NamaIbu, NIK, TempatLahir, TglLahir, Alamat, Posyandu, Telepon, Email;
    Button btnUpdate;
    String yNama, yNIK, yTempat, yTgl, yAlamat, yPosyandu, yTelepon, yEmail;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profil);

        getSupportActionBar().setTitle("Profil");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(UpdateProfilActivity.this);
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);
        NamaIbu = sessionManager.getUserDetail().get(SessionManager.NAMA_IBU);
        NIK = sessionManager.getUserDetail().get(SessionManager.NIK_IBU);
        TempatLahir = sessionManager.getUserDetail().get(SessionManager.TEMPAT_LAHIR);
        TglLahir = sessionManager.getUserDetail().get(SessionManager.TGL_LAHIR);
        Alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
        Posyandu = sessionManager.getUserDetail().get(SessionManager.POSYANDU);
        Telepon = sessionManager.getUserDetail().get(SessionManager.TELEPON);
        Email = sessionManager.getUserDetail().get(SessionManager.EMAIL);

        etNamaIbu = findViewById(R.id.etUbahNamaIbu);
        etNIK = findViewById(R.id.etUbahNIK);
        etTempatLahir = findViewById(R.id.etUbahTempatLahir);
        etTglLahir = findViewById(R.id.etUbahTglLahir);
        etAlamat = findViewById(R.id.etUbahAlamat);
        etPosyandu = findViewById(R.id.etUbahPosyandu);
        etTelepon = findViewById(R.id.etUbahTelepon);
        etEmail = findViewById(R.id.etUbahEmail);
        btnUpdate = findViewById(R.id.btnUbahProfil);

        etNamaIbu.setText(NamaIbu);
        etNIK.setText(NIK);
        etTempatLahir.setText(TempatLahir);
        etTglLahir.setText(TglLahir);
        etAlamat.setText(Alamat);
        etPosyandu.setText(Posyandu);
        etTelepon.setText(Telepon);
        etEmail.setText(Email);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateProfilActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = year+"-"+month+"-"+day;
                        etTglLahir.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yNama = etNamaIbu.getText().toString();
                yNIK = etNIK.getText().toString();
                yTempat = etTempatLahir.getText().toString();
                yTgl = etTglLahir.getText().toString();
                yAlamat = etAlamat.getText().toString();
                yPosyandu = etPosyandu.getText().toString();
                yTelepon = etTelepon.getText().toString();
                yEmail = etEmail.getText().toString();

                if(yNama.trim().equals("")) {
                    etNamaIbu.setError("Nama Harus Di isi!");
                }
                else if (yNIK.trim().equals("")) {
                    etNIK.setError("NIK Harus Di isi!");
                }
                else if (yTempat.trim().equals("")) {
                    etTempatLahir.setError("Tempat Lahir Harus Di isi!");
                }
                else if (yTgl.trim().equals("")) {
                    etTglLahir.setError("Tanggal Lahir Harus Di isi!");
                }
                else if (yAlamat.trim().equals("")) {
                    etAlamat.setError("Alamat Harus Di isi!");
                }
                else if (yPosyandu.trim().equals("")) {
                    etPosyandu.setError("Posyandu Harus Di isi!");
                }
                else if (yTelepon.trim().equals("")) {
                    etTelepon.setError("Nomor Telepon Harus Di isi!");
                }
                else if (yEmail.trim().equals("")) {
                    etEmail.setError("Email Harus Di isi!");
                }
                else if (!isEmailValid(yEmail)) {
                    etEmail.setError("Format Email Salah!");
                }
                else {
                    updateProfil();
                }
            }
        });
    }

    boolean isEmailValid(CharSequence Email) {
        return Patterns.EMAIL_ADDRESS.matcher(Email).matches();
    }

    public void updateProfil() {
        final ProgressDialog progressBar = ProgressDialog.show(UpdateProfilActivity.this, "Ubah Profil", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
             @Override
             public void run() {
                 apiInterface = ApiClient.getClient().create(ApiInterface.class);
                 Call<LoginResponse> updateProfil = apiInterface.profilResponse(UserID, yNama, yNIK, yTempat, yTgl, yAlamat, yPosyandu, yTelepon, yEmail);
                 updateProfil.enqueue(new Callback<LoginResponse>() {
                     @Override
                     public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                         if(response.body() != null && response.isSuccessful() && response.body().isStatus()){

                             // Ini untuk menyimpan sesi
                             sessionManager = new SessionManager(UpdateProfilActivity.this);
                             LoginData loginData = response.body().getLoginData();
                             sessionManager.createLoginSession(loginData);

                             Toast.makeText(UpdateProfilActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                             progressBar.cancel();
                             finish();

                         } else {
                             Toast.makeText(UpdateProfilActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                             progressBar.cancel();
                         }
                     }

                     @Override
                     public void onFailure(Call<LoginResponse> call, Throwable t) {
                         Toast.makeText(UpdateProfilActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                         progressBar.cancel();
                     }
                 });
             }
         }).start();
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}