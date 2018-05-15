package com.example.pavel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    public static final String MY_PREFS_NAME = "MyPrefsFile";
    SharedPreferences prefs = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = getSharedPreferences(MY_PREFS_NAME, MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (prefs.getBoolean("firstRun", true)) {
            // При первом запуске (или если юзер удалял все данные приложения).

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            prefs.edit().putBoolean("firstRun", false).commit();
        }
    }
}
