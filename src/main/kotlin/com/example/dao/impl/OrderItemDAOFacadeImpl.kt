package com.example.dao.impl

import com.example.dao.DatabaseFactory.dbQuery
import com.example.dao.OrderItemDAOFacade
import com.example.dao.table.OrderItems
import com.example.models.Order
import com.example.models.OrderItem
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import java.math.BigDecimal

class OrderItemDAOFacadeImpl : OrderItemDAOFacade {
    override suspend fun all(): List<Order> = dbQuery {
        val orderItems = OrderItems.selectAll().map(::resultRowToOrderItem)
        return@dbQuery orderItems
            .groupBy { it.orderId }
            .map { (orderId, orderItems) -> Order(orderId, orderItems) }
    }

    override suspend fun findById(id: Int): OrderItem? = dbQuery {
        OrderItems
            .select(OrderItems.id eq id)
            .map(::resultRowToOrderItem)
            .singleOrNull()
    }

    override suspend fun findByOrderId(orderId: Int): Order? = dbQuery {
        val orderItems = OrderItems
            .select(OrderItems.orderId eq orderId)
            .map(::resultRowToOrderItem)

        if (orderItems.isEmpty()) {
            return@dbQuery null
        }

        return@dbQuery Order(orderId, orderItems)
    }

    override suspend fun add(orderId: Int, itemName: String, quantity: Int, price: BigDecimal): OrderItem? = dbQuery {
        val insertStatement = OrderItems.insert {
            it[OrderItems.orderId] = orderId
            it[OrderItems.itemName] = itemName
            it[OrderItems.quantity] = quantity
            it[OrderItems.price] = price
        }

        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToOrderItem)
    }

    override suspend fun edit(id: Int, orderId: Int, itemName: String, quantity: Int, price: BigDecimal): Boolean =
        dbQuery {
            OrderItems.update({ OrderItems.id eq id }) {
                it[OrderItems.orderId] = orderId
                it[OrderItems.itemName] = itemName
                it[OrderItems.quantity] = quantity
                it[OrderItems.price] = price
            } > 0
        }

    override suspend fun deleteById(id: Int): Boolean = dbQuery {
        OrderItems.deleteWhere { OrderItems.id eq id } > 0
    }

    override suspend fun deleteByOrderId(orderId: Int): Boolean = dbQuery {
        OrderItems.deleteWhere { OrderItems.orderId eq orderId } > 0
    }

    private fun resultRowToOrderItem(row: ResultRow) = OrderItem(
        id = row[OrderItems.id],
        orderId = row[OrderItems.orderId],
        itemName = row[OrderItems.itemName],
        quantity = row[OrderItems.quantity],
        price = row[OrderItems.price].toString()
    )
}

val orderItemDao: OrderItemDAOFacade = OrderItemDAOFacadeImpl()