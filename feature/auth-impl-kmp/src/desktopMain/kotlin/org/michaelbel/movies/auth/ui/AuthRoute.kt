package org.michaelbel.movies.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.michaelbel.movies.common.browser.openUrl

@Composable
fun AuthRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AuthScreenContent(
        error = null,
        signInLoading = false,
        loginLoading = false,
        requestToken = null,
        onBackClick = onBackClick,
        onSignInClick = { username, password -> },
        onLoginClick = {},
        onResetRequestToken = {},
        onUrlClick = { url -> openUrl(url) },
        modifier = modifier
    )
}