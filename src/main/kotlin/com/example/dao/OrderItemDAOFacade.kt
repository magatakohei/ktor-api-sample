package com.example.dao

import com.example.models.Order
import com.example.models.OrderItem
import java.math.BigDecimal

interface OrderItemDAOFacade {
    suspend fun all(): List<Order>
    suspend fun findById(id: Int): OrderItem?
    suspend fun findByOrderId(orderId: Int): Order?
    suspend fun add(orderId: Int, itemName: String, quantity: Int, price: BigDecimal): OrderItem?
    suspend fun edit(id: Int, orderId: Int, itemName: String, quantity: Int, price: BigDecimal): Boolean
    suspend fun deleteById(id: Int): Boolean
    suspend fun deleteByOrderId(orderId: Int): Boolean
}