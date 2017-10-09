package com.rijo.umbrella.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.rijo.umbrella.R;
import com.rijo.umbrella.model.Repository;
import com.rijo.umbrella.model.Weather;
import com.rijo.umbrella.util.Utilities;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    Repository repository;
    boolean showMetric=true;
    TextView locationTv,tempTv,statusTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        repository=new Repository();
        locationTv=(TextView)findViewById(R.id.locationTv);
        tempTv=(TextView)findViewById(R.id.tempTv);
        statusTv=(TextView)findViewById(R.id.statusTv);
        if(Utilities.IsInternetAvailable(getApplicationContext())) {
            updateWeatherData();
        }
        else {
            showNoNetworkDialog();
        }
    }

    private void updateWeatherData() {
        Weather weather=null;
        ExecutorService executor= Executors.newSingleThreadExecutor();
        Future<Weather> imageFuture=executor.submit(new Callable<Weather>() {
            @Override
            public Weather call() throws Exception {
                Weather weather=repository.fetchWeatherData();
                if(weather!=null){
                    updateGUI(weather);
                }
                else {

                }
                return weather;
            }
        });
        try {
            imageFuture.get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        //Weather weather=repository.fetchWeatherData();
    }

    private void updateGUI(Weather weather) {
        locationTv.setText(weather.getLocation());
        if(showMetric)
            tempTv.setText(String.valueOf(Math.round(weather.getTempF())));
        else
            tempTv.setText(String.valueOf(Math.round(weather.getTempC())));
        statusTv.setText(weather.getWeatherStatus());

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


