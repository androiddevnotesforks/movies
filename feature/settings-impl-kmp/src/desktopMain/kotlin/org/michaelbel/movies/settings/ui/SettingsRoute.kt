package org.michaelbel.movies.settings.ui

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.michaelbel.movies.common.appearance.FeedView
import org.michaelbel.movies.common.browser.openUrl
import org.michaelbel.movies.common.gender.GrammaticalGender
import org.michaelbel.movies.common.list.MovieList
import org.michaelbel.movies.common.localization.model.AppLanguage
import org.michaelbel.movies.common.theme.AppTheme
import org.michaelbel.movies.settings.model.SettingsData
import org.michaelbel.movies.settings.model.isAboutFeatureEnabled
import org.michaelbel.movies.settings.model.isAppIconFeatureEnabled
import org.michaelbel.movies.settings.model.isBiometricFeatureEnabled
import org.michaelbel.movies.settings.model.isDynamicColorsFeatureEnabled
import org.michaelbel.movies.settings.model.isFeedViewFeatureEnabled
import org.michaelbel.movies.settings.model.isGenderFeatureEnabled
import org.michaelbel.movies.settings.model.isGithubFeatureEnabled
import org.michaelbel.movies.settings.model.isLanguageFeatureEnabled
import org.michaelbel.movies.settings.model.isMovieListFeatureEnabled
import org.michaelbel.movies.settings.model.isNotificationsFeatureEnabled
import org.michaelbel.movies.settings.model.isReviewAppFeatureEnabled
import org.michaelbel.movies.settings.model.isThemeFeatureEnabled
import org.michaelbel.movies.settings.model.isTileFeatureEnabled
import org.michaelbel.movies.settings.model.isUpdateAppFeatureEnabled
import org.michaelbel.movies.settings.model.isWidgetFeatureEnabled
import org.michaelbel.movies.ui.appicon.IconAlias

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val snackbarHostState = remember { SnackbarHostState() }

    SettingsScreenContent(
        settingsData = SettingsData(
            onBackClick = onBackClick,
            languageData = SettingsData.ListData(
                isFeatureEnabled = isLanguageFeatureEnabled,
                current = AppLanguage.English(),
                onSelect = {}
            ),
            themeData = SettingsData.ListData(
                isFeatureEnabled = isThemeFeatureEnabled,
                current = AppTheme.FollowSystem,
                onSelect = {}
            ),
            feedViewData = SettingsData.ListData(
                isFeatureEnabled = isFeedViewFeatureEnabled,
                current = FeedView.FeedList,
                onSelect = {}
            ),
            movieListData = SettingsData.ListData(
                isFeatureEnabled = isMovieListFeatureEnabled,
                current = MovieList.NowPlaying(),
                onSelect = {}
            ),
            genderData = SettingsData.ListData(
                isFeatureEnabled = isGenderFeatureEnabled,
                current = GrammaticalGender.NotSpecified(),
                onSelect = {}
            ),
            dynamicColorsData = SettingsData.DynamicColorsData(
                isFeatureEnabled = isDynamicColorsFeatureEnabled,
                isEnabled = false,
                onChange = {}
            ),
            notificationsData = SettingsData.NotificationsData(
                isFeatureEnabled = isNotificationsFeatureEnabled,
                isEnabled = false,
                onClick = {},
                onNavigateToAppNotificationSettings = {}
            ),
            biometricData = SettingsData.BiometricData(
                isFeatureEnabled = isBiometricFeatureEnabled,
                isEnabled = false,
                onChange = {}
            ),
            widgetData = SettingsData.WidgetData(
                isFeatureEnabled = isWidgetFeatureEnabled,
                onRequest = {}
            ),
            tileData = SettingsData.TileData(
                isFeatureEnabled = isTileFeatureEnabled,
                onRequest = {}
            ),
            appIconData = SettingsData.ListData(
                isFeatureEnabled = isAppIconFeatureEnabled,
                current = IconAlias.Red,
                onSelect = {}
            ),
            githubData = SettingsData.GithubData(
                isFeatureEnabled = isGithubFeatureEnabled,
                onClick = { url -> openUrl(url) }
            ),
            reviewAppData = SettingsData.ReviewAppData(
                isFeatureEnabled = isReviewAppFeatureEnabled,
                onRequest = {}
            ),
            updateAppData = SettingsData.UpdateAppData(
                isFeatureEnabled = isUpdateAppFeatureEnabled,
                onRequest = {}
            ),
            aboutData = SettingsData.AboutData(
                isFeatureEnabled = isAboutFeatureEnabled,
                versionName = "1.0.0",
                versionCode = 1L,
                flavor = "FOSS",
                isDebug = false
            )
        ),
        windowInsets = WindowInsets(0.dp, 0.dp, 0.dp, 0.dp),
        snackbarHostState = snackbarHostState,
        modifier = modifier
    )
}