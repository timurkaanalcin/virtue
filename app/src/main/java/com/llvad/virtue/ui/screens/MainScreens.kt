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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke

// Ana sayfa, portföy ve piyasa ekranları

@Composable
fun HomeScreen(onAdvice: () -> Unit, onAssistant: () -> Unit, vm: HomeViewModel = viewModel()) {
    val p by vm.portfolio.collectAsStateWithLifecycle()
    val news by vm.news.collectAsStateWithLifecycle()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        item {
            Text(stringResource(R.string.home_greeting), style = MaterialTheme.typography.headlineSmall)
            Text(stringResource(R.string.home_intro))
            Text(stringResource(R.string.mock_notice), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
        }
        item {
            Card(Modifier.fillMaxWidth()) {
                Column(Modifier.padding(16.dp)) {
                    Text(stringResource(R.string.home_portfolio_card), style = MaterialTheme.typography.labelLarge)
                    Text(formatTl(p.totalValue), style = MaterialTheme.typography.headlineMedium)
                    ChangeText(p.dailyChange, p.dailyChangePercent)
                }
            }
        }
        item { SectionTitle(stringResource(R.string.home_latest_news)) }
        items(news) { NewsCard(it) }
        item {
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedButton(onClick = onAdvice, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.home_go_advice), textAlign = TextAlign.Center) }
                OutlinedButton(onClick = onAssistant, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.home_go_assistant), textAlign = TextAlign.Center) }
            }
        }
        item { RiskDisclaimer() }
    }
}

@Composable
fun ChangeText(abs: Double, pct: Double) {
    Text("${stringResource(R.string.portfolio_daily)}: ${formatTl(abs)} (${formatPct(pct)})",
        color = if (abs >= 0) NeutralUp else NeutralDown)
}

@Composable
fun PortfolioScreen(vm: PortfolioViewModel = viewModel()) {
    val p by vm.portfolio.collectAsStateWithLifecycle()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item {
            Text(stringResource(R.string.portfolio_total), style = MaterialTheme.typography.labelLarge)
            Text(formatTl(p.totalValue), style = MaterialTheme.typography.headlineMedium)
            ChangeText(p.dailyChange, p.dailyChangePercent)
        }
        item { SectionTitle(stringResource(R.string.portfolio_chart)); LineChart(p.history) }
        item { SectionTitle(stringResource(R.string.portfolio_allocation)) }
        items(p.allocations) { a ->
            Column {
                Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(a.name); Text("%${"%.0f".format(a.percent)} · ${formatTl(a.value)}")
                }
                LinearProgressIndicator(progress = { (a.percent / 100).toFloat() }, modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp))
            }
        }
        item { Text(stringResource(R.string.mock_notice), style = MaterialTheme.typography.bodySmall) }
    }
}

/** Canvas ile çizilen basit çizgi grafik */
@Composable
fun LineChart(values: List<Double>) {
    val color = MaterialTheme.colorScheme.primary
    Canvas(Modifier.fillMaxWidth().height(160.dp)) {
        if (values.size < 2) return@Canvas
        val min = values.min(); val max = values.max(); val range = (max - min).takeIf { it > 0 } ?: 1.0
        val stepX = size.width / (values.size - 1)
        val path = Path()
        values.forEachIndexed { i, v ->
            val pt = Offset(i * stepX, (size.height * (1 - (v - min) / range)).toFloat())
            if (i == 0) path.moveTo(pt.x, pt.y) else path.lineTo(pt.x, pt.y)
        }
        drawPath(path, color, style = Stroke(width = 4f))
    }
}

@Composable
fun MarketScreen(vm: MarketViewModel = viewModel()) {
    val quotes by vm.quotes.collectAsStateWithLifecycle()
    val news by vm.news.collectAsStateWithLifecycle()
    LazyColumn(contentPadding = PaddingValues(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
        item { Text(stringResource(R.string.market_title), style = MaterialTheme.typography.headlineSmall); Text(stringResource(R.string.mock_notice), style = MaterialTheme.typography.bodySmall) }
        item { SectionTitle(stringResource(R.string.market_prices)) }
        items(quotes) { q ->
            Row(Modifier.fillMaxWidth().padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                Column { Text(q.symbol, fontWeight = FontWeight.SemiBold); Text(q.name, style = MaterialTheme.typography.bodySmall) }
                Column(horizontalAlignment = Alignment.End) {
                    Text("%,.2f".format(q.price))
                    Text(formatPct(q.changePercent), color = if (q.changePercent >= 0) NeutralUp else NeutralDown)
                }
            }
            HorizontalDivider()
        }
        item { SectionTitle(stringResource(R.string.market_news)) }
        items(news) { NewsCard(it) }
    }
}

@Composable
fun NewsCard(n: NewsItem) {
    Card(Modifier.fillMaxWidth()) {
        Column(Modifier.padding(12.dp)) {
            Text(n.title, style = MaterialTheme.typography.titleSmall)
            Text(n.summary, style = MaterialTheme.typography.bodyMedium)
            Text("${n.source} · ${n.time}", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
        }
    }
}
