package com.emirozsucu.easybot.retrofit

import com.emirozsucu.easybot.model.NewsResponse
import retrofit2.http.GET
import retrofit2.http.Query

// NewsAPI servisi - Güncel haberleri çeker
interface NewsApiService {
    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String = "tr",
        @Query("apiKey") apiKey: String
    ): NewsResponse
}
