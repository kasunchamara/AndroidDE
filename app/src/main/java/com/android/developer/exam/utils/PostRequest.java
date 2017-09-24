package com.android.developer.exam.utils;

import android.content.Context;
import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Iterator;

import javax.net.ssl.HttpsURLConnection;

import com.android.developer.exam.cmn.IPostResponse;
import com.android.developer.exam.R;

/**
 * Created by KASUN CHAMARA on 2017-09-23.
 */

public class PostRequest {
    private String key1,key2,value1,value2;
    private IPostResponse pIPR;
    private Context pContext;

    public void PostRequest(Context mContext,IPostResponse ipr,String pkey1,String pvalue1,String pkey12,String pvalue2){
        key1 = pkey1;
        value1 = pvalue1;
        key2 = pkey12;
        value2 = pvalue2;
        pIPR = ipr;
        pContext = mContext;
        new SendPostRequest().execute();
    }

    public class SendPostRequest extends AsyncTask<String, Void, String> {

        protected void onPreExecute(){}

        protected String doInBackground(String... arg0) {

            try {

                /*
                * Request URL
                * */
                URL url = new URL(pContext.getString(R.string.request_url));

                JSONObject postDataParams = new JSONObject();

                postDataParams.put(key1, value1);
                postDataParams.put(key2, value2);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                writer.write(getPostDataString(postDataParams));
                writer.flush();
                writer.close();
                os.close();

                int responseCode=conn.getResponseCode();

                if (responseCode == HttpsURLConnection.HTTP_OK) {

                    BufferedReader in=new BufferedReader(new
                            InputStreamReader(
                            conn.getInputStream()));
                    StringBuffer sb = new StringBuffer("");
                    String line="";

                    while((line = in.readLine()) != null) {
                        sb.append(line);
                        break;
                    }
                    in.close();
                    return sb.toString();
                }
                else {
                    return new String("False : "+responseCode);
                }
            }
            catch(Exception e){
                return new String("Exception: " + e.getMessage());
            }

        }

        @Override
        protected void onPostExecute(String result) {
            JSONObject jObject  = null;
            try {
                jObject = new JSONObject(result);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            JSONObject  pjObject = null;
            try {
                pjObject = jObject.getJSONObject("data");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            pIPR.postRequest(pjObject);
        }
    }

    public String getPostDataString(JSONObject params) throws Exception {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        Iterator<String> itrator = params.keys();

        while(itrator.hasNext()){
            String key= itrator.next();
            Object value = params.get(key);
            if (first) {
                first = false;
            }
            else {
                result.append("&");
            }
            result.append(URLEncoder.encode(key, "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(value.toString(), "UTF-8"));
        }
        return result.toString();
    }
}
