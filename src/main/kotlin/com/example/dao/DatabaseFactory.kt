package com.example.dao

import io.ktor.server.application.ApplicationEnvironment
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction

object DatabaseFactory {
    fun init(environment: ApplicationEnvironment) {
        val driver = environment.config.property("database.driver").getString()
        val url = environment.config.property("database.url").getString()
        val user = environment.config.property("database.user").getString()
        val password = environment.config.property("database.password").getString()

        val database = Database.connect(url, driver, user, password)
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}