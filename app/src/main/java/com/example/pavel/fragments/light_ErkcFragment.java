package com.example.pavel.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.pavel.MyPrefs;
import com.example.pavel.R;

public final class light_ErkcFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.light_erkc_layout, container, false);

        String[] regions = {" ", "Дзержинск", "Кстово", "Балахна"};


        Spinner spinnerRegion = (Spinner) v.findViewById(R.id.spinnerLocationsErkc_light);
        ArrayAdapter<String> adapterRegion = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, regions);
        adapterRegion.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRegion.setAdapter(adapterRegion);

        //load Account
        {
            String account = MyPrefs.getLightErkcAccount(this.getActivity());
            if (account != "default") {
                EditText editTextAccount = (EditText) v.findViewById(R.id.editTextErkcAccount_light);
                editTextAccount.setText(account);
            }
        }

        //load Region
        int spinnerPosition = adapterRegion.getPosition(MyPrefs.getLightErkcLocation(this.getActivity()));
        spinnerRegion.setSelection(spinnerPosition);

        return v;
    }
}