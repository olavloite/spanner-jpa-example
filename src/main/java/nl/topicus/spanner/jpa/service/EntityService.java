package nl.topicus.spanner.jpa.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.topicus.spanner.jpa.entities.Customer;
import nl.topicus.spanner.jpa.entities.CustomerRepository;
import nl.topicus.spanner.jpa.entities.Phone;

@Service
public class EntityService
{

	@Autowired
	private CustomerRepository customerRepo;

	@Transactional(value = TxType.REQUIRES_NEW)
	public void addPhone(Long customerId, Integer tel)
	{
		Customer customer = customerRepo.findOne(customerId);
		Phone phone = new Phone();
		phone.setNumber(tel);
		customer.getPhones().add(phone);
		customerRepo.save(customer);
	}

	public void setPhones(Long customerId, List<Integer> tels)
	{
		List<Phone> phones = tels.stream().map(tel -> {
			return new Phone(tel);
		}).collect(Collectors.toList());
		Customer customer = customerRepo.findOne(customerId);
		customer.setPhones(phones);
		customerRepo.save(customer);
	}

}
