package com.yxr.language;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @author ciba
 * @description SharedPreferences工具类
 * @date 2018/10/26
 */
public class SPUtil {

    public static SharedPreferences getSP() {
        Context context = LanguageManager.getInstance().getContext();
        return context.getSharedPreferences(context.getPackageName() + ".Language", Context.MODE_PRIVATE);
    }

    /***************************string********************************/

    public static void putString(String key, String value) {
        getSP().edit().putString(key, value).apply();
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String defaultValue) {
        return getSP().getString(key, defaultValue);
    }

    /***************************long********************************/

    public static void putLong(String key, long value) {
        getSP().edit().putLong(key, value).apply();
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        return getSP().getLong(key, defaultValue);
    }

    /***************************boolean********************************/

    public static void putBoolean(String key, boolean value) {
        getSP().edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getSP().getBoolean(key, defaultValue);
    }

    /***************************float********************************/

    public static void putFloat(String key, float value) {
        getSP().edit().putFloat(key, value).apply();
    }

    public static float getFloat(String key) {
        return getFloat(key, 0f);
    }

    public static float getFloat(String key, float defaultValue) {
        return getSP().getFloat(key, defaultValue);
    }

}
