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

public final class InputGasNizhegorodenergogasrasschetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View v = inflater.inflate(R.layout.fragment_input_gas_nizhegorodenergogasrasschet, container, false);

        String[] regions = {" ", "Нижний Новгород", "Ардатовский", "Балахнинский", "Богородский", "Большеболдинский",
                "Большемурашенский", "Борский", "Бутурлинский", "Вадский", "Вачский", "Вознесенский", "Володарский", "Воротынский", "Ворсма",
                "Воскресенский", "Выксунский", "Ганинский", "Городецкий", "Дальнеконстантиновский", "Дзержинский", "Дивеевский", "Заволжье",
                "Княгининский", "Коверинский", "Краснооктябрьский", "Краснобаковский", "Кстовский", "Кулебакский", "Лукояновский", "Лысковский",
                "Навашинский", "Павловский", "Перевозский", "Первомайский", "Пильненский", "Починковский", "Семеновский", "Сергачский", "Сеченовский",
                "Сокольский", "Сосновский", "Спасский", "Уренский", "Чкаловский", "Шатковский"};


        Spinner spinnerRegion = (Spinner) v.findViewById(R.id.spinnerLocationsNizhegorogEnergoGasRasschet_gas);
        ArrayAdapter<String> adapterGasRegion = new ArrayAdapter<String>(this.getActivity(), android.R.layout.simple_spinner_item, regions);
        adapterGasRegion.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinnerRegion.setAdapter(adapterGasRegion);

        //load Account
        {
            String account = MyPrefs.getGasNizhegorogEnergoGasRasschetAccount(this.getActivity());
            if (account != MyPrefs.getDefaultString()) {
                EditText editTextAccount = (EditText) v.findViewById(R.id.editTextNizhegorogEnergoGasRasschetAccount_gas);
                editTextAccount.setText(account);
            }
        }

        //load Region
        int spinnerPosition = adapterGasRegion.getPosition(MyPrefs.getGasNizhegorogEnergoGasRasschetLocation(this.getActivity()));
        spinnerRegion.setSelection(spinnerPosition);

        return v;

    }
}