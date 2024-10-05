package de.pdbm;

import jakarta.json.JsonObject;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.WebTarget;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HelloWorldIT {
    Client client;

    @BeforeEach
    public void before() {
        client = ClientBuilder.newClient();
    }

    @AfterEach
    public void after() {
        client.close();
    }

    @Test
    public void getHelloWorld() {
        WebTarget target = client.target("http://localhost:8080/starter/api/helloworld");
        Response response = target.request(MediaType.APPLICATION_JSON).get(Response.class);

        assertEquals(200, response.getStatus());

        JsonObject requestBody = response.readEntity(JsonObject.class);
        assertEquals("Hello World", requestBody.getString("message"));
    }

    @Test
    public void delete() {
        WebTarget target = client.target("http://localhost:8080/starter/api/helloworld/1");
        try (Response response = target.request(MediaType.APPLICATION_JSON).delete(Response.class)) {
            assertEquals(204, response.getStatus());
        }
    }
}
