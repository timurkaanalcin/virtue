package com.llvad.virtue.ui.screens

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.llvad.virtue.R
import com.llvad.virtue.data.*
import com.llvad.virtue.ui.*
import com.llvad.virtue.ui.components.*
import com.llvad.virtue.ui.theme.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.Send

// Tavsiye kartları ve Kaan Erdem asistan ekranı

@Composable
fun AdviceScreen(vm: AdviceViewModel = viewModel()) {
    val list by vm.advice.collectAsStateWithLifecycle()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item { Text(stringResource(R.string.advice_title), style = MaterialTheme.typography.headlineSmall); RiskDisclaimer() }
        items(list) { AdviceCard(it) }
    }
}

@Composable
fun riskLabel(r: RiskLevel) = stringResource(when (r) { RiskLevel.DUSUK -> R.string.risk_low; RiskLevel.ORTA -> R.string.risk_medium; RiskLevel.YUKSEK -> R.string.risk_high })

/** Tek tavsiye kartı: risk, gerekçe, vade, olumlu/olumsuz senaryo ve yasal uyarı birlikte gösterilir */
@Composable
fun AdviceCard(a: Advice) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Text(stringResource(R.string.advice_expert_view), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
            Text(a.title, style = MaterialTheme.typography.titleMedium)
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(onClick = {}, label = { Text("${stringResource(R.string.advice_risk)}: ${riskLabel(a.risk)}") })
                AssistChip(onClick = {}, label = { Text("${stringResource(R.string.advice_horizon)}: ${a.horizon}") })
            }
            Labeled(stringResource(R.string.advice_rationale), a.rationale)
            Labeled(stringResource(R.string.advice_upside), a.upside)
            Labeled(stringResource(R.string.advice_downside), a.downside)
            RiskDisclaimer()
        }
    }
}

@Composable
private fun Labeled(label: String, body: String) {
    Text(label, style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
    Text(body, style = MaterialTheme.typography.bodyMedium)
}

@Composable
fun AssistantScreen(onAbout: () -> Unit = {}, vm: AssistantViewModel = viewModel()) {
    val messages by vm.messages.collectAsStateWithLifecycle()
    var input by rememberSaveable { mutableStateOf("") }
    val expert = stringResource(R.string.expert_name)
    Column(Modifier.fillMaxSize().imePadding()) {
        // Kalıcı yasal uyarı: sohbet boyunca ekranın üstünde sabit kalır
        Column(Modifier.padding(horizontal = 16.dp, vertical = 8.dp)) {
            Row(Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Text(stringResource(R.string.assistant_expert_subtitle), style = MaterialTheme.typography.labelLarge, modifier = Modifier.weight(1f))
                TextButton(onClick = onAbout) { Text(stringResource(R.string.about_title)) }
            }
            RiskDisclaimer()
        }
        LazyColumn(Modifier.weight(1f), contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
            item { Bubble(ChatMessage(stringResource(R.string.assistant_welcome), false), expert) }
            items(messages) { Bubble(it, expert) }
        }
        Row(Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(input, { input = it }, Modifier.weight(1f), placeholder = { Text(stringResource(R.string.assistant_hint)) })
            IconButton(onClick = { vm.send(input); input = "" }) {
                Icon(Icons.AutoMirrored.Outlined.Send, contentDescription = stringResource(R.string.assistant_send))
            }
        }
    }
}

@Composable
private fun Bubble(m: ChatMessage, expert: String) {
    Column(Modifier.fillMaxWidth(), horizontalAlignment = if (m.fromUser) Alignment.End else Alignment.Start) {
        if (!m.fromUser) Text(expert, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        Surface(
            color = if (m.fromUser) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant,
            shape = MaterialTheme.shapes.medium, modifier = Modifier.widthIn(max = 300.dp)
        ) { Text(m.text, Modifier.padding(10.dp)) }
    }
}
