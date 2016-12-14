package br.ufg.inf.adopet.util;

/**
 * Created by root on 14/12/16.
 */

import android.content.Context;
import android.content.SharedPreferences;

public class AppData {

    public static void setData(Context context, String preference, int value){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(preference,value);
        editor.apply();
    }
    public static void setData(Context context,String preference,String value){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preference, value);
        editor.apply();
    }

    public static String getData(Context context,String preferencia,String defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        return preferences.getString(preferencia,defaultValue);
    }
    public static int getData(Context context,String preferencia,int defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        return preferences.getInt(preferencia, defaultValue);
    }

    public static void removeData(Context context,String preference){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(preference).apply();
    }
    public static void removeDatas(Context context,String[] preferencesArray){
        SharedPreferences preferences = context.getSharedPreferences(AppConfig.PREFERENCE_USER_TAG,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        for (String aPreferencesArray : preferencesArray) editor.remove(aPreferencesArray).apply();
    }
    
    public interface Preferences{
        String ID = "user_id";
        String NAME = "user_name";
        String UID = "user_uid";
        String MAIL = "user_mail";
        String ACCESS_TOKEN = "user_access_token";
        String CLIENT_TOKEN = "user_client_token";
        String TOKEN_TYPE = "user_token_type";
    }

}
