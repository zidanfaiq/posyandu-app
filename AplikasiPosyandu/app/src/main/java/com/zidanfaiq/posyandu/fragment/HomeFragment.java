package com.zidanfaiq.posyandu.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.activity.LoginActivity;
import com.zidanfaiq.posyandu.adapter.AnakAdapter;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.AnakData;
import com.zidanfaiq.posyandu.model.AnakResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment implements AnakAdapter.DataAnak {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    TextView tvNamaLengkap, tvPosyandu, tvAlamat, tvEmpty;
    RecyclerView rvData;
    RecyclerView.Adapter anakAdapter;
    RecyclerView.LayoutManager lmData;
    List<AnakData> listAnak = new ArrayList<>();

    ApiInterface apiInterface;
    SwipeRefreshLayout srlAnak;
    ProgressBar pbAnak;
    String UserID;
    SessionManager sessionManager;
    String NamaIbu, Alamat, Posyandu;

    @SuppressLint("SetTextI18n")
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getActivity());
        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);
        if(!sessionManager.isLoggedIn()){
            moveToLogin();
        }

        rvData = getView().findViewById(R.id.rvData);
        lmData = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        rvData.setLayoutManager(lmData);

        srlAnak = getView().findViewById(R.id.srlAnak);
        pbAnak = getView().findViewById(R.id.pbAnak);
        tvEmpty = getView().findViewById(R.id.tvEmpty);

        srlAnak.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srlAnak.setRefreshing(true);
                getDataAnak();
                srlAnak.setRefreshing(false);
            }
        });

        NamaIbu = sessionManager.getUserDetail().get(SessionManager.NAMA_IBU);
        Alamat = sessionManager.getUserDetail().get(SessionManager.ALAMAT);
        Posyandu = sessionManager.getUserDetail().get(SessionManager.POSYANDU);

        tvNamaLengkap = getView().findViewById(R.id.tvNamaLengkap);
        tvPosyandu = getView().findViewById(R.id.tvPosyandu);
        tvAlamat = getView().findViewById(R.id.tvAlamat);

        tvNamaLengkap.setText(("Hi, " + NamaIbu).toUpperCase());
        tvPosyandu.setText(("Posyandu " + Posyandu).toUpperCase());
        tvAlamat.setText(Alamat.toUpperCase());
    }

    @Override
    public void onResume() {
        pbAnak.setVisibility(View.VISIBLE);
        tvEmpty.setVisibility(View.GONE);
        rvData.setVisibility(View.GONE);
        getDataAnak();
        super.onResume();
    }

    private void moveToLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        getActivity().finish();
    }

    public void getDataAnak(){
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AnakResponse> tampilData = apiInterface.anakResponse(UserID);
        tampilData.enqueue(new Callback<AnakResponse>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {

                listAnak = response.body().getAnakData();
                anakAdapter = new AnakAdapter(getActivity(), listAnak,  HomeFragment.this::getDataAnak);
                rvData.setAdapter(anakAdapter);
                rvData.setLayoutManager(lmData);
                anakAdapter.notifyDataSetChanged();

                tvEmpty.setText("Belum ada data anak");
                if (listAnak == null) {
                    rvData.setVisibility(View.GONE);
                    tvEmpty.setVisibility(View.VISIBLE);
                }
                else {
                    rvData.setVisibility(View.VISIBLE);
                    tvEmpty.setVisibility(View.GONE);
                }

                pbAnak.setVisibility(View.INVISIBLE);
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onFailure(Call<AnakResponse> call, Throwable t) {
                tvEmpty.setText("Gagal memuat data anak");
                rvData.setVisibility(View.GONE);
                tvEmpty.setVisibility(View.VISIBLE);
                pbAnak.setVisibility(View.INVISIBLE);
            }
        });
    }
}