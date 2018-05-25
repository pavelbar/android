package com.example.pavel;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pavel.fragments.EmptyFragment;
import com.example.pavel.fragments.gas_NizhegorodenergogasrasschetFragment;
import com.example.pavel.fragments.light_CentersbkFragment;
import com.example.pavel.fragments.light_ErkcFragment;
import com.example.pavel.fragments.light_TnsEnergoFragment;
import com.example.pavel.fragments.water_CentersbkFragment;
import com.example.pavel.fragments.water_ErkcFragment;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private String saveFirstName;
    private String saveSecondName;

    private String saveWaterCompany;
    private String saveGasCompany;
    private String saveLightCompany;


    private FragmentManager manager;
    private FragmentTransaction transaction;

    private String[] gasCompany = {" ", "Нижегородэнергогазрассчет"};
    private String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
    private String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};


    private boolean spinnerGasCompanyInitial = true;
    private boolean spinnerWaterCompanyInitial = true;
    private boolean spinnerLightCompanyInitial = true;

    private SettingsActivity mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        mContext = this;
        manager = getFragmentManager();

        Spinner spinnerGasCompany = (Spinner) findViewById(R.id.spinnerGasCompany);
        ArrayAdapter<String> adapterGasCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gasCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGasCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerGasCompany.setAdapter(adapterGasCompany); // Применяем адаптер к элементу spinner
        spinnerGasCompany.setOnItemSelectedListener(this);
        spinnerGasCompany.setSelection(0);

        Spinner spinnerWaterCompany = (Spinner) findViewById(R.id.spinnerWaterCompany);
        ArrayAdapter<String> adapterWaterCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, waterCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterWaterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerWaterCompany.setAdapter(adapterWaterCompany); // Применяем адаптер к элементу spinner
        spinnerWaterCompany.setOnItemSelectedListener(this);
        spinnerWaterCompany.setSelection(0);

        Spinner spinnerLightCompany = (Spinner) findViewById(R.id.spinnerLightCompany);
        ArrayAdapter<String> adapterLightCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lightCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterLightCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerLightCompany.setAdapter(adapterLightCompany); // Применяем адаптер к элементу spinner
        spinnerLightCompany.setOnItemSelectedListener(this);
        spinnerLightCompany.setSelection(0);


        //load Fname
        {
            String fName = MyPrefs.getFirstName(mContext);
            if(fName != "default") {
                EditText editTextFirstName = (EditText) findViewById(R.id.editTextFname);
                editTextFirstName.setText(fName);
            }
        }

        //load Sname
        {
            String sName = MyPrefs.getSecondName(mContext);
            if(sName != "default") {
                EditText editTextSecondName = (EditText) findViewById(R.id.editTextSname);
                editTextSecondName.setText(sName);
            }
        }


        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //save fName
                EditText fName = (EditText) findViewById(R.id.editTextFname);
                saveFirstName = fName.getText().toString();
                MyPrefs.setFirstName(mContext, saveFirstName);

                //save sName
                EditText sName = (EditText) findViewById(R.id.editTextSname);
                saveSecondName = sName.getText().toString();
                MyPrefs.setSecondName(mContext, saveSecondName);

                //save Company
                MyPrefs.setGasCompany(mContext, saveGasCompany);
                MyPrefs.setWaterCompany(mContext, saveWaterCompany);
                MyPrefs.setLightCompany(mContext, saveLightCompany);



                //___________________________________
                //----GAS----
                //___________________________________


                if (saveGasCompany == gasCompany[1]) {
                    String saveGasNizhegorogEnergoGasRasschetAccount;//
                    String saveGasNizhegorogEnergoGasRasschetLocation;//

                    EditText editTextNizhegorogEnergoGasRasschetAccount_gas = (EditText) findViewById(R.id.editTextNizhegorogEnergoGasRasschetAccount_gas);
                    Spinner spinnerLocationsNizhegorogEnergoGasRasschet_gas = (Spinner) findViewById(R.id.spinnerLocationsNizhegorogEnergoGasRasschet_gas);

                    saveGasNizhegorogEnergoGasRasschetAccount = editTextNizhegorogEnergoGasRasschetAccount_gas.getText().toString();
                    saveGasNizhegorogEnergoGasRasschetLocation = spinnerLocationsNizhegorogEnergoGasRasschet_gas.getSelectedItem().toString();

                    MyPrefs.setGasNizhegorogEnergoGasRasschetAccount(mContext, saveGasNizhegorogEnergoGasRasschetAccount);
                    MyPrefs.setGasNizhegorogEnergoGasRasschetLocation(mContext, saveGasNizhegorogEnergoGasRasschetLocation);
                }

                //___________________________________
                //------------WATER--------------
                // String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
                //___________________________________
                if (saveWaterCompany == waterCompany[1]) {
                    String saveWaterCentersbkAccount;//

                    EditText editTexCentersbkAccount_water = (EditText) findViewById(R.id.editTexCentersbkAccount_water);

                    saveWaterCentersbkAccount = editTexCentersbkAccount_water.getText().toString();

                    MyPrefs.setWaterCentersbkAccount(mContext, saveWaterCentersbkAccount);

                } else if (saveWaterCompany == waterCompany[2]) {
                    String saveWaterErkcAccount;//
                    String saveWaterErkcLocation;//

                    EditText editTextErkcAccount_water = (EditText) findViewById(R.id.editTextErkcAccount_water);
                    Spinner spinnerLocationsErkc_water = (Spinner) findViewById(R.id.spinnerLocationsErkc_water);

                    saveWaterErkcAccount = editTextErkcAccount_water.getText().toString();
                    saveWaterErkcLocation = spinnerLocationsErkc_water.getSelectedItem().toString();

                    MyPrefs.setWaterErkcAccount(mContext, saveWaterErkcAccount);
                    MyPrefs.setWaterErkcLocation(mContext, saveWaterErkcLocation);

                }

                //___________________________________
                //---------------LIGHT----------------
                //    String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
                //___________________________________

                if (saveLightCompany == lightCompany[1]) {
                    String saveLightCentersbkAccount;//

                    EditText editTexCentersbkAccount_light = (EditText) findViewById(R.id.editTexCentersbkAccount_light);

                    saveLightCentersbkAccount = editTexCentersbkAccount_light.getText().toString();

                    MyPrefs.setLightCentersbkAccount(mContext, saveLightCentersbkAccount);

                } else if (saveLightCompany == lightCompany[2]) {
                    String saveLightErkcAccount;//
                    String saveLightErkcLocation;//

                    EditText editTextErkcAccount_light = (EditText) findViewById(R.id.editTextErkcAccount_light);
                    Spinner spinnerLocationsErkc_light = (Spinner) findViewById(R.id.spinnerLocationsErkc_light);

                    saveLightErkcAccount = editTextErkcAccount_light.getText().toString();
                    saveLightErkcLocation = spinnerLocationsErkc_light.getSelectedItem().toString();

                    MyPrefs.setLightErkcAccount(mContext, saveLightErkcAccount);
                    MyPrefs.setLightErkcLocation(mContext, saveLightErkcLocation);

                } else if (saveLightCompany == lightCompany[3]) {
                    String saveLightTnsEnergoAccount;//
                    String saveLightTnsEnergoLocation;//

                    EditText editTextTnsEnergoAccount_light = (EditText) findViewById(R.id.editTextTnsEnergoAccount_light);
                    Spinner spinnerLocationsTnsEnergo_light = (Spinner) findViewById(R.id.spinnerLocationsTnsEnergo_light);

                    saveLightTnsEnergoAccount = editTextTnsEnergoAccount_light.getText().toString();
                    saveLightTnsEnergoLocation = spinnerLocationsTnsEnergo_light.getSelectedItem().toString();

                    MyPrefs.setLightTnsenergoAccount(mContext, saveLightTnsEnergoAccount);
                    MyPrefs.setLightTnsenergoLocation(mContext, saveLightTnsEnergoLocation);

                }


            }
        };
        Button btnSave = (Button) findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(oclBtnOk);        // присвоим обработчик кнопке OK (btnOk)
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {


        Spinner spinner = (Spinner) parent;
        //GAS
        if (spinner.getId() == R.id.spinnerGasCompany) {
            if (spinnerGasCompanyInitial) {
                spinnerGasCompanyInitial = false;
            } else {
                saveGasCompany = spinner.getSelectedItem().toString();
                if (pos == 1) {
                    transaction = manager.beginTransaction();
                    gas_NizhegorodenergogasrasschetFragment gasNizhegorodenergogasrasschetFragment = new gas_NizhegorodenergogasrasschetFragment();
                    transaction.replace(R.id.containerGas, gasNizhegorodenergogasrasschetFragment);
                    transaction.commit();
                } else if (pos == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerGas, emptyFragment);
                    transaction.commit();
                }
            }
        }

        //WATER
        if (spinner.getId() == R.id.spinnerWaterCompany) {
            if (spinnerWaterCompanyInitial) {
                spinnerWaterCompanyInitial = false;
            } else {
                saveWaterCompany = spinner.getSelectedItem().toString();
                if (pos == 2) {
                    water_ErkcFragment waterErkcFragment = new water_ErkcFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.containerWater, waterErkcFragment);
                    transaction.commit();
                } else if (pos == 1) {
                    transaction = manager.beginTransaction();
                    water_CentersbkFragment waterCentersbkFragment = new water_CentersbkFragment();
                    transaction.replace(R.id.containerWater, waterCentersbkFragment);
                    transaction.commit();
                } else if (pos == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerWater, emptyFragment);
                    transaction.commit();
                }
            }
        }

        //LIGHT
        if (spinner.getId() == R.id.spinnerLightCompany) {
            if (spinnerLightCompanyInitial) {
                spinnerLightCompanyInitial = false;
            } else {
                saveLightCompany = spinner.getSelectedItem().toString();
                if (pos == 3) {
                    transaction = manager.beginTransaction();
                    light_TnsEnergoFragment lightTnsEnergoFragment = new light_TnsEnergoFragment();
                    transaction.replace(R.id.containerLight, lightTnsEnergoFragment);
                    transaction.commit();
                } else if (pos == 2) {
                    transaction = manager.beginTransaction();
                    light_ErkcFragment lightErkcFragment = new light_ErkcFragment();
                    transaction.replace(R.id.containerLight, lightErkcFragment);
                    transaction.commit();
                } else if (pos == 1) {
                    transaction = manager.beginTransaction();
                    light_CentersbkFragment lightCentersbkFragment = new light_CentersbkFragment();
                    transaction.replace(R.id.containerLight, lightCentersbkFragment);
                    transaction.commit();
                } else if (pos == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerLight, emptyFragment);
                    transaction.commit();
                }
            }
        }

//        Toast toast = Toast.makeText(this, pos + "", Toast.LENGTH_SHORT);
//        toast.show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}
