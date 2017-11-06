package com.anaplan.interview.dao;


import com.anaplan.interview.domain.Customer;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerRowMapper implements RowMapper {
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer(rs.getLong("id"),
                rs.getString("first_name"), rs.getString("last_name"));
        return customer;
    }
}
