package nl.topicus.spanner.jpa.service;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import nl.topicus.spanner.jpa.entities.Customer;
import nl.topicus.spanner.jpa.entities.CustomerRepository;
import nl.topicus.spanner.jpa.entities.Phone;

@Service
public class EntityService {
  private static final Logger log = LoggerFactory.getLogger(EntityService.class);

  @Autowired
  private CustomerRepository customerRepo;

  @Transactional(value = TxType.REQUIRED)
  public void addPhone(Long customerId, Integer tel) {
    Customer customer = customerRepo.findById(customerId).get();
    Phone phone = new Phone();
    phone.setNumber(tel);
    customer.getPhones().add(phone);
    customerRepo.save(customer);
  }

  @Transactional(value = TxType.REQUIRED)
  public void setPhones(Long customerId, List<Integer> tels) {
    List<Phone> phones = tels.stream().map(tel -> {
      return new Phone(tel);
    }).collect(Collectors.toList());
    Customer customer = customerRepo.findById(customerId).get();
    customer.setPhones(phones);
    customerRepo.save(customer);
  }

  @Transactional(value = TxType.REQUIRED)
  public void printPhones(Long customerId) {
    Customer customer = customerRepo.findById(customerId).get();
    Iterator<Phone> phones = customer.getPhones().iterator();
    while (phones.hasNext())
      log.info(phones.next().getNumber().toString());
  }

  /**
   * This method emulates a long transaction. Transactions should be kept as short as possible on
   * Cloud Spanner, and the JDBC driver will log this slow transaction in a log file if logging has
   * been setup for the JDBC driver.
   * 
   * @return All customers, but it will take at least 7 seconds
   */
  @Transactional(value = TxType.REQUIRED)
  public Iterable<Customer> findAllCustomersSlow() {
    Iterable<Customer> res = customerRepo.findAll();
    try {
      Thread.sleep(7000L);
    } catch (InterruptedException e) {
      // ignore
    }
    return res;
  }

}
