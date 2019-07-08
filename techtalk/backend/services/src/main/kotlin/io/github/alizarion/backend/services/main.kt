package io.github.alizarion.backend.services

import io.vertx.core.Vertx

fun main(){
    val vertx = Vertx.vertx()
    vertx.deployVerticle(ServiceVerticle::class.java.name)
}