package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.RiwayatResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddRiwayatActivity extends AppCompatActivity {

    Spinner spinnerImunisasi, spinnerVitamin, spinnerGizi;
    EditText etTanggal, etTinggi, etBerat, etPenyuluhan;
    Button btnSimpan;
    String Tanggal, Tinggi, Berat, Imunisasi, Vitamin, Gizi, Penyuluhan, AnakID;
    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_riwayat);

        getSupportActionBar().setTitle("Tambah Data Pemeriksaan");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        AnakID = intent.getStringExtra("xAnakID");

        etTanggal = findViewById(R.id.etSimpanTanggal);
        etTinggi = findViewById(R.id.etSimpanTinggi);
        etBerat = findViewById(R.id.etSimpanBerat);
        etPenyuluhan = findViewById(R.id.etSimpanPenyuluhan);
        spinnerImunisasi = findViewById(R.id.spinnerImunisasi);
        spinnerVitamin = findViewById(R.id.spinnerVitamin);
        spinnerGizi = findViewById(R.id.spinnerGizi);
        btnSimpan = findViewById(R.id.btnSimpanPemeriksaan);

        ArrayAdapter<CharSequence> adapterImunisasi = ArrayAdapter.createFromResource(this, R.array.imunisasi, android.R.layout.simple_spinner_item);
        adapterImunisasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImunisasi.setAdapter(adapterImunisasi);
        spinnerImunisasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Imunisasi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterVitamin = ArrayAdapter.createFromResource(this, R.array.vitamin, android.R.layout.simple_spinner_item);
        adapterVitamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVitamin.setAdapter(adapterVitamin);
        spinnerVitamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Vitamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterGizi = ArrayAdapter.createFromResource(this, R.array.gizi, android.R.layout.simple_spinner_item);
        adapterGizi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGizi.setAdapter(adapterGizi);
        spinnerGizi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Gizi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTanggal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        AddRiwayatActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        month = month+1;
                        String date = year+"-"+month+"-"+day;
                        etTanggal.setText(date);
                    }
                },year,month,day);
                datePickerDialog.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Tanggal = etTanggal.getText().toString();
                Tinggi = etTinggi.getText().toString();
                Berat = etBerat.getText().toString();
                Penyuluhan = etPenyuluhan.getText().toString();

                if(Tanggal.trim().equals("")) {
                    etTanggal.setError("Harus Di isi!");
                }
                else if (Tinggi.trim().equals("")) {
                    etTinggi.setError("Harus Di isi!");
                }
                else if (Berat.trim().equals("")) {
                    etBerat.setError("Harus Di isi!");
                }
                else if (Penyuluhan.trim().equals("")) {
                    etPenyuluhan.setError("Harus Di isi!");
                }
                else {
                    simpanDataPemeriksaan();
                }
            }
        });
    }

    public void simpanDataPemeriksaan() {
        final ProgressDialog progressBar = ProgressDialog.show(AddRiwayatActivity.this, "Tambah Data Pemeriksaan", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RiwayatResponse> simpanData = apiInterface.pemeriksaanResponse(Tanggal, Tinggi, Berat, Imunisasi, Vitamin, Gizi, Penyuluhan, AnakID);
                simpanData.enqueue(new Callback<RiwayatResponse>() {
                    @Override
                    public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                        Toast.makeText(AddRiwayatActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                        Toast.makeText(AddRiwayatActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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