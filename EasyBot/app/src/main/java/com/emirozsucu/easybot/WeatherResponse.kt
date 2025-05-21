package com.emirozsucu.easybot.model

data class WeatherResponse(
    val name: String,
    val main: Main,
    val weather: List<Weather>
)

data class Main(
    val temp: Double,       // Sıcaklık
    val humidity: Int       // Nem
)

data class Weather(
    val description: String // Açıklama (örneğin: "clear sky")
)
