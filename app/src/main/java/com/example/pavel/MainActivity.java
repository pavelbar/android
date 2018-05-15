package com.example.pavel;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity{

    SharedPreferences firstStartApp = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstStartApp = getSharedPreferences("com.example.pavel", MODE_PRIVATE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (firstStartApp.getBoolean("firstrun", true)) {
            // При первом запуске (или если юзер удалял все данные приложения).

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            //  При следующих запусках этот код не вызывается.
            firstStartApp.edit().putBoolean("firstrun", false).commit();
        }
    }
}
