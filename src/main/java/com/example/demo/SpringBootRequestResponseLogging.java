package com.example.demo;

import ch.qos.logback.access.servlet.TeeFilter;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;

@SpringBootApplication
@RestController
@Slf4j
public class SpringBootRequestResponseLogging {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootRequestResponseLogging.class, args);
    }

    @PostMapping("/v1")
    public Customer createCustomer(@RequestBody Customer customer) {
        log.info("This is application log");
        customer.setName("Firstname Lastname");
        return customer;
    }

    @PostMapping("/v2")
    public Customer v2CreateCustomer(@RequestBody Customer customer) {
        log.info("This is application log");
        customer.setName("Firstname Middlename Lastname");
        return customer;
    }

    @Bean
    public FilterRegistrationBean requestResponseFilter() {
        final FilterRegistrationBean filterRegBean = new FilterRegistrationBean();
        TeeFilter filter = new TeeFilter();
        filterRegBean.setFilter(filter);
        filterRegBean.addUrlPatterns("/v1/*");
        filterRegBean.setName("Request Response Filter");
        filterRegBean.setAsyncSupported(Boolean.TRUE);
        return filterRegBean;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class Customer {
        private String name;
        private String address;
        private String state;
        private String zip;
    }
}
