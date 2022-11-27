package com.example.models

import kotlinx.serialization.Serializable

@Serializable
data class Order(val orderId: Int, val contents: List<OrderItem>)

@Serializable
data class OrderItem(
    val id: Int,
    val orderId: Int,
    val itemName: String,
    val quantity: Int,
    val price: String
)