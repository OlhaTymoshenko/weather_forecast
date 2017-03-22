package com.amicablesoft.weatherforecast.ui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amicablesoft.weatherforecast.R;
import com.amicablesoft.weatherforecast.model.Forecast;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olha on 3/20/17.
 */

class WeatherAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    private final List<Forecast> forecasts = new ArrayList<>();

    WeatherAdapter (Context context) {
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_item, parent, false);
        return new WeatherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        WeatherViewHolder weatherViewHolder = (WeatherViewHolder) holder;
        Forecast forecast = forecasts.get(position);
        Picasso.with(context).load(forecast.getIconUrl()).into(weatherViewHolder.cardImage);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE dd MMMM");
        String date = simpleDateFormat.format(forecast.getDate());
        String title = date;

        weatherViewHolder.cardTitle.setText(title);
        weatherViewHolder.tempMax.setText(formatTemp(forecast.getTempMax()));
        weatherViewHolder.tempMin.setText(formatTemp(forecast.getTempMin()));
        weatherViewHolder.forecast = forecast;
    }

    @Override
    public int getItemCount() {
        return forecasts.size();
    }

    void setForecasts(List<Forecast> forecasts) {
        this.forecasts.clear();
        this.forecasts.addAll(forecasts);
        notifyDataSetChanged();
    }

    private String formatTemp(int temp) {
        return (temp > 0 ? "+" + String.valueOf(temp): String.valueOf(temp));
    }

    private class WeatherViewHolder extends RecyclerView.ViewHolder {

        ImageView cardImage;
        TextView cardTitle;
        TextView tempMax;
        TextView tempMin;
        Forecast forecast;

        WeatherViewHolder(View itemView) {
            super(itemView);
            cardImage = (ImageView) itemView.findViewById(R.id.card_image);
            cardTitle = (TextView) itemView.findViewById(R.id.card_title);
            tempMax = (TextView) itemView.findViewById(R.id.card_temp_max);
            tempMin = (TextView) itemView.findViewById(R.id.card_temp_min);
        }
    }
}

