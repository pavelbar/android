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
    String captcha_dataForParsing;
    String captcha_url = "";
    String captcha_sid = "";
    String captcha_token = "";
    String captcha_response = "";
    String captcha_form_build_id = "";

    //afterLogin
    String service_dataForParsing;
    String service_addres = "";
    String service_name = "";                   //услуга
    String service_deviceNumber = "";           //номер прибора
    String service_previousReading = "";        //предыдущее показание
    String service_currentReading = "";         //текущее показание
    String service_amountOfConsumedResource = "";//количество потребленного ресурса
    String service_form_build_id = "";

    //requestChangeValues
    String request_form_build_id = "";

    //changeValues
    String lightOldValue = "";


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
        //btn SEND VALUE
        //------------------------
        View.OnClickListener oclbuttonSendValue = new View.OnClickListener() {
            @Override
            public void onClick(View vi) {

                //Send RequestChangeValue

                RequestChangeValues requestChangeValues = new RequestChangeValues();
                requestChangeValues.execute();


//                SendAjaxOne sendAjaxOne = new SendAjaxOne();
//                sendAjaxOne.execute();

//                SendChangeValues sendChangeValues = new SendChangeValues();
//                sendChangeValues.execute();



            }
        };
        Button buttonSendValue = (Button) v.findViewById(R.id.buttonSendValue);
        buttonSendValue.setOnClickListener(oclbuttonSendValue);


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

            captcha_dataForParsing = s.substring(s.lastIndexOf("<div class=\"captcha\"><input type=\"hidden\" name=\"captcha_sid\" value=\"") + 68, s.lastIndexOf("<input type=\"hidden\" name=\"form_id\" value=\"readings_page_form\" />") + 61);

            captcha_sid = captcha_dataForParsing.substring(0, 6);
            captcha_token = captcha_dataForParsing.substring(60, 92);
            captcha_url = "http://www.bcnn.ru" + captcha_dataForParsing.substring(127, 170);
            //captcha_form_build_id = captcha_dataForParsing.substring(888, 930);

            String tagcaptcha_form_build_id = "<input type=\"hidden\" name=\"form_build_id\" value=\"";
            captcha_form_build_id = s.substring(s.lastIndexOf(tagcaptcha_form_build_id) + tagcaptcha_form_build_id.length(), s.lastIndexOf(tagcaptcha_form_build_id) + tagcaptcha_form_build_id.length() + 48);


            //load image
            new DownloadImageTask((ImageView) v.findViewById(R.id.sendCaptchaWaterCenterSbk)).execute(captcha_url);


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
                    .add("captcha_form_build_id", captcha_form_build_id)
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

            service_dataForParsing = s.substring(s.lastIndexOf("<tr class=\"service odd\"><td>") + 28, s.lastIndexOf("<tr class=\"service even\"><td>") - 19);

            service_addres = s.substring(s.lastIndexOf("<p><b>Адрес </b>") + 16,  s.lastIndexOf("/p></div></fieldset>") - 1);
            service_name = service_dataForParsing.substring(0, 3);
            service_deviceNumber = service_dataForParsing.substring(19, 29);
            service_previousReading = service_dataForParsing.substring(38, 51);
            service_currentReading = service_dataForParsing.substring(228, 241);
            service_amountOfConsumedResource = service_dataForParsing.substring(service_dataForParsing.length() - 13, service_dataForParsing.length());

            String tagservice_form_build_id = "<input type=\"hidden\" name=\"form_build_id\" value=\"";
            service_form_build_id = s.substring(s.lastIndexOf(tagservice_form_build_id) + tagservice_form_build_id.length(), s.lastIndexOf(tagservice_form_build_id) + tagservice_form_build_id.length() + 48);


            textView.setText(
                    "Адрес: " + service_addres + "\n" +
                    "Услуга: " + service_name + "\n" +
                            "Номер прибора: " + service_deviceNumber + "\n" +
                            "Предыдущее показание: " + service_previousReading + "\n" +
                            "Текущее показание: " + service_currentReading + "\n" +
                            "Количество потребленного ресурса: " + service_amountOfConsumedResource + "\n\n" +
                            "captcha_form_build_id: " + captcha_form_build_id + "\n\n" +
                            "service_form_build_id: " + service_form_build_id + "\n\n" +
                            service_dataForParsing + "\n\n" +
                            s
            );

            EditText editTextNewValueWaterCenterSbk = (EditText) v.findViewById(R.id.editTextNewValueWaterCenterSbk);
            editTextNewValueWaterCenterSbk.setText(service_currentReading, TextView.BufferType.EDITABLE);

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();

            /*
            captcha_sid = captcha_dataForParsing.toString().substring(0, 6);
             */
        }

    }

    //----------------
    //----------------

    private class RequestChangeValues extends AsyncTask<Void, Void, String> {

        private Request requestChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle("Запрос на изменение данных");
            mProgressDialog.setMessage("Please wait, we are downloading...");
            mProgressDialog.show();

            mProgressDialog.setProgress(10);

            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("readings", "Изменить показания")
                    .add("count", "2")
                    .add("form_build_id", service_form_build_id)
                    .add("form_id", "readings_page_form")
                    .build();

            requestChangeValues = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(requestChangeValues).execute();
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

            String tagrequest_form_build_id = "<input type=\"hidden\" name=\"form_build_id\" value=\"";
            request_form_build_id = s.substring(s.lastIndexOf(tagrequest_form_build_id) + tagrequest_form_build_id.length(), s.lastIndexOf(tagrequest_form_build_id) + tagrequest_form_build_id.length() + 48);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);

            textView.setText("REQUEST CHANGE VALUES answer\n\n" +
                    "captcha_form_build_id: " + captcha_form_build_id + "\n\n" +
                    "service_form_build_id: " + service_form_build_id + "\n\n" +
                    "request_form_build_id: " + request_form_build_id + "\n\n" +
                    s);

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();
        }
    }

    //----------------
    //----------------

    private class SendAjaxOne extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle("Запрос на изменение данных");
            mProgressDialog.setMessage("Please wait, we are downloading...");
            mProgressDialog.show();

            mProgressDialog.setProgress(10);




            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("cur0", service_currentReading)
                    .add("cur1", "")
                    .add("count", "2")
                    .add("form_build_id", request_form_build_id)
                    .add("form_id", "readings_page_form")

                    .add("_triggering_element_name", "cur0")
                    .add("ajax_html_ids[]", "skip-link")
                    .add("ajax_html_ids[]", "page-wrapper")
                    .add("ajax_html_ids[]", "page")
                    .add("ajax_html_ids[]", "header")
                    .add("ajax_html_ids[]", "logo")
                    .add("ajax_html_ids[]", "name-and-slogan")
                    .add("ajax_html_ids[]", "site-name")
                    .add("ajax_html_ids[]", "main-menu")
                    .add("ajax_html_ids[]", "main-menu-links")
                    .add("ajax_html_ids[]", "main-wrapper")
                    .add("ajax_html_ids[]", "main")
                    .add("ajax_html_ids[]", "sidebar-first")
                    .add("ajax_html_ids[]", "block-menu-menu-submenu")
                    .add("ajax_html_ids[]", "content")
                    .add("ajax_html_ids[]", "main-content")
                    .add("ajax_html_ids[]", "page-title")
                    .add("ajax_html_ids[]", "account_form")
                    .add("ajax_html_ids[]", "block-system-main")
                    .add("ajax_html_ids[]", "readings-page-form")
                    .add("ajax_html_ids[]", "edit-acc")
                    .add("ajax_html_ids[]", "readings_0")
                    .add("ajax_html_ids[]", "edit-cur0--2")
                    .add("ajax_html_ids[]", "resource_0")
                    .add("ajax_html_ids[]", "readings_1")
                    .add("ajax_html_ids[]", "edit-cur1")
                    .add("ajax_html_ids[]", "resource_1")
                    .add("ajax_html_ids[]", "readings_button")
                    .add("ajax_html_ids[]", "edit-submit")
                    .add("ajax_html_ids[]", "edit-submit-ok")
                    .add("ajax_html_ids[]", "edit-submit-submit")
                    .add("ajax_html_ids[]", "footer-wrapper")
                    .add("ajax_html_ids[]", "footer-columns")
                    .add("ajax_html_ids[]", "block-menu-menu-lower-menu")
                    .add("ajax_html_ids[]", "footer")
                    .add("ajax_html_ids[]", "block-block-1")


                    .add("ajax_page_state[theme]", "csbk_responsive")
                    .add("ajax_page_state[theme_token]", "qPEavy-Or2FliHIVlTaiQQpIQiPuyhgwqH2C6_Z-f1w")
                    .add("ajax_page_state[css][modules/system/system.base.css]", "1")
                    .add("ajax_page_state[css][modules/system/system.menus.css]", "1")
                    .add("ajax_page_state[css][modules/system/system.messages.css]", "1")
                    .add("ajax_page_state[css][modules/system/system.theme.css]", "1")
                    .add("ajax_page_state[css][modules/comment/comment.css]", "1")
                    .add("ajax_page_state[css][modules/field/theme/field.css]", "1")
                    .add("ajax_page_state[css][modules/node/node.css]", "1")
                    .add("ajax_page_state[css][modules/user/user.css]", "1")
                    .add("ajax_page_state[css][sites/all/modules/views/css/views.css]", "1")
                    .add("ajax_page_state[css][sites/all/modules/ckeditor/css/ckeditor.css]", "1")
                    .add("ajax_page_state[css][sites/all/modules/ctools/css/ctools.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/layout.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/style.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/colors.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/print.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/ie.css]", "1")
                    .add("ajax_page_state[css][sites/all/themes/csbk_responsive/css/ie6.css]", "1")
                    .add("ajax_page_state[js][misc/jquery.js]", "1")
                    .add("ajax_page_state[js][misc/jquery.once.js]", "1")
                    .add("ajax_page_state[js][misc/drupal.js]", "1")
                    .add("ajax_page_state[js][misc/jquery.cookie.js]", "1")
                    .add("ajax_page_state[js][misc/jquery.form.js]", "1")
                    .add("ajax_page_state[js][misc/form.js]", "1")
                    .add("ajax_page_state[js][misc/ajax.js]", "1")
                    .add("ajax_page_state[js][public://languages/ru_KLCLewJWvAqe-qHFp0Fga1K4nfZ1Azk9e5mXNfgYKKQ.js]", "1")
                    .add("ajax_page_state[js][misc/progress.js]", "1")
                    .add("ajax_page_state[js][misc/tableheader.js]", "1")
                    .add("ajax_page_state[js][sites/all/modules/support/disable_show_row_weights.js]", "1")
                    .add("ajax_page_state[js][sites/all/themes/csbk_responsive/js/script.js]", "1")
                    .build();

            sendChangeValues = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(sendChangeValues).execute();
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

            textView.setText("SEND ajax1  answer\n\n" +
                    s);


            textView.setText(
                    "sendAjax" + "\n\n" + s
            );

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();
        }
    }


    //----------------
    //----------------

    private class SendChangeValues extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.setIndeterminate(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            mProgressDialog.setTitle("Запрос на изменение данных");
            mProgressDialog.setMessage("Please wait, we are downloading...");
            mProgressDialog.show();

            mProgressDialog.setProgress(10);


//            acc[account_number]: 992111639
//            cur0: 000000217.000
//            cur1: 03660.000
//            submit[ok]: 1
//            readings: Передать показания
//            count: 2
//            form_build_id: form-m8MuHXAwX5yaIYCqRxZAcSlckWpK4vjkvtf8LtL_bzQ
//            form_id: readings_page_form

            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("cur0", service_currentReading)
                    .add("cur1", "03660.000")
                    .add("submit[ok]", "1")
                    .add("readings", "Передать показания")
                    .add("count", "2")
                    .add("form_build_id", request_form_build_id)
                    .add("form_id", "readings_page_form")
                    .build();

            sendChangeValues = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
        }

        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(sendChangeValues).execute();
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

            textView.setText("SEND CHANGE VALUES answer\n\n" +
                    s);


            textView.setText(
                    "finish" + "\n\n" + s
            );

            mProgressDialog.setProgress(100);
            mProgressDialog.dismiss();
        }
    }



}
