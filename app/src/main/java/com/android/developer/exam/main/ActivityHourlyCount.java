package com.android.developer.exam.main;

/**
 * Created by KASUN CHAMARA on 2017-09-23.
 */

import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import com.android.developer.exam.cmn.IPostResponse;
import com.android.developer.exam.utils.PostRequest;

import com.android.developer.exam.R;

public class ActivityHourlyCount extends AppCompatActivity {

    public Context pContext = this;
    private BarChart barChart = null;
    private ArrayList<String> labels = null;
    private BarDataSet dataset = null;
    private BarData data = null;
    private ArrayList<BarEntry> entries = null;
    private int i = 0;
    private ProgressBar bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_hourly_count);

        barChart = (BarChart) findViewById(R.id.chart);
        labels = new ArrayList<String>();
        entries = new ArrayList<>();

        bar = (ProgressBar) this.findViewById(R.id.progressBar);
        new ProgressTask().execute();
    }

    public void onResume(){
        super.onResume();
    }

    private class ProgressTask extends AsyncTask<Object, Object, Void> {
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
        IPostResponse postResponse = new IPostResponse(){

            @Override
            public void postRequest(JSONObject result){
                postResult(result);
            }
        };

         /*
        * activity hourly count request parameter
        * @param String post data parameter data_type and hourly_activity_count
        * @param String post data parameter date and 15-09-2017
        *
        * */
        pr.PostRequest(pContext,postResponse,getString(R.string.hac_key1),getString(R.string.hac_value1),getString(R.string.hac_key2),getString(R.string.hac_value2));
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
            labels.add(key);
            entries.add(new BarEntry(Integer.valueOf(value), i));
            i++;
        }

        dataset = new BarDataSet(entries, "Activity Count");
        data = new BarData(labels, dataset);

        dataset.setDrawValues(false);
        dataset.setColors(LIGHT_BLUE_COLORS);

        barChart.setData(data);

        barChart.getAxisLeft().setDrawGridLines(false);
        barChart.getXAxis().setDrawGridLines(false);

        barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        barChart.getAxisRight().setDrawLabels(false);
        barChart.getXAxis().setLabelsToSkip(0);

        barChart.setDoubleTapToZoomEnabled(false);
        barChart.setPinchZoom(false);
        barChart.setDescription("");
        barChart.animateY(5000);

        barChart.setGridBackgroundColor(Color.WHITE);
        dataset.setColors(LIGHT_BLUE_COLORS);
    }

    public static final int[] LIGHT_BLUE_COLORS = {
            Color.rgb(109, 158, 235)
    };

}
