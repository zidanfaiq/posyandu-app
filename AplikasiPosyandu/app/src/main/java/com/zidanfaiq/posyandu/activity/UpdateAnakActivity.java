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
import com.zidanfaiq.posyandu.model.AnakResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAnakActivity extends AppCompatActivity {

    Spinner spinnerJK, spinnerDarah;
    EditText etNamaAnak, etAnakKe, etAkte, etNIKAnak, etTanggalLahir, etTempatLahir;
    String AnakID, NamaAnak, AnakKe, Akte, NIKAnak, TglLahir, TempatLahir, JenisKelamin, Darah, UserID;
    Button btnUpdate;
    ApiInterface apiInterface;
    String yNamaAnak, yAnakKe, yAkte, yNIKAnak, yTempatLahir, yTglLahir, yJenisKelamin, yDarah;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_anak);

        getSupportActionBar().setTitle("Biodata Anak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        AnakID = intent.getStringExtra("xAnakID");
        NamaAnak = intent.getStringExtra("xNamaAnak");
        AnakKe = intent.getStringExtra("xAnakKe");
        Akte = intent.getStringExtra("xNoAkte");
        NIKAnak = intent.getStringExtra("xNIKAnak");
        TempatLahir = intent.getStringExtra("xTempatLahir");
        TglLahir = intent.getStringExtra("xTglLahir");
        JenisKelamin = intent.getStringExtra("xJenisKelamin");
        Darah = intent.getStringExtra("xGolDarah");
        UserID = intent.getStringExtra("xUserID");

        etNamaAnak = findViewById(R.id.etUpdateNamaAnak);
        etAnakKe = findViewById(R.id.etUpdateAnakKe);
        etAkte = findViewById(R.id.etUpdateAkte);
        etNIKAnak = findViewById(R.id.etUpdateNIK);
        etTempatLahir = findViewById(R.id.etUpdateTempatLahir);
        etTanggalLahir = findViewById(R.id.etUpdateTglLahir);
        btnUpdate = findViewById(R.id.btnUpdate);
        spinnerJK = findViewById(R.id.spinnerUbahJK);
        spinnerDarah = findViewById(R.id.spinnerUbahDarah);

        etNamaAnak.setText(NamaAnak);
        etAnakKe.setText(AnakKe);
        etAkte.setText(Akte);
        etNIKAnak.setText(NIKAnak);
        etTempatLahir.setText(TempatLahir);
        etTanggalLahir.setText(TglLahir);

        Calendar calendar = Calendar.getInstance();
        final int year = calendar.get(Calendar.YEAR);
        final int month = calendar.get(Calendar.MONTH);
        final int day = calendar.get(Calendar.DAY_OF_MONTH);

        etTanggalLahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        UpdateAnakActivity.this, new DatePickerDialog.OnDateSetListener() {
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

        ArrayAdapter<CharSequence> adapterJK =  ArrayAdapter.createFromResource(this, R.array.jenis_kelamin, android.R.layout.simple_spinner_item);
        adapterJK.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerJK.setAdapter(adapterJK);
        if (JenisKelamin != null) {
            int spinnerPosition = adapterJK.getPosition(JenisKelamin);
            spinnerJK.setSelection(spinnerPosition);
        }
        spinnerJK.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yJenisKelamin = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        ArrayAdapter<CharSequence> adapterDarah = ArrayAdapter.createFromResource(this, R.array.Darah, android.R.layout.simple_spinner_item);
        adapterDarah.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerDarah.setAdapter(adapterDarah);
        if (Darah != null) {
            int spinnerPosition = adapterDarah.getPosition(Darah);
            spinnerDarah.setSelection(spinnerPosition);
        }
        spinnerDarah.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                yDarah = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yNamaAnak = etNamaAnak.getText().toString();
                yAnakKe = etAnakKe.getText().toString();
                yAkte = etAkte.getText().toString();
                yNIKAnak = etNIKAnak.getText().toString();
                yTempatLahir = etTempatLahir.getText().toString();
                yTglLahir = etTanggalLahir.getText().toString();

                if(yNamaAnak.trim().equals("")) {
                    etNamaAnak.setError("Nama Harus Di isi!");
                }
                else if (yAnakKe.trim().equals("")) {
                    etAnakKe.setError("Anak ke- Harus Di isi!");
                }
                else if (yAkte.trim().equals("")) {
                    etAkte.setError("No Akte Harus Di isi!");
                }
                else if (yNIKAnak.trim().equals("")) {
                    etNIKAnak.setError("NIK Harus Di isi!");
                }
                else if (yTempatLahir.trim().equals("")) {
                    etTempatLahir.setError("Tempat Lahir Harus Di isi!");
                }
                else if (yTglLahir.trim().equals("")) {
                    etTanggalLahir.setError("Tanggal Harus Di isi!");
                }
                else {
                    updateDataAnak();
                }
            }
        });
    }

    public void updateDataAnak() {
        final ProgressDialog progressBar = ProgressDialog.show(UpdateAnakActivity.this, "Update Data Anak", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<AnakResponse> updateData = apiInterface.updateResponse(AnakID, yNamaAnak, yAnakKe, yAkte, yNIKAnak, yTempatLahir, yTglLahir, yJenisKelamin, yDarah);
                updateData.enqueue(new Callback<AnakResponse>() {
                    @Override
                    public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {
                        if(response.body() != null && response.isSuccessful() && response.body().isStatus()) {
                            Toast.makeText(UpdateAnakActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                            finish();
                        }
                        else {
                            Toast.makeText(UpdateAnakActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.cancel();
                        }
                    }

                    @Override
                    public void onFailure(Call<AnakResponse> call, Throwable t) {
                        Toast.makeText(UpdateAnakActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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