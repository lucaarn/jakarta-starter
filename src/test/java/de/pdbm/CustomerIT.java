package de.pdbm;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CustomerIT {
    Client client;

    @BeforeEach
    public void before() {
        client = ClientBuilder.newClient();

        WebTarget target = client.target("http://localhost:8080/starter/api/customers");
        target.request().delete();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void getAllCustomers() {
        WebTarget target = client.target("http://localhost:8080/starter/api/customers");
        Response response = target.request(MediaType.APPLICATION_JSON).get(Response.class);

        assertEquals(200, response.getStatus());

        JsonObject[] customers = response.readEntity(JsonObject[].class);
        assertEquals(0, customers.length);
    }

    @Test
    public void getCustomer() {
        WebTarget postTarget = client.target("http://localhost:8080/starter/api/customers");
        JsonObject input = Json.createObjectBuilder()
                .add("firstName", "Luca")
                .add("lastName", "Arnecke")
                .add("dob", "2002-11-26")
                .build();
        try (Response postResponse = postTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(input))) {
            URI locationHeader = postResponse.getLocation();

            WebTarget getTarget = client.target(locationHeader);
            Response getResponse = getTarget.request(MediaType.APPLICATION_JSON).get(Response.class);

            assertEquals(200, getResponse.getStatus());

            JsonObject customer = getResponse.readEntity(JsonObject.class);
            assertEquals("Luca", customer.getString("firstName"));
            assertEquals("Arnecke", customer.getString("lastName"));
            assertEquals("2002-11-26", customer.getString("dob"));
        }
    }

    @Test
    public void createCustomer() {
        WebTarget postTarget = client.target("http://localhost:8080/starter/api/customers");
        JsonObject input = Json.createObjectBuilder()
                .add("firstName", "Luca")
                .add("lastName", "Arnecke")
                .add("dob", "2002-11-26")
                .build();
        try (Response postResponse = postTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(input))) {
            assertEquals(201, postResponse.getStatus());

            URI locationHeader = postResponse.getLocation();
            assertNotNull(locationHeader, "Location header is missing in the response");

            WebTarget getTarget = client.target(locationHeader);
            Response getResponse = getTarget.request(MediaType.APPLICATION_JSON).get(Response.class);

            assertEquals(200, getResponse.getStatus());

            JsonObject customer = getResponse.readEntity(JsonObject.class);
            assertEquals("Luca", customer.getString("firstName"));
            assertEquals("Arnecke", customer.getString("lastName"));
            assertEquals("2002-11-26", customer.getString("dob"));
        }
    }

    @Test
    public void putCustomer() {
        WebTarget postTarget = client.target("http://localhost:8080/starter/api/customers");
        JsonObject input = Json.createObjectBuilder()
                .add("firstName", "Luca")
                .add("lastName", "Arnecke")
                .add("dob", "2002-11-26")
                .build();
        try (Response postResponse = postTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(input))) {
            URI locationHeader = postResponse.getLocation();

            WebTarget putTarget = client.target(locationHeader);
            JsonObject putInput = Json.createObjectBuilder()
                    .add("firstName", "Lucaa")
                    .add("lastName", "Arneckee")
                    .add("dob", "2002-11-25")
                    .build();
            try (Response putResponse = putTarget.request(MediaType.APPLICATION_JSON).put(Entity.json(putInput))) {
                assertEquals(204, putResponse.getStatus());

                Response getResponse = putTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
                JsonObject customer = getResponse.readEntity(JsonObject.class);
                assertEquals("Lucaa", customer.getString("firstName"));
                assertEquals("Arneckee", customer.getString("lastName"));
                assertEquals("2002-11-25", customer.getString("dob"));
            }
        }
    }

    @Test
    public void patchCustomer() {
        WebTarget postTarget = client.target("http://localhost:8080/starter/api/customers");
        JsonObject input = Json.createObjectBuilder()
                .add("firstName", "Luca")
                .add("lastName", "Arnecke")
                .add("dob", "2002-11-26")
                .build();
        try (Response postResponse = postTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(input))) {
            URI locationHeader = postResponse.getLocation();

            WebTarget patchTarget = client.target(locationHeader);
            JsonObject patchInput = Json.createObjectBuilder()
                    .add("firstName", "Lucaa")
                    .build();
            try (Response putResponse = patchTarget.request(MediaType.APPLICATION_JSON).method("PATCH", Entity.json(patchInput))) {
                assertEquals(204, putResponse.getStatus());

                Response getResponse = patchTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
                JsonObject customer = getResponse.readEntity(JsonObject.class);
                assertEquals("Lucaa", customer.getString("firstName"));
                assertEquals("Arnecke", customer.getString("lastName"));
                assertEquals("2002-11-26", customer.getString("dob"));
            }
        }
    }

    @Test
    public void deleteCustomer() {
        WebTarget postTarget = client.target("http://localhost:8080/starter/api/customers");
        JsonObject input = Json.createObjectBuilder()
                .add("firstName", "Luca")
                .add("lastName", "Arnecke")
                .add("dob", "2002-11-26")
                .build();
        try (Response postResponse = postTarget.request(MediaType.APPLICATION_JSON).post(Entity.json(input))) {
            URI locationHeader = postResponse.getLocation();

            WebTarget deleteTarget = client.target(locationHeader);
            try (Response deleteResponse = deleteTarget.request(MediaType.APPLICATION_JSON).delete(Response.class)) {
                assertEquals(204, deleteResponse.getStatus());

                Response getResponse = deleteTarget.request(MediaType.APPLICATION_JSON).get(Response.class);
                assertEquals(404, getResponse.getStatus());
            }
        }
    }
}
