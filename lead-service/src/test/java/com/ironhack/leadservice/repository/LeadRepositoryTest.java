package com.ironhack.leadservice.repository;

import com.ironhack.leadservice.model.Lead;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LeadRepositoryTest {

    @Autowired
    private LeadRepository leadRepository;

    @BeforeEach
    void setUp() {

        Lead lead1 = leadRepository.save(new Lead(001, "Pedro Luis",
                "pedro.luis@gmail.com", "IKEA", "666 333 222 111", 1));
        Lead lead2 = leadRepository.save(new Lead(002, "Pedro Juan",
                "pedro.luis@gmail.comm", "Muebles bonicos", "666 333 222 1112", 1));
        Lead lead3 = leadRepository.save(new Lead(003, "Pedro piedras",
                "pedro.luis@gmail.commm", "Mueblesfeos", "666 333 222 1113", 1));

    }

    @AfterEach
    void tearDown() {
        leadRepository.deleteAll();
    }


    @Test
    void countOfLeadsBySalesReps_salesRepExistent_listOfNameAndCount() {

        List<Object[]> result = leadRepository.countOfLeadsBySalesReps();
        assertEquals(1, result.size());
        assertEquals(1, result.get(0)[0]);
        assertEquals(3L, result.get(0)[1]);
    }

}