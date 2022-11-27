package com.example.dao

import com.example.dao.table.Customers
import com.example.dao.table.OrderItems
import io.ktor.server.application.ApplicationEnvironment
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

object DatabaseFactory {
    fun init(environment: ApplicationEnvironment) {
        val driver = environment.config.property("database.driver").getString()
        val url = environment.config.property("database.url").getString()
        val user = environment.config.property("database.user").getString()
        val password = environment.config.property("database.password").getString()

        val database = Database.connect(url, driver, user, password)
        transaction(database) {
            // テーブルが存在しなければ作成
            SchemaUtils.create(
                Customers,
                OrderItems
            )
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}