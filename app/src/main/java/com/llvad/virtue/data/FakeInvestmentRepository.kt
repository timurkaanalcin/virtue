package com.llvad.virtue.data

import kotlin.math.sin

// Sahte (mock) veri kaynağı: ağ bağlantısı ve API anahtarı gerektirmez
object FakeInvestmentRepository : InvestmentRepository {

    override fun getQuotes() = listOf(
        Quote("BIST100", "BIST 100 Endeksi (örnek)", 9_812.40, 0.42),
        Quote("USDTRY", "Dolar/TL (örnek)", 34.21, 0.08),
        Quote("XAU", "Altın ons (örnek)", 2_405.10, -0.31),
        Quote("BTC", "Bitcoin (örnek)", 61_250.0, -1.85),
        Quote("EURUSD", "Euro/Dolar (örnek)", 1.0842, 0.05),
        Quote("TAHVIL", "2 yıllık tahvil faizi (örnek)", 41.30, -0.12)
    )

    override fun getPortfolio(): Portfolio {
        // Grafik için yumuşak dalgalı, sahte 30 günlük seri üretilir (iniş ve çıkışlar içerir)
        val history = (0 until 30).map { i -> 100_000 + 2_500 * sin(i / 4.0) + i * 40.0 - (if (i in 18..23) 1_800.0 else 0.0) }
        val total = history.last()
        val prev = history[history.size - 2]
        val allocations = listOf(
            Allocation("Hisse senetleri", total * 0.40, 40.0),
            Allocation("Tahvil / bono", total * 0.25, 25.0),
            Allocation("Altın", total * 0.15, 15.0),
            Allocation("Döviz mevduatı", total * 0.12, 12.0),
            Allocation("Nakit", total * 0.08, 8.0)
        )
        return Portfolio(total, total - prev, (total - prev) / prev * 100, allocations, history)
    }

    override fun getNews() = listOf(
        NewsItem(1, "Merkez bankası faiz kararını açıkladı (örnek)", "Örnek senaryoda politika faizi sabit bırakıldı. Analistler kararın enflasyon görünümüne bağlı olarak ileride değişebileceğini belirtiyor.", "Örnek Haber Ajansı", "09:30"),
        NewsItem(2, "Altında dalgalı seyir (örnek)", "Ons altın haftayı hafif düşüşle kapattı. Güvenli liman talebi sürse de güçlü dolar fiyatları baskılıyor.", "Örnek Finans", "11:05"),
        NewsItem(3, "Teknoloji hisselerinde karışık görünüm (örnek)", "Bazı şirketler beklentileri aştı, bazıları geride kaldı. Sektörde değerlemelerin yüksekliği risk olarak öne çıkıyor.", "Örnek Piyasa Bülteni", "13:20"),
        NewsItem(4, "Kripto paralarda sert hareketler (örnek)", "Bitcoin gün içinde %5'e varan dalgalanma gösterdi. Kripto varlıklar yüksek oynaklık ve düzenleyici belirsizlik taşır.", "Örnek Kripto Gündemi", "15:45")
    )

    override fun getAdvice() = listOf(
        Advice(1, "Acil durum fonu oluşturmak", RiskLevel.DUSUK, "Sürekli",
            "Birkaç aylık giderin likit ve düşük riskli araçlarda tutulması, beklenmedik durumlarda yatırımları zararına bozma ihtiyacını azaltabilir.",
            "Beklenmedik giderlerde finansal esneklik sağlar.",
            "Getirisi enflasyonun altında kalabilir; paranın alım gücü zamanla azalabilir."),
        Advice(2, "Çeşitlendirilmiş endeks yaklaşımı", RiskLevel.ORTA, "5 yıl ve üzeri",
            "Tek bir şirket yerine geniş bir endekse yayılmak, tek bir şirkete özgü riski azaltabilir.",
            "Uzun vadede ekonomik büyümeden pay alma imkânı sunabilir.",
            "Piyasa genelinde düşüşlerde endeks de %30 veya daha fazla değer kaybedebilir ve toparlanması yıllar sürebilir."),
        Advice(3, "Portföyde altın payı", RiskLevel.ORTA, "3 yıl ve üzeri",
            "Altın bazı dönemlerde diğer varlıklarla farklı hareket edebilir; bu, portföy dalgalanmasını dengelemeye yardımcı olabilir.",
            "Belirsizlik dönemlerinde değerini koruyabilir.",
            "Faiz veya düzenli gelir üretmez; uzun süre yatay ya da düşüşte kalabilir."),
        Advice(4, "Kripto varlıklara küçük pay", RiskLevel.YUKSEK, "Belirsiz",
            "Kripto varlıklar çok yüksek oynaklık taşır. Bir kişi ilgilenecekse, kaybetmeyi göze alabileceği küçük bir tutarla sınırlamayı düşünebilir.",
            "Yüksek fiyat artışları yaşanabilir, ancak bu öngörülemez.",
            "Kısa sürede değerin büyük bölümü kaybedilebilir; borsa iflası, dolandırıcılık ve düzenleme riskleri vardır.")
    )

    // Kaan Erdem karakterinin yanıtları: anahtar kelimeye dayalı basit kurallar; hiçbir yanıt belirli bir alım/satım önermez
    private val rules = listOf(
        listOf("risk") to "Risk, beklenen sonucun gerçekleşmeme ihtimalidir. Daha yüksek getiri potansiyeli genellikle daha yüksek kayıp ihtimaliyle birlikte gelir. Kendi risk toleransınızı vadenize ve gelir durumunuza göre değerlendirmek önemlidir.",
        listOf("çeşit", "cesit", "dağıt", "dagit") to "Çeşitlendirme, birikimi farklı varlık sınıflarına yaymaktır. Tek bir varlığa bağlı riski azaltabilir, ancak tüm piyasaların aynı anda düştüğü dönemlerde kaybı önlemez.",
        listOf("altın", "altin") to "Altın bazı dönemlerde değer saklama aracı olarak görülür. Ancak faiz ya da temettü üretmez ve fiyatı uzun süre düşebilir.",
        listOf("hisse", "borsa") to "Hisse senetleri şirket ortaklığıdır. Uzun vadede büyüme potansiyeli taşıyabilir, fakat kısa vadede sert düşüşler yaşanabilir ve şirket batarsa yatırım tamamen kaybedilebilir.",
        listOf("enflasyon") to "Enflasyon, paranın alım gücünün azalmasıdır. Getirisi enflasyonun altında kalan bir araç reel olarak kayıp ettirebilir.",
        listOf("kripto", "bitcoin") to "Kripto varlıklar çok oynaktır ve düzenleyici belirsizlik taşır. Değerin büyük kısmının kısa sürede kaybedilmesi mümkündür.",
        listOf("garanti", "kesin", "zengin") to "Hiçbir yatırım getiri garantisi vermez. Kesin kazanç vaat eden tekliflere karşı dikkatli olun; bunlar sıklıkla dolandırıcılık işaretidir.",
        listOf("al", "sat", "hangi") to "Belirli bir varlığı alıp satmanızı öneremem. Bu bir eğitim prototipidir; kişisel kararlar için lisanslı bir yatırım danışmanına başvurun."
    )

    override fun reply(question: String): String {
        val q = question.lowercase()
        val words = q.split(Regex("[^\\p{L}]+"))
        val hit = rules.firstOrNull { (keys, _) ->
            keys.any { k -> if (k.length <= 3) words.contains(k) else q.contains(k) }
        }
        return (hit?.second ?: "Bu konuda genel bir bilgim yok. \"Risk\", \"çeşitlendirme\", \"altın\", \"hisse\", \"enflasyon\" veya \"kripto\" hakkında sorabilirsiniz.") +
            "\n\nBu içerik yatırım tavsiyesi değildir."
    }
}
