package com.example.models.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateCustomerRequest(
    val firstName: String,
    val lastName: String,
    val email: String
)
