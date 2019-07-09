package io.github.alizarion.backend.web

import io.github.alizarion.backend.api.PersonService
import io.vertx.core.AbstractVerticle
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.ext.web.Router
import io.vertx.ext.web.RoutingContext
import io.vertx.ext.web.handler.BodyHandler
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.handler.CorsHandler
import java.util.HashSet


class MainVerticle : AbstractVerticle() {

    private var personService: PersonService? = null

    override fun start(startFuture: Future<Void>) {

        val router = createRouter()

        personService = PersonService.createProxy(vertx, "persons")

        val allowedHeaders = HashSet<String>()
        allowedHeaders.add("x-requested-with")
        allowedHeaders.add("Access-Control-Allow-Origin")
        allowedHeaders.add("origin")
        allowedHeaders.add("Content-Type")
        allowedHeaders.add("accept")
        allowedHeaders.add("X-PINGARUNER")

        val allowedMethods = HashSet<HttpMethod>()
        allowedMethods.add(HttpMethod.GET)
        allowedMethods.add(HttpMethod.POST)
        allowedMethods.add(HttpMethod.OPTIONS)
        /*
     * these methods aren't necessary for this sample,
     * but you may need them for your projects
     */
        allowedMethods.add(HttpMethod.DELETE)
        allowedMethods.add(HttpMethod.PATCH)
        allowedMethods.add(HttpMethod.PUT)

        router.route().handler(CorsHandler.create("*").allowedHeaders(allowedHeaders).allowedMethods(allowedMethods))

        vertx.createHttpServer()
                .requestHandler(router)
                .listen(8080)



    }

    private fun createRouter() = Router.router(vertx).apply {
        route().handler(BodyHandler.create())
        get("/").handler{ rc ->
            vertx.eventBus().send<Any>("hello-world-service", "yah !") { ar ->
                if(ar.succeeded()){

                    val message = ar.result().body()
                    rc.response().end(message as String)

                } else {
                    rc.response().setStatusCode(500).end(ar.cause().toString())
                }
            }
        }
        get("/persons").handler(personList)
        post("/persons").handler(createPerson)
        get("/persons/:ID").handler(personById)

    }


    private val personList = Handler<RoutingContext> { req ->
        personService?.personList{ar ->
            if (ar.succeeded()){

                req.response().end(ar.result().encode())
            } else {
                req.response().setStatusCode(500).end(ar.cause().toString())
            }
        }
    }

    private val createPerson = Handler<RoutingContext> { rc ->
        System.out.println(Json.encode(rc.bodyAsJson))
        personService?.createPerson(rc.bodyAsJson) {ar ->
            if (ar.succeeded()){

                rc.response().end(ar.result().encode())
            } else {
                rc.response().setStatusCode(500).end(ar.cause().toString())
            }
        }
    }

    private val personById = Handler<RoutingContext> { rc ->
        personService?.personById(rc.request().getParam("ID")) {ar ->
            if (ar.succeeded()){

                rc.response().end(ar.result().encode())
            } else {
                rc.response().setStatusCode(500).end(ar.cause().toString())
            }
        }
    }


}

