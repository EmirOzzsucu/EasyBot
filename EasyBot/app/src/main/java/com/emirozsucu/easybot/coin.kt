package com.emirozsucu.easybot.api

import retrofit2.http.GET
import retrofit2.http.Query

// CoinGecko API arayüzü - Kripto fiyatlarını getirir
interface CoinGeckoApi {
    @GET("simple/price")
    suspend fun getFinancialPrices(
        @Query("ids") ids: String = "bitcoin,ethereum,dogecoin,litecoin,cardano,binancecoin",
        @Query("vs_currencies") vsCurrencies: String = "usd,eur"
    ): FinancialPrices
}

// Gelen API yanıtı: Farklı kripto para fiyatlarını tutar
data class FinancialPrices(
    val bitcoin: CoinPrice,
    val ethereum: CoinPrice,
    val dogecoin: CoinPrice,
    val litecoin: CoinPrice,
    val cardano: CoinPrice,
    val binancecoin: CoinPrice
)

data class CoinPrice(
    val usd: Double,
    val eur: Double
)
