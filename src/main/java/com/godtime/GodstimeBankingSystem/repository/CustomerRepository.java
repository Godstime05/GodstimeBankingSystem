package com.godtime.GodstimeBankingSystem.repository;


import com.godtime.GodstimeBankingSystem.model.customer.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query(value = "SELECT * FROM customer where id = 1", nativeQuery = true)
    String selectCustomer(Long id);
    Optional<Customer> findCustomerByNationalId(String nationalId);
}
