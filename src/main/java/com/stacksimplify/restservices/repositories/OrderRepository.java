package com.stacksimplify.restservices.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.stacksimplify.restservices.entities.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {

}
