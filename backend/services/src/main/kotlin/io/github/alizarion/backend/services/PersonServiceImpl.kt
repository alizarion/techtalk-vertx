package io.github.alizarion.backend.services

import io.github.alizarion.backend.api.Person
import io.github.alizarion.backend.api.PersonService
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject

import java.util.Collections
import java.util.HashMap
import java.util.UUID

class PersonServiceImpl : PersonService {

    override fun createPerson(person: JsonObject, handler: Handler<AsyncResult<JsonObject>>) {
        val id = UUID.randomUUID().toString()
        println(Json.encode(person))
        val entity = Json.decodeValue(Json.encode(person), Person::class.java)
        entity.id = id
        PERSON_MAP[id] = entity
        handler.handle(Future.succeededFuture(JsonObject(Json.encode(entity))))
    }

    override fun removePerson(id: String, handler: Handler<AsyncResult<JsonObject>>) {
        PERSON_MAP.remove(id)
        handler.handle(Future.succeededFuture())
    }

    override fun personList(handler: Handler<AsyncResult<JsonArray>>) {
        handler.handle(Future.succeededFuture(JsonArray(Json.encode(PERSON_MAP.values))))
    }

    override fun personById(id: String, handler: Handler<AsyncResult<JsonObject>>) {

        handler.handle(Future.succeededFuture(JsonObject(Json.encode(PERSON_MAP[id]))))

    }

    companion object {

        private val PERSON_MAP = Collections.synchronizedMap(HashMap<String, Person>())
    }
}
