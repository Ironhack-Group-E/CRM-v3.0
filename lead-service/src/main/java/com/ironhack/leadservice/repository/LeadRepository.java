package com.ironhack.leadservice.repository;

import com.ironhack.leadservice.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Integer> {

    //  Returns a list of names and count of all Leads by SalesRep
    @Query(value = "SELECT salesRepId, COUNT(*) FROM Lead GROUP BY salesRepId")
    List<Object[]> countOfLeadsBySalesReps();


    /**
    *
    *   ESTAS QUERIES NO HACEN FALTA INTEGRARLAS (NO TENÍAN SENTIDO), SOLO LA PRIMERA
    *
     **/



//    //Returns a list of products and count of all Leads by Product
//    @Query(value = "sELECT o.product, COUNT(*) FROM leads l JOIN sales_rep s ON l.sales_rep_id = s.id JOIN opportunities o ON s.id = o.sales_rep_id GROUP BY o.product;", nativeQuery = true)
//    List<Object[]> countOfLeadsByProduct();
//
//    //Return a list of Leads and count all Lead by Country
//    @Query(value = "SELECT a.country, COUNT(*) FROM accounts a " +
//            "JOIN opportunities o ON a.id = o.account_id " +
//            "JOIN sales_rep s ON s.id = o.sales_rep_id " +
//            "JOIN leads l ON s.id = l.sales_rep_id GROUP BY a.country", nativeQuery = true)
//    List<Object[]> countOfLeadsByCountry();
//
//    //Return a list of Leads and count all Lead by city
//    @Query(value = "SELECT a.city, COUNT(*) FROM accounts a " +
//            "JOIN opportunities o ON a.id = o.account_id " +
//            "JOIN sales_rep s ON s.id = o.sales_rep_id " +
//            "JOIN leads l ON s.id = l.sales_rep_id GROUP BY a.city", nativeQuery = true)
//    List<Object[]> countOfLeadsByCity();
//
//    //Return a list of Leads and count all Lead by industry
//    @Query(value = "SELECT a.industry, COUNT(*) FROM accounts a " +
//            "JOIN opportunities o ON a.id = o.account_id " +
//            "JOIN sales_rep s ON s.id = o.sales_rep_id " +
//            "JOIN leads l ON s.id = l.sales_rep_id GROUP BY a.industry", nativeQuery = true)
//    List<Object[]> countOfLeadsByIndustry();

}