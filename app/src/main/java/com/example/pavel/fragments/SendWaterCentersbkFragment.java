package com.example.pavel.fragments;


import android.app.Fragment;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.pavel.MainActivity;
import com.example.pavel.MyPrefs;
import com.example.pavel.R;

import java.io.InputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SendWaterCentersbkFragment extends Fragment {

    private final String url = "http://www.bcnn.ru/cntr_readings/";

    private View v;

    private ProgressDialog mProgressDialog;

    OkHttpClient client;

    //captcha
    String dataCaptchaForParsing;
    String urlCaptcha = "";
    String captcha_sid = "";
    String captcha_token = "";
    String captcha_response = "";
    String form_build_id = "";

    //afterLogin
    String dataServiceForParsing;
    String serviceAdres = "";
    String serviceName = "";            //услуга
    String deviceNumber = "";           //номер прибора
    String previousReading = "";        //предыдущее показание
    String currentReading = "";         //текущее показание
    String amountOfConsumedResource = "";//количество потребленного ресурса

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_send_water_centersbk, container, false);

        mProgressDialog = new ProgressDialog(this.getContext());

        //---------------------------------
        //PARSING
        //---------------------------------


        client = new OkHttpClient();

        nGetCaptchaWaterCenterSbk ngetCaptchaWaterCenterSbk = new nGetCaptchaWaterCenterSbk();
        ngetCaptchaWaterCenterSbk.execute();


        //------------------------
        //btn continue
        //------------------------
        View.OnClickListener oclbuttonToContinue = new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                EditText editTextCaptchaWaterCenterSbk = (EditText) v.findViewById(R.id.editTextCaptchaWaterCenterSbk);
                captcha_response = editTextCaptchaWaterCenterSbk.getText().toString();

                SetCaptchaWaterCenterSbk setCaptchaWaterCenterSbk = new SetCaptchaWaterCenterSbk();
                setCaptchaWaterCenterSbk.execute();

                //after login

                Button btnSendValues = v.findViewById(R.id.buttonSendValue);
                btnSendValues.setVisibility(View.VISIBLE);

                LinearLayout layoutSendValue = v.findViewById(R.id.layoutSendValueWaterCenterSbk);
                layoutSendValue.setVisibility(View.VISIBLE);


            }
        };
        Button buttonToContinue = (Button) v.findViewById(R.id.buttonToContinue);
        buttonToContinue.setOnClickListener(oclbuttonToContinue);


        //------------------------
        //btn BACK
        //------------------------
        View.OnClickListener oclbuttonToMainMenu = new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                ((MainActivity) getActivity()).setFragmentMain();
            }
        };
        Button buttonToMainMenu = (Button) v.findViewById(R.id.buttonToMainMenu);
        buttonToMainMenu.setOnClickListener(oclbuttonToMainMenu);

        return v;
    }

    //----------------
    //----------------

    private class nGetCaptchaWaterCenterSbk extends AsyncTask<Void, Void, String> {

        private Request requestGetCaptcha;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle("AsyncTask");
            mProgressDialog.setMessage("Please wait, we are downloading...");
            mProgressDialog.show();

            mProgressDialog.setProgress(10);

            requestGetCaptcha = new Request.Builder()
                    .url(url)
                    .build();
        }

        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(requestGetCaptcha).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                mProgressDialog.setProgress(50);
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            dataCaptchaForParsing = s.substring(s.lastIndexOf("<div class=\"captcha\"><input type=\"hidden\" name=\"captcha_sid\" value=\"") + 68, s.lastIndexOf("<input type=\"hidden\" name=\"form_id\" value=\"readings_page_form\" />") + 61);

            captcha_sid = dataCaptchaForParsing.substring(0, 6);
            captcha_token = dataCaptchaForParsing.substring(60, 92);
            urlCaptcha = "http://www.bcnn.ru" + dataCaptchaForParsing.substring(127, 170);
            form_build_id = dataCaptchaForParsing.substring(888, 936);

            //load image
            new DownloadImageTask((ImageView) v.findViewById(R.id.sendCaptchaWaterCenterSbk)).execute(urlCaptcha);


            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText("GET CAPTCHA answer\n\n" + s);

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();
        }
    }


    //----------------
    //----------------


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        protected DownloadImageTask(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e(" ", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {

            bmImage.setImageBitmap(result);
        }
    }

    //----------------
    //----------------

    private class SetCaptchaWaterCenterSbk extends AsyncTask<Void, Void, String> {

        private Request requestSetCaptcha;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle("AsyncTask");
            mProgressDialog.setMessage("Please wait, we are downloading...");
            mProgressDialog.show();

            mProgressDialog.setProgress(10);

            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("captcha_sid", captcha_sid)
                    .add("captcha_token", captcha_token)
                    .add("captcha_response", captcha_response)
                    .add("find_account", "OK")
                    .add("form_build_id", form_build_id)
                    .add("form_id", "readings_page_form")
                    .build();

            requestSetCaptcha = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(requestSetCaptcha).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                mProgressDialog.setProgress(50);
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText("SET CAPTCHA answer\n\n" + s);

            dataServiceForParsing = s.substring(s.lastIndexOf("<tr class=\"service odd\"><td>") + 28, s.lastIndexOf("<tr class=\"service even\"><td>") - 19);

            serviceAdres = s.substring(s.lastIndexOf("<p><b>Адрес </b>") + 16,  s.lastIndexOf("/p></div></fieldset>") - 1);
            serviceName = dataServiceForParsing.substring(0, 3);
            deviceNumber = dataServiceForParsing.substring(19, 29);
            previousReading = dataServiceForParsing.substring(38, 51);
            currentReading = dataServiceForParsing.substring(228, 241);
            amountOfConsumedResource = dataServiceForParsing.substring(dataServiceForParsing.length() - 13, dataServiceForParsing.length());

            textView.setText(
                    "Адрес: " + serviceAdres + "\n" +
                    "Услуга: " + serviceName + "\n" +
                            "Номер прибора: " + deviceNumber + "\n" +
                            "Предыдущее показание: " + previousReading + "\n" +
                            "Текущее показание: " + currentReading + "\n" +
                            "Количество потребленного ресурса: " + amountOfConsumedResource + "\n\n" +
                            dataServiceForParsing
            );

            EditText editTextNewValueWaterCenterSbk = (EditText) v.findViewById(R.id.editTextNewValueWaterCenterSbk);
            editTextNewValueWaterCenterSbk.setText(currentReading, TextView.BufferType.EDITABLE);

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();

            /*
            captcha_sid = dataCaptchaForParsing.toString().substring(0, 6);
             */
        }

    }


}
