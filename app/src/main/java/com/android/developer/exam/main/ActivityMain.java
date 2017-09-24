package com.android.developer.exam.main;

/**
 * Created by KASUN CHAMARA on 2017-09-23.
 */

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.android.developer.exam.R;

public class ActivityMain extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /*
    *
    * Button onClick event selectActivity()
    * @param btn_monthly_sales - Main activity monthly sales button
    * @param btn_hourly_ac - Main activity activity hourly count button
    *
    * */

    public void selectActivity(View v) {
        switch (v.getId()){
            case R.id.btn_monthly_sales:
                if(isNetworkConnected()){
                    Intent i = new Intent(this,ActivityMonthlySales.class);
                    startActivity(i);
                }else{
                    dialogBox();
                }
                break;

            case R.id.btn_hourly_ac:
                if(isNetworkConnected()){
                    Intent j = new Intent(this,ActivityHourlyCount.class);
                    startActivity(j);
                }else{
                    dialogBox();
                }
                break;
        }
    }

    private void dialogBox(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Please check your internet connection.");
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
