package com.ironhack.salesRepservice.repository;

import com.ironhack.salesRepservice.model.SalesRep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SalesRepRepository extends JpaRepository<SalesRep, Integer> {
}
