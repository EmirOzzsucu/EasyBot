package com.emirozsucu.easybot.model

// Gemini API’den gelen kripto fiyat verisi
data class CryptoPriceResponse(
    val last: Double,
    val ask: Double,
    val bid: Double,
    val volume: Double
)
