package com.example.pavel.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.pavel.MainActivity;
import com.example.pavel.MyPrefs;
import com.example.pavel.R;


public class SettingsFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private FragmentManager manager;

    private String saveFirstName;
    private String saveSecondName;

    private String saveWaterCompany;
    private String saveGasCompany;
    private String saveLightCompany;

    private int saveGasPosition   = 0;
    private int saveWaterPosition = 0;
    private int saveLightPosition = 0;

    private String[] gasCompany   = {" ", "Нижегородэнергогазрассчет"};
    private String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
    private String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};

    private boolean spinnerGasCompanyInitial   = true;
    private boolean spinnerWaterCompanyInitial = true;
    private boolean spinnerLightCompanyInitial = true;

    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View viewSettingsLayout = inflater.inflate(R.layout.settings_layout, container, false);

        mContext = this.getActivity();
        manager = getFragmentManager();

        final Spinner spinnerGasCompany =   (Spinner) viewSettingsLayout.findViewById(R.id.spinnerGasCompany);
        final Spinner spinnerWaterCompany = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerWaterCompany);
        final Spinner spinnerLightCompany = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLightCompany);

        initializeMySpinner(spinnerGasCompany,   new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, gasCompany));
        initializeMySpinner(spinnerWaterCompany, new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, waterCompany));
        initializeMySpinner(spinnerLightCompany, new ArrayAdapter<>(mContext, android.R.layout.simple_spinner_item, lightCompany));

        //--------------
        //----LOAD------
        //--------------

        //load Fname
        {
            TextView textViewHello = (TextView) viewSettingsLayout.findViewById(R.id.textViewHello);
            TextView textViewInfo = (TextView) viewSettingsLayout.findViewById(R.id.textViewInfo);

            String fName = MyPrefs.getFirstName(mContext);
            if ((fName != MyPrefs.getDefaultString()) && (fName.isEmpty() == false)) {
                EditText editTextFirstName = (EditText) viewSettingsLayout.findViewById(R.id.editTextFname);
                editTextFirstName.setText(fName);

                textViewHello.setText(getString(R.string.hello) + ", " + fName);
                textViewInfo.setText(" ");
            }
            else
            {
                textViewHello.setText(getString(R.string.hello));
                textViewInfo.setText(getString(R.string.info));
            }
        }

        //load Sname
        {
            String sName = MyPrefs.getSecondName(mContext);
            if (sName != MyPrefs.getDefaultString()) {
                EditText editTextSecondName = (EditText) viewSettingsLayout.findViewById(R.id.editTextSname);
                editTextSecondName.setText(sName);
            }
        }

        //load Gas Company
        //gasCompany = {" ", "Нижегородэнергогазрассчет"};
        {
            saveGasPosition = MyPrefs.getGasPosition(mContext);
            if (saveGasPosition != MyPrefs.getDefaultInt()) {
                spinnerGasCompany.setSelection(saveGasPosition);

                if (saveGasPosition == 1) {
                    setMyFragment(R.id.containerGas, new gas_NizhegorodenergogasrasschetFragment());
                } else if (saveGasPosition == 0) {
                    setMyFragment(R.id.containerGas, new EmptyFragment());
                }
            }
        }

        //load Water Company
        //waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
        {
            saveWaterPosition = MyPrefs.getWaterPosition(mContext);
            if (saveWaterPosition != MyPrefs.getDefaultInt()) {
                spinnerWaterCompany.setSelection(saveWaterPosition);

                if (saveWaterPosition == 2) {
                    setMyFragment(R.id.containerWater, new water_ErkcFragment());
                } else if (saveWaterPosition == 1) {
                    setMyFragment(R.id.containerWater, new water_CentersbkFragment());
                } else if (saveWaterPosition == 0) {
                    setMyFragment(R.id.containerWater, new EmptyFragment());
                }
            }
        }

        //load Light Company
        //lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
        {
            saveLightPosition = MyPrefs.getLightPosition(mContext);
            if (saveLightPosition != MyPrefs.getDefaultInt()) {
                spinnerLightCompany.setSelection(saveLightPosition);

                if (saveLightPosition == 3) {
                    setMyFragment(R.id.containerLight, new light_TnsEnergoFragment());
                } else if (saveLightPosition == 2) {
                    setMyFragment(R.id.containerLight, new light_ErkcFragment());
                } else if (saveLightPosition == 1) {
                    setMyFragment(R.id.containerLight, new light_CentersbkFragment());
                } else if (saveLightPosition == 0) {
                    setMyFragment(R.id.containerLight, new EmptyFragment());
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
                if (saveGasPosition != MyPrefs.getDefaultInt()) {
                    saveGasCompany = gasCompany[saveGasPosition];
                }

                if (saveWaterPosition != MyPrefs.getDefaultInt()) {
                    saveWaterCompany = waterCompany[saveWaterPosition];
                }

                if (saveLightPosition != MyPrefs.getDefaultInt()) {
                    saveLightCompany = lightCompany[saveLightPosition];
                }

                MyPrefs.setGasCompany(mContext, saveGasCompany);
                MyPrefs.setWaterCompany(mContext, saveWaterCompany);
                MyPrefs.setLightCompany(mContext, saveLightCompany);

                //-----------------------------------------
                //---------------save GAS------------------
                //-----------------------------------------

                if (saveGasCompany == gasCompany[1]) {

                    EditText editTextNizhegorogEnergoGasRasschetAccount_gas = (EditText) viewSettingsLayout.findViewById(R.id.editTextNizhegorogEnergoGasRasschetAccount_gas);
                    Spinner spinnerLocationsNizhegorogEnergoGasRasschet_gas = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsNizhegorogEnergoGasRasschet_gas);

                    String saveGasNizhegorogEnergoGasRasschetAccount = editTextNizhegorogEnergoGasRasschetAccount_gas.getText().toString();
                    String saveGasNizhegorogEnergoGasRasschetLocation = spinnerLocationsNizhegorogEnergoGasRasschet_gas.getSelectedItem().toString();

                    MyPrefs.setGasNizhegorogEnergoGasRasschetAccount(mContext, saveGasNizhegorogEnergoGasRasschetAccount);
                    MyPrefs.setGasNizhegorogEnergoGasRasschetLocation(mContext, saveGasNizhegorogEnergoGasRasschetLocation);
                }

                //-----------------------------------------
                //------------save WATER-------------------
                //-----------------------------------------
                // String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
                //___________________________________
                if (saveWaterCompany == waterCompany[1]) {
                    EditText editTexCentersbkAccount_water = (EditText) viewSettingsLayout.findViewById(R.id.editTexCentersbkAccount_water);

                    String saveWaterCentersbkAccount = editTexCentersbkAccount_water.getText().toString();

                    MyPrefs.setWaterCentersbkAccount(mContext, saveWaterCentersbkAccount);

                } else if (saveWaterCompany == waterCompany[2]) {
                    EditText editTextErkcAccount_water = (EditText) viewSettingsLayout.findViewById(R.id.editTextErkcAccount_water);
                    Spinner spinnerLocationsErkc_water = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsErkc_water);

                    String saveWaterErkcAccount = editTextErkcAccount_water.getText().toString();
                    String saveWaterErkcLocation = spinnerLocationsErkc_water.getSelectedItem().toString();

                    MyPrefs.setWaterErkcAccount(mContext, saveWaterErkcAccount);
                    MyPrefs.setWaterErkcLocation(mContext, saveWaterErkcLocation);
                }

                //-----------------------------------------
                //---------------save LIGHT----------------
                //    String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
                //-----------------------------------------

                if (saveLightCompany == lightCompany[1]) {
                    EditText editTexCentersbkAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTexCentersbkAccount_light);

                    String saveLightCentersbkAccount = editTexCentersbkAccount_light.getText().toString();

                    MyPrefs.setLightCentersbkAccount(mContext, saveLightCentersbkAccount);

                } else if (saveLightCompany == lightCompany[2]) {
                    EditText editTextErkcAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTextErkcAccount_light);
                    Spinner spinnerLocationsErkc_light = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsErkc_light);

                    String saveLightErkcAccount = editTextErkcAccount_light.getText().toString();
                    String saveLightErkcLocation = spinnerLocationsErkc_light.getSelectedItem().toString();

                    MyPrefs.setLightErkcAccount(mContext, saveLightErkcAccount);
                    MyPrefs.setLightErkcLocation(mContext, saveLightErkcLocation);

                } else if (saveLightCompany == lightCompany[3]) {
                    EditText editTextTnsEnergoAccount_light = (EditText) viewSettingsLayout.findViewById(R.id.editTextTnsEnergoAccount_light);
                    Spinner spinnerLocationsTnsEnergo_light = (Spinner) viewSettingsLayout.findViewById(R.id.spinnerLocationsTnsEnergo_light);

                    String saveLightTnsEnergoAccount = editTextTnsEnergoAccount_light.getText().toString();
                    String saveLightTnsEnergoLocation = spinnerLocationsTnsEnergo_light.getSelectedItem().toString();

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
        //gasCompany = {" ", "Нижегородэнергогазрассчет"};
        if (spinner.getId() == R.id.spinnerGasCompany) {
            if (spinnerGasCompanyInitial) {
                spinnerGasCompanyInitial = false;
            } else {
                saveGasCompany = spinner.getSelectedItem().toString();
                saveGasPosition = pos;
                if (pos == 1) {
                    setMyFragment(R.id.containerGas, new gas_NizhegorodenergogasrasschetFragment());
                } else if (pos == 0) {
                    setMyFragment(R.id.containerGas, new EmptyFragment());
                }
            }
        }

        //WATER
        //waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
        if (spinner.getId() == R.id.spinnerWaterCompany) {
            if (spinnerWaterCompanyInitial) {
                spinnerWaterCompanyInitial = false;
            } else {
                saveWaterCompany = spinner.getSelectedItem().toString();
                saveWaterPosition = pos;
                if (pos == 2) {
                    setMyFragment(R.id.containerWater, new water_ErkcFragment());
                } else if (pos == 1) {
                    setMyFragment(R.id.containerWater, new water_CentersbkFragment());
                } else if (pos == 0) {
                    setMyFragment(R.id.containerWater, new EmptyFragment());
                }
            }
        }

        //LIGHT
        //lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
        if (spinner.getId() == R.id.spinnerLightCompany) {
            if (spinnerLightCompanyInitial) {
                spinnerLightCompanyInitial = false;
            } else {
                saveLightCompany = spinner.getSelectedItem().toString();
                saveLightPosition = pos;
                if (pos == 3) {
                    setMyFragment(R.id.containerLight, new light_TnsEnergoFragment());
                } else if (pos == 2) {
                    setMyFragment(R.id.containerLight, new light_ErkcFragment());
                } else if (pos == 1) {
                    setMyFragment(R.id.containerLight, new light_CentersbkFragment());
                } else if (pos == 0) {
                    setMyFragment(R.id.containerLight, new EmptyFragment());
                }
            }
        }

    }

    private void setMyFragment(int myContainerViewId, Fragment myFragment) {
        //my method
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(myContainerViewId, myFragment);
        transaction.commit();
    }

    private void initializeMySpinner(Spinner mySpinner, ArrayAdapter<String> myAdapter)
    {
        //my method
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        mySpinner.setAdapter(myAdapter); // Применяем адаптер к элементу spinner
        mySpinner.setOnItemSelectedListener(this);
        mySpinner.setSelection(0);
    }

    @Override
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }

}

//Toast toast = Toast.makeText(this, pos + "", Toast.LENGTH_SHORT);
//toast.show();