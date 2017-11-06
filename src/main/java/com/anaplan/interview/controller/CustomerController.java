package com.anaplan.interview.controller;

import com.anaplan.interview.dao.CustomerDao;
import com.anaplan.interview.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    @Autowired
    private CustomerDao customerDao;

    @RequestMapping("customers/{id}")
    public Customer getCustomerById(@PathVariable int id) {
        Customer customer = customerDao.findByCustomerId(id);
        return customer;
    }

    @RequestMapping(value = "/customers/{id}", method = RequestMethod.DELETE)
    public String deletCustomerById(@PathVariable int id) {
        int count = customerDao.deleteCustomer(id);
        if (count > 0) {
            return "SUCCESS";
        }
        return "FAILURE";
    }

    @RequestMapping("customers")
    public List<Customer> getCustomers() {
        List<Customer> customers = customerDao.getAllCustomers();
        return customers;
    }

    @RequestMapping(value = "customers", method = RequestMethod.POST)
    public String createCustomer(@RequestBody Customer customer) {
        int count = customerDao.createCustomer(customer.getFirstName(), customer.getLastName());
        if (count > 0) {
            return "SUCCESS";
        }
        return "FAILURE";
    }
}
