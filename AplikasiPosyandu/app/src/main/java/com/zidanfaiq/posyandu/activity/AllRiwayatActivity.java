package com.zidanfaiq.posyandu.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.adapter.RiwayatAdapter;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.RiwayatData;
import com.zidanfaiq.posyandu.model.RiwayatResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AllRiwayatActivity extends AppCompatActivity {

    TextView tvNull, tvNull2;
    ImageView ivNull;
    RecyclerView rvData;
    RecyclerView.Adapter riwayatAdapter;
    RecyclerView.LayoutManager lmData;
    List<RiwayatData> listRiwayat = new ArrayList<>();

    SessionManager sessionManager;
    ApiInterface apiInterface;
    SwipeRefreshLayout srlAllRiwayat;
    ProgressBar pbAllRiwayat;
    String UserID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_riwayat);

        getSupportActionBar().setTitle("Riwayat Anak");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessionManager = new SessionManager(AllRiwayatActivity.this);
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);

        rvData = findViewById(R.id.rvRiwayat);
        lmData = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlAllRiwayat = findViewById(R.id.srlAllRiwayat);
        pbAllRiwayat = findViewById(R.id.pbAllRiwayat);
        tvNull = findViewById(R.id.tvNull);
        tvNull2 = findViewById(R.id.tvNull2);
        ivNull = findViewById(R.id.ivNull);

        srlAllRiwayat.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlAllRiwayat.setRefreshing(true);
                getDataPemeriksaan();
                srlAllRiwayat.setRefreshing(false);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        pbAllRiwayat.setVisibility(View.VISIBLE);
        tvNull.setVisibility(View.GONE);
        tvNull2.setVisibility(View.GONE);
        ivNull.setVisibility(View.GONE);
        rvData.setVisibility(View.GONE);
        getDataPemeriksaan();
    }

    public void  getDataPemeriksaan() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RiwayatResponse> tampilData = apiInterface.allRiwayatResponse(UserID);
        tampilData.enqueue(new Callback<RiwayatResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                listRiwayat = response.body().getRiwayatData();
                riwayatAdapter = new RiwayatAdapter(AllRiwayatActivity.this, listRiwayat);
                rvData.setAdapter(riwayatAdapter);
                rvData.setLayoutManager(lmData);
                riwayatAdapter.notifyDataSetChanged();

                tvNull.setText("Riwayat Anak Masih Kosong");
                if (listRiwayat == null) {
                    rvData.setVisibility(View.GONE);
                    tvNull.setVisibility(View.VISIBLE);
                    tvNull2.setVisibility(View.VISIBLE);
                    ivNull.setVisibility(View.VISIBLE);
                }
                else {
                    rvData.setVisibility(View.VISIBLE);
                    tvNull.setVisibility(View.GONE);
                    tvNull2.setVisibility(View.GONE);
                    ivNull.setVisibility(View.GONE);
                }

                pbAllRiwayat.setVisibility(View.INVISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                tvNull.setText("Gagal memuat riwayat anak");
                rvData.setVisibility(View.GONE);
                tvNull.setVisibility(View.VISIBLE);
                tvNull2.setVisibility(View.GONE);
                ivNull.setVisibility(View.GONE);
                pbAllRiwayat.setVisibility(View.INVISIBLE);
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}