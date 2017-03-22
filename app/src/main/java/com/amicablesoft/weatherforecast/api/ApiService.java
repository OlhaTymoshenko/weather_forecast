package com.amicablesoft.weatherforecast.api;

import com.amicablesoft.weatherforecast.api.dto.WeatherDto;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by olha on 3/21/17.
 */

public interface ApiService {

    @GET("forecast/daily")
    Observable<WeatherDto> requestList(
            @Query("lat") double lat,
            @Query("lon") double lon,
            @Query("units") String units,
            @Query("APPID") String appId);
}
