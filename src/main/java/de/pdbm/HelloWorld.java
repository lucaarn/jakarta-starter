package de.pdbm;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.logging.Logger;

@Path("helloworld")
@Produces(MediaType.APPLICATION_JSON)
public class HelloWorld {
    private static final Logger LOGGER = Logger.getLogger(HelloWorld.class.getCanonicalName());

    @GET
    public Response getHelloWorld() {
        return Response.ok(toJson("Hello World"), MediaType.APPLICATION_JSON).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") int id) {
        LOGGER.info("DELETE: " + id);
        return Response.noContent().build();
    }

    @PUT
    public Response put(JsonObject jsonObject) {
        JsonObject result = Json.createObjectBuilder()
                .add("message", "Hello World PUT")
                .add("input", jsonObject)
                .build();
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

    @POST
    public Response post(JsonObject jsonObject) {
        JsonObject result = Json.createObjectBuilder()
                .add("message", "Hello World POST")
                .add("input", jsonObject)
                .build();
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

    @PATCH
    public Response patch(JsonObject jsonObject) {
        JsonObject result = Json.createObjectBuilder()
                .add("message", "Hello World PATCH")
                .add("input", jsonObject)
                .build();
        return Response.ok(result, MediaType.APPLICATION_JSON).build();
    }

    private JsonObject toJson(String message) {
        return Json.createObjectBuilder().add("message", message).build();
    }
}
