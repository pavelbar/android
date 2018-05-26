package com.example.pavel.fragments;


import android.annotation.SuppressLint;
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
import android.widget.TextView;

import com.example.pavel.MainActivity;
import com.example.pavel.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
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

     TextView editText;

     OkHttpClient client;

     Request request;

     String DELETE = "";
    String urlCaptcha = "";
    String captcha_sid = "";
    String captcha_token = "";
    String captcha_response = "";
    String form_build_id = "";
    String form_id = "";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        v = inflater.inflate(R.layout.fragment_send_water_centersbk, container, false);

        // Initialize the progress dialog
        mProgressDialog = new ProgressDialog(this.getContext());
        mProgressDialog.setIndeterminate(false);
        // Progress dialog horizontal style
        mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        // Progress dialog title
        mProgressDialog.setTitle("AsyncTask");
        // Progress dialog message
        mProgressDialog.setMessage("Please wait, we are downloading...");


        //btn BACK
        View.OnClickListener oclbuttonToMainMenu = new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                ((MainActivity) getActivity()).setFragmentMain();
            }
        };
        Button buttonToMainMenu = (Button) v.findViewById(R.id.buttonToMainMenu);
        buttonToMainMenu.setOnClickListener(oclbuttonToMainMenu);

        //---------------------------------
        //PARSING
        //---------------------------------

        MyLoading myLoading = new MyLoading();
        myLoading.execute(url);


        //--
        editText = (TextView) v.findViewById(R.id.textViewPOSTanswer);

          client = new OkHttpClient();


        EditText editTextCaptchaWaterCenterSbk = (EditText) v.findViewById(R.id.editTextCaptchaWaterCenterSbk);
        captcha_response = editTextCaptchaWaterCenterSbk.getText().toString();

        RequestBody formBody = new FormBody.Builder()
                .add("acc[account_number]", "992111639") //acc%5Baccount_number%5D
                .add("captcha_sid", captcha_sid)
                .add("captcha_token", captcha_token)
                .add("captcha_response", captcha_response)
                .add("find_account", "OK")
                .add("form_build_id", form_build_id)
                .add("form_id", "readings_page_form")


//                .add("acc[account_number]", "992111639") //acc%5Baccount_number%5D
//                .add("captcha_sid", captcha_sid)
//                .add("captcha_token", captcha_token)
//                .add("captcha_response", captcha_response)
//                .add("find_account", "OK")
//                .add("form_build_id", form_build_id)
//                .add("form_id", form_id)
                .build();

          request = new Request.Builder()
                .url(url)
                  .post(formBody)
                .build();


        //btn continue
        View.OnClickListener oclbuttonToContinue = new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                asyncTask.execute();
            }
        };
        Button buttonToContinue = (Button) v.findViewById(R.id.buttonToContinue);
        buttonToContinue.setOnClickListener(oclbuttonToContinue);




        return v;
    }

    //----------------
    //LOADING
    //----------------

    private class MyLoading extends AsyncTask<String, Void, Document> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
        }

        @Override
        protected Document doInBackground(String... params) {
            int count = params.length;

            Document doc = null;

            try {
                doc = Jsoup.connect(url).timeout(0).maxBodySize(0).get();
                onProgressUpdate(50);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return doc;
        }

        // After each task done
        protected void onProgressUpdate(int progress) {
            // Update the progress bar on dialog
            mProgressDialog.setProgress(progress);
        }

        @Override
        protected void onPostExecute(Document doc) {
            super.onPostExecute(doc);

            final TextView tvInfo = (TextView) v.findViewById(R.id.captchaInfo);

            Elements images = doc.select("img");
            Elements hiddens = doc.select("input[type=hidden]");

            DELETE = hiddens.toString();

           captcha_sid = hiddens.toString().substring(47, 53);
           captcha_token = hiddens.toString().substring(105, 137);
           form_build_id = hiddens.toString().substring(189, 237);
           form_id = hiddens.toString().substring(283, 301);


            for (Element image : images) {
                urlCaptcha = image.absUrl("src");
            }
                //captcha_sid = urlCaptcha.substring(37, 43);
                //captcha_token= urlCaptcha.substring(47, urlCaptcha.length());

            //tvInfo.setText(urlCaptcha);
            //load image
            new DownloadImageTask((ImageView) v.findViewById(R.id.sendCaptchaWaterCenterSbk)).execute(urlCaptcha);

            onProgressUpdate(100);
            mProgressDialog.dismiss();
        }

    }


    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public DownloadImageTask(ImageView bmImage) {
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


    //

    @SuppressLint("StaticFieldLeak")
    AsyncTask<Void, Void, String> asyncTask = new AsyncTask<Void, Void, String>() {

        @Override
        protected String doInBackground(Void... params) {
            try {
                Response response = client.newCall(request).execute();
                if (!response.isSuccessful()) {
                    return null;
                }
                return response.body().string();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null) {
                editText.setText(DELETE + "\n\n sid: " +  captcha_sid + "\n\n token: " + captcha_token + "\n\n ответ: " + captcha_response + "\n\n build id:" + form_build_id + "\n\n form id: " + form_id + "\n\n" + s);

                //editText.setText(DELETE + "\n" +  captcha_sid + "\n" + captcha_token + "\n" + captcha_response + "\n" + form_build_id + "\n" + form_id);

               // textView.setText(urlCaptcha + "\n" + captcha_sid + "\n" + captcha_token);
                //String urlCaptcha = "";

            }
        }
    };


}
