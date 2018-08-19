package nl.topicus.spanner.jpa.entities;

import java.sql.Timestamp;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
  /**
   * 
   * @return The last commit timestamp from Cloud Spanner. This query does not actually do a round
   *         trip to Cloud Spanner, but fetches a value stored in memory in the Cloud Spanner JDBC
   *         connection. Note that this query is not specific for the Customer entity or this
   *         repository, but could be used in any context to get the latest commit timestamp
   *         returned by Cloud Spanner.
   */
  @Query(nativeQuery = true, value = "GET_LAST_COMMIT_TIMESTAMP")
  Timestamp getLastCommitTimestamp();

  List<Customer> findByLastName(String lastName);

  @Query("SELECT c FROM Customer c")
  Page<Customer> findCustomer(Pageable pageable);

  @Query("SELECT c FROM Customer c where CONCAT(firstName, ' ', lastName)=:fullName")
  Page<Customer> findCustomerByConcatFullName(Pageable pageable,
      @Param("fullName") String fullName);

  Page<Customer> findCustomerByFullName(Pageable pageable, @Param("fullName") String fullName);

  /**
   * Example query calling a custom function
   */
  @Query("SELECT c.uuid AS code, CONCAT('CODE-', c.uuid) AS formattedCode FROM Customer c")
  List<FormattedCustomerProjection> listCustomerProjections();

}
