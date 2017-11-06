package com.anaplan.interview.dao;

import com.anaplan.interview.domain.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomerDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public Customer findByCustomerId(int custId){
        String sql = "SELECT * FROM CUSTOMERS WHERE ID = ?";
        Customer customer = (Customer)jdbcTemplate.queryForObject(
                sql, new Object[] { custId }, new CustomerRowMapper());
        return customer;
    }

    public List<Customer> getAllCustomers() {
        String sql = "SELECT * FROM CUSTOMERS";
        List<Customer> customers = jdbcTemplate.query(sql, new CustomerRowMapper());
        return customers;
    }

    public int deleteCustomer(int custId) {
        String sql = "DELETE FROM CUSTOMERS WHERE ID = " + custId;
        return jdbcTemplate.update(sql);

    }

    public int createCustomer(String firstName, String lastName) {
        String sql = "INSERT INTO CUSTOMERS(first_name, last_name) VALUES('" + firstName + "','"
                + lastName + "');";
        return jdbcTemplate.update(sql);
    }
}
