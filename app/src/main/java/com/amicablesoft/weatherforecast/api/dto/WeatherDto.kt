package com.amicablesoft.weatherforecast.api.dto

import com.google.gson.annotations.SerializedName

/**
 * Created by olha on 3/21/17.
 */
data class WeatherDto(
    @SerializedName("list") val list: List<ForecastDto>
)

data class ForecastDto(
    @SerializedName("temp") val main: MainItemDto,
    @SerializedName("weather") val weather: List<WeatherItemDto>,
    @SerializedName("dt") val timestamp: Int
)

data class MainItemDto(
    @SerializedName("min") val tempMin: Double,
    @SerializedName("max") val tempMax: Double
)

data class WeatherItemDto(
    @SerializedName("icon") val icon: String
)
