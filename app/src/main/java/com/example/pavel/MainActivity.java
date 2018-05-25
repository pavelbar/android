package com.example.pavel;


import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;


public class MainActivity extends AppCompatActivity{




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load buttonGas name
        {
            String gasCompany = MyPrefs.getGasCompany(this);
            if (gasCompany != "default") {
                Button bGas = (Button) findViewById(R.id.buttonSendGas);
                bGas.setText(bGas.getText().toString() + "\n(" + MyPrefs.getGasCompany(this) + ")");
            }
        }

        //load buttonWater name
        {
            String waterCompany = MyPrefs.getWaterCompany(this);
            if (waterCompany != "default") {
                Button bWater = (Button) findViewById(R.id.buttonSendWater);
                bWater.setText(bWater.getText().toString() + "\n(" + MyPrefs.getWaterCompany(this) + ")");
            }
        }

        //load buttonLight name
        {
            String lightCompany = MyPrefs.getLightCompany(this);
            if (lightCompany != "default") {
                Button bLight = (Button) findViewById(R.id.buttonSendLight);
                bLight.setText(bLight.getText().toString() + "\n(" + MyPrefs.getLightCompany(this) + ")");
            }
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        if ( MyPrefs.getFirstRun(this) == true) {
            // Зайдем при первом запуске или если юзер удалял все данные приложения

            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            MyPrefs.setFirstRun(this, false);
           // prefs.edit().putBoolean("firstRun", false).apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
        return true;
    }
}
