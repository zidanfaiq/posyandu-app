package com.zidanfaiq.posyandu.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.activity.AllRiwayatActivity;
import com.zidanfaiq.posyandu.activity.ChangePasswordActivity;
import com.zidanfaiq.posyandu.activity.FeedbackActivity;
import com.zidanfaiq.posyandu.activity.LoginActivity;
import com.zidanfaiq.posyandu.activity.UpdateProfilActivity;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.LoginData;
import com.zidanfaiq.posyandu.model.LoginResponse;
import com.zidanfaiq.posyandu.sharedpreference.SessionManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AkunFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_akun, container, false);
    }

    RelativeLayout rlProfilNama;
    SessionManager sessionManager;
    TextView tvProfil, tvNamaLengkap, tvEmail;
    String UserID, NamaIbu, Email;
    RelativeLayout rlProfil, rlPassword, rlRiwayat, rlFeedback, rlKeluar;
    ApiInterface apiInterface;
    ProgressBar pbAkun;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sessionManager = new SessionManager(getActivity());

        UserID = sessionManager.getUserDetail().get(SessionManager.USER_ID);

        rlProfilNama = getView().findViewById(R.id.rlProfilNama);
        tvProfil = getView().findViewById(R.id.tvProfil);
        tvNamaLengkap = getView().findViewById(R.id.tvProfilNama);
        tvEmail = getView().findViewById(R.id.tvProfilEmail);
        rlProfil = getView().findViewById(R.id.rlProfil);
        rlPassword = getView().findViewById(R.id.rlPassword);
        rlRiwayat = getView().findViewById(R.id.rlRiwayat);
        rlFeedback = getView().findViewById(R.id.rlFeedback);
        rlKeluar = getView().findViewById(R.id.rlExit);
        pbAkun = getView().findViewById(R.id.pbAkun);

        rlProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UpdateProfilActivity.class));
            }
        });

        rlRiwayat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AllRiwayatActivity.class));
            }
        });

        rlPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        rlFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), FeedbackActivity.class));
            }
        });

        rlKeluar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Keluar");
                builder.setMessage("Apakah anda yakin ingin keluar akun?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        moveToLogin();
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
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        rlProfilNama.setVisibility(View.INVISIBLE);
        tvNamaLengkap.setVisibility(View.INVISIBLE);
        tvEmail.setVisibility(View.INVISIBLE);
        pbAkun.setVisibility(View.VISIBLE);
        getAkun();
    }

    public void getAkun () {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<LoginResponse> tampilAkun = apiInterface.akunResponse(UserID);
        tampilAkun.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                LoginData akunData = response.body().getLoginData();
                NamaIbu = akunData.getNamaIbu();
                Email = akunData.getEmail();

                GradientDrawable drawable = new GradientDrawable();
                drawable.setShape(GradientDrawable.OVAL);
                drawable.setColor(Color.WHITE);

                rlProfilNama.setVisibility(View.VISIBLE);
                tvNamaLengkap.setVisibility(View.VISIBLE);
                tvEmail.setVisibility(View.VISIBLE);

                rlProfilNama.setBackground(drawable);
                tvProfil.setText(NamaIbu);
                if (tvProfil.length() >= 2) {
                    tvProfil.setText(NamaIbu.toUpperCase().substring(0, 2));
                } else {
                    tvProfil.setText(NamaIbu.toUpperCase().substring(0, 1));
                }

                tvNamaLengkap.setText(NamaIbu.toUpperCase());
                tvEmail.setText(Email);

                pbAkun.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                rlProfilNama.setVisibility(View.INVISIBLE);
                tvNamaLengkap.setVisibility(View.INVISIBLE);
                tvEmail.setVisibility(View.INVISIBLE);
                pbAkun.setVisibility(View.VISIBLE);
            }
        });

    }

    private void moveToLogin() {
        sessionManager.logoutSession();
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        getActivity().finish();
    }
}