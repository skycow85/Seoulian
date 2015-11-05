package kr.go.seoul.seoulian.manager;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by jkwoo on 2015-10-26.
 */
public class SeoulianPreferenceManager {
    private static SharedPreferences mPref;
    public final static String NAME_TO_CURRENCY = "toCurrency";
    public final static String NAME_FROM_CURRENCY = "fromCurrency";
    public final static String NAME_TIME_ZONE = "timeZone";
    public final static String NAME_DONT_SHOW_GUIDE = "isCheck";
    public final static String NAME_TTS_PITCH = "pitch";
    public final static String NAME_TTS_SPEECH_RATE = "speechRate";
    public static void createSharedPreference(Context context){
        if(mPref==null){
            mPref = PreferenceManager.getDefaultSharedPreferences(context);
        }
    }
    public static void saveString(String key, String value){
        SharedPreferences.Editor editor = mPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getString(String key, String defaultValue){
        return mPref.getString(key, defaultValue);
    }

    public static void saveBoolean(String key, Boolean value){
        SharedPreferences.Editor editor = mPref.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public static Boolean getBoolean(String key, Boolean defaultValue){
        return mPref.getBoolean(key, defaultValue);
    }

    public static void saveFloat(String key, float value){
        SharedPreferences.Editor editor = mPref.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    public static float getFloat(String key, float defaultValue){
        return mPref.getFloat(key, defaultValue);
    }

    public static void removeString(String key){
        SharedPreferences.Editor editor = mPref.edit();
        editor.remove(key);
        editor.commit();
    }
}
