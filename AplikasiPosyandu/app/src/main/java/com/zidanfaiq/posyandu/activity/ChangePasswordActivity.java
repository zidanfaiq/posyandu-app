package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.LoginResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordActivity extends AppCompatActivity {

    EditText etPassword, etConfirmPassword;
    String UserID, Password, ConfirmPassword;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    Button btnUbahPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        getSupportActionBar().setTitle("Password");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(ChangePasswordActivity.this);
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);

        etPassword = findViewById(R.id.etPasswordBaru);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        btnUbahPassword = findViewById(R.id.btnUbahPassword);

        btnUbahPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = etPassword.getText().toString();
                ConfirmPassword = etConfirmPassword.getText().toString();

                if(Password.trim().equals("")) {
                    Toast.makeText(ChangePasswordActivity.this, "Password tidak boleh kosong", Toast.LENGTH_SHORT).show();
                }
                else if (Password.length() < 8) {
                    Toast.makeText(ChangePasswordActivity.this, "Password minimal 8 karakter", Toast.LENGTH_SHORT).show();
                }
                else if (!Password.equals(ConfirmPassword)) {
                    Toast.makeText(ChangePasswordActivity.this, "Konfirmasi password tidak cocok", Toast.LENGTH_SHORT).show();
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(ChangePasswordActivity.this);
                    builder.setTitle("Ubah Password");
                    builder.setMessage("Apakah anda yakin ingin mengubah password?");
                    builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ubahPassword();
                            dialog.dismiss();
                        }
                    });
                    builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    AlertDialog alert = builder.create();
                    alert.show();
                }
            }
        });
    }

    public void ubahPassword() {
        final ProgressDialog progressBar = ProgressDialog.show(ChangePasswordActivity.this, "Ubah Password", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginResponse> ubahPassword = apiInterface.passwordResponse(UserID, ConfirmPassword);
                ubahPassword.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                            Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                            finish();
                        } else {
                            Toast.makeText(ChangePasswordActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(ChangePasswordActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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