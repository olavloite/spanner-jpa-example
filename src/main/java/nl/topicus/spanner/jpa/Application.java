package nl.topicus.spanner.jpa;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import nl.topicus.spanner.jpa.entities.Customer;
import nl.topicus.spanner.jpa.entities.CustomerRepository;
import nl.topicus.spanner.jpa.entities.Invoice;
import nl.topicus.spanner.jpa.entities.InvoiceRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Application
{
	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args)
	{
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository customerRepo, InvoiceRepository invoiceRepo)
	{
		return (args) -> {
			// reset repositories
			invoiceRepo.deleteAll();
			customerRepo.deleteAll();
			// save a couple of customers
			List<Long> customerIds = new ArrayList<>();
			customerIds.add(customerRepo.save(new Customer("Jack", "Bauer")).getId());
			customerIds.add(customerRepo.save(new Customer("Chloe", "O'Brian")).getId());
			customerIds.add(customerRepo.save(new Customer("Kim", "Bauer")).getId());
			customerIds.add(customerRepo.save(new Customer("David", "Palmer")).getId());
			customerIds.add(customerRepo.save(new Customer("Michelle", "Dessler")).getId());

			byte[] pdf = Files.readAllBytes(Paths.get(Application.class.getResource("pdf-sample.pdf").toURI()));
			List<Long> invoiceIds = new ArrayList<>();
			invoiceIds.add(invoiceRepo.save(
					new Invoice(customerRepo.findOne(customerIds.get(0)), "001", BigDecimal.valueOf(29.50), pdf))
					.getId());

			// fetch all customers
			log.info("Customers found with findAll():");
			log.info("-------------------------------");
			for (Customer customer : customerRepo.findAll())
			{
				log.info(customer.toString());
			}
			log.info("");

			// fetch an individual customer by ID
			Customer customer = customerRepo.findOne(customerIds.get(0));
			log.info("Customer found with findOne(" + customerIds.get(0) + "):");
			log.info("--------------------------------");
			log.info(customer.toString());
			log.info("");

			// fetch customers by last name
			log.info("Customer found with findByLastName('Bauer'):");
			log.info("--------------------------------------------");
			for (Customer bauer : customerRepo.findByLastName("Bauer"))
			{
				log.info(bauer.toString());
			}
			log.info("");

			// fetch invoices
			Invoice invoice = invoiceRepo.findOne(invoiceIds.get(0));
			log.info("Invoice found with findOne(" + invoiceIds.get(0) + "):");
			log.info("--------------------------------");
			log.info(invoice.toString());
			log.info("");
		};
	}
}
