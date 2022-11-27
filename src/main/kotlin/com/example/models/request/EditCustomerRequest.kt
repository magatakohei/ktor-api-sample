package com.example.models.request

import kotlinx.serialization.Serializable

@Serializable
data class EditCustomerRequest(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String
)