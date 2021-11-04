package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.AnakResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddAnakActivity extends AppCompatActivity {

    Spinner spinnerJK, spinnerDarah;
    EditText etNamaAnak, etAnakKe, etAkte, etNIKAnak, etTanggalLahir, etTempatLahir;
    Button btnSimpan;
    String NamaAnak, AnakKe, Akte, NIKAnak, TglLahir, TempatLahir, JenisKelamin, Darah, UserID;
    ApiInterface apiInterface;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_anak);

        getSupportActionBar().setTitle("Tambah Data Anak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(AddAnakActivity.this);
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);

        etNamaAnak = findViewById(R.id.etSimpanNamaAnak);
        etAnakKe = findViewById(R.id.etSimpanAnakKe);
        etAkte = findViewById(R.id.etSimpanAkte);
        etNIKAnak = findViewById(R.id.etSimpanNIK);
        etTempatLahir = findViewById(R.id.etSimpanTempatLahir);
        etTanggalLahir = findViewById(R.id.etSimpanTglLahir);
        btnSimpan = findViewById(R.id.btnSimpan);
        spinnerJK = findViewById(R.id.spinnerJK);
        spinnerDarah = findViewById(R.id.spinnerDarah);

        ArrayAdapter<CharSequence> adapterJK =  ArrayAdapter.createFromResource(this, R.array.jenis_kelamin, android.R.layout.simple_spinner_item);
        adapterJK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJK.setAdapter(adapterJK);
        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                JenisKelamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterDarah = ArrayAdapter.createFromResource(this, R.array.Darah, android.R.layout.simple_spinner_item);
        adapterDarah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDarah.setAdapter(adapterDarah);
        spinnerDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Darah = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddAnakActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = year+"-"+month+"-"+day;
                        etTanggalLahir.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NamaAnak = etNamaAnak.getText().toString();
                AnakKe = etAnakKe.getText().toString();
                Akte = etAkte.getText().toString();
                NIKAnak = etNIKAnak.getText().toString();
                TempatLahir = etTempatLahir.getText().toString();
                TglLahir = etTanggalLahir.getText().toString();

                if(NamaAnak.trim().equals("")) {
                    etNamaAnak.setError("Nama Harus Di isi!");
                }
                else if (AnakKe.trim().equals("")) {
                    etAnakKe.setError("Anak ke Harus Di isi!");
                }
                else if (Akte.trim().equals("")) {
                    etAkte.setError("No Akte Harus Di isi!");
                }
                else if (NIKAnak.trim().equals("")) {
                    etNIKAnak.setError("NIK Harus Di isi!");
                }
                else if (TempatLahir.trim().equals("")) {
                    etTempatLahir.setError("Tempat Lahir Harus Di isi!");
                }
                else if (TglLahir.trim().equals("")) {
                    etTanggalLahir.setError("Tanggal Lahir Harus Di isi!");
                }
                else {
                    simpanDataAnak();
                }
            }
        });
    }

    public void simpanDataAnak() {
        final ProgressDialog progressBar = ProgressDialog.show(AddAnakActivity.this, "Tambah Data Anak", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<AnakResponse> simpanData = apiInterface.insertResponse(NamaAnak, AnakKe, Akte, NIKAnak, TempatLahir, TglLahir, JenisKelamin, Darah, UserID);
                simpanData.enqueue(new Callback<AnakResponse>() {
                    @Override
                    public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()){
                            Toast.makeText(AddAnakActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                            finish();
                        } else {
                            Toast.makeText(AddAnakActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnakResponse> call, Throwable t) {
                        Toast.makeText(AddAnakActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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