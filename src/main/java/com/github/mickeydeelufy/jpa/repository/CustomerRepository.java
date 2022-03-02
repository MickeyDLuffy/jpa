package com.github.mickeydeelufy.jpa.repository;

import com.github.mickeydeelufy.jpa.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.List;

/** The generic params of JpaRepository (Customer, Long): Customer represents the jpa entity whose repository this is
 * and the Long is the daqta type of the Id column in thee Customer class **/
public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Customer findById(long id);

    /***
     *  Pagina data with @Query and Page
     *  **/

    @Query("SELECT c FROM Customer c")
    Page<Customer> findAllPaged(Pageable pageable) ;


    /***
     * Using named params i,e @Param. it makes the code more readable, telling us the params passed to the jpql
     */
    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstname ")
    Customer getCustomerByFirstName(@Param("firstname") String firstname);

    /***
     *
     * @param names : List of names to check from
     * @return customers whose firstname is in NAMES list
     */
    @Query("SELECT c FROM Customer c WHERE c.firstName IN :names")
    List<Customer> getAllCustomersWhereNameInList(@Param("names") List<String> names);

    /***
     * Using @Modifying to mutate the db with UPDATE
     * Note that JPA does not support INSERT queries hence you must use native queries
     *
     */
    @Modifying
    @Query("UPDATE Customer  c SET c.firstName = :newName WHERE c.id = 1")
    int updateNameWith(@Param("newName") String newName);

//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO Customer(id, firstname, lastName ) VALUES(4L, 'Linlin', 'Charlotte')", nativeQuery = true)
//    void insertIntoTable() ;
}
