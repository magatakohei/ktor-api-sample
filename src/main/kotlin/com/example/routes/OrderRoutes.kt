package com.example.routes

import com.example.dao.impl.orderItemDao
import com.example.models.request.CreateOrderItemRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.route
import java.math.BigDecimal


fun Route.orderRouting() {
    route("/order") {
        get {
            call.respond(orderItemDao.all())
        }

        get("/{id?}") {
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

        post {
            val orderItemRequest = call.receive<CreateOrderItemRequest>()
            val orderItem =
                orderItemDao.add(
                    orderId = orderItemRequest.orderId,
                    itemName = orderItemRequest.itemName,
                    quantity = orderItemRequest.quantity,
                    price = BigDecimal(orderItemRequest.price)
                ) ?: return@post call.respondText(
                    "Create Customer Failed",
                    status = HttpStatusCode.InternalServerError
                )
            call.respond(orderItem)
        }

        get("/{id?}/total") {
            val orderId = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                "Bad Request",
                status = HttpStatusCode.BadRequest
            )
            val order = orderItemDao.findByOrderId(orderId) ?: return@get call.respondText(
                "Not Found",
                status = HttpStatusCode.NotFound
            )
            val total = order.contents.sumOf { BigDecimal(it.price) * BigDecimal(it.quantity) }.toString()
            call.respond(total)
        }
    }
}