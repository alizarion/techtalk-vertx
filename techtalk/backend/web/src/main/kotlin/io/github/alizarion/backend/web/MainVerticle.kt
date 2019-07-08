package io.github.alizarion.backend.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future


class MainVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {

        vertx.createHttpServer()
                .requestHandler { req ->
                    vertx.eventBus().send<Any>("hello-world-service", "yah !") { ar ->
                        if(ar.succeeded()){

                            val message = ar.result().body()
                            req.response().end(message as String)

                        } else {
                            req.response().setStatusCode(500).end(ar.cause().toString())
                        }
                    }
                }
                .listen(8080)
    }


}

