package io.github.alizarion.backend.services

import io.github.alizarion.backend.api.Person
import io.github.alizarion.backend.api.PersonService
import io.vertx.core.AsyncResult
import io.vertx.core.Future
import io.vertx.core.Handler
import io.vertx.core.json.Json
import io.vertx.core.json.JsonArray
import io.vertx.core.json.JsonObject
import io.vertx.redis.RedisClient

import java.util.Collections
import java.util.HashMap
import java.util.UUID

class PersonServiceImpl(redis: RedisClient) : PersonService {

    val redis = redis


    override fun createPerson(person: JsonObject, handler: Handler<AsyncResult<JsonObject>>) {
        val id = UUID.randomUUID().toString()
        println(Json.encode(person))
        val entity = Json.decodeValue(Json.encode(person), Person::class.java)
        entity.id = id
        redis.hset("PERSONS", id, Json.encode(entity)) { x ->
            handler.handle(Future.succeededFuture(JsonObject(Json.encode(entity))))
        }

    }

    override fun removePerson(id: String, handler: Handler<AsyncResult<JsonObject>>) {
        PERSON_MAP.remove(id)
        redis.hdel("PERSONS", id) { x -> handler.handle(Future.succeededFuture()) }


    }

    override fun personList(handler: Handler<AsyncResult<JsonArray>>) {
        println("personList")
        redis.hgetall("PERSONS") { json ->
            if (json.failed()) {
                handler.handle(Future.failedFuture(json.toString()))
            } else {
                println("personList" + json.result().map.values)
                handler.handle(Future.succeededFuture(JsonArray(json.result().map.values.toString())))
            }
        }

    }

    override fun personById(id: String, handler: Handler<AsyncResult<JsonObject>>) {
        redis.hget("PERSONS", id) {json ->
            if (json.failed()) {
                handler.handle(Future.failedFuture(json.toString()))
            } else  {
                handler.handle(Future.succeededFuture(JsonObject(json.result())))

            }
        }


    }



    companion object {
        private val PERSON_MAP = Collections.synchronizedMap(HashMap<String, Person>())
    }
}
