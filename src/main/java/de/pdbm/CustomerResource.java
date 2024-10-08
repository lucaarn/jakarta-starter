package de.pdbm;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;

import java.util.UUID;
import java.util.logging.Logger;

@Path("/customers")
@Produces(MediaType.APPLICATION_JSON)
public class CustomerResource {

    @Inject
    CustomerService customerService;

    @Context
    UriInfo uriInfo;

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") String uuid) {
        Customer customer = customerService.getCustomer(uuid);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(customer).build();
        }
    }

    @POST
    public Response createCustomer(Customer customer) {
        UUID uuid = customerService.saveCustomer(customer);
        Link link = Link.fromUri(uriInfo.getPath() + "/" + uuid).build();
        return Response.created(link.getUri()).build();
    }

    @PostConstruct
    public void init() {
        Logger.getLogger(CustomerService.class.getCanonicalName()).info("created customer");
    }
}
