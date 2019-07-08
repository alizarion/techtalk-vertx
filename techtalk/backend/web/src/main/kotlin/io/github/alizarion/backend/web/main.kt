package io.github.alizarion.backend.web

import io.vertx.core.Vertx

fun main(){
    val vertx = Vertx.vertx()
    vertx.deployVerticle(MainVerticle::class.java.name)
}