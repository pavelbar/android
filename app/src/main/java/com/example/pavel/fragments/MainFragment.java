package com.example.pavel.fragments;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.pavel.MyPrefs;
import com.example.pavel.R;


public class MainFragment extends Fragment {

    private Context mContext;
    private FragmentManager manager;
    private FragmentTransaction transaction;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_main, container, false);

        manager = getFragmentManager();
        mContext = this.getActivity();

        Button bGas =   (Button) v.findViewById(R.id.buttonSendGas);
        Button bWater = (Button) v.findViewById(R.id.buttonSendWater);
        Button bLight = (Button) v.findViewById(R.id.buttonSendLight);

        //--------------
        //---RENAME----
        //-------------
//        //buttonGas name
//        {
//            String gasCompany = MyPrefs.getGasCompany(mContext);
//            if ((gasCompany != MyPrefs.getDefaultString()) && (gasCompany != " ")) {
//                bGas.setText(bGas.getText().toString() + "\n" + MyPrefs.getGasCompany(mContext) );
//            }
//        }
//
//        //buttonWater name
//        {
//            String waterCompany = MyPrefs.getWaterCompany(mContext);
//            if ((waterCompany != MyPrefs.getDefaultString()) && (waterCompany != " ")) {
//                bWater.setText(bWater.getText().toString() + "\n" + MyPrefs.getWaterCompany(mContext) );
//            }
//        }
//
//        //buttonLight name
//        {
//            String lightCompany = MyPrefs.getLightCompany(mContext);
//            if ((lightCompany != MyPrefs.getDefaultString()) && (lightCompany != " ")) {
//                bLight.setText(bLight.getText().toString() + "\n" + MyPrefs.getLightCompany(mContext) );
//            }
//        }

        //----------------
        //------LISTNER---
        //----------------

        //GAS
        //gasCompany = {" ", "Нижегородэнергогазрассчет"};
        {
            int saveGasPosition = MyPrefs.getGasPosition(mContext);
            if (saveGasPosition == 1) {
                bGas.setText(bGas.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getGasCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getGasNizhegorogEnergoGasRasschetAccount(mContext)
                        + "\n" + getString(R.string.region) +  ": " + MyPrefs.getGasNizhegorogEnergoGasRasschetLocation(mContext)
                );
            } else if (saveGasPosition == 0) {
                bGas.setVisibility(View.GONE);
            }
        }

        //WATER
        //waterCompany = {" ", "Центр СБК", "ЕРКЦ"};
        {
            int saveWaterPosition = MyPrefs.getWaterPosition(mContext);
            if (saveWaterPosition == 2) {
                bWater.setText(bWater.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getWaterCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getWaterErkcAccount(mContext)
                        + "\n" + getString(R.string.region) +  ": " + MyPrefs.getWaterErkcLocation(mContext)
                );
            } else if (saveWaterPosition == 1) {
                bWater.setText(bWater.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getWaterCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getWaterCentersbkAccount(mContext)
                );
            } else if (saveWaterPosition == 0) {
                bWater.setVisibility(View.GONE);
            }
        }

        //LIGHT
        //lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};
        {
            int saveLightPosition = MyPrefs.getLightPosition(mContext);
            if (saveLightPosition == 3) {
                bLight.setText(bLight.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getLightCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getLightTnsenergoAccount(mContext)
                        + "\n" + getString(R.string.region) +  ": " + MyPrefs.getLightTnsenergoLocation(mContext)
                );
                bLight.setClickable(false);
            } else if (saveLightPosition == 2) {
                bLight.setText(bLight.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getLightCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getLightErkcAccount(mContext)
                        + "\n" + getString(R.string.region) +  ": " + MyPrefs.getLightErkcLocation(mContext)
                );
            } else if (saveLightPosition == 1) {
                bLight.setText(bLight.getText().toString() + "\n"
                        + "\n" + getString(R.string.company) +  ": " + MyPrefs.getLightCompany(mContext)
                        + "\n" + getString(R.string.account) +  ": " + MyPrefs.getLightCentersbkAccount(mContext)
                );
            } else if (saveLightPosition == 0) {
                bLight.setVisibility(View.GONE);
            }
        }


        return v;
    }

    private void setMyFragment(int myContainerViewId, Fragment myFragment) {
        //my method
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(myContainerViewId, myFragment);
        transaction.commit();
    }


}
