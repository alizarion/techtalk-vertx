package io.github.alizarion.backend.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject


class ServiceVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {


        vertx.eventBus().consumer<Any>("hello-world-service") { message ->
            println("I have received a message: ${message.body()}")
            message.reply("hello world")

        }

    }


}

