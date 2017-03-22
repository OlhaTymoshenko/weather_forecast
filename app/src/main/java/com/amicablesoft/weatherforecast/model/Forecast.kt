package com.amicablesoft.weatherforecast.model

import java.util.*

/**
 * Created by olha on 3/21/17.
 */
data class Forecast(val date: Date, val tempMin: Int, val tempMax: Int, val iconUrl: String)
