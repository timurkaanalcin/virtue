package com.llvad.virtue.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.llvad.virtue.R
import java.text.NumberFormat
import java.util.Locale

// Ekranlarda ortak kullanılan bileşenler

/** Her tavsiye yüzeyinde gösterilen risk uyarısı ve yasal not */
@Composable
fun RiskDisclaimer(modifier: Modifier = Modifier) {
    Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium, modifier = modifier.fillMaxWidth()) {
        Row(Modifier.padding(12.dp), verticalAlignment = Alignment.Top) {
            Icon(Icons.Outlined.Info, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Column {
                Text(stringResource(R.string.disclaimer_short), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
                Text(stringResource(R.string.risk_warning), style = MaterialTheme.typography.bodySmall)
            }
        }
    }
}

@Composable
fun SectionTitle(text: String) {
    Text(text, style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(vertical = 8.dp))
}

/** Türk lirası biçimlendirme */
fun formatTl(v: Double): String = NumberFormat.getCurrencyInstance(Locale("tr", "TR")).format(v)

/** Yüzde biçimlendirme (işaretli) */
fun formatPct(v: Double): String = String.format(Locale("tr", "TR"), "%+.2f%%", v)
