package com.example.plugins

import com.example.routes.customerRouting
import com.example.routes.getOrderRoute
import com.example.routes.listOrdersRoute
import com.example.routes.totalizeOrderRoute
import com.example.routes.userRouting
import io.ktor.server.application.Application
import io.ktor.server.routing.routing

fun Application.configureRouting() {

    routing {
        customerRouting()
        userRouting()
        listOrdersRoute()
        getOrderRoute()
        totalizeOrderRoute()
    }
}
