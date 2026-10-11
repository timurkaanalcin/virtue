package com.llvad.virtue.navigation

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.*
import com.llvad.virtue.R
import com.llvad.virtue.ui.OnboardingViewModel
import com.llvad.virtue.ui.screens.*
import kotlinx.coroutines.delay

// Alt gezinme çubuğundaki sekmeler
enum class Tab(val route: String, @StringRes val label: Int, val icon: ImageVector) {
    HOME("home", R.string.nav_home, Icons.Outlined.Home),
    PORTFOLIO("portfolio", R.string.nav_portfolio, Icons.Outlined.PieChart),
    MARKET("market", R.string.nav_market, Icons.Outlined.Newspaper),
    ADVICE("advice", R.string.nav_advice, Icons.Outlined.Lightbulb),
    ASSISTANT("assistant", R.string.nav_assistant, Icons.Outlined.Forum)
}

/** Uygulama kökü: açılış ekranı → ilk açılış uyarısı → ana gezinme */
@Composable
fun VirtueApp(onExit: () -> Unit) {
    var splashDone by remember { mutableStateOf(false) }
    LaunchedEffect(Unit) { delay(1200); splashDone = true }
    val onboarding: OnboardingViewModel = viewModel()
    val accepted by onboarding.accepted.collectAsStateWithLifecycle()
    when {
        !splashDone -> SplashScreen()
        !accepted -> OnboardingScreen(onAccept = onboarding::accept, onExit = onExit)
        else -> MainScaffold()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MainScaffold() {
    val nav = rememberNavController()
    val entry by nav.currentBackStackEntryAsState()
    val route = entry?.destination?.route
    val title = Tab.entries.firstOrNull { it.route == route }?.let { stringResource(it.label) }
        ?: when (route) { "settings" -> stringResource(R.string.nav_settings); "about" -> stringResource(R.string.about_title); else -> "" }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("${stringResource(R.string.app_name)} · $title") },
                actions = {
                    IconButton(onClick = { nav.navigate("settings") { launchSingleTop = true } }) {
                        Icon(Icons.Outlined.Settings, contentDescription = stringResource(R.string.nav_settings))
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                Tab.entries.forEach { tab ->
                    NavigationBarItem(
                        selected = route == tab.route,
                        onClick = {
                            nav.navigate(tab.route) {
                                popUpTo(nav.graph.findStartDestination().id) { saveState = true }
                                launchSingleTop = true; restoreState = true
                            }
                        },
                        icon = { Icon(tab.icon, contentDescription = null) },
                        label = { Text(stringResource(tab.label)) }
                    )
                }
            }
        }
    ) { pad ->
        NavHost(nav, startDestination = Tab.HOME.route, modifier = Modifier.padding(pad)) {
            composable(Tab.HOME.route) { HomeScreen(onAdvice = { nav.navigate(Tab.ADVICE.route) }, onAssistant = { nav.navigate(Tab.ASSISTANT.route) }) }
            composable(Tab.PORTFOLIO.route) { PortfolioScreen() }
            composable(Tab.MARKET.route) { MarketScreen() }
            composable(Tab.ADVICE.route) { AdviceScreen() }
            composable(Tab.ASSISTANT.route) { AssistantScreen(onAbout = { nav.navigate("about") }) }
            composable("settings") { SettingsScreen(onAbout = { nav.navigate("about") }) }
            composable("about") { AboutScreen() }
        }
    }
}
