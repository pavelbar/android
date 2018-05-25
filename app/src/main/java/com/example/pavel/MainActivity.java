package com.example.pavel;


import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.pavel.fragments.MainFragment;
import com.example.pavel.fragments.SettingsFragment;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();

        transaction = manager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        transaction.replace(R.id.containerMain, mainFragment);
        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (MyPrefs.getFirstRun(this) == true) {
            // Зайдем при первом запуске или если юзер удалял все данные приложения
            setFragmentSettings();
        }

    }

    public void setFragmentMain() {
        //Зайдем при всех остальных запусках
        transaction = manager.beginTransaction();
        MainFragment mainFragment = new MainFragment();
        transaction.replace(R.id.containerMain, mainFragment);
        transaction.commit();
    }

    public void setFragmentSettings() {
        transaction = manager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        transaction.replace(R.id.containerMain, settingsFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        transaction = manager.beginTransaction();
        SettingsFragment settingsFragment = new SettingsFragment();
        transaction.replace(R.id.containerMain, settingsFragment);
        transaction.commit();
        return true;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
