package io.github.alizarion.backend.services

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.json.JsonObject


class ServiceVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {

        vertx.createHttpServer()
                .requestHandler { req ->  req.response()
                        .end(JsonObject()
                                .put("message","hello world")
                                .encode()
                        )}
                .listen(8081)

    }


}

