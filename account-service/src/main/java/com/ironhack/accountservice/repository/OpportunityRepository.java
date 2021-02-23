package com.ironhack.accountservice.repository;

import com.ironhack.accountservice.model.Opportunity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OpportunityRepository extends JpaRepository<Opportunity, Integer> {

    //Returns a list of ids and count of all Opportunities by SalesRep
    /*@Query(value = "SELECT s.name, COUNT(*) FROM SalesRep s JOIN FETCH Opportunity o ON s.id = o.salesRepId " +
            "GROUP BY s.name")*/
    @Query("SELECT salesRepId, COUNT(*) FROM Opportunity GROUP BY salesRepId")
    List<Object[]> countOfOpportunitiesBySalesRepId();

    //Returns a list of names and count of all Opportunities by SalesRep where status is CLOSED_WON
    /*@Query(value = "SELECT s.name, COUNT(*) FROM SalesRep s JOIN FETCH Opportunity o ON s.id = o.salesRepId " +
            "WHERE o.status = CLOSED_WON GROUP BY s.name")*/
    @Query("SELECT salesRepId, COUNT(*) FROM Opportunity WHERE status = CLOSED_WON GROUP BY salesRepId")
    List<Object[]> countOfOpportunitiesBySalesRepsWhereClosedWon();

    //Returns a list of names and count of all Opportunities by SalesRep where status is CLOSED_LOST
    /*@Query(value = "SELECT s.name, COUNT(*) FROM SalesRep s JOIN FETCH Opportunity o ON s.id = o.salesRepId " +
            "WHERE o.status = CLOSED_LOST GROUP BY s.name")*/
    @Query("SELECT salesRepId, COUNT(*) FROM Opportunity WHERE status = CLOSED_LOST GROUP BY salesRepId")
    List<Object[]> countOfOpportunitiesBySalesRepsWhereClosedLost();

    //Returns a list of names and count of all Opportunities by SalesRep where status is OPEN
    /*@Query(value = "SELECT s.name, COUNT(*) FROM SalesRep s JOIN FETCH Opportunity o ON s.id = o.salesRepId " +
            "WHERE o.status = OPEN GROUP BY s.name")*/
    @Query("SELECT salesRepId, COUNT(*) FROM Opportunity WHERE status = OPEN GROUP BY salesRepId")
    List<Object[]> countOfOpportunitiesBySalesRepsWhereOpen();

    //Returns a list of products and count of all Opportunities by Product
    @Query("SELECT product, COUNT(*) FROM Opportunity GROUP BY product")
    List<Object[]> countOfOpportunitiesByProduct();

    //Returns a list of products and count of all Opportunities by Product where status is CLOSED_WON
    @Query("SELECT product, COUNT(*) FROM Opportunity o WHERE o.status = 'CLOSED_WON' GROUP BY product")
    List<Object[]> countOfOpportunitiesByProductWhereClosedWon();

    //Returns a list of products and count of all Opportunities by Product where status is CLOSED_LOST
    @Query("SELECT product, COUNT(*) FROM Opportunity o WHERE o.status = 'CLOSED_LOST' GROUP BY product")
    List<Object[]> countOfOpportunitiesByProductWhereClosedLost();

    //Returns a list of products and count of all Opportunities by Product where status is OPEN
    @Query("SELECT product, COUNT(*) FROM Opportunity o WHERE o.status = 'OPEN' GROUP BY product")
    List<Object[]> countOfOpportunitiesByProductWhereOpen();

    //Returns a list of products and count of all Opportunities by Country
    @Query("SELECT a.country, COUNT(*) FROM Opportunity o JOIN FETCH Account a ON a.id = o.account " +
            "GROUP BY a.country")
    List<Object[]> countOfOpportunitiesByCountry();

    //Returns a list of products and count of all Opportunities by Country where status is CLOSED_WON
    @Query("SELECT a.country, COUNT(*) FROM Opportunity o JOIN Account a ON a.id = o.account WHERE o.status = 'CLOSED_WON' GROUP BY a.country")
    List<Object[]> countOfOpportunitiesByCountryWhereClosedWon();

    //Returns a list of products and count of all Opportunities by Country where status is CLOSED_LOST
    @Query("SELECT a.country, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = 'CLOSED_LOST' GROUP BY a.country")
    List<Object[]> countOfOpportunitiesByCountryWhereClosedLost();

    //Returns a list of products and count of all Opportunities by Country where status is OPEN
    @Query("SELECT a.country, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = 'OPEN' GROUP BY a.country")
    List<Object[]> countOfOpportunitiesByCountryWhereOpen();

    //Returns a list of products and count of all Opportunities by City
    @Query(value = "SELECT a.city, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "GROUP BY a.city")
    List<Object[]> countOfOpportunitiesByCity();

    //Returns a list of products and count of all Opportunities by City where status is CLOSED_WON
    @Query(value = "SELECT a.city, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = CLOSED_WON GROUP BY a.city")
    List<Object[]> countOfOpportunitiesByCityWhereClosedWon();

    //Returns a list of products and count of all Opportunities by City where status is CLOSED_LOST
    @Query(value = "SELECT a.city, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = CLOSED_LOST GROUP BY a.city")
    List<Object[]> countOfOpportunitiesByCityWhereClosedLost();

    //Returns a list of products and count of all Opportunities by City where status is OPEN
    @Query(value = "SELECT a.city, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = OPEN GROUP BY a.city")
    List<Object[]> countOfOpportunitiesByCityWhereOpen();

    //Returns a list of products and count of all Opportunities by Industry
    @Query(value = "SELECT a.industry, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "GROUP BY a.industry")
    List<Object[]> countOfOpportunitiesByIndustry();

    //Returns a list of products and count of all Opportunities by Industry where status is CLOSED_WON
    @Query(value = "SELECT a.industry, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = CLOSED_WON GROUP BY a.industry")
    List<Object[]> countOfOpportunitiesByIndustryWhereClosedWon();

    //Returns a list of products and count of all Opportunities by Industry where status is CLOSED_LOST
    @Query(value = "SELECT a.industry, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = CLOSED_LOST GROUP BY a.industry")
    List<Object[]> countOfOpportunitiesByIndustryWhereClosedLost();

    //Returns a list of products and count of all Opportunities by Industry where status is OPEN
    @Query(value = "SELECT a.industry, COUNT(*) FROM Account a JOIN FETCH Opportunity o ON a.id = o.account " +
            "WHERE o.status = OPEN GROUP BY a.industry")
    List<Object[]> countOfOpportunitiesByIndustryWhereOpen();


    @Query("SELECT AVG(quantity) FROM Opportunity")
    public double meanOfQuantity();

    @Query(value = "SELECT AVG(op.quantity) FROM " +
            "(SELECT o.quantity, @rownum\\:=@rownum+1 as 'row_number', @total_rows\\:=@rownum " +
            "FROM opportunities o, (SELECT @rownum\\:=0) r " +
            "ORDER BY o.quantity) as op " +
            "WHERE op.row_number IN (FLOOR((@total_rows+1)/2), FLOOR((@total_rows+2)/2))", nativeQuery = true)
    public double medianOfQuantity();

    @Query("SELECT MAX(quantity) FROM Opportunity")
    public Integer maxOfQuantity();

    @Query("SELECT MIN(quantity) FROM Opportunity")
    public Integer minOfQuantity();


    //Returns a count of Opportunities for a specific SalesRep Id
    Integer countBySalesRepId(Integer salesRepId);

    //Return a count of Opportunities for a specific SalesRep Name
    //Integer countBySalesRepName(String salesRep);

}
