package io.github.alizarion.backend.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future


class MainVerticle : AbstractVerticle() {



    override fun start(startFuture: Future<Void>) {
        vertx.createHttpServer()
                .requestHandler { req ->  req.response().end("Hello world!") }
                .listen(8080)

    }

}

