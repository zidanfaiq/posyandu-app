package com.zidanfaiq.posyandu.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.activity.AllRiwayatActivity;
import com.zidanfaiq.posyandu.activity.RiwayatActivity;
import com.zidanfaiq.posyandu.activity.UpdateRiwayatActivity;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.RiwayatData;
import com.zidanfaiq.posyandu.model.RiwayatResponse;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.HolderData> {
    private Context ctx;
    private List<RiwayatData> listRiwayat;
    List<RiwayatData> listData;
    String IDPemeriksaan;
    ApiInterface apiInterface;

    public RiwayatAdapter(Context ctx, List<RiwayatData> listRiwayat) {
        this.ctx = ctx;
        this.listRiwayat = listRiwayat;
    }

    @NonNull
    @Override
    public RiwayatAdapter.HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_riwayat, parent,false);
        RiwayatAdapter.HolderData holder = new RiwayatAdapter.HolderData(layout);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RiwayatAdapter.HolderData holder, int position) {

        RiwayatData riwayatData = listRiwayat.get(position);

        Random r = new Random();
        int red = r.nextInt(255 + 1);
        int green = r.nextInt(255 + 1);
        int blue = r.nextInt(255 + 1);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.rgb(red, green, blue));

        holder.rlNamaAnak.setBackground(drawable);

        holder.tvNameAnak.setText(riwayatData.getNama_anak().toUpperCase().substring(0, 2));
        holder.tvRNamaAnak.setText(riwayatData.getNama_anak().toUpperCase());
        holder.tvPemeriksaanID.setText(riwayatData.getId_pemeriksaan());
        holder.tvTanggal.setText(riwayatData.getTgl_pemeriksaan());
        holder.tvTinggi.setText("TB : " + riwayatData.getTinggi_badan() + " CM");
        holder.tvBerat.setText("BB : " + riwayatData.getBerat_badan() + " KG");
        holder.tvImunisasi.setText(riwayatData.getImunisasi());
        holder.tvVitamin.setText(riwayatData.getVitamin());
        holder.tvGizi.setText(riwayatData.getStatus_gizi());
        holder.tvPenyuluhan.setText(riwayatData.getPenyuluhan());
        holder.tvIDAnak.setText(riwayatData.getId_anak());
        holder.imgOptionMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDPemeriksaan = holder.tvPemeriksaanID.getText().toString();
                PopupMenu popupMenu = new PopupMenu(ctx, holder.imgOptionMenu);
                popupMenu.inflate(R.menu.menu_pemeriksaan);
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.actionView:
                                getDataPemeriksaanID();
                                break;
                            case R.id.actionDelete:
                                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                                builder.setMessage("Apakah anda yakin ingin menghapus data ini?");
                                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        deleteDataPemeriksaan();
                                        Handler hand = new Handler();
                                        hand.postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                if(ctx instanceof RiwayatActivity) {
                                                    ((RiwayatActivity) ctx).getDataPemeriksaan();
                                                }
                                                else {
                                                    ((AllRiwayatActivity) ctx).getDataPemeriksaan();
                                                }
                                            }
                                        }, 500);
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
                                break;
                            default:
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return null!=listRiwayat?listRiwayat.size():0;
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvNameAnak, tvRNamaAnak;
        TextView tvPemeriksaanID, tvTanggal, tvTinggi, tvBerat, tvImunisasi, tvVitamin, tvGizi, tvPenyuluhan, tvIDAnak;
        ImageView imgOptionMenu;
        RelativeLayout rlNamaAnak;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            rlNamaAnak = itemView.findViewById(R.id.rlNamaAnak);
            tvNameAnak = itemView.findViewById(R.id.tvNameAnak);
            tvRNamaAnak = itemView.findViewById(R.id.tvRNamaAnak);
            tvPemeriksaanID = itemView.findViewById(R.id.tvPemeriksaanID);
            tvTanggal = itemView.findViewById(R.id.tvTanggal);
            tvTinggi = itemView.findViewById(R.id.tvTinggiBadan);
            tvBerat = itemView.findViewById(R.id.tvBeratBadan);
            tvImunisasi = itemView.findViewById(R.id.tvImunisasi);
            tvVitamin = itemView.findViewById(R.id.tvVitamin);
            tvGizi = itemView.findViewById(R.id.tvStatusGizi);
            tvPenyuluhan = itemView.findViewById(R.id.tvPenyuluhan);
            tvIDAnak = itemView.findViewById(R.id.tvIDAnak);
            imgOptionMenu = itemView.findViewById(R.id.imgOptionMenu);
        }
    }

    public void deleteDataPemeriksaan() {
        final ProgressDialog progressBar = ProgressDialog.show(ctx, "Hapus Data", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<RiwayatResponse> deleteData = apiInterface.hapusResponse(IDPemeriksaan);
                deleteData.enqueue(new Callback<RiwayatResponse>() {
                    @Override
                    public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                        Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }

                    @Override
                    public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                        Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }
                });
            }
        }).start();
    }

    public void getDataPemeriksaanID() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<RiwayatResponse> updateData = apiInterface.getUbahResponse(IDPemeriksaan);
        updateData.enqueue(new Callback<RiwayatResponse>() {
            @Override
            public void onResponse(Call<RiwayatResponse> call, Response<RiwayatResponse> response) {
                listData = response.body().getRiwayatData();

                String id_pemeriksaan = listData.get(0).getId_pemeriksaan();
                String tgl_pemeriksaan = listData.get(0).getTgl_pemeriksaan();
                String tinggi_badan = listData.get(0).getTinggi_badan();
                String berat_badan = listData.get(0).getBerat_badan();
                String imunisasi = listData.get(0).getImunisasi();
                String vitamin = listData.get(0).getVitamin();
                String status_gizi = listData.get(0).getStatus_gizi();
                String penyuluhan = listData.get(0).getPenyuluhan();
                String id_anak = listData.get(0).getId_anak();

                Intent intent = new Intent(ctx, UpdateRiwayatActivity.class);
                intent.putExtra("xPemeriksaanID", id_pemeriksaan);
                intent.putExtra("xTanggal", tgl_pemeriksaan);
                intent.putExtra("xTinggi", tinggi_badan);
                intent.putExtra("xBerat", berat_badan);
                intent.putExtra("xImunisasi", imunisasi);
                intent.putExtra("xVitamin", vitamin);
                intent.putExtra("xStatusGizi", status_gizi);
                intent.putExtra("xPenyuluhan", penyuluhan);
                intent.putExtra("xAnakID", id_anak);
                ctx.startActivity(intent);
            }

            @Override
            public void onFailure(Call<RiwayatResponse> call, Throwable t) {
                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}