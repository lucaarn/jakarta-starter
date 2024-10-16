package de.pdbm;

import jakarta.enterprise.context.RequestScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@RequestScoped
public class CustomerService {
    @PersistenceContext
    EntityManager em;

    public List<Customer> getAllCustomers() {
        return em.createQuery("SELECT c FROM Customer c", Customer.class).getResultList();
    }

    public Customer getCustomer(Integer id) {
        return em.find(Customer.class, id);
    }

    @Transactional
    public void saveCustomer(Customer customer) {
        em.persist(customer);
    }

    @Transactional
    public boolean putCustomer(Integer id, Customer newCustomer) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return false;
        }

        customer.setFirstName(newCustomer.getFirstName());
        customer.setLastName(newCustomer.getLastName());
        customer.setDob(newCustomer.getDob());
        em.merge(customer);
        return true;
    }

    @Transactional
    public boolean patchCustomer(Integer id, Customer newCustomer) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return false;
        }

        if (newCustomer.getFirstName() != null) {
            customer.setFirstName(newCustomer.getFirstName());
        }
        if (newCustomer.getLastName() != null) {
            customer.setLastName(newCustomer.getLastName());
        }
        if (newCustomer.getDob() != null) {
            customer.setDob(newCustomer.getDob());
        }
        return true;
    }

    @Transactional
    public boolean deleteCustomer(Integer id) {
        Customer customer = em.find(Customer.class, id);
        if (customer == null) {
            return false;
        }

        em.remove(customer);
        return true;
    }

    @Transactional
    public void deleteAllCustomers() {
        em.createQuery("DELETE FROM Customer ").executeUpdate();
    }
}
