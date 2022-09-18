package org.michaelbel.movies.core.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Username(
    @SerialName("username") val username: String?,
    @SerialName("password") val password: String?,
    @SerialName("request_token") val requestToken: String?
)