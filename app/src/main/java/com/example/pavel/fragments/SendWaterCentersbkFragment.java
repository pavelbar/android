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


public class SendWaterCentersbkFragment extends Fragment {

    private final String url = "http://www.bcnn.ru/cntr_readings/";
    private View v;

    private ProgressDialog mProgressDialog;


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
        mProgressDialog.setMessage("Please wait, we are downloading your files...");


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
            String urlCaptcha = "";
            for (Element image : images) {
                urlCaptcha = image.absUrl("src");
            }
            //tvInfo.setText(urlCaptcha);
            //load image
            new DownloadImageTask((ImageView) v.findViewById(R.id.sendWaterCenterSbk)).execute(urlCaptcha);

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

}
