package io.github.alizarion.backend.api;


import io.vertx.codegen.annotations.ProxyGen;
import io.vertx.codegen.annotations.VertxGen;
import io.vertx.core.AsyncResult;
import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

@VertxGen
@ProxyGen
public interface PersonService {

    void createPerson(JsonObject person, Handler<AsyncResult<JsonObject>> handler);

    void removePerson(String id, Handler<AsyncResult<JsonObject>> handler);

    void personList(Handler<AsyncResult<JsonArray>> handler);

    void personById(String id, Handler<AsyncResult<JsonObject>> handler);

     static PersonService createProxy(Vertx vertx, String address) {
        return new PersonServiceVertxEBProxy(vertx, address);
    }

}
