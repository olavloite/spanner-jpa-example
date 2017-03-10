package nl.topicus.spanner.jpa.entities;

import java.util.Date;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

import com.fasterxml.uuid.Generators;

@MappedSuperclass
public class BaseEntity
{
	@Id
	private Long id;

	@Column(nullable = false, columnDefinition = "BYTES(16)", unique = true)
	private UUID uuid;

	@Column(nullable = false)
	private Date created;

	@Column(nullable = false)
	private Date updated;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public UUID getUuid()
	{
		if (uuid == null)
			uuid = Generators.randomBasedGenerator().generate();
		return uuid;
	}

	public void setUuid(UUID uuid)
	{
		this.uuid = uuid;
	}

	@PrePersist
	protected void onCreate()
	{
		created = new Date();
		updated = new Date();
		if (uuid == null)
			uuid = Generators.randomBasedGenerator().generate();
		if (id == null)
			id = uuid.getMostSignificantBits();
	}

	@PreUpdate
	protected void onUpdate()
	{
		updated = new Date();
	}

	@Override
	public boolean equals(Object other)
	{
		if (this == other)
			return true;
		if (!(other instanceof Customer))
			return false;

		return ((Customer) other).getUuid().equals(this.getUuid());
	}

	@Override
	public int hashCode()
	{
		return getUuid().hashCode();
	}

	public boolean isSaved()
	{
		return getId() != null;
	}

}
