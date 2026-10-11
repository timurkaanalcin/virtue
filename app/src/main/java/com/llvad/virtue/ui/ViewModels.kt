package com.llvad.virtue.ui

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.llvad.virtue.data.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

// MVVM katmanı: her ekran durumunu StateFlow ile sunar

/** İlk açılış uyarısının onaylanıp onaylanmadığını cihazda saklar */
class OnboardingViewModel(app: Application) : AndroidViewModel(app) {
    private val prefs = app.getSharedPreferences("ayarlar", Context.MODE_PRIVATE)
    private val _accepted = MutableStateFlow(prefs.getBoolean("uyari_onaylandi", false))
    val accepted: StateFlow<Boolean> = _accepted.asStateFlow()

    fun accept() {
        prefs.edit().putBoolean("uyari_onaylandi", true).apply()
        _accepted.value = true
    }
}

class HomeViewModel(repo: InvestmentRepository = FakeInvestmentRepository) : ViewModel() {
    val portfolio: StateFlow<Portfolio> = MutableStateFlow(repo.getPortfolio())
    val news: StateFlow<List<NewsItem>> = MutableStateFlow(repo.getNews().take(2))
}

class PortfolioViewModel(repo: InvestmentRepository = FakeInvestmentRepository) : ViewModel() {
    val portfolio: StateFlow<Portfolio> = MutableStateFlow(repo.getPortfolio())
}

class MarketViewModel(repo: InvestmentRepository = FakeInvestmentRepository) : ViewModel() {
    val quotes: StateFlow<List<Quote>> = MutableStateFlow(repo.getQuotes())
    val news: StateFlow<List<NewsItem>> = MutableStateFlow(repo.getNews())
}

class AdviceViewModel(repo: InvestmentRepository = FakeInvestmentRepository) : ViewModel() {
    val advice: StateFlow<List<Advice>> = MutableStateFlow(repo.getAdvice())
}

class AssistantViewModel(private val repo: InvestmentRepository = FakeInvestmentRepository) : ViewModel() {
    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    /** Kullanıcı mesajını ekler ve kural tabanlı yanıtı üretir */
    fun send(text: String) {
        if (text.isBlank()) return
        _messages.update { it + ChatMessage(text.trim(), true) + ChatMessage(repo.reply(text), false) }
    }
}

/** Ayarlar: bildirim tercihleri (DataStore) ve sahte test e-postası */
class SettingsViewModel(app: Application) : AndroidViewModel(app) {
    private val store = NotificationSettingsStore(app)
    private val sender: EmailSender = MockEmailSender()
    val settings: StateFlow<NotificationSettings> =
        store.settings.stateIn(viewModelScope, SharingStarted.Eagerly, NotificationSettings())

    fun saveEmail(v: String) = viewModelScope.launch { store.setEmail(v.trim()) }
    fun setDaily(v: Boolean) = viewModelScope.launch { store.setDaily(v) }
    fun setAdvice(v: Boolean) = viewModelScope.launch { store.setAdvice(v) }
    fun setRisk(v: Boolean) = viewModelScope.launch { store.setRisk(v) }

    /** Test e-postasını sahte göndericiyle "gönderir" ve sonucu bildirir */
    fun sendTest(subject: String, body: String, onDone: (String) -> Unit) = viewModelScope.launch {
        val to = settings.value.email
        if (sender.send(to, subject, body)) onDone(to)
    }
}
