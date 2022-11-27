package com.example.routes

import com.example.dao.impl.orderItemDao
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import java.math.BigDecimal

fun Route.listOrdersRoute() {
    get("/order") {
        call.respond(orderItemDao.all())
    }
}

fun Route.getOrderRoute() {
    get("/order/{id?}") {
        val orderId = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
            "Bad Request",
            status = HttpStatusCode.BadRequest
        )
        val order = orderItemDao.findByOrderId(orderId) ?: return@get call.respondText(
            "Not Found",
            status = HttpStatusCode.NotFound
        )
        call.respond(order)
    }
}

fun Route.totalizeOrderRoute() {
    get("/order/{id?}/total") {
        val orderId = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
            "Bad Request",
            status = HttpStatusCode.BadRequest
        )
        val order = orderItemDao.findByOrderId(orderId) ?: return@get call.respondText(
            "Not Found",
            status = HttpStatusCode.NotFound
        )
        val total = order.contents.sumOf { BigDecimal(it.price) * BigDecimal(it.quantity) }
        call.respond(total)
    }
}