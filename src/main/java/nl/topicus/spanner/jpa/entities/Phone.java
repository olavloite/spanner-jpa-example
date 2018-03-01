package nl.topicus.spanner.jpa.entities;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Phone extends BaseEntity
{
	private static final long serialVersionUID = 1L;

	@Column()
	private Integer number;

	public Phone()
	{
	}

	public Phone(Integer number)
	{
		setNumber(number);
	}

	public Integer getNumber()
	{
		return number;
	}

	public void setNumber(Integer number)
	{
		this.number = number;
	}

}
