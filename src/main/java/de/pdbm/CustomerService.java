package de.pdbm;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.HashMap;
import java.util.UUID;

@RequestScoped
public class CustomerService {
    private static final HashMap<String, Customer> CUSTOMERS = new HashMap<String, Customer>();
    @PersistenceContext
    EntityManager em;

    public HashMap<String, Customer> getAllCustomers() {
        return CUSTOMERS;
    }

    public Customer getCustomer(Integer id) {
        return em.find(Customer.class, id);
    }

    @Transactional
    public void saveCustomer(Customer customer) {
        em.persist(customer);
    }

    public boolean putCustomer(String uuid, Customer customer) {
        if (!CUSTOMERS.containsKey(uuid)) {
            return false;
        }
        CUSTOMERS.put(uuid, customer);
        return true;
    }

    public boolean patchCustomer(String uuid, Customer customer) {
        if (!CUSTOMERS.containsKey(uuid)) {
            return false;
        }
        if (customer.getFirstName() != null) {
            CUSTOMERS.get(uuid).setFirstName(customer.getFirstName());
        }
        if (customer.getLastName() != null) {
            CUSTOMERS.get(uuid).setLastName(customer.getLastName());
        }
        if (customer.getDob() != null) {
            CUSTOMERS.get(uuid).setDob(customer.getDob());
        }
        return true;
    }

    public boolean deleteCustomer(String uuid) {
        if (CUSTOMERS.containsKey(uuid)) {
            CUSTOMERS.remove(uuid);
            return true;
        }
        return false;
    }
}
