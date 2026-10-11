package com.llvad.virtue.data

import android.content.Context
import android.util.Log
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.llvad.virtue.AppConfig
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Bildirim ayarları DataStore ile cihazda saklanır
private val Context.dataStore by preferencesDataStore(name = "bildirim_ayarlari")

data class NotificationSettings(
    val email: String = AppConfig.DEFAULT_NOTIFICATION_EMAIL,
    val dailySummary: Boolean = false,
    val newAdvice: Boolean = false,
    val riskAlert: Boolean = false
)

class NotificationSettingsStore(private val context: Context) {
    private val kEmail = stringPreferencesKey("eposta")
    private val kDaily = booleanPreferencesKey("gunluk_ozet")
    private val kAdvice = booleanPreferencesKey("yeni_tavsiye")
    private val kRisk = booleanPreferencesKey("risk_uyarisi")

    val settings: Flow<NotificationSettings> = context.dataStore.data.map { p ->
        NotificationSettings(
            email = p[kEmail] ?: AppConfig.DEFAULT_NOTIFICATION_EMAIL,
            dailySummary = p[kDaily] ?: false,
            newAdvice = p[kAdvice] ?: false,
            riskAlert = p[kRisk] ?: false
        )
    }

    suspend fun setEmail(v: String) = context.dataStore.edit { it[kEmail] = v }
    suspend fun setDaily(v: Boolean) = context.dataStore.edit { it[kDaily] = v }
    suspend fun setAdvice(v: Boolean) = context.dataStore.edit { it[kAdvice] = v }
    suspend fun setRisk(v: Boolean) = context.dataStore.edit { it[kRisk] = v }
}

/** E-posta gönderim arayüzü */
interface EmailSender {
    /** Gönderimi dener; başarılıysa true döner */
    suspend fun send(to: String, subject: String, body: String): Boolean
}

/** Sahte gönderici: gerçek e-posta göndermez, yalnızca günlüğe yazar */
class MockEmailSender : EmailSender {
    override suspend fun send(to: String, subject: String, body: String): Boolean {
        // TODO(ileride): Gerçek gönderim burada yapılacak — SMTP (ör. Jakarta Mail) veya
        // bir e-posta API'si (sunucu tarafında, API anahtarı uygulamaya gömülmeden) bağlanacak.
        Log.i("MockEmailSender", "Simüle e-posta → alıcı=$to, konu=$subject, içerik=$body")
        return true
    }
}

/** Basit e-posta doğrulaması */
fun isValidEmail(v: String): Boolean =
    Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$").matches(v.trim())
