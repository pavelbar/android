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
    private String newValueWater = "";
    private String currentValueLight = "";

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
                newValueWater = editText.getText().toString();


                Toast toast = Toast.makeText(getContext(),
                        "(" + newValueWater + ")", Toast.LENGTH_SHORT);
                toast.show();



                RequestChangeValues requestChangeValues = new RequestChangeValues();
                requestChangeValues.execute();

                SendAjax sendAjax = new SendAjax();
                sendAjax.execute();

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
            textView.setText(s);

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
                    .add("acc[account_number]:", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("captcha_sid", captcha_sid)
                    .add("captcha_token", captcha_token)
                    .add("captcha_response", captcha_response)
                    .add("find_account", "OK")
                    .add("form_build_id", "form_build_id")
                    .add("form_id", "readings_page_form")
                    .build();

            requestSetCaptcha = new Request.Builder()
                    .url(url)

                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Mobile Safari/537.36")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    //.addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "227")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cookie", "has_js=1")
                    .addHeader("Host", "www.bcnn.ru")
                    .addHeader("Origin", "http://www.bcnn.ru")
                    .addHeader("Referer", "http://www.bcnn.ru/cntr_readings")
                    .addHeader("Upgrade-Insecure-Requests", "1")

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

            String currentValueWater = getCurrentValueWater(s);

            currentValueLight = getCurrentValueLight(s);

            form_build_id = getBuildId(s);

            TextView textView = (TextView) v.findViewById(R.id.textViewInfoWaterCenterSbk);

            textView.setText(
                            "Адрес: " + getAddres(s) + "\n" +
                            "Услуга: " + getServiceName(s) + "\n" +
                            "Номер прибора: " + getDeviceNumber(s) + "\n" +
                            "Предыдущее показание: " + getOldValueWater(s) + "\n" +
                            "Текущее показание: " + currentValueWater + "\n" +
                            "Количество потребленного ресурса: " + getAmountOfConsumedResource(s) +
                            "\n\n" + s
            );


            EditText editTextNewValueWaterCenterSbk = (EditText) v.findViewById(R.id.editTextNewValueWaterCenterSbk);
            editTextNewValueWaterCenterSbk.setText(currentValueWater, TextView.BufferType.EDITABLE);

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
                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Mobile Safari/537.36")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    //.addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "244")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cookie", "has_js=1")
                    .addHeader("Origin", "http://www.bcnn.ru")
                    .addHeader("Referer", "http://www.bcnn.ru/cntr_readings")
                    .addHeader("Upgrade-Insecure-Requests", "1")

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

    private class SendAjax extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
            mProgressDialog.setProgress(50);



            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", MyPrefs.getWaterCentersbkAccount(getContext()))
                    .add("cur0", newValueWater)
                    .add("cur1", currentValueLight)
                    .add("count", "2")
                    .add("form_build_id", form_build_id)
                    .add("form_id", "readings_page_form")
                    .add("_triggering_element_name", "cur0")

                    .build();

                    /*
            Accept-Encoding: gzip, deflate
            Accept-Language: ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7
            Connection: keep-alive
            Content-Length: 3495
            Content-Type: application/x-www-form-urlencoded
            Cookie: has_js=1
            Host: www.bcnn.ru
            Origin: http://www.bcnn.ru
            Referer: http://www.bcnn.ru/cntr_readings
            X-Requested-With: XMLHttpRequest
                     */

            sendChangeValues = new Request.Builder()
                    .url(url)
                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Mobile Safari/537.36")
                    .addHeader("Accept", "application/json, text/javascript, */*; q=0.01")
                    //.addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "3495")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cookie", "has_js=1")
                    .addHeader("Host", "www.bcnn.ru")
                    .addHeader("Origin", "http://www.bcnn.ru")
                    .addHeader("Referer", "http://www.bcnn.ru/cntr_readings")
                    .addHeader("X-Requested-With", "XMLHttpRequest")

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

   ///////////
    ///////////

    private class SendChangeValues extends AsyncTask<Void, Void, String> {

        private Request sendChangeValues;

        protected void onPreExecute() {
            super.onPreExecute();

            mProgressDialog.show();
            mProgressDialog.setProgress(50);

            RequestBody formBody = new FormBody.Builder()
                    .add("acc[account_number]", "992111639")
                    .add("cur0", newValueWater)
                    .add("cur1", currentValueLight)
                    .add("submit[ok]", "1")
                    .add("readings", "Передать показания")
                    .add("count", "2")
                    .add("form_build_id", form_build_id)
                    .add("form_id", "readings_page_form")

                    .build();

            sendChangeValues = new Request.Builder()
                    .url(url)

                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.170 Mobile Safari/537.36")
                    .addHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8")
                    //.addHeader("Accept-Encoding", "gzip, deflate")
                    .addHeader("Accept-Language", "ru-RU,ru;q=0.9,en-US;q=0.8,en;q=0.7")
                    .addHeader("Cache-Control", "max-age=0")
                    .addHeader("Connection", "keep-alive")
                    .addHeader("Content-Length", "295")
                    .addHeader("Content-Type", "application/x-www-form-urlencoded")
                    .addHeader("Cookie", "has_js=1")
                    .addHeader("Host", "www.bcnn.ru")
                    .addHeader("Origin", "http://www.bcnn.ru")
                    .addHeader("Referer", "http://www.bcnn.ru/cntr_readings")
                    .addHeader("Upgrade-Insecure-Requests", "1")

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

    protected String getOldValueWater(String html) {
        String tagEnd = "</td><td><div id=\"readings_0\"><div class=\"form-item form-type-textfield form-item-cur0 form-disabled\">";

        int endIndex = html.indexOf(tagEnd);

        return html.substring(endIndex - 13, endIndex);
    }

    protected String getCurrentValueWater(String html) {
        String tagStart = "<input disabled=\"disabled\" type=\"text\" id=\"edit-cur0\" name=\"cur0\" value=\"";
        String tagEnd = "\"";

        return getItemByTag(html, tagStart, tagEnd);
    }

    protected String getCurrentValueLight(String html) {
        String tagStart = "<input disabled=\"disabled\" type=\"text\" id=\"edit-cur1\" name=\"cur1\" value=\"";
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