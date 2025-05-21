package com.emirozsucu.easybot.api

import com.emirozsucu.easybot.Constants
import com.emirozsucu.easybot.model.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,  // Kullanıcının girdiği şehir
        @Query("appid") apiKey: String = Constants.WEATHER_API_KEY,
        @Query("units") units: String = "metric"  // Celsius
    ): WeatherResponse
}
