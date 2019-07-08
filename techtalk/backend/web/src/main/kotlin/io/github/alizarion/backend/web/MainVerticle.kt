package io.github.alizarion.backend.web

import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.ext.web.client.WebClient


class MainVerticle : AbstractVerticle() {


    override fun start(startFuture: Future<Void>) {

        val client: WebClient = WebClient.create(vertx)

        vertx.createHttpServer()
                .requestHandler { req ->
                   val request =  client.get(8081,"localhost","/")
                    request.send { ar ->
                        if(ar.succeeded()){

                           val message = ar.result().bodyAsJsonObject().getString("message")
                            req.response().end(message)

                        } else {
                            req.response().setStatusCode(500).end()
                        }
                    }
                 }
                .listen(8080)
    }


}

