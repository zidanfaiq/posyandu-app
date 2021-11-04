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

public class UpdateRiwayatActivity extends AppCompatActivity {

    Spinner spinnerImunisasi, spinnerVitamin, spinnerGizi;
    EditText etTanggal, etTinggi, etBerat, etPenyuluhan;
    String PemeriksaanID, Tanggal, Tinggi, Berat, Imunisasi, Vitamin, Gizi, Penyuluhan, AnakID;
    Button btnUbah;
    ApiInterface apiInterface;
    String yTanggal, yTinggi, yBerat, yImunisasi, yVitamin, yGizi, yPenyuluhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_riwayat);

        getSupportActionBar().setTitle("Riwayat Anak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        PemeriksaanID = intent.getStringExtra("xPemeriksaanID");
        Tanggal = intent.getStringExtra("xTanggal");
        Tinggi = intent.getStringExtra("xTinggi");
        Berat = intent.getStringExtra("xBerat");
        Imunisasi = intent.getStringExtra("xImunisasi");
        Vitamin = intent.getStringExtra("xVitamin");
        Gizi = intent.getStringExtra("xStatusGizi");
        Penyuluhan = intent.getStringExtra("xPenyuluhan");
        AnakID = intent.getStringExtra("xAnakID");

        etTanggal = findViewById(R.id.etUbahTanggal);
        etTinggi = findViewById(R.id.etUbahTinggi);
        etBerat = findViewById(R.id.etUbahBerat);
        etPenyuluhan = findViewById(R.id.etUbahPenyuluhan);
        btnUbah = findViewById(R.id.btnUbahPemeriksaan);
        spinnerImunisasi = findViewById(R.id.spinnerUbahImunisasi);
        spinnerVitamin = findViewById(R.id.spinnerUbahVitamin);
        spinnerGizi = findViewById(R.id.spinnerUbahGizi);

        etTanggal.setText(Tanggal);
        etTinggi.setText(Tinggi);
        etBerat.setText(Berat);
        etPenyuluhan.setText(Penyuluhan);

        ArrayAdapter<CharSequence> adapterImunisasi = ArrayAdapter.createFromResource(this, R.array.imunisasi, android.R.layout.simple_spinner_item);
        adapterImunisasi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerImunisasi.setAdapter(adapterImunisasi);
        if (Imunisasi != null) {
            int spinnerPosition = adapterImunisasi.getPosition(Imunisasi);
            spinnerImunisasi.setSelection(spinnerPosition);
        }
        spinnerImunisasi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yImunisasi = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterVitamin = ArrayAdapter.createFromResource(this, R.array.vitamin, android.R.layout.simple_spinner_item);
        adapterVitamin.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerVitamin.setAdapter(adapterVitamin);
        if (Vitamin != null) {
            int spinnerPosition = adapterVitamin.getPosition(Vitamin);
            spinnerVitamin.setSelection(spinnerPosition);
        }
        spinnerVitamin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yVitamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterGizi = ArrayAdapter.createFromResource(this, R.array.gizi, android.R.layout.simple_spinner_item);
        adapterGizi.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGizi.setAdapter(adapterGizi);
        if (Gizi != null) {
            int spinnerPosition = adapterGizi.getPosition(Gizi);
            spinnerGizi.setSelection(spinnerPosition);
        }
        spinnerGizi.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yGizi = parent.getItemAtPosition(position).toString();
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
                        UpdateRiwayatActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        btnUbah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                yTanggal = etTanggal.getText().toString();
                yTinggi = etTinggi.getText().toString();
                yBerat = etBerat.getText().toString();
                yPenyuluhan = etPenyuluhan.getText().toString();

                if(yTanggal.trim().equals("")) {
                    etTanggal.setError("Harus Di isi!");
                }
                else if (yTinggi.trim().equals("")) {
                    etTinggi.setError("Harus Di isi!");
                }
                else if (yBerat.trim().equals("")) {
                    etBerat.setError("Harus Di isi!");
                } else {
                    ubahDataPemeriksaan();
                }
            }
        });
    }

    public void ubahDataPemeriksaan() {
        final ProgressDialog progressBar = ProgressDialog.show(UpdateRiwayatActivity.this, "Update Data Pemeriksaan", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RiwayatResponse> ubahData = apiInterface.ubahResponse(PemeriksaanID, yTanggal, yTinggi, yBerat, yImunisasi, yVitamin, yGizi, yPenyuluhan);
                ubahData.enqueue(new Callback<RiwayatResponse>() {
                    @Override
                    public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                            Toast.makeText(UpdateRiwayatActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                            finish();
                        }
                        else {
                            Toast.makeText(UpdateRiwayatActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                        Toast.makeText(UpdateRiwayatActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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