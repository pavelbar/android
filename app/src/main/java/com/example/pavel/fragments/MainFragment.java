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
        final View v = inflater.inflate(R.layout.main_layout, container, false);

        manager = getFragmentManager();
        mContext = this.getActivity();
        ;

        //load buttonGas name
        {
            String gasCompany = MyPrefs.getGasCompany(mContext);
            if ((gasCompany != MyPrefs.getDefaultString()) && (gasCompany != " ")) {
                Button bGas = (Button) v.findViewById(R.id.buttonSendGas);
                bGas.setText(bGas.getText().toString() + "\n(" + MyPrefs.getGasCompany(mContext) + ")");
            }
        }

        //load buttonWater name
        {
            String waterCompany = MyPrefs.getWaterCompany(mContext);
            if ((waterCompany != MyPrefs.getDefaultString()) && (waterCompany != " ")) {
                Button bWater = (Button) v.findViewById(R.id.buttonSendWater);
                bWater.setText(bWater.getText().toString() + "\n(" + MyPrefs.getWaterCompany(mContext) + ")");
            }
        }

        //load buttonLight name
        {
            String lightCompany = MyPrefs.getLightCompany(mContext);
            if ((lightCompany != MyPrefs.getDefaultString()) && (lightCompany != " ")) {
                Button bLight = (Button) v.findViewById(R.id.buttonSendLight);
                bLight.setText(bLight.getText().toString() + "\n(" + MyPrefs.getLightCompany(mContext) + ")");
            }
        }

        return v;
    }


}
