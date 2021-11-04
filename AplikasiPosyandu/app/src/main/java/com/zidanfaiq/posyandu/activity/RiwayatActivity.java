package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.adapter.RiwayatAdapter;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.RiwayatData;
import com.zidanfaiq.posyandu.model.RiwayatResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatActivity extends AppCompatActivity {

    RecyclerView rvData;
    RecyclerView.Adapter riwayatAdapter;
    RecyclerView.LayoutManager lmData;
    List<RiwayatData> listRiwayat = new ArrayList<>();

    FloatingActionButton fabAddRiwayat;
    ApiInterface apiInterface;
    SwipeRefreshLayout srlRiwayat;
    ProgressBar pbRiwayat;
    String AnakID;
    TextView tvKosong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_riwayat);

        getSupportActionBar().setTitle("Riwayat Anak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        AnakID = intent.getStringExtra("xAnakID");

        rvData = findViewById(R.id.rvDataRiwayat);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlRiwayat = findViewById(R.id.srlRiwayat);
        pbRiwayat = findViewById(R.id.pbRiwayat);
        fabAddRiwayat = findViewById(R.id.fabAddRiwayat);
        tvKosong = findViewById(R.id.tvKosong);

        srlRiwayat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlRiwayat.setRefreshing(true);
                getDataPemeriksaan();
                srlRiwayat.setRefreshing(false);
            }
        });

        fabAddRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(RiwayatActivity.this, AddRiwayatActivity.class);
                myIntent.putExtra("xAnakID", AnakID);
                startActivity(myIntent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pbRiwayat.setVisibility(View.VISIBLE);
        tvKosong.setVisibility(View.GONE);
        rvData.setVisibility(View.GONE);
        getDataPemeriksaan();
    }

    public void getDataPemeriksaan(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RiwayatResponse> tampilData = apiInterface.riwayatResponse(AnakID);
        tampilData.enqueue(new Callback<RiwayatResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {

                listRiwayat = response.body().getRiwayatData();
                riwayatAdapter = new RiwayatAdapter(RiwayatActivity.this, listRiwayat);
                rvData.setAdapter(riwayatAdapter);
                rvData.setLayoutManager(lmData);
                riwayatAdapter.notifyDataSetChanged();

                tvKosong.setText("Belum ada riwayat anak");
                if (listRiwayat == null) {
                    rvData.setVisibility(View.GONE);
                    tvKosong.setVisibility(View.VISIBLE);
                }
                else {
                    rvData.setVisibility(View.VISIBLE);
                    tvKosong.setVisibility(View.GONE);
                }

                pbRiwayat.setVisibility(View.INVISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                tvKosong.setText("Gagal memuat riwayat anak");
                rvData.setVisibility(View.GONE);
                tvKosong.setVisibility(View.VISIBLE);
                pbRiwayat.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}