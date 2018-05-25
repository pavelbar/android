package com.example.pavel.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.MainActivity;
import com.example.pavel.MyPrefs;
import com.example.pavel.R;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private String saveFirstName;
    private String saveSecondName;

    private String saveWaterCompany;
    private String saveGasCompany;
    private String saveLightCompany;

    private int saveGasPosition;
    private int saveWaterPosition;
    private int saveLightPosition;

    private FragmentManager manager;
    FragmentTransaction transaction;

    private String[] gasCompany = {" ", "Нижегородэнергогазрассчет"};
    private String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
    private String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};


    private boolean spinnerGasCompanyInitial = true;
    private boolean spinnerWaterCompanyInitial = true;
    private boolean spinnerLightCompanyInitial = true;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View viewSettingsLayout = inflater.inflate(R.layout.settings_layout, container, false);

        mContext = this.getActivity();
        manager = getFragmentManager();

        Spinner spinnerGasCompany = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerGasCompany);
        ArrayAdapter<String> adapterGasCompany = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, gasCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGasCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerGasCompany.setAdapter(adapterGasCompany); // Применяем адаптер к элементу spinner
        spinnerGasCompany.setOnItemSelectedListener(this);
        spinnerGasCompany.setSelection(0);

        Spinner spinnerWaterCompany = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerWaterCompany);
        ArrayAdapter<String> adapterWaterCompany = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, waterCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterWaterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerWaterCompany.setAdapter(adapterWaterCompany); // Применяем адаптер к элементу spinner
        spinnerWaterCompany.setOnItemSelectedListener(this);
        spinnerWaterCompany.setSelection(0);

        Spinner spinnerLightCompany = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLightCompany);
        ArrayAdapter<String> adapterLightCompany = new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, lightCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterLightCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerLightCompany.setAdapter(adapterLightCompany); // Применяем адаптер к элементу spinner
        spinnerLightCompany.setOnItemSelectedListener(this);
        spinnerLightCompany.setSelection(0);


        //--------------
        //----LOAD------
        //--------------


        //load Fname
        {
            String fName = MyPrefs.getFirstName(mContext);

            if ((fName != "default") && (fName.isEmpty() == false)) {
                EditText editTextFirstName = (EditText) viewSettingsLayout.findViewById(R.id.editTextFname);
                editTextFirstName.setText(fName);

                TextView textViewHello = (TextView) viewSettingsLayout.findViewById(R.id.textViewHello);
                TextView textViewInfo = (TextView) viewSettingsLayout.findViewById(R.id.textViewInfo);

                textViewHello.setText("Здравствуйте, " + fName);
                textViewInfo.setText(" ");
            }
        }

        //load Sname
        {
            String sName = MyPrefs.getSecondName(mContext);
            if (sName != "default") {
                EditText editTextSecondName = (EditText) viewSettingsLayout.findViewById(R.id.editTextSname);
                editTextSecondName.setText(sName);
            }
        }

        //load Gas Company
        {
            saveGasPosition = MyPrefs.getGasPosition(mContext);
            if (saveGasPosition != 100500) {
                spinnerGasCompany.setSelection(saveGasPosition);

                if (saveGasPosition == 1) {
                    transaction = manager.beginTransaction();
                    gas_NizhegorodenergogasrasschetFragment gasNizhegorodenergogasrasschetFragment = new gas_NizhegorodenergogasrasschetFragment();
                    transaction.replace(R.id.containerGas, gasNizhegorodenergogasrasschetFragment);
                    transaction.commit();
                } else if (saveGasPosition == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerGas, emptyFragment);
                    transaction.commit();
                }
            }
        }

        //load Water Company
        {
            saveWaterPosition = MyPrefs.getWaterPosition(mContext);
            if (saveWaterPosition != 100500) {
                spinnerWaterCompany.setSelection(saveWaterPosition);

                if (saveWaterPosition == 2) {
                    water_ErkcFragment waterErkcFragment = new water_ErkcFragment();
                    transaction = manager.beginTransaction();
                    transaction.replace(R.id.containerWater, waterErkcFragment);
                    transaction.commit();
                } else if (saveWaterPosition == 1) {
                    transaction = manager.beginTransaction();
                    water_CentersbkFragment waterCentersbkFragment = new water_CentersbkFragment();
                    transaction.replace(R.id.containerWater, waterCentersbkFragment);
                    transaction.commit();
                } else if (saveWaterPosition == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerWater, emptyFragment);
                    transaction.commit();
                }
            }
        }

        //load Light Company
        {
            saveLightPosition = MyPrefs.getLightPosition(mContext);
            if (saveLightPosition != 100500) {
                spinnerLightCompany.setSelection(saveLightPosition);

                if (saveLightPosition == 3) {
                    transaction = manager.beginTransaction();
                    light_TnsEnergoFragment lightTnsEnergoFragment = new light_TnsEnergoFragment();
                    transaction.replace(R.id.containerLight, lightTnsEnergoFragment);
                    transaction.commit();
                } else if (saveLightPosition == 2) {
                    transaction = manager.beginTransaction();
                    light_ErkcFragment lightErkcFragment = new light_ErkcFragment();
                    transaction.replace(R.id.containerLight, lightErkcFragment);
                    transaction.commit();
                } else if (saveLightPosition == 1) {
                    transaction = manager.beginTransaction();
                    light_CentersbkFragment lightCentersbkFragment = new light_CentersbkFragment();
                    transaction.replace(R.id.containerLight, lightCentersbkFragment);
                    transaction.commit();
                } else if (saveLightPosition == 0) {
                    transaction = manager.beginTransaction();
                    EmptyFragment emptyFragment = new EmptyFragment();
                    transaction.replace(R.id.containerLight, emptyFragment);
                    transaction.commit();
                }
            }
        }

        // создаем обработчик нажатия
        View.OnClickListener oclBtnOk = new View.OnClickListener() {

            @Override
            public void onClick(View vi) {
                //--------------
                //----SAVE------
                //--------------

                //!!! CLEAR
                MyPrefs.clearAllPreferences(mContext);

                //save fName
                EditText fName = (EditText) viewSettingsLayout.findViewById(R.id.editTextFname);
                saveFirstName = fName.getText().toString();
                MyPrefs.setFirstName(mContext, saveFirstName);

                //save sName
                EditText sName = (EditText) viewSettingsLayout.findViewById(R.id.editTextSname);
                saveSecondName = sName.getText().toString();
                MyPrefs.setSecondName(mContext, saveSecondName);

                //save Positions
                MyPrefs.setGasPosition(mContext, saveGasPosition);
                MyPrefs.setWaterPosition(mContext, saveWaterPosition);
                MyPrefs.setLightPosition(mContext, saveLightPosition);

                //save Company
                saveGasCompany = gasCompany[saveGasPosition];
                saveWaterCompany = waterCompany[saveWaterPosition];
                saveLightCompany = lightCompany[saveLightPosition];

                MyPrefs.setGasCompany(mContext, saveGasCompany);
                MyPrefs.setWaterCompany(mContext, saveWaterCompany);
                MyPrefs.setLightCompany(mContext, saveLightCompany);


                //___________________________________
                //----save GAS----
                //___________________________________


                if (saveGasCompany == gasCompany[1]) {
                    String saveGasNizhegorogEnergoGasRasschetAccount;//
                    String saveGasNizhegorogEnergoGasRasschetLocation;//

                    EditText editTextNizhegorogEnergoGasRasschetAccount_gas = (EditText) viewSettingsLayout.findViewById(R.id.editTextNizhegorogEnergoGasRasschetAccount_gas);
                    Spinner spinnerLocationsNizhegorogEnergoGasRasschet_gas = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsNizhegorogEnergoGasRasschet_gas);

                    saveGasNizhegorogEnergoGasRasschetAccount = editTextNizhegorogEnergoGasRasschetAccount_gas.getText().toString();
                    saveGasNizhegorogEnergoGasRasschetLocation = spinnerLocationsNizhegorogEnergoGasRasschet_gas.getSelectedItem().toString();

                    MyPrefs.setGasNizhegorogEnergoGasRasschetAccount(mContext, saveGasNizhegorogEnergoGasRasschetAccount);
                    MyPrefs.setGasNizhegorogEnergoGasRasschetLocation(mContext, saveGasNizhegorogEnergoGasRasschetLocation);
                }

                //___________________________________
                //------------save WATER--------------
                // String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
                //___________________________________
                if (saveWaterCompany == waterCompany[1]) {
                    String saveWaterCentersbkAccount;//

                    EditText editTexCentersbkAccount_water = (EditText) viewSettingsLayout.findViewById(R.id.editTexCentersbkAccount_water);

                    saveWaterCentersbkAccount = editTexCentersbkAccount_water.getText().toString();

                    MyPrefs.setWaterCentersbkAccount(mContext, saveWaterCentersbkAccount);

                } else if (saveWaterCompany == waterCompany[2]) {
                    String saveWaterErkcAccount;//
                    String saveWaterErkcLocation;//

                    EditText editTextErkcAccount_water = (EditText) viewSettingsLayout.findViewById(R.id.editTextErkcAccount_water);
                    Spinner spinnerLocationsErkc_water = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsErkc_water);

                    saveWaterErkcAccount = editTextErkcAccount_water.getText().toString();
                    saveWaterErkcLocation = spinnerLocationsErkc_water.getSelectedItem().toString();

                    MyPrefs.setWaterErkcAccount(mContext, saveWaterErkcAccount);
                    MyPrefs.setWaterErkcLocation(mContext, saveWaterErkcLocation);

                }

                //___________________________________
                //---------------save LIGHT----------------
                //    String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
                //___________________________________

                if (saveLightCompany == lightCompany[1]) {
                    String saveLightCentersbkAccount;//

                    EditText editTexCentersbkAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTexCentersbkAccount_light);

                    saveLightCentersbkAccount = editTexCentersbkAccount_light.getText().toString();

                    MyPrefs.setLightCentersbkAccount(mContext, saveLightCentersbkAccount);

                } else if (saveLightCompany == lightCompany[2]) {
                    String saveLightErkcAccount;//
                    String saveLightErkcLocation;//

                    EditText editTextErkcAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTextErkcAccount_light);
                    Spinner spinnerLocationsErkc_light = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsErkc_light);

                    saveLightErkcAccount = editTextErkcAccount_light.getText().toString();
                    saveLightErkcLocation = spinnerLocationsErkc_light.getSelectedItem().toString();

                    MyPrefs.setLightErkcAccount(mContext, saveLightErkcAccount);
                    MyPrefs.setLightErkcLocation(mContext, saveLightErkcLocation);

                } else if (saveLightCompany == lightCompany[3]) {
                    String saveLightTnsEnergoAccount;//
                    String saveLightTnsEnergoLocation;//

                    EditText editTextTnsEnergoAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTextTnsEnergoAccount_light);
                    Spinner spinnerLocationsTnsEnergo_light = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsTnsEnergo_light);

                    saveLightTnsEnergoAccount = editTextTnsEnergoAccount_light.getText().toString();
                    saveLightTnsEnergoLocation = spinnerLocationsTnsEnergo_light.getSelectedItem().toString();

                    MyPrefs.setLightTnsenergoAccount(mContext, saveLightTnsEnergoAccount);
                    MyPrefs.setLightTnsenergoLocation(mContext, saveLightTnsEnergoLocation);

                }


                ((MainActivity) getActivity()).setFragmentMain();//!!

            }
        };
        Button btnSave = (Button) viewSettingsLayout.findViewById(R.id.buttonSave);
        btnSave.setOnClickListener(oclBtnOk);        // присвоим обработчик кнопке OK (btnOk)

        return viewSettingsLayout;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        //-------------------
        //----SELECT--------
        //-------------------

        Spinner spinner = (Spinner) parent;

        //GAS
        if (spinner.getId() == R.id.spinnerGasCompany) {
            if (spinnerGasCompanyInitial) {
                spinnerGasCompanyInitial = false;
            } else {
                saveGasCompany = spinner.getSelectedItem().toString();
                saveGasPosition = pos;
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
                saveWaterPosition = pos;
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
                saveLightPosition = pos;
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

    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


}

//        Toast toast = Toast.makeText(this, pos + "", Toast.LENGTH_SHORT);
//        toast.show();