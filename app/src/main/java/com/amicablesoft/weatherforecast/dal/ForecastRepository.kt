package com.amicablesoft.weatherforecast.dal

import com.amicablesoft.weatherforecast.api.ApiService
import com.amicablesoft.weatherforecast.api.ApiServiceGenerator
import com.amicablesoft.weatherforecast.api.dto.ForecastDto
import com.amicablesoft.weatherforecast.model.Forecast
import rx.Observable
import java.util.*

/**
 * Created by olha on 3/21/17.
 */

class ForecastRepository {

    val apiService: ApiService

    init {
        val apiServiceGenerator = ApiServiceGenerator()
        apiService = apiServiceGenerator.createService(ApiService::class.java)
    }

    fun fetchForecastFor(latitude: Double, longitude: Double) : Observable<List<Forecast>> {
        val units = "metric"
        val appId = "a2f4c4a973a8855259648587d2268215"
        return apiService.requestList(latitude, longitude, units, appId)
                .map { dto ->
                    dto.list.take(6).map { dto -> dto.toForecast() }
                }
    }
}

fun ForecastDto.toForecast(): Forecast {
    return Forecast(
            Date(timestamp.toLong() * 1000L),
            this.main.tempMin.toInt(),
            this.main.tempMax.toInt(),
            "http://openweathermap.org/img/w/${this.weather.first().icon}.png"
    )
}
