package com.android.developer.exam.main;

/**
 * Created by KASUN CHAMARA on 2017-09-19.
 */

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import com.android.developer.exam.cmn.IPostResponse;
import com.android.developer.exam.utils.Adapter;
import com.android.developer.exam.utils.PostRequest;

import com.android.developer.exam.R;

public class ActivityMonthlySales extends AppCompatActivity {

    public Context pContext = this;
    private ListView listView = null;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_monthly_sales);

        listView = (ListView) findViewById(R.id.list);
        bar = (ProgressBar) this.findViewById(R.id.progressBar2);
        new ProgressTaskMS().execute();
    }

    private class ProgressTaskMS extends AsyncTask<Object, Object, Void> {
        @Override
        protected void onPreExecute(){
            bar.setVisibility(View.VISIBLE);
            SendRequest();
        }

        @Override
        protected Void doInBackground(Object... arg0) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            bar.setVisibility(View.GONE);
        }
    }

    public void SendRequest(){

        PostRequest pr = new PostRequest();
        // interface
        IPostResponse postResponse = new IPostResponse(){

        @Override
        public void postRequest(JSONObject result){
            postResult(result);
        }
        };
        /*
        * monthly sales request parameter
        * @param String post data parameter data_type and monthly_sales
        * @param String post data parameter year and 2017
        *
        * */
        pr.PostRequest(pContext,postResponse,getString(R.string.ms_key1),getString(R.string.ms_value1),getString(R.string.ms_key2),getString(R.string.ms_value2));
    }

    private void postResult(JSONObject jsonResult){
        HashMap<String,String> map = new HashMap<String,String>();

        Iterator iterator = jsonResult.keys();
        while(iterator.hasNext()){
            String key = (String)iterator.next();
            String value = null;
            try {
                value = jsonResult.getString(key);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            map.put(key,value);
        }

        Adapter adapter = new Adapter(map);
        listView.setAdapter(adapter);
    }
}