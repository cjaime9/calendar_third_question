package com.anaplan.interview.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class EventDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

}
