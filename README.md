# VIRTUE

VIRTUE, kurgusal yatırım uzmanı karakteri **Kaan Erdem** ile yatırım kavramlarını sahte verilerle keşfetmeye yarayan, **eğitim ve prototip amaçlı** bir Android uygulamasıdır.

> **Bu içerik yatırım tavsiyesi değildir.** Uygulamadaki tüm fiyatlar, haberler ve öneriler uydurma örnek verilerdir. Kaan Erdem kurgusal bir karakterdir.

## Özellikler
- Açılış ekranı (splash) ve ilk açılışta onaylanması gereken yasal uyarı (önceden işaretli seçenek yok, nötr düğmeler)
- Ana Sayfa, Portföy (toplam değer, günlük değişim, dağılım, Canvas grafik), Piyasa, Tavsiyeler ("Kaan Erdem'in görüşü" kartları: risk, gerekçe, vade, olumlu/olumsuz senaryo, uyarı), Asistan (kural tabanlı yanıtlar, kalıcı uyarı)
- Ayarlar ve Hakkımda (Kaan Erdem biyografisi) ekranları
- Ağ izni yok, API anahtarı yok; `InvestmentRepository` arayüzü + `FakeInvestmentRepository`
- Kotlin, Jetpack Compose, Material 3, Navigation Compose, MVVM (ViewModel + StateFlow), minSdk 26

## Fotoğraflar
Hakkımda galerisindeki 20 yer tutucu için liste: [docs/FOTOGRAFLAR.md](docs/FOTOGRAFLAR.md)

## Dosya yapısı
```
virtue/
├── build.gradle.kts, settings.gradle.kts, gradle.properties
├── gradle/libs.versions.toml, gradle/wrapper/
├── gradlew, gradlew.bat
├── docs/FOTOGRAFLAR.md
└── app/
    ├── build.gradle.kts
    └── src/main/
        ├── AndroidManifest.xml
        ├── res/values/strings.xml, res/drawable/, res/mipmap-anydpi-v26/
        └── java/com/llvad/virtue/
            ├── MainActivity.kt
            ├── navigation/VirtueApp.kt
            ├── data/ (Models.kt, Gallery.kt, InvestmentRepository.kt, FakeInvestmentRepository.kt)
            └── ui/
                ├── ViewModels.kt
                ├── theme/Theme.kt
                ├── components/Common.kt
                └── screens/ (StartScreens.kt, MainScreens.kt, AdviceAssistantScreens.kt)
```

## Android Studio ile açma
1. Android Studio'yu (Ladybug veya daha yeni) açın, **Open** ile `virtue` klasörünü seçin.
2. Gradle senkronizasyonunun bitmesini bekleyin (JDK 17 gerekir).
3. Bir emülatör veya USB hata ayıklaması açık bir cihaz seçip **Run ▶** düğmesine basın.

## Komut satırından derleme
```bash
./gradlew assembleDebug
# APK: app/build/outputs/apk/debug/app-debug.apk
adb install -r app/build/outputs/apk/debug/app-debug.apk
```
