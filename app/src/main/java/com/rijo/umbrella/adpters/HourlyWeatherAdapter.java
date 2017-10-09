package com.rijo.umbrella.adpters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.rijo.umbrella.R;
import com.rijo.umbrella.model.HourlyWeathers;

/**
 * Created by rijogeorge on 10/9/17.
 */

public class HourlyWeatherAdapter extends RecyclerView.Adapter<HourlyWeatherAdapter.ViewHolder>{

    private HourlyWeathers hourlyWeathers;
    private Context context;
    private boolean showMetric;

    public HourlyWeatherAdapter(HourlyWeathers hourlyWeathers, Context context,boolean showMetric) {
        this.hourlyWeathers = hourlyWeathers;
        this.context = context;
        this.showMetric = showMetric;
    }

    @Override
    public HourlyWeatherAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView= LayoutInflater.from(parent.getContext()).inflate(R.layout.hourly_weather_row, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(HourlyWeatherAdapter.ViewHolder holder, int position) {
        holder.hourTextView.setText(hourlyWeathers.getHourlyWeather().get(position).getHour());
        if(showMetric)
            holder.tempTextView.setText(hourlyWeathers.getHourlyWeather().get(position).getTempF());
        else
            holder.tempTextView.setText(hourlyWeathers.getHourlyWeather().get(position).getTempC());
        Glide.with(context)
                .load(hourlyWeathers.getHourlyWeather().get(position).getImageUrl())
                .into(holder.icon);
    }

    @Override
    public int getItemCount() {
        return hourlyWeathers.getHourlyWeather().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView hourTextView,tempTextView;
        private ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            hourTextView=itemView.findViewById(R.id.hourTextView);
            tempTextView=itemView.findViewById(R.id.tempTextView);
            icon=itemView.findViewById(R.id.tempIcon);
        }
    }
}
