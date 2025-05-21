package com.emirozsucu.easybot.api

import com.emirozsucu.easybot.model.CryptoPriceResponse
import retrofit2.http.GET
import retrofit2.http.Path

// Gemini API - Kripto fiyat verilerini getirir
interface GeminiApi {

    @GET("v1/pubticker/{crypto}")
    suspend fun getCryptoPrice(@Path("crypto") crypto: String): CryptoPriceResponse
}
