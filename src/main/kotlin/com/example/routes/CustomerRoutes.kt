package com.example.routes

import com.example.dao.impl.customerDao
import com.example.models.request.CreateCustomerRequest
import com.example.models.request.EditCustomerRequest
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.call
import io.ktor.server.request.receive
import io.ktor.server.response.respond
import io.ktor.server.response.respondText
import io.ktor.server.routing.Route
import io.ktor.server.routing.delete
import io.ktor.server.routing.get
import io.ktor.server.routing.post
import io.ktor.server.routing.put
import io.ktor.server.routing.route

fun Route.customerRouting() {
    route("/customer") {
        get {
            val customers = customerDao.allCustomers()
            if (customers.isNotEmpty()) {
                call.respond(customers)
            } else {
                call.respondText("No customers found", status = HttpStatusCode.OK)
            }
        }

        get("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@get call.respondText(
                "Missing id",
                status = HttpStatusCode.BadRequest
            )

            val customer =
                customerDao.customer(id) ?: return@get call.respondText(
                    "No customer with id $id",
                    status = HttpStatusCode.NotFound
                )
            call.respond(customer)
        }

        post {
            val customerRequest = call.receive<CreateCustomerRequest>()
            val customer =
                customerDao.addNewCustomer(
                    customerRequest.firstName,
                    customerRequest.lastName ?: "default name",
                    customerRequest.email
                ) ?: return@post call.respondText(
                    "Create Customer Failed",
                    status = HttpStatusCode.InternalServerError
                )
            call.respond(customer)
        }

        put {
            val customerRequest = call.receive<EditCustomerRequest>()
            val isSuccess = customerDao.editCustomer(
                id = customerRequest.id,
                firstName = customerRequest.firstName,
                lastName = customerRequest.lastName,
                email = customerRequest.email
            )
            if (isSuccess) {
                call.respondText("Customer edit correctly", status = HttpStatusCode.OK)
            } else {
                call.respondText("Customer edit falied", status = HttpStatusCode.NotFound)
            }
        }

        delete("{id?}") {
            val id = call.parameters["id"]?.toIntOrNull() ?: return@delete call.respond(HttpStatusCode.BadRequest)
            if (customerDao.deleteCustomer(id)) {
                call.respondText("Customer removed correctly", status = HttpStatusCode.Accepted)
            } else {
                call.respondText("Not Found", status = HttpStatusCode.NotFound)
            }
        }
    }
}
