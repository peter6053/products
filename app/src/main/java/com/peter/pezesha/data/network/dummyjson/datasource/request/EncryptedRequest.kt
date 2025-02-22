package com.peter.pezesha.data.network.dummyjson.datasource.request

import kotlinx.serialization.Serializable

@Serializable
data class EncryptedRequest(
    val data: String
)
