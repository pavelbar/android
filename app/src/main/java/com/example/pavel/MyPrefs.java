package com.example.pavel;

import android.content.Context;
import android.content.SharedPreferences;


public class MyPrefs {

    private SharedPreferences sharedPreferences;
    private static final String MY_PREFS_NAME = "MyPrefsFile";

    private static final String DEFAULT_STRING = "default";
    private static final int DEFAULT_INT = 100500;

    public MyPrefs() {
        // Blank
    }

    private static SharedPreferences getPrefs(Context context) {
        return context.getSharedPreferences(MY_PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static void clearAllPreferences(Context context) {
        getPrefs(context).edit().clear().apply();
    }

    public static int getDefaultInt() {
        return DEFAULT_INT;
    }

    public static String getDefaultString() {
        return DEFAULT_STRING;
    }


    //----------------------
    //--------GET-----------
    //----------------------

    //position

    public static int getGasPosition(Context context) {
        return getPrefs(context).getInt("saveGasPosition", DEFAULT_INT);
    }

    public static int getWaterPosition(Context context) {
        return getPrefs(context).getInt("saveWaterPosition", DEFAULT_INT);
    }

    public static int getLightPosition(Context context) {
        return getPrefs(context).getInt("saveLightPosition", DEFAULT_INT);
    }

    //firstRun

    public static Boolean getFirstRun(Context context) {
        return getPrefs(context).getBoolean("saveFirstRun", true);
    }

    //l/f Name

    public static String getFirstName(Context context) {
        return getPrefs(context).getString("saveFirstName", DEFAULT_STRING);
    }

    public static String getSecondName(Context context) {
        return getPrefs(context).getString("saveSecondName", DEFAULT_STRING);
    }

    //Company


    public static String getGasCompany(Context context) {
        return getPrefs(context).getString("saveGasCompany", DEFAULT_STRING);
    }

    public static String getWaterCompany(Context context) {
        return getPrefs(context).getString("saveWaterCompany", DEFAULT_STRING);
    }

    public static String getLightCompany(Context context) {
        return getPrefs(context).getString("saveLightCompany", DEFAULT_STRING);
    }
    //gas_NizhegorogEnergoGasRasschet

    public static String getGasNizhegorogEnergoGasRasschetAccount(Context context) {
        return getPrefs(context).getString("saveGasNizhegorogEnergoGasRasschetAccount", DEFAULT_STRING);
    }

    public static String getGasNizhegorogEnergoGasRasschetLocation(Context context) {
        return getPrefs(context).getString("saveGasNizhegorogEnergoGasRasschetLocation", DEFAULT_STRING);
    }

    //water_Centersbk

    public static String getWaterCentersbkAccount(Context context) {
        return getPrefs(context).getString("saveWaterCentersbkAccount", DEFAULT_STRING);
    }

    //water_Erkc

    public static String getWaterErkcAccount(Context context) {
        return getPrefs(context).getString("saveWaterErkcAccount", DEFAULT_STRING);
    }

    public static String getWaterErkcLocation(Context context) {
        return getPrefs(context).getString("saveWaterErkcLocation", DEFAULT_STRING);
    }

    //light_Centersbk

    public static String getLightCentersbkAccount(Context context) {
        return getPrefs(context).getString("saveLightCentersbkAccount", DEFAULT_STRING);
    }

    //light_Erkc

    public static String getLightErkcAccount(Context context) {
        return getPrefs(context).getString("saveLightErkcAccount", DEFAULT_STRING);
    }

    public static String getLightErkcLocation(Context context) {
        return getPrefs(context).getString("saveLightErkcLocation", DEFAULT_STRING);
    }

    //light_Tnsenergo

    public static String getLightTnsenergoAccount(Context context) {
        return getPrefs(context).getString("saveLightTnsEnergoAccount", DEFAULT_STRING);
    }

    public static String getLightTnsenergoLocation(Context context) {
        return getPrefs(context).getString("saveLightTnsEnergoLocation", DEFAULT_STRING);
    }

    //----------------------
    //--------SET-----------
    //----------------------

    //positions

    public static void setGasPosition(Context context, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("saveGasPosition", value);
        editor.apply();
    }

    public static void setWaterPosition(Context context, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("saveWaterPosition", value);
        editor.apply();
    }

    public static void setLightPosition(Context context, int value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putInt("saveLightPosition", value);
        editor.apply();
    }
    //firstRun

    public static void setFirstRun(Context context, Boolean value) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putBoolean("saveFirstRun", value);
        editor.apply();
    }

    //l/f name

    public static void setFirstName(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveFirstName", input);
        editor.apply();
    }

    public static void setSecondName(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveSecondName", input);
        editor.apply();
    }

    //Company

    public static void setGasCompany(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveGasCompany", input);
        editor.apply();
    }

    public static void setWaterCompany(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveWaterCompany", input);
        editor.apply();
    }

    public static void setLightCompany(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightCompany", input);
        editor.apply();
    }


    //gas_NizhegorogEnergoGasRasschet

    public static void setGasNizhegorogEnergoGasRasschetAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveGasNizhegorogEnergoGasRasschetAccount", input);
        editor.apply();
    }

    public static void setGasNizhegorogEnergoGasRasschetLocation(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveGasNizhegorogEnergoGasRasschetLocation", input);
        editor.apply();
    }

    //water_Centersbk

    public static void setWaterCentersbkAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveWaterCentersbkAccount", input);
        editor.apply();
    }

    //water_Erkc

    public static void setWaterErkcAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveWaterErkcAccount", input);
        editor.apply();
    }

    public static void setWaterErkcLocation(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveWaterErkcLocation", input);
        editor.apply();
    }

    //light_Centersbk

    public static void setLightCentersbkAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightCentersbkAccount", input);
        editor.apply();
    }

    //light_Erkc

    public static void setLightErkcAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightErkcAccount", input);
        editor.apply();
    }

    public static void setLightErkcLocation(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightErkcLocation", input);
        editor.apply();
    }

    //light_TnsEnergo

    public static void setLightTnsenergoAccount(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightTnsEnergoAccount", input);
        editor.apply();
    }

    public static void setLightTnsenergoLocation(Context context, String input) {
        SharedPreferences.Editor editor = getPrefs(context).edit();
        editor.putString("saveLightTnsEnergoLocation", input);
        editor.apply();
    }
}