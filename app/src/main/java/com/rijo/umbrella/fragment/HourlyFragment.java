package com.rijo.umbrella.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.rijo.umbrella.R;
import com.rijo.umbrella.activities.MainActivity;
import com.rijo.umbrella.adpters.HourlyWeatherAdapter;
import com.rijo.umbrella.model.HourlyWeather;
import com.rijo.umbrella.model.HourlyWeathers;

import java.util.List;

/**
 * Created by rijogeorge on 10/8/17.
 */

public class HourlyFragment extends Fragment {

    HourlyWeathers hourlyWeatherList;
    String headding;
    private boolean showMetric;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            hourlyWeatherList = getArguments().getParcelable(MainActivity.HOURLY_WEATHER);
            headding=getArguments().getString(MainActivity.HOURLY_WEATHER_DAY);
            showMetric=getArguments().getBoolean(MainActivity.HOURLY_WEATHER_SHOWMETRIC);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dayweather_fragment, container, false);
        TextView headdingTv=(TextView)view.findViewById(R.id.dayTv);
        headdingTv.setText(headding);
        RecyclerView mRecyclerView=(RecyclerView)view.findViewById(R.id.hourly_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        GridLayoutManager mLayoutManager=new GridLayoutManager(getActivity().getApplicationContext(),4);
        mRecyclerView.setLayoutManager(mLayoutManager);
        HourlyWeatherAdapter hourlyAdapter=new HourlyWeatherAdapter(hourlyWeatherList,getActivity().getApplicationContext(),showMetric);
        mRecyclerView.setAdapter(hourlyAdapter);
        return view;
    }
}
