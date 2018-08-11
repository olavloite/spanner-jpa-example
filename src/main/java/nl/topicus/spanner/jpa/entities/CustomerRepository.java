package nl.topicus.spanner.jpa.entities;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
	List<Customer> findByLastName(String lastName);

	@Query("SELECT c FROM Customer c")
	Page<Customer> findCustomer(Pageable pageable);

	@Query("SELECT c FROM Customer c where CONCAT(firstName, ' ', lastName)=:fullName")
	Page<Customer> findCustomerByConcatFullName(Pageable pageable, @Param("fullName") String fullName);

	Page<Customer> findCustomerByFullName(Pageable pageable, @Param("fullName") String fullName);

	/**
	 * The first (commented) query does not work because of the CONCAT(...) call
	 * 
	 * @return
	 */
	// @Query("SELECT c.uuid AS code, CONCAT('CODE-', c.uuid) AS
	// formattedCode FROM Customer c")
	@Query("SELECT c.uuid AS code, c.fullName as formattedCode FROM Customer c")
	List<FormattedCustomerProjection> listCustomerProjections();

}
