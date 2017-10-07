package com.rijo.umbrella.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rijo.umbrella.R;
import com.rijo.umbrella.Utilities;
import com.rijo.umbrella.services.FetchWeatherService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(Utilities.IsInternetAvailable(getApplicationContext())) {
            updateWeatherData();
        }
        else {
            showNoNetworkDialog();
        }
    }

    private void updateWeatherData() {
        Intent intent=new Intent(this, FetchWeatherService.class);
        startService(intent);
    }

    private void showNoNetworkDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.internet_dialog_message)
                .setTitle(R.string.internet_dialog_title);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        });

        AlertDialog dialog = builder.create();
        builder.show();
    }
}

