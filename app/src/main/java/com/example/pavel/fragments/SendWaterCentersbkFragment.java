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
    private String newReadingWater = "";

    OkHttpClient client;
    String form_build_id = "";
    String theme_token = "";

    //captcha
    String captcha_sid = "";
    String captcha_token = "";
    String captcha_response = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_send_water_centersbk, container, false);

        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setIndeterminate(false);
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        mProgressDialog.setTitle("Загрузка");
        mProgressDialog.setMessage("Пожалуйста, подождите...");

        client = new OkHttpClient();

        //------------------------
        //get captcha
        //------------------------
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

                LinearLayout layoutSendCaptchaValueWaterCenterSbk = (LinearLayout) v.findViewById(R.id.layoutSendCaptchaWaterCenterSbk);
                layoutSendCaptchaValueWaterCenterSbk.setVisibility(View.GONE);

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

                EditText editText = (EditText) v.findViewById(R.id.editTextNewValueWaterCenterSbk);
                newReadingWater = editText.getText().toString();

                Toast toast = Toast.makeText(getContext(),
                        "(" + newReadingWater + ")", Toast.LENGTH_SHORT);
                toast.show();

                RequestChangeValues requestChangeValues = new RequestChangeValues();
                requestChangeValues.execute();

//                SendAjaxOne sendAjaxOne = new SendAjaxOne();
//                sendAjaxOne.execute();
//
//                SendAjaxTwo sendAjaxTwo = new SendAjaxTwo();
//                sendAjaxTwo.execute();

                SendChangeValues sendChangeValues = new SendChangeValues();
                sendChangeValues.execute();
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
            mProgressDialog.show();
            mProgressDialog.setProgress(50);

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
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            captcha_sid = getCaptchaSid(s);
            captcha_token = getCaptchaToken(s);

            form_build_id = getBuildId(s);

            //load image
            new DownloadImageTask((ImageView) v.findViewById(R.id.sendCaptchaWaterCenterSbk)).execute(getCaptchaUrl(s));

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);

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
            mProgressDialog.show();
            mProgressDialog.setProgress(50);

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
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            String currentReadingWater = getCurrentReadingWater(s);

            form_build_id = getBuildId(s);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText(
                            "Адрес: " + getAddres(s) + "\n" +
                            "Услуга: " + getServiceName(s) + "\n" +
                            "Номер прибора: " + getDeviceNumber(s) + "\n" +
                            "Предыдущее показание: " + getPreviousReadingWater(s) + "\n" +
                            "Текущее показание: " + currentReadingWater + "\n" +
                            "Количество потребленного ресурса: " + getAmountOfConsumedResource(s)
            );


            EditText editTextNewValueWaterCenterSbk = (EditText) v.findViewById(R.id.editTextNewValueWaterCenterSbk);
            editTextNewValueWaterCenterSbk.setText(currentReadingWater, TextView.BufferType.EDITABLE);

            mProgressDialog.dismiss();
        }

    }

    //----------------
    //----------------

    private class RequestChangeValues extends AsyncTask<Void, Void, String> {

        private Request requestChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
            mProgressDialog.setProgress(50);


            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("readings", "Изменить показания")
                    .add("count", "2")
                    .add("form_build_id", form_build_id)
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
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            form_build_id = getBuildId(s);
            theme_token = getThemeToken(s);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText(s);

            mProgressDialog.dismiss();
        }
    }

    //----------------
    //----------------
    //.add("cur1", service_currentReadingLight)

    private class SendAjaxOne extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
            mProgressDialog.setProgress(50);


            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("cur0", newReadingWater)
                    .add("cur1", "")
                    .add("count", "2")
                    .add("form_build_id", form_build_id)
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
                    .add("ajax_html_ids[]", "edit-cur0")
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
                    .add("ajax_page_state[theme_token]", theme_token)
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
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            theme_token = getThemeToken(s);

            textView.setText(s);

            mProgressDialog.dismiss();
        }
    }

    //----------------
    //----------------

    private class SendAjaxTwo extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
            mProgressDialog.setProgress(50);


            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("cur0", newReadingWater)
                    .add("cur1", "")
                    .add("count", "2")
                    .add("form_build_id", form_build_id)
                    .add("form_id", "readings_page_form")

                    .add("_triggering_element_name", "cur1")
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
                    .add("ajax_page_state[theme_token]", theme_token)
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
                return null;
            }
        }


        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            theme_token = getThemeToken(s);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText(s);

            mProgressDialog.dismiss();
        }
    }


    //----------------
    //----------------

    private class SendChangeValues extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
            mProgressDialog.setProgress(50);

            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", "992111639")
                    .add("count", "2")
                    .add("cur0", newReadingWater)
                    .add("cur1", "")
                    .add("form_build_id", form_build_id)
                    .add("form_id", "readings_page_form")
                    .add("readings", "Передать+показания")
                    .add("submit[ok]", "1")
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
                return null;
            }
        }

        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);
            textView.setText(s);

            mProgressDialog.dismiss();
        }
    }

    //------------------
    //MyMethods
    //------------------

    protected String getCaptchaSid(String html) {
        String tagStart = "<div class=\"captcha\"><input type=\"hidden\" name=\"captcha_sid\" value=\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getCaptchaToken(String html) {
        String tagStart = "<input type=\"hidden\" name=\"captcha_token\" value=\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getCaptchaUrl(String html) {
        String tagStart = "<img typeof=\"foaf:Image\" src=\"";
        String tagEnd = "\"";

        return "http://www.bcnn.ru" + getItemByTag(html, tagStart, tagEnd);
    }


    protected String getAddres(String html) {
        String tagStart = "<p><b>Адрес </b>";
        String tagEnd = "<";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getServiceName(String html) {
        String tagStart = "<tr class=\"service odd\"><td>";
        String tagEnd = "<";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getDeviceNumber(String html) {
        String tagEnd = "</td><td><div id=\"readings_0\"><div class=\"form-item form-type-textfield form-item-cur0 form-disabled\">";

        int endIndex = html.indexOf(tagEnd);

        return html.substring(endIndex - 32, endIndex - 22);
    }

    protected String getPreviousReadingWater(String html) {
        String tagEnd = "</td><td><div id=\"readings_0\"><div class=\"form-item form-type-textfield form-item-cur0 form-disabled\">";

        int endIndex = html.indexOf(tagEnd);

        return html.substring(endIndex - 13, endIndex);
    }

    protected String getCurrentReadingWater(String html) {
        String tagStart = "<input disabled=\"disabled\" type=\"text\" id=\"edit-cur0\" name=\"cur0\" value=\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getAmountOfConsumedResource(String html) {
        String tagStart = "</div></td><td><div id=\"resource_0\">";
        String tagEnd = "<";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getBuildId(String html) {
        String tagStart = "\"form_build_id\" value=\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getThemeToken(String html) {
        String tagStart = "\"theme_token\":\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getItemByTag(String html, String tagStart, String tagEnd) {

        int startIndex = html.lastIndexOf(tagStart) + tagStart.length();
        int endIndex = html.indexOf(tagEnd, startIndex);

        return html.substring(startIndex, endIndex);
    }


}