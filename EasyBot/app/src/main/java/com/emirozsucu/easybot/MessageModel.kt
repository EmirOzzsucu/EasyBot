package com.emirozsucu.easybot.model

// Sohbet mesajlarını temsil eden veri modeli
data class MessageModel(
    val message: String,  // Mesajın içeriği
    val role: String      // "user" veya "model" (yapay zeka)
)
