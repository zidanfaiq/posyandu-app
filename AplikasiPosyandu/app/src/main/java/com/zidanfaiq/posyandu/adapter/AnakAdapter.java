package com.zidanfaiq.posyandu.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.zidanfaiq.posyandu.R;
import com.zidanfaiq.posyandu.activity.RiwayatActivity;
import com.zidanfaiq.posyandu.activity.UpdateAnakActivity;
import com.zidanfaiq.posyandu.api.ApiClient;
import com.zidanfaiq.posyandu.api.ApiInterface;
import com.zidanfaiq.posyandu.model.AnakResponse;
import com.zidanfaiq.posyandu.model.AnakData;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AnakAdapter extends RecyclerView.Adapter<AnakAdapter.HolderData>{
    private Context ctx;
    private List<AnakData> listAnak;
    List<AnakData> listData;
    String IDanak;
    ApiInterface apiInterface;
    DataAnak dataAnak;

    public interface DataAnak {
        void getDataAnak();
    }

    public AnakAdapter(Context ctx, List<AnakData> listAnak, DataAnak dataAnak) {
        this.ctx = ctx;
        this.listAnak = listAnak;
        this.dataAnak = dataAnak;
    }

    @NonNull
    @Override
    public HolderData onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layout = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_anak, parent,false);
        HolderData holder = new HolderData(layout);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HolderData holder, int position) {
        AnakData anakData = listAnak.get(position);

        Random r = new Random();
        int red = r.nextInt(255 + 1);
        int green = r.nextInt(255 + 1);
        int blue = r.nextInt(255 + 1);

        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.OVAL);
        drawable.setColor(Color.rgb(red, green, blue));

        holder.rlNama.setBackground(drawable);
        holder.tvNama.setText(anakData.getNama_anak().toUpperCase());
        if (holder.tvNama.length() >= 2) {
            holder.tvNama.setText(anakData.getNama_anak().toUpperCase().substring(0, 2));
        } else {
            holder.tvNama.setText(anakData.getNama_anak().toUpperCase().substring(0, 1));
        }
        holder.tvAnakID.setText(anakData.getId_anak());
        holder.tvNamaAnak.setText(anakData.getNama_anak().toUpperCase());
        holder.tvAnakKe.setText(anakData.getAnak_ke());
        holder.tvAkte.setText(anakData.getNo_akte());
        holder.tvNIKAnak.setText(anakData.getNik_anak());
        holder.tvTempatLahir.setText(anakData.getTempat_lahir());
        holder.tvTglLahir.setText(anakData.getTgl_lahir());
        holder.tvJK.setText(anakData.getJenis_kelamin().toUpperCase());
        holder.tvDarah.setText(anakData.getGol_darah());
        holder.tvUserID.setText(anakData.getUser_id());
        holder.imgOption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IDanak = holder.tvAnakID.getText().toString();
                showDialog();
            }
        });
    }

    @Override
    public int getItemCount() {
        return null!=listAnak?listAnak.size():0;
    }

    public class HolderData extends RecyclerView.ViewHolder {
        TextView tvNama, tvAnakID, tvNamaAnak, tvAnakKe, tvAkte, tvNIKAnak, tvTempatLahir, tvTglLahir, tvJK, tvDarah, tvUserID;
        RelativeLayout rlNama;
        ImageView imgOption;

        public HolderData(@NonNull View itemView) {
            super(itemView);

            tvAnakID = itemView.findViewById(R.id.tvAnakID);
            tvNama = itemView.findViewById(R.id.tvNama);
            tvNamaAnak = itemView.findViewById(R.id.tvNamaAnak);
            tvAnakKe = itemView.findViewById(R.id.tvAnakKe);
            tvAkte = itemView.findViewById(R.id.tvAkte);
            tvNIKAnak = itemView.findViewById(R.id.tvNIKAnak);
            tvTempatLahir = itemView.findViewById(R.id.tvTempatLahir);
            tvTglLahir = itemView.findViewById(R.id.tvTglLahir);
            tvJK = itemView.findViewById(R.id.tvJK);
            tvDarah = itemView.findViewById(R.id.tvDarah);
            tvUserID = itemView.findViewById(R.id.tvUserID);
            rlNama = itemView.findViewById(R.id.rlNama);
            imgOption = itemView.findViewById(R.id.imgOption);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    IDanak = tvAnakID.getText().toString();
                    showDialog();
                }
            });
        }
    }

    public void showDialog() {
        final Dialog dialog = new Dialog(ctx);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView((R.layout.bottom_layout));

        RelativeLayout Bio = dialog.findViewById(R.id.Bio);
        RelativeLayout History = dialog.findViewById(R.id.Riwayat);
        RelativeLayout Hapus = dialog.findViewById(R.id.Hapus);

        Bio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataAnakID();
                dialog.dismiss();
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAnakID();
                dialog.dismiss();
            }
        });

        Hapus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                AlertDialog.Builder builder = new AlertDialog.Builder(ctx);
                builder.setTitle("Hapus");
                builder.setMessage("Apakah anda yakin ingin menghapus data ini?");
                builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteDataAnak();
                        Handler hand = new Handler();
                        hand.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dataAnak.getDataAnak();
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
            }
        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
    }

    public void deleteDataAnak() {
        final ProgressDialog progressBar = ProgressDialog.show(ctx, "Hapus Data", "Harap tunggu sebentar", true, false);
        new Thread(new Runnable() {
            @Override
            public void run() {
                apiInterface = ApiClient.getClient().create(ApiInterface.class);
                Call<AnakResponse> deleteData = apiInterface.deleteResponse(IDanak);
                deleteData.enqueue(new Callback<AnakResponse>() {
                    @Override
                    public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {
                        Toast.makeText(ctx, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }

                    @Override
                    public void onFailure(Call<AnakResponse> call, Throwable t) {
                        Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        progressBar.cancel();
                    }
                });
            }
        }).start();
    }

    public void getDataAnakID() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AnakResponse> getData = apiInterface.getResponse(IDanak);
        getData.enqueue(new Callback<AnakResponse>() {
            @Override
            public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {
                listData = response.body().getAnakData();

                String id_anak = listData.get(0).getId_anak();
                String nama_anak = listData.get(0).getNama_anak();
                String anak_ke = listData.get(0).getAnak_ke();
                String no_akte = listData.get(0).getNo_akte();
                String nik_anak = listData.get(0).getNik_anak();
                String tempat_lahir = listData.get(0).getTempat_lahir();
                String tgl_lahir = listData.get(0).getTgl_lahir();
                String jenis_kelamin = listData.get(0).getJenis_kelamin();
                String gol_darah = listData.get(0).getGol_darah();
                String user_id = listData.get(0).getUser_id();

                Intent intent = new Intent(ctx, UpdateAnakActivity.class);
                intent.putExtra("xAnakID", id_anak);
                intent.putExtra("xNamaAnak", nama_anak);
                intent.putExtra("xAnakKe", anak_ke);
                intent.putExtra("xNoAkte", no_akte);
                intent.putExtra("xNIKAnak", nik_anak);
                intent.putExtra("xTempatLahir", tempat_lahir);
                intent.putExtra("xTglLahir", tgl_lahir);
                intent.putExtra("xJenisKelamin", jenis_kelamin);
                intent.putExtra("xGolDarah", gol_darah);
                intent.putExtra("xUserID", user_id);
                ctx.startActivity(intent);
            }

            @Override
            public void onFailure(Call<AnakResponse> call, Throwable t) {
                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void getAnakID() {
        apiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<AnakResponse> getData = apiInterface.getResponse(IDanak);
        getData.enqueue(new Callback<AnakResponse>() {
            @Override
            public void onResponse(Call<AnakResponse> call, Response<AnakResponse> response) {
                listData = response.body().getAnakData();

                String id_anak = listData.get(0).getId_anak();

                Intent intent = new Intent(ctx, RiwayatActivity.class);
                intent.putExtra("xAnakID", id_anak);
                ctx.startActivity(intent);
            }

            @Override
            public void onFailure(Call<AnakResponse> call, Throwable t) {
                Toast.makeText(ctx, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
