package nl.topicus.spanner.jpa.entities;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long>
{
	List<Customer> findByLastName(String lastName);

	@Query("SELECT c FROM Customer c")
	Page<Customer> findCustomer(Pageable pageable);

}
