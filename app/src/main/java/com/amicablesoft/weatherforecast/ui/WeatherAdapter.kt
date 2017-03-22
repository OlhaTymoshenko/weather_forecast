package com.amicablesoft.weatherforecast.ui

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.amicablesoft.weatherforecast.R
import com.amicablesoft.weatherforecast.model.Forecast
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by olha on 3/22/17.
 */
class WeatherAdapter(private val context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val forecasts = ArrayList<Forecast>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_view_item, parent, false)
        return WeatherViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val weatherViewHolder = holder as WeatherViewHolder
        val forecast = forecasts[position]
        Picasso.with(context).load(forecast.iconUrl).into(weatherViewHolder.cardImage)
        val simpleDateFormat = SimpleDateFormat("EEE dd MMMM")
        val title = simpleDateFormat.format(forecast.date)
        weatherViewHolder.cardTitle.text = title
        weatherViewHolder.tempMax.text = formatTemp(forecast.tempMax)
        weatherViewHolder.tempMin.text = formatTemp(forecast.tempMin)
        weatherViewHolder.forecast = forecast
    }

    override fun getItemCount(): Int {
        return forecasts.size
    }

    fun setForecasts(forecasts: List<Forecast>) {
        this.forecasts.clear()
        this.forecasts.addAll(forecasts)
        notifyDataSetChanged()
    }

    private fun formatTemp(temp: Int): String {
        return (if (temp > 0) "+" + temp.toString() else temp.toString()) + 0x00B0.toChar()
    }

    private inner class WeatherViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var cardImage: ImageView = itemView.findViewById(R.id.card_image) as ImageView
        internal var cardTitle: TextView = itemView.findViewById(R.id.card_title) as TextView
        internal var tempMax: TextView = itemView.findViewById(R.id.card_temp_max) as TextView
        internal var tempMin: TextView = itemView.findViewById(R.id.card_temp_min) as TextView
        internal var forecast: Forecast? = null
    }
}