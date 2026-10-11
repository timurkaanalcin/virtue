package com.llvad.virtue.data

// Veri katmanı arayüzü: gerçek bir API eklenirse yalnızca yeni bir uygulama yazılır
interface InvestmentRepository {
    fun getQuotes(): List<Quote>
    fun getPortfolio(): Portfolio
    fun getNews(): List<NewsItem>
    fun getAdvice(): List<Advice>
    /** Kaan Erdem karakterinin kural tabanlı sahte yanıtı */
    fun reply(question: String): String
}
