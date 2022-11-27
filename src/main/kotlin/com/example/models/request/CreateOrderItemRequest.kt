package com.example.models.request

import kotlinx.serialization.Serializable

@Serializable
data class CreateOrderItemRequest(
    val orderId: Int,
    val itemName: String,
    val quantity: Int,
    val price: String
)