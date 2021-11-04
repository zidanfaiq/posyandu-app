package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.RegisterResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    EditText etNamaIbu, etNIK, etTempatLahir, etTglLahir, etAlamat, etPosyandu, etTelepon, etEmail, etPassword, etKonfirmasiPassword;
    Button btnDaftar;
    TextView tvLogin;
    String NamaIbu, NIK, TempatLahir, TglLahir, Alamat, Posyandu, Telepon, Email, Password, KonfirmasiPassword;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();

        etNamaIbu = findViewById(R.id.etDaftarNamaIbu);
        etNIK = findViewById(R.id.etDaftarNIK);
        etTempatLahir = findViewById(R.id.etDaftarTempatLahir);
        etTglLahir = findViewById(R.id.etDaftarTglLahir);
        etAlamat = findViewById(R.id.etDaftarAlamat);
        etPosyandu = findViewById(R.id.etDaftarPosyandu);
        etTelepon = findViewById(R.id.etDaftarTelepon);
        etEmail = findViewById(R.id.etDaftarEmail);
        etPassword = findViewById(R.id.etDaftarPassword);
        etKonfirmasiPassword = findViewById(R.id.etKonfirmasiPassword);

        btnDaftar = findViewById(R.id.btnDaftar);
        btnDaftar.setOnClickListener(this);

        tvLogin = findViewById(R.id.tvLogin);
        tvLogin.setOnClickListener(this);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTglLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
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
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnDaftar:
                NamaIbu = etNamaIbu.getText().toString();
                NIK = etNIK.getText().toString();
                TempatLahir = etTempatLahir.getText().toString();
                TglLahir = etTglLahir.getText().toString();
                Alamat = etAlamat.getText().toString();
                Posyandu = etPosyandu.getText().toString();
                Telepon = etTelepon.getText().toString();
                Email = etEmail.getText().toString();
                Password = etPassword.getText().toString();
                KonfirmasiPassword = etKonfirmasiPassword.getText().toString();

                if(NamaIbu.trim().equals("")) {
                    etNamaIbu.setError("Nama Harus Di isi!");
                }
                else if (NIK.trim().equals("")) {
                    etNIK.setError("NIK Harus Di isi!");
                }
                else if (TempatLahir.trim().equals("")) {
                    etTempatLahir.setError("Tempat Lahir Harus Di isi!");
                }
                else if (TglLahir.trim().equals("")) {
                    etTglLahir.setError("Tanggal Lahir Harus Di isi!");
                }
                else if (Alamat.trim().equals("")) {
                    etAlamat.setError("Alamat Harus Di isi!");
                }
                else if (Posyandu.trim().equals("")) {
                    etPosyandu.setError("Posyandu Harus Di isi!");
                }
                else if (Telepon.trim().equals("")) {
                    etTelepon.setError("Nomor Telepon Harus Di isi!");
                }
                else if (Email.trim().equals("")) {
                    etEmail.setError("Email Harus Di isi!");
                }
                else if (Password.trim().equals("")) {
                    etPassword.setError("Password Harus Di isi!");
                }
                else if (!Password.equals(KonfirmasiPassword)) {
                    Toast.makeText(RegisterActivity.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                } else {
                    register(NamaIbu, NIK, TempatLahir, TglLahir, Alamat, Posyandu, Telepon, Email, Password);
                }
                break;

            case R.id.tvLogin:
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }

    public void register(String nama_ibu, String nik_ibu, String tempat_lahir, String tgl_lahir, String alamat, String posyandu, String telepon, String email, String password) {
        final ProgressDialog progressBar = ProgressDialog.show(RegisterActivity.this, "Register", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RegisterResponse> call = apiInterface.registerResponse(nama_ibu, nik_ibu, tempat_lahir, tgl_lahir, alamat, posyandu, telepon, email, password);
                call.enqueue(new Callback<RegisterResponse>() {
                    @Override
                    public void onResponse(Call<RegisterResponse> call, Response<RegisterResponse> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(RegisterActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterResponse> call, Throwable t) {
                        Toast.makeText(RegisterActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }
                });
            }
        }).start();
    }
}