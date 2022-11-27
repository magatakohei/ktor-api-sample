package com.example.dao.table

import org.jetbrains.exposed.sql.Table

object Customers : Table("customers") {
    val id = integer("id").autoIncrement()
    val firstName = varchar("first_name", 255)
    val lastName = varchar("last_name", 255)
    val email = varchar("email", 255)

    override val primaryKey = PrimaryKey(id)
}