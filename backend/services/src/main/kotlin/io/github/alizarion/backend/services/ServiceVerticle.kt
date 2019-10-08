package io.github.alizarion.backend.services

import io.github.alizarion.backend.api.PersonService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject
import io.vertx.servicediscovery.ServiceDiscovery
import io.vertx.servicediscovery.types.RedisDataSource
import io.vertx.serviceproxy.ServiceBinder


class ServiceVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {


        vertx.eventBus().consumer<Any>("hello-world-service") { message ->  message.reply("hello world") }

        ServiceDiscovery.create(vertx) { discovery ->
            RedisDataSource.getRedisClient(discovery,
                    { rec -> rec.name == "redis" }, { ar ->
                if (ar.failed()) {
                    println("D'oh!")
                } else {
                    val redisCLient = ar.result()
                    val personService = PersonServiceImpl(redisCLient)
                    ServiceBinder(vertx)
                            .setAddress("persons")
                            .register(PersonService::class.java, personService)
                    vertx.createHttpServer().requestHandler { req -> req.response().end("ok") }.listen(8080)
                }
            })
        }

    }
}

