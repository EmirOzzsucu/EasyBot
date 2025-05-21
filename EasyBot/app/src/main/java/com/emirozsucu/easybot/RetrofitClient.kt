package com.emirozsucu.easybot.retrofit

import com.emirozsucu.easybot.Constants
import com.emirozsucu.easybot.api.CoinGeckoApi
import com.emirozsucu.easybot.api.FinnhubApi
import com.emirozsucu.easybot.api.GeminiApi
import com.emirozsucu.easybot.api.WeatherApi
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Retrofit istemcisi: Tüm API’ler için Retrofit objelerini oluşturur
object RetrofitClient {

    val weatherApi: WeatherApi by lazy {
        Retrofit.Builder()
            .baseUrl(Constants.WEATHER_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    // CoinGecko API Retrofit örneği
    private val retrofitCoinGecko = Retrofit.Builder()
        .baseUrl(Constants.COINGECKO_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Finnhub API Retrofit örneği
    private val retrofitFinnhub = Retrofit.Builder()
        .baseUrl(Constants.FINNHUB_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Gemini API Retrofit örneği
    private val retrofitGemini = Retrofit.Builder()
        .baseUrl("https://api.gemini.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // News API Retrofit örneği
    private val retrofitNews = Retrofit.Builder()
        .baseUrl("https://newsapi.org/") // ⭐ News API Base URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // API arayüzlerinin örnekleri
    val coinGeckoApi: CoinGeckoApi = retrofitCoinGecko.create(CoinGeckoApi::class.java)
    val finnhubApi: FinnhubApi = retrofitFinnhub.create(FinnhubApi::class.java)
    val geminiApi: GeminiApi = retrofitGemini.create(GeminiApi::class.java)
    val newsApi: NewsApiService =
        retrofitNews.create(NewsApiService::class.java) // ⭐ News API erişimi


}

