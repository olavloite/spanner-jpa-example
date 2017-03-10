package nl.topicus.spanner.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Customer extends BaseEntity
{

	@Column(length = 50, nullable = true)
	private String firstName;

	@Column(length = 100, nullable = false)
	private String lastName;

	protected Customer()
	{
	}

	public Customer(String firstName, String lastName)
	{
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@Override
	public String toString()
	{
		return String.format("Customer[id=%d, firstName='%s', lastName='%s']", getId(), firstName, lastName);
	}
}
