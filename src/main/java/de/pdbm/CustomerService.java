package de.pdbm;

import jakarta.enterprise.context.RequestScoped;

import java.util.HashMap;
import java.util.UUID;

@RequestScoped
public class CustomerService {
    private static final HashMap<String, Customer> CUSTOMERS = new HashMap<String, Customer>();

    public Customer getCustomer(String uuid) {
        return CUSTOMERS.get(uuid);
    }

    public UUID saveCustomer(Customer customer) {
        UUID uuid = UUID.randomUUID();
        CUSTOMERS.put(uuid.toString(), customer);
        return uuid;
    }
}
