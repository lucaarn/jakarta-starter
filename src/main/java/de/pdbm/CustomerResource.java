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
    public Response getAllCustomers() {
        return Response.ok(customerService.getAllCustomers()).build();
    }

    @GET
    @Path("{id}")
    public Response getCustomer(@PathParam("id") Integer id) {
        Customer customer = customerService.getCustomer(id);
        if (customer == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.ok(customer).build();
        }
    }

    @POST
    public Response createCustomer(Customer customer) {
        customerService.saveCustomer(customer);
        Link link = Link.fromUri(uriInfo.getPath() + "/" + customer.getId()).build();
        return Response.created(link.getUri()).build();
    }

    @PUT
    @Path("{id}")
    public Response putCustomer(@PathParam("id") String uuid, Customer customer) {
        boolean updated = customerService.putCustomer(uuid, customer);
        if (!updated) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PATCH
    @Path("{id}")
    public Response patchCustomer(@PathParam("id") String uuid, Customer customer) {
        boolean updated = customerService.patchCustomer(uuid, customer);
        if (!updated) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.noContent().build();
        }
    }

    @DELETE
    @Path("{id}")
    public Response deleteCustomer(@PathParam("id") String uuid) {
        boolean deleted = customerService.deleteCustomer(uuid);
        if (!deleted) {
            return Response.status(Response.Status.NOT_FOUND).build();
        } else {
            return Response.noContent().build();
        }
    }

    @PostConstruct
    public void init() {
        Logger.getLogger(CustomerService.class.getCanonicalName()).info("created customer");
    }
}
