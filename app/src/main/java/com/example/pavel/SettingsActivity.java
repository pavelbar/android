package com.example.pavel;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class SettingsActivity extends AppCompatActivity {

    String[] locations = {" ", "Нижний Новгород", "Ардатов", "Арзамас", "Арья", "Афонино", "Балахна", "Богородск", "Большое Болдино", "Большое Козино", "Большое Мурашкино",
            "Бор", "Буревестник", "Бутурлино", "Вад", "Варнавино", "Вахтан", "Вача", "Ветлуга", "Ветлужский", "Виля", "Возненское", "Володарск", "Воротынец", "Ворсме",
            "Воскресенское", "Выездное", "Выкса", "Гагино", "Гидроторф", "Горбатов", "Горбатовка", "Городец", "Гремячево", "Дальнее Константиново", "Дзержинск", "Дивеево",
            "Досчатое", "Дружба", "Ждановский", "Заволжье", "Ильиногорск", "Княгинино", "Коверино", "Красные Баки", "Кстово", "Кулебаки", "Линда", "Лукино", "Лукоянов",
            "Лысково", "Мулино", "Мухтолово", "Новашино", "Новосмолинский", "Павлово", "Память Парижской Комуны", "Первомайск", "Перевоз", "Пижма", "Пильна", "Починки",
            "Решетиха", "Саваслейка", "Саров", "Сатис", "Семенов", "Сергач", "Сеченово", "Сокольское", "Сосновское", "Спасское", "Степана Разина", "Суроватиха",
            "Сухобезводное", "Сява", "Тонкино", "Тоншаево", "Тумботино", "Урень", "Центральный", "Чернуха", "Чкаловск", "Шаранга", "Шатки", "Шахунья", "Шиморское", "Юганец"};

    String[] gasCompany = {" ", "Нижегородэнергогазрассчет"};

    String[] waterCompany = {" ", "Центр СБК", "ЕРКЦ"};

    String[] lightCompany = {" ", "Центр СБК", "ЕРКЦ", "ТНСЭНЕРГО"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);


        Spinner spinnerLocations = (Spinner) findViewById(R.id.spinnerLocations);
        ArrayAdapter<String> adapterLocations = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, locations);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterLocations.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerLocations.setAdapter(adapterLocations); // Применяем адаптер к элементу spinner

        Spinner spinnerGasCompany = (Spinner) findViewById(R.id.spinnerGasCompany);
        ArrayAdapter<String> adapterGasCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gasCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterGasCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerGasCompany.setAdapter(adapterGasCompany); // Применяем адаптер к элементу spinner

        Spinner spinnerWaterCompany = (Spinner) findViewById(R.id.spinnerWaterCompany);
        ArrayAdapter<String> adapterWaterCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, waterCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterWaterCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerWaterCompany.setAdapter(adapterWaterCompany); // Применяем адаптер к элементу spinner

        Spinner spinnerLightCompany = (Spinner) findViewById(R.id.spinnerLightCompany);
        ArrayAdapter<String> adapterLightCompany = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lightCompany);// Создаем адаптер ArrayAdapter с помощью массива строк и стандартной разметки элемета spinner
        adapterLightCompany.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);// Определяем разметку для использования при выборе элемента
        spinnerLightCompany.setAdapter(adapterLightCompany); // Применяем адаптер к элементу spinner

    }
}
