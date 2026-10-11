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
import androidx.compose.material.icons.outlined.Image

// Açılış (splash), ilk açılış uyarısı ve ayarlar ekranları

/** Basit açılış ekranı: uygulama adı ve uzman karakteri */
@Composable
fun SplashScreen() {
    Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.primary), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(stringResource(R.string.app_name), style = MaterialTheme.typography.displayMedium, color = MaterialTheme.colorScheme.onPrimary, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(8.dp))
            Text(stringResource(R.string.splash_tagline), color = MaterialTheme.colorScheme.onPrimary, textAlign = TextAlign.Center)
            Text(stringResource(R.string.splash_prototype), color = MaterialTheme.colorScheme.onPrimary, style = MaterialTheme.typography.labelMedium)
        }
    }
}

/** İlk açılış uyarısı: kutucuk önceden işaretli değildir, iki düğme de aynı nötr görünümdedir */
@Composable
fun OnboardingScreen(onAccept: () -> Unit, onExit: () -> Unit) {
    var checked by rememberSaveable { mutableStateOf(false) }
    Column(Modifier.fillMaxSize().systemBarsPadding().verticalScroll(rememberScrollState()).padding(24.dp)) {
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
        Text(stringResource(R.string.onboarding_title), style = MaterialTheme.typography.titleLarge)
        Spacer(Modifier.height(16.dp))
        Text(stringResource(R.string.onboarding_body))
        Spacer(Modifier.height(16.dp))
        RiskDisclaimer()
        Spacer(Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.clickable { checked = !checked }) {
            Checkbox(checked = checked, onCheckedChange = { checked = it })
            Text(stringResource(R.string.onboarding_checkbox))
        }
        Spacer(Modifier.height(16.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            OutlinedButton(onClick = onExit, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.onboarding_exit)) }
            OutlinedButton(onClick = onAccept, enabled = checked, modifier = Modifier.weight(1f)) { Text(stringResource(R.string.onboarding_continue)) }
        }
    }
}

/** Ayarlar: uygulama adı, sürüm, uzman karakteri ve yasal uyarı */
@Composable
fun SettingsScreen(onAbout: () -> Unit = {}) {
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(16.dp)) {
        SettingRow(stringResource(R.string.settings_app_name), stringResource(R.string.app_name))
        SettingRow(stringResource(R.string.settings_version), "0.1.0")
        SettingRow(stringResource(R.string.settings_expert), stringResource(R.string.expert_name) + " — " + stringResource(R.string.expert_role))
        OutlinedButton(onClick = onAbout, modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) { Text(stringResource(R.string.about_title)) }
        SectionTitle(stringResource(R.string.settings_disclaimer))
        Text(stringResource(R.string.onboarding_body), style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(12.dp))
        RiskDisclaimer()
    }
}

@Composable
private fun SettingRow(label: String, value: String) {
    Column(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.secondary)
        Text(value, style = MaterialTheme.typography.bodyLarge)
    }
    HorizontalDivider()
}

/** Hakkımda: kurgusal Kaan Erdem karakterinin biyografisi ve fotoğraf galerisi */
@Composable
fun AboutScreen() {
    LazyColumn(Modifier.fillMaxSize(), contentPadding = PaddingValues(24.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        item {
            Text(stringResource(R.string.about_title), style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
        }
        item {
            // Gerçek fotoğraf yerine baş harflerden oluşan dairesel yer tutucu
            Box(Modifier.size(96.dp).background(MaterialTheme.colorScheme.primaryContainer, androidx.compose.foundation.shape.CircleShape), contentAlignment = Alignment.Center) {
                Text(stringResource(R.string.about_initials), style = MaterialTheme.typography.headlineMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
            }
            Spacer(Modifier.height(12.dp))
        }
        item {
            Text(stringResource(R.string.expert_name), style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
            Text(stringResource(R.string.about_fictional), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
            Spacer(Modifier.height(8.dp))
        }
        // Biyografi bölümleri
        listOf(
            R.string.about_h_life to R.string.bio_yasam,
            R.string.about_h_edu to R.string.bio_egitim,
            R.string.about_h_career to R.string.bio_kariyer,
            R.string.about_h_phil to R.string.bio_felsefe
        ).forEach { (h, b) ->
            item {
                Column(Modifier.fillMaxWidth()) {
                    SectionTitle(stringResource(h))
                    Text(stringResource(b), style = MaterialTheme.typography.bodyMedium)
                }
            }
        }
        item {
            Column(Modifier.fillMaxWidth()) {
                SectionTitle(stringResource(R.string.about_gallery))
                Text(stringResource(R.string.about_gallery_note), style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.height(8.dp))
            }
        }
        // 20 kutuyu ikişerli satırlar hâlinde gösterir (iç içe kaydırma sorunu olmasın diye LazyGrid yerine)
        items(galleryItems.chunked(2)) { row ->
            Row(Modifier.fillMaxWidth().padding(bottom = 8.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                row.forEach { g -> GalleryTile(g, Modifier.weight(1f)) }
                if (row.size == 1) Spacer(Modifier.weight(1f))
            }
        }
        item {
            Spacer(Modifier.height(16.dp))
            Surface(color = MaterialTheme.colorScheme.surfaceVariant, shape = MaterialTheme.shapes.medium, modifier = Modifier.fillMaxWidth()) {
                Text(stringResource(R.string.about_risk_note), Modifier.padding(12.dp), style = MaterialTheme.typography.labelLarge, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

/** Gri tonlu fotoğraf yer tutucusu: simge, başlık ve kısa açıklama */
@Composable
private fun GalleryTile(g: GalleryItem, modifier: Modifier) {
    Column(modifier.background(androidx.compose.ui.graphics.Color(0xFFDADDE1), MaterialTheme.shapes.small).padding(8.dp)) {
        Box(Modifier.fillMaxWidth().height(80.dp).background(androidx.compose.ui.graphics.Color(0xFFBFC4CA), MaterialTheme.shapes.small), contentAlignment = Alignment.Center) {
            Icon(androidx.compose.material.icons.Icons.Outlined.Image, contentDescription = null, tint = androidx.compose.ui.graphics.Color(0xFF6E757D))
        }
        Text(g.title, style = MaterialTheme.typography.labelLarge, color = androidx.compose.ui.graphics.Color(0xFF33383D), modifier = Modifier.padding(top = 4.dp))
        Text(g.caption, style = MaterialTheme.typography.bodySmall, color = androidx.compose.ui.graphics.Color(0xFF50565C))
    }
}
