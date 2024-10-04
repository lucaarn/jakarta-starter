package de.pdbm;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("helloworld")
public class HelloWorld {
    private static final Logger LOGGER = Logger.getLogger(HelloWorld.class.getCanonicalName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getHelloWorld() {
        return Response.ok(toJson("hello world"), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        LOGGER.info("Path param: " + id);
        return Response.noContent().build();
    }

    @PUT
    public Response put(JsonObject jsonObject) {
        LOGGER.info(jsonObject.toString());
        return Response.ok().build();
    }

    private JsonObject toJson(String message) {
        return Json.createObjectBuilder().add("message", message).build();
    }
}
