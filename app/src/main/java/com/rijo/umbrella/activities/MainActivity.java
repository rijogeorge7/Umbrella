package com.rijo.umbrella.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rijo.umbrella.R;
import com.rijo.umbrella.fragment.HourlyFragment;
import com.rijo.umbrella.model.HourlyWeather;
import com.rijo.umbrella.model.HourlyWeathers;
import com.rijo.umbrella.model.Repository;
import com.rijo.umbrella.model.Weather;
import com.rijo.umbrella.util.Utilities;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class MainActivity extends AppCompatActivity {

    public static final String HOURLY_WEATHER="com.rijo.umbrella.activities.MainActivity.weather.hourlyWeather";
    public static final String HOURLY_WEATHER_DAY="com.rijo.umbrella.activities.MainActivity.weather.hourlyWeatherDay";
    public static final String HOURLY_WEATHER_SHOWMETRIC="com.rijo.umbrella.activities.MainActivity.weather.hourlyWeatherShowMetric";
    public static boolean preferenceChanged=false;
    Repository repository;
    boolean showMetric;
    private String zip;
    SharedPreferences SPreference;
    TextView locationTv,tempTv,statusTv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        repository=new Repository();
        tempTv=(TextView)findViewById(R.id.tempTv);
        statusTv=(TextView)findViewById(R.id.statusTv);
        if (Utilities.IsInternetAvailable(getApplicationContext())) {
            String unit = SPreference.getString("showMetricString", "1");
            if (unit.equals("1"))
                showMetric = true;
            else
                showMetric = false;
            //showMetric=SPreference.getBoolean("showMetric",true);
            zip = SPreference.getString("zipValue", null);
            if (zip == null) {
                showEnterZipDialog();
            } else {
                updateWeatherData(zip);
            }

        } else {
            showNoNetworkDialog();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(preferenceChanged) {
            preferenceChanged=false;
            String unit = SPreference.getString("showMetricString", "1");
            if (unit.equals("1"))
                showMetric = true;
            else
                showMetric = false;
            //showMetric=SPreference.getBoolean("showMetric",true);
            zip = SPreference.getString("zipValue", null);
            if (zip == null) {
                showEnterZipDialog();
            } else {
                updateWeatherData(zip);
            }
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void updateWeatherData(final String zip) {
        Weather weather=null;
        ExecutorService executor= Executors.newSingleThreadExecutor();
        Future<Weather> imageFuture=executor.submit(new Callable<Weather>() {
            @Override
            public Weather call() throws Exception {
                final Weather weather=repository.fetchWeatherData(zip);
                if(weather!=null){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            updateGUI(weather);
                        }
                    });

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
        ImageButton preferenceButton=(ImageButton)findViewById(R.id.preferenceButton);
        preferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UmbrellaPreferencesActivity.class));
            }
        });
        ConstraintLayout headView=(ConstraintLayout)findViewById(R.id.headView);
        if(weather.getTempF()>60){
            headView.setBackgroundColor(getResources().getColor(R.color.colorHotTemp));
        }
        else {
            headView.setBackgroundColor(getResources().getColor(R.color.colorCoolTemp));
        }
        locationTv=(TextView)findViewById(R.id.locationTv);
        locationTv.setText(weather.getLocation());
        if(showMetric)
            tempTv.setText(String.valueOf(Math.round(weather.getTempF())));
        else
            tempTv.setText(String.valueOf(Math.round(weather.getTempC())));
        statusTv.setText(weather.getWeatherStatus());
        HourlyFragment hFragment=new HourlyFragment();
        Bundle args = new Bundle();
        HourlyWeathers hWeathers=new HourlyWeathers();
        hWeathers.setHourlyWeather((ArrayList)weather.getDay1());
        args.putParcelable(HOURLY_WEATHER,hWeathers);
        args.putString(HOURLY_WEATHER_DAY,"Today");
        args.putBoolean(HOURLY_WEATHER_SHOWMETRIC,showMetric);
        hFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .add(R.id.day1Container,hFragment,"hourlyFragmentToday")
                .commit();

        //adding tomorrows data
        hFragment=new HourlyFragment();
        args = new Bundle();
        hWeathers=new HourlyWeathers();
        hWeathers.setHourlyWeather((ArrayList)weather.getDay2());
        args.putParcelable(HOURLY_WEATHER,hWeathers);
        args.putString(HOURLY_WEATHER_DAY,"Tomorrow");
        args.putBoolean(HOURLY_WEATHER_SHOWMETRIC,showMetric);
        hFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .add(R.id.tomorrowContainer,hFragment,"hourlyFragmentTomorrow")
                .commit();

        //adding day after tomorrows data (but not geting day after tomorrow from api)

        /*hFragment=new HourlyFragment();
        args = new Bundle();
        hWeathers=new HourlyWeathers();
        hWeathers.setHourlyWeather((ArrayList)weather.getDay3());
        args.putParcelable(HOURLY_WEATHER,hWeathers);
        args.putString(HOURLY_WEATHER_DAY,"Day after tomorrow");
        args.putBoolean(HOURLY_WEATHER_SHOWMETRIC,showMetric);
        hFragment.setArguments(args);
        getFragmentManager().beginTransaction()
                .add(R.id.dayaftertContainer,hFragment,"hourlyFragmentDayAfterTomorrow")
                .commit();*/


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

    private void showEnterZipDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter zip code");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_NUMBER);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String m_Text = input.getText().toString();
                if(m_Text.length()==5){
                    //SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = SPreference.edit();
                    editor.putString("zipValue", m_Text);
                    editor.commit();
                    updateWeatherData(m_Text);

                }
                else{
                    zipInputErrorDialog();
                }
            }
        });

        builder.show();
    }
    private void zipInputErrorDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage(R.string.enter_correct_zip)
                .setTitle(R.string.zip_issue);
        builder.setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                showEnterZipDialog();
            }
        });

        AlertDialog dialog = builder.create();
        builder.show();
    }
}


