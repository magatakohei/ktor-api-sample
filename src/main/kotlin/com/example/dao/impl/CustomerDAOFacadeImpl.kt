package com.example.dao.impl

import com.example.dao.CustomerDAOFacade
import com.example.dao.DatabaseFactory.dbQuery
import com.example.dao.table.Customers
import com.example.models.Customer
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update

class CustomerDAOFacadeImpl : CustomerDAOFacade {
    override suspend fun allCustomers(): List<Customer> = dbQuery {
        Customers.selectAll().map(::resultRowToCustomer)
    }

    override suspend fun customer(id: Int): Customer? = dbQuery {
        Customers
            .select { Customers.id eq id }
            .map(::resultRowToCustomer)
            .singleOrNull()
    }

    override suspend fun addNewCustomer(firstName: String, lastName: String, email: String): Customer? = dbQuery {
        val insertStatement = Customers.insert {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToCustomer)
    }

    override suspend fun editCustomer(id: Int, firstName: String, lastName: String, email: String): Boolean = dbQuery {
        Customers.update({ Customers.id eq id }) {
            it[Customers.firstName] = firstName
            it[Customers.lastName] = lastName
            it[Customers.email] = email
        } > 0
    }

    override suspend fun deleteCustomer(id: Int): Boolean = dbQuery {
        Customers.deleteWhere { Customers.id eq id } > 0
    }

    private fun resultRowToCustomer(row: ResultRow) = Customer(
        id = row[Customers.id],
        firstName = row[Customers.firstName],
        lastName = row[Customers.lastName],
        email = row[Customers.email]
    )
}

val customerDao: CustomerDAOFacade = CustomerDAOFacadeImpl().apply {
    runBlocking {
        if (allCustomers().isEmpty()) {
            addNewCustomer("firstName", "lastName", "email@example.com")
        }
    }
}