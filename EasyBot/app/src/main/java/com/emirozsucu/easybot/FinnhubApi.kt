package com.emirozsucu.easybot.api

import com.emirozsucu.easybot.Constants
import com.google.gson.annotations.SerializedName
import retrofit2.http.GET
import retrofit2.http.Query

// Finnhub API - Hisse senedi fiyatlarını çeker
interface FinnhubApi {
    @GET("quote")
    suspend fun getStockPrice(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = Constants.FINNHUB_API_KEY
    ): StockResponse
}

// Finnhub’tan dönen hisse verileri
data class StockResponse(
    @SerializedName("c") val currentPrice: Double,  // Güncel fiyat
    @SerializedName("h") val highPrice: Double,     // Gün içi en yüksek fiyat
    @SerializedName("l") val lowPrice: Double,      // Gün içi en düşük fiyat
    @SerializedName("o") val openPrice: Double      // Açılış fiyatı
)
