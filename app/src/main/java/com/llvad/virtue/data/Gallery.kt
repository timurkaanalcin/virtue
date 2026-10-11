package com.llvad.virtue.data

// Hakkımda galerisi için 20 fotoğraf yer tutucusu (gerçek fotoğraflar docs/FOTOGRAFLAR.md'ye göre eklenecek)
data class GalleryItem(val id: String, val title: String, val caption: String, val fileName: String)

val galleryItems = listOf(
    GalleryItem("ke_01", "Galatasaray Lisesi bahçesi", "Okul yıllarından, çınar ağaçlarının altında bir sonbahar sabahı", "ke_01.webp"),
    GalleryItem("ke_02", "Hukuk Fakültesi amfisi", "Kalabalık bir amfide ders notları alırken", "ke_02.webp"),
    GalleryItem("ke_03", "Borsa kulübü toplantısı", "Arkadaşlarıyla bilanço incelediği haftalık toplantı", "ke_03.webp"),
    GalleryItem("ke_04", "LSE kütüphanesi", "Tez çalışması sırasında kütüphanede", "ke_04.webp"),
    GalleryItem("ke_05", "Londra Canary Wharf", "Akşamüstü finans bölgesi silueti", "ke_05.webp"),
    GalleryItem("ke_06", "Londra ofisi", "Bankacılık yıllarından genel bir ofis görüntüsü", "ke_06.webp"),
    GalleryItem("ke_07", "Çalışma masası ve kahve", "Gece geç saatte analiz tabloları ve bir fincan kahve", "ke_07.webp"),
    GalleryItem("ke_08", "Singapur skyline", "Singapur'a taşındığı ilk hafta", "ke_08.webp"),
    GalleryItem("ke_09", "Marina Bay", "Marina Bay kıyısında yürüyüş", "ke_09.webp"),
    GalleryItem("ke_10", "Toplantı odası", "Risk değerlendirme toplantısı", "ke_10.webp"),
    GalleryItem("ke_11", "Kitaplık", "Ekonomi ve tarih kitaplarıyla dolu ev kitaplığı", "ke_11.webp"),
    GalleryItem("ke_12", "Boğaz manzarası", "İstanbul'a dönüşte Boğaz kıyısında", "ke_12.webp"),
    GalleryItem("ke_13", "Portre (stüdyo)", "Sade arka planlı stüdyo portresi", "ke_13.webp"),
    GalleryItem("ke_14", "Seyahat / havalimanı", "Bir konferans yolculuğunda havalimanında", "ke_14.webp"),
    GalleryItem("ke_15", "Not defteri ve grafikler", "El çizimi grafiklerle dolu not defteri", "ke_15.webp"),
    GalleryItem("ke_16", "Sabah koşusu", "Güne erken bir koşuyla başlarken", "ke_16.webp"),
    GalleryItem("ke_17", "Konferans konuşması", "Risk yönetimi üzerine bir konuşma", "ke_17.webp"),
    GalleryItem("ke_18", "Erdem Capital ofisi", "Fonun küçük ve sade ofisi", "ke_18.webp"),
    GalleryItem("ke_19", "VIRTUE ekip masası", "VIRTUE ekibiyle içerik planlaması", "ke_19.webp"),
    GalleryItem("ke_20", "Akşam okuma köşesi", "Akşamları kitap okuduğu köşe", "ke_20.webp")
)
