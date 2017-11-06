package com.anaplan.interview;

import com.anaplan.interview.domain.Customer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {
        SpringApplication.run(Application.class, args);
    }

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... strings) throws Exception {

        log.info("Creating tables");

        jdbcTemplate.execute("DROP TABLE customers IF EXISTS");
        jdbcTemplate.execute("CREATE TABLE customers(" +
                "id SERIAL, first_name VARCHAR(255), last_name VARCHAR(255))");

        jdbcTemplate.execute("DROP TABLE events IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE events(" +
                "id SERIAL, event_name VARCHAR(255), start_time BIGINT, end_time BIGINT)");

        jdbcTemplate.execute("DROP TABLE customers_events IF EXISTS");

        jdbcTemplate.execute("CREATE TABLE customers_events(" +
                "customer_id BIGINT, event_id BIGINT)");
        
        // Split up the array of whole names into an array of first/last names
        List<Object[]> splitUpNames = Arrays.asList("John Woo", "Jeff Dean", "Josh Bloch", "Josh Long").stream()
                .map(name -> name.split(" "))
                .collect(Collectors.toList());

        // Use a Java 8 stream to print out each tuple of the list
        splitUpNames.forEach(name -> log.info(String.format("Inserting customer record for %s %s", name[0], name[1])));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);

        log.info("Querying for customer records where first_name = 'Josh':");
        jdbcTemplate.query(
                "SELECT id, first_name, last_name FROM customers WHERE first_name = ?", new Object[] { "Josh" },
                (rs, rowNum) -> new Customer(rs.getLong("id"), rs.getString("first_name"), rs.getString("last_name"))
        ).forEach(customer -> log.info(customer.toString()));

        // Uses JdbcTemplate's batchUpdate operation to bulk load data
        jdbcTemplate.batchUpdate("INSERT INTO customers(first_name, last_name) VALUES (?,?)", splitUpNames);
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas breakfast', 1514214000000, 1514217600000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas lunch', 1514232000000, 1514235600000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas dinner', 1514257200000, 1514264400000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('Root Canal', 1514210400000, 1514242800000)");


        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas lunch', 1514232000000, 1514235600000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('xmas dinner', 1514257200000, 1514264400000)");
        jdbcTemplate.update("INSERT INTO events (event_name, start_time, end_time) VALUES('Root Canal', 1514210400000, 1514242800000)");

        jdbcTemplate.update("INSERT INTO customers_events VALUES(1,2);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(1,3);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(2,1);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,1);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,2);");
        jdbcTemplate.update("INSERT INTO customers_events VALUES(3,3);");
    }
}