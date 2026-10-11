package com.llvad.virtue.data

// Uygulamada kullanılan veri modelleri (tamamı sahte verilerle doldurulur)

/** Risk seviyesi: renkler nötr tutulur, korku/aciliyet yaratacak tasarım kullanılmaz */
enum class RiskLevel { DUSUK, ORTA, YUKSEK }

/** Tek bir varlığın örnek fiyat bilgisi */
data class Quote(val symbol: String, val name: String, val price: Double, val changePercent: Double)

/** Portföydeki bir varlık sınıfı payı */
data class Allocation(val name: String, val value: Double, val percent: Double)

/** Portföy özeti */
data class Portfolio(
    val totalValue: Double,
    val dailyChange: Double,
    val dailyChangePercent: Double,
    val allocations: List<Allocation>,
    val history: List<Double>
)

/** Piyasa haberi / özeti */
data class NewsItem(val id: Int, val title: String, val summary: String, val source: String, val time: String)

/** Örnek tavsiye kartı: her kart olumlu ve olumsuz senaryoyu birlikte içerir */
data class Advice(
    val id: Int,
    val title: String,
    val risk: RiskLevel,
    val horizon: String,
    val rationale: String,
    val upside: String,
    val downside: String
)

/** Sohbet mesajı */
data class ChatMessage(val text: String, val fromUser: Boolean)
