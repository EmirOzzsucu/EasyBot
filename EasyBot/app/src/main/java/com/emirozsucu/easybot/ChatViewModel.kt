package com.emirozsucu.easybot.viewmodel

import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emirozsucu.easybot.Constants
import com.emirozsucu.easybot.model.MessageModel
import com.emirozsucu.easybot.retrofit.RetrofitClient
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import kotlinx.coroutines.launch

class ChatViewModel : ViewModel() {

    // Kullanıcı ve model arasındaki mesaj geçmişini tutar
    val messageList = mutableStateListOf<MessageModel>()

    // Google Gemini AI modelini tanımlıyoruz (chat için kullanılıyor)
    private val generativeModel: GenerativeModel = GenerativeModel(
        modelName = "gemini-1.5-flash",
        apiKey = Constants.GEMINI_API_KEY,
    )

    // Kullanıcıdan gelen mesajı işleyen fonksiyon
    fun sendMessage(question: String) {
        viewModelScope.launch {
            try {
                messageList.add(MessageModel(question, "user"))
                messageList.add(MessageModel("Typing...", "model"))

                val lowerQuestion = question.lowercase()

                // 🌤️ HAVA DURUMU
                if (lowerQuestion.contains("hava") || lowerQuestion.contains("hava durumu")) {
                    val city = lowerQuestion
                        .replace("hava durumu", "")
                        .replace("hava", "")
                        .trim()

                    try {
                        val weatherResponse = RetrofitClient.weatherApi.getWeather(city)
                        messageList.removeAt(messageList.size - 1)

                        messageList.add(
                            MessageModel(
                                "${weatherResponse.name} için hava durumu:\n" +
                                        "🌤️ ${weatherResponse.weather[0].description}\n" +
                                        "🌡️ Sıcaklık: ${weatherResponse.main.temp}°C\n" +
                                        "💧 Nem: ${weatherResponse.main.humidity}%",
                                "model"
                            )
                        )
                    } catch (e: Exception) {
                        messageList.removeAt(messageList.size - 1)
                        messageList.add(
                            MessageModel("Hava durumu alınamadı: ${e.localizedMessage}", "model")
                        )
                    }
                    return@launch
                }

                // 💰 COINGECKO – KRİPTO FİYATLARI
                val coins =
                    listOf("bitcoin", "ethereum", "dogecoin", "litecoin", "cardano", "binance")
                if (coins.any { lowerQuestion.contains(it) }) {
                    val financialPrices = RetrofitClient.coinGeckoApi.getFinancialPrices()
                    messageList.removeAt(messageList.size - 1)

                    coins.forEach { coin ->
                        if (lowerQuestion.contains(coin)) {
                            val price = when (coin) {
                                "bitcoin" -> financialPrices.bitcoin
                                "ethereum" -> financialPrices.ethereum
                                "dogecoin" -> financialPrices.dogecoin
                                "litecoin" -> financialPrices.litecoin
                                "cardano" -> financialPrices.cardano
                                "binance" -> financialPrices.binancecoin
                                else -> null
                            }
                            price?.let {
                                messageList.add(
                                    MessageModel(
                                        "$coin fiyatı: ${it.usd} USD / ${it.eur} EUR",
                                        "model"
                                    )
                                )
                            }
                        }
                    }
                    return@launch
                }

                // 💹 GEMINI – KRİPTO FİYATLARI (DETAYLI)
                val geminiCoins = listOf("bitcoin", "ethereum", "dogecoin")
                if (geminiCoins.any { lowerQuestion.contains(it) }) {
                    val coinSymbol = geminiCoins.firstOrNull { lowerQuestion.contains(it) }
                    coinSymbol?.let {
                        try {
                            val cryptoPriceResponse = RetrofitClient.geminiApi.getCryptoPrice(it)
                            messageList.removeAt(messageList.size - 1)
                            messageList.add(
                                MessageModel(
                                    "$it Fiyat: ${cryptoPriceResponse.last} USD\n" +
                                            "Alış: ${cryptoPriceResponse.ask} USD\n" +
                                            "Satış: ${cryptoPriceResponse.bid} USD\n" +
                                            "Hacim: ${cryptoPriceResponse.volume}",
                                    "model"
                                )
                            )
                        } catch (e: Exception) {
                            messageList.removeAt(messageList.size - 1)
                            messageList.add(
                                MessageModel(
                                    "Gemini API hatası: ${e.localizedMessage}",
                                    "model"
                                )
                            )
                        }
                    }
                    return@launch
                }

                // 📈 FINNHUB – HİSSE SENEDİ
                val stocks = listOf("AAPL", "GOOGL", "MSFT", "AMZN", "TSLA")
                if (stocks.any { lowerQuestion.contains(it.lowercase()) }) {
                    val stockSymbol = stocks.firstOrNull { lowerQuestion.contains(it.lowercase()) }
                    stockSymbol?.let {
                        try {
                            val stockPrice = RetrofitClient.finnhubApi.getStockPrice(it)
                            messageList.removeAt(messageList.size - 1)
                            messageList.add(
                                MessageModel(
                                    "$it Fiyat: ${stockPrice.currentPrice} USD\n" +
                                            "Yüksek: ${stockPrice.highPrice} USD\n" +
                                            "Düşük: ${stockPrice.lowPrice} USD\n" +
                                            "Açılış: ${stockPrice.openPrice} USD",
                                    "model"
                                )
                            )
                        } catch (e: Exception) {
                            messageList.removeAt(messageList.size - 1)
                            messageList.add(
                                MessageModel(
                                    "Hisse senedi verisi alınamadı: ${e.localizedMessage}",
                                    "model"
                                )
                            )
                        }
                    }
                    return@launch
                }

                // 📰 NEWSAPI – HABERLER
                val newsKeywords = listOf("haber", "gündem", "son dakika", "breaking news", "news", "güncel")
                if (newsKeywords.any { lowerQuestion.contains(it) }) {
                    try {
                        val newsResponse =
                            RetrofitClient.newsApi.getTopHeadlines(apiKey = Constants.NEWS_API_KEY)
                        messageList.removeAt(messageList.size - 1)

                        Log.d("NewsAPI", "Received articles: ${newsResponse.articles.size}")

                        if (newsResponse.articles.isNotEmpty()) {
                            val topArticles = newsResponse.articles.take(3) // İlk 3 haberi al
                            topArticles.forEach { article ->
                                messageList.add(
                                    MessageModel(
                                        "📰 ${article.title}\n\n${article.description ?: "Açıklama yok."}\n\n🔗 ${article.url}",
                                        "model"
                                    )
                                )
                            }
                        } else {
                            messageList.add(MessageModel("Şu an güncel haber bulunamadı.", "model"))
                        }
                    } catch (e: Exception) {
                        Log.e("NewsAPI", "Error fetching news: ${e.localizedMessage}")
                        messageList.removeAt(messageList.size - 1)
                        messageList.add(
                            MessageModel(
                                "Haber verisi alınamadı: ${e.localizedMessage}",
                                "model"
                            )
                        )
                    }
                    return@launch
                }


                // 🧠 GENEL – Gemini AI Sohbet
                try {
                    val chat = generativeModel.startChat(
                        history = messageList.map {
                            content(it.role) { text(it.message) }
                        }
                    )
                    val response = chat.sendMessage(question)
                    messageList.removeAt(messageList.size - 1)
                    messageList.add(MessageModel(response.text.toString(), "model"))
                } catch (e: Exception) {
                    messageList.removeAt(messageList.size - 1)
                    messageList.add(
                        MessageModel(
                            "Gemini yanıt hatası: ${e.localizedMessage}",
                            "model"
                        )
                    )
                }

            } catch (e: Exception) {
                Log.e("Send Message Error", "Hata: ${e.localizedMessage}", e)
                if (messageList.isNotEmpty()) messageList.removeAt(messageList.size - 1)
                messageList.add(MessageModel("Genel hata: ${e.message}", "model"))
            }
        }
    }
}
