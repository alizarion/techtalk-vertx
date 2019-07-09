package io.github.alizarion.backend.services

import io.github.alizarion.backend.api.PersonService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.serviceproxy.ServiceBinder


class ServiceVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {

        val personService = PersonServiceImpl()

        // Register the handler
        ServiceBinder(vertx)
                .setAddress("persons")
                .register(PersonService::class.java, personService)


        vertx.eventBus().consumer<Any>("hello-world-service") { message ->  message.reply("hello world") }

        vertx.createHttpServer().requestHandler { req -> req.response().end("ok") }.listen(8080)


    }
}

