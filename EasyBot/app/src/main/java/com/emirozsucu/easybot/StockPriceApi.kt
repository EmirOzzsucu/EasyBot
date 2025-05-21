package com.emirozsucu.easybot.api

import com.emirozsucu.easybot.Constants
import com.emirozsucu.easybot.model.StockPrice
import retrofit2.http.GET
import retrofit2.http.Query

interface StockPriceApi {

    @GET("quote")
    suspend fun getStockPrice(
        @Query("symbol") symbol: String,
        @Query("token") apiKey: String = Constants.GEMINI_API_KEY // Finnhub API Key burada kullanılıyor
    ): StockPrice
}
