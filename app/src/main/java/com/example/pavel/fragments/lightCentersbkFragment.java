package com.example.pavel.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.pavel.MyPrefs;
import com.example.pavel.R;

public class lightCentersbkFragment extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_light_centersbk, container, false);

        //load Account
        {
            String account = MyPrefs.getLightCentersbkAccount(this.getActivity());
            if (account != MyPrefs.getDefaultString()) {
                EditText editTextAccount = (EditText) v.findViewById(R.id.editTexCentersbkAccount_light);
                editTextAccount.setText(account);
            }
        }

        return v;

    }

}
