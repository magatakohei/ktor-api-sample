package com.example

import com.example.dao.DatabaseFactory
import com.example.plugins.configureJwt
import com.example.plugins.configureRouting
import com.example.plugins.configureSerialization
import io.ktor.server.application.Application

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@kotlin.jvm.JvmOverloads
fun Application.module() {
    DatabaseFactory.init(environment)
    configureSerialization()
    configureRouting()
    configureJwt()
}
