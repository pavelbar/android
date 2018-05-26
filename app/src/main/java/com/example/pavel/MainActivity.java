package com.example.pavel;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.example.pavel.fragments.MainFragment;
import com.example.pavel.fragments.SendLightCentersbkFragment;
import com.example.pavel.fragments.SettingsFragment;
import com.example.pavel.fragments.SendWaterCentersbkFragment;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private FragmentManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        manager = getFragmentManager();
    }


    @Override
    protected void onResume() {
        super.onResume();

        if (MyPrefs.getFirstRun(this) == true) {
            // Зайдем при первом запуске или если юзер удалял все данные приложения
            setFragmentSettings();

            MyPrefs.setFirstRun(this, false);
        }
        else
        {
            //Зайдем при остальных хапусках
            setFragmentMain();
        }

    }

    public void setFragmentSendLightCentersbk() {
        //my method
        setMyFragment(R.id.containerMain, new SendLightCentersbkFragment());
    }

    public void setFragmentSendWaterCentersbk() {
        //my method
        setMyFragment(R.id.containerMain, new SendWaterCentersbkFragment());
    }

    public void setFragmentMain() {
        //my method
        setMyFragment(R.id.containerMain, new MainFragment());
    }

    public void setFragmentSettings() {
        //my method
        setMyFragment(R.id.containerMain, new SettingsFragment());
    }

    public void setMyFragment(int myContainerViewId, Fragment myFragment)
    {
        //my method
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(myContainerViewId, myFragment);
        transaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        setFragmentSettings();
        return true;
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
