package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.LoginResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackActivity extends AppCompatActivity {

    EditText etKeterangan;
    Spinner spinnerKategori;
    String UserID, Kategori, Keterangan;
    ApiInterface apiInterface;
    SessionManager sessionManager;
    Button btnKirim;
    RelativeLayout rlKirim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        getSupportActionBar().setTitle("Feedback");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(this);
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);

        etKeterangan = findViewById(R.id.etKeterangan);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnKirim = findViewById(R.id.btnKirim);
        rlKirim = findViewById(R.id.rlKirim);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.kategori, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerKategori.setAdapter(adapter);
        spinnerKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Kategori = parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        rlKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Keterangan = etKeterangan.getText().toString();
                if (Keterangan.trim().equals("")) {
                    Toast.makeText(FeedbackActivity.this, "Keterangan harap diisi", Toast.LENGTH_LONG).show();
                } else {
                    kirimFeedback();
                }
            }
        });

    }

    public void kirimFeedback() {
        final ProgressDialog progressBar = ProgressDialog.show(FeedbackActivity.this, "Feedback", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<LoginResponse> kirimData = apiInterface.feedbackResponse(Kategori, Keterangan, UserID);
                kirimData.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        Toast.makeText(FeedbackActivity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        Toast.makeText(FeedbackActivity.this, "Gagal terhubung ke server", Toast.LENGTH_SHORT).show();
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