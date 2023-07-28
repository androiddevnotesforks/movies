package org.michaelbel.movies.account

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.michaelbel.movies.common.viewmodel.BaseViewModel
import org.michaelbel.movies.domain.data.entity.AccountDb
import org.michaelbel.movies.domain.interactor.account.AccountInteractor
import org.michaelbel.movies.domain.interactor.authentication.AuthenticationInteractor
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val authenticationInteractor: AuthenticationInteractor,
    accountInteractor: AccountInteractor,
): BaseViewModel() {

    var loading: Boolean by mutableStateOf(false)

    val account: StateFlow<AccountDb?> = accountInteractor.account
        .stateIn(
            scope = this,
            started = SharingStarted.Lazily,
            initialValue = AccountDb.Empty
        )

    override fun handleError(throwable: Throwable) {
        loading = false
        super.handleError(throwable)
    }

    fun onLogoutClick(onResult: () -> Unit) = launch {
        loading = true

        authenticationInteractor.deleteSession()
        onResult()
    }
}