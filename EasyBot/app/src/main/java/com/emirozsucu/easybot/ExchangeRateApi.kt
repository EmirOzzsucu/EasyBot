package com.emirozsucu.easybot.api

import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query
import com.emirozsucu.easybot.Constants

interface ExchangeRateApi {
    @GET("query")
    suspend fun getExchangeRate(
        @Query("function") function: String = "CURRENCY_EXCHANGE_RATE",
        @Query("from_currency") fromCurrency: String,  // Kullanıcıdan gelen para birimi
        @Query("to_currency") toCurrency: String,  // Kullanıcıdan gelen para birimi
        @Query("apikey") apiKey: String = Constants.EXCHANGE_RATE_API_KEY  // API anahtarını Constants'tan alıyoruz
    ): ExchangeRateResponse
}

// Döviz kuru yanıtını tutan veri sınıfı
data class ExchangeRateResponse(
    @SerializedName("Realtime Currency Exchange Rate")
    val realtimeCurrencyExchangeRate: CurrencyExchangeRate
)

data class CurrencyExchangeRate(
    @SerializedName("1. From_Currency Code")
    val fromCurrency: String,
    @SerializedName("2. To_Currency Code")
    val toCurrency: String,
    @SerializedName("5. Exchange Rate")
    val exchangeRate: String
)
