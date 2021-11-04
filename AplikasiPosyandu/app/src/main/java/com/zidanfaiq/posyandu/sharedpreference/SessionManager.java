package com.zidanfaiq.posyandu.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.zidanfaiq.posyandu.model.LoginData;

import java.util.HashMap;

public class SessionManager {
    private Context _context;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    public static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String USER_ID = "user_id";
    public static final String NAMA_IBU = "nama_ibu";
    public static final String NIK_IBU = "nik_ibu";
    public static final String TEMPAT_LAHIR = "tempat_lahir";
    public static final String TGL_LAHIR = "tgl_lahir";
    public static final String ALAMAT = "alamat";
    public static final String POSYANDU = "posyandu";
    public static final String TELEPON = "telepon";
    public static final String EMAIL = "email";

    public SessionManager (Context context){
        this._context = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = sharedPreferences.edit();
    }

    public void createLoginSession(LoginData user){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(USER_ID, user.getUserId());
        editor.putString(NAMA_IBU, user.getNamaIbu());
        editor.putString(NIK_IBU, user.getNIKIbu());
        editor.putString(TEMPAT_LAHIR, user.getTempatlahir());
        editor.putString(TGL_LAHIR, user.getTglLahir());
        editor.putString(ALAMAT, user.getAlamat());
        editor.putString(POSYANDU, user.getPosyandu());
        editor.putString(TELEPON, user.getTelepon());
        editor.putString(EMAIL, user.getEmail());
        editor.commit();
    }

    public HashMap<String,String> getUserDetail(){
        HashMap<String,String> user = new HashMap<>();
        user.put(USER_ID, sharedPreferences.getString(USER_ID,null));
        user.put(NAMA_IBU, sharedPreferences.getString(NAMA_IBU,null));
        user.put(NIK_IBU, sharedPreferences.getString(NIK_IBU,null));
        user.put(TEMPAT_LAHIR, sharedPreferences.getString(TEMPAT_LAHIR,null));
        user.put(TGL_LAHIR, sharedPreferences.getString(TGL_LAHIR,null));
        user.put(ALAMAT, sharedPreferences.getString(ALAMAT,null));
        user.put(POSYANDU, sharedPreferences.getString(POSYANDU,null));
        user.put(TELEPON, sharedPreferences.getString(TELEPON,null));
        user.put(EMAIL, sharedPreferences.getString(EMAIL,null));
        return user;
    }

    public void logoutSession(){
        editor.clear();
        editor.commit();
    }

    public boolean isLoggedIn(){
        return sharedPreferences.getBoolean(IS_LOGGED_IN, false);
    }
}