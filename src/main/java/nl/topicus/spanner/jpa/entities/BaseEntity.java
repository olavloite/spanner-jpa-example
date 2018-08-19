package nl.topicus.spanner.jpa.entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import com.fasterxml.uuid.Generators;
import com.fasterxml.uuid.NoArgGenerator;

@MappedSuperclass
public class BaseEntity implements Serializable {
  private static final long serialVersionUID = 1L;

  private static final NoArgGenerator GENERATOR = Generators.randomBasedGenerator();

  @Id
  private Long id;

  @Column(nullable = false, columnDefinition = "BYTES(16)", unique = true)
  private UUID uuid;

  // This field is automatically filled with the commit timestamp on insert,
  // and never updated. Note that the value is only available after a commit, and that you need to
  // refresh the entity before the last value is available.
  @Column(columnDefinition = "timestamp not null options (allow_commit_timestamp=true)",
      updatable = false)
  @Convert(converter = CommitTimestampAttributeConverter.class)
  private Timestamp created;

  // This field is automatically filled with the commit timestamp on insert
  // and update. Note that the value is only available after a commit, and that you need to refresh
  // the entity before the last value is available.
  @Column(columnDefinition = "timestamp not null options (allow_commit_timestamp=true)")
  @Convert(converter = CommitTimestampAttributeConverter.class)
  private Timestamp updated;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public UUID getUuid() {
    if (uuid == null)
      uuid = GENERATOR.generate();
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  @PrePersist
  protected void onCreate() {
    if (uuid == null)
      uuid = GENERATOR.generate();
    if (id == null)
      id = uuid.getMostSignificantBits() ^ uuid.getLeastSignificantBits();
  }

  @Override
  public boolean equals(Object other) {
    if (this == other)
      return true;
    if (!(other instanceof BaseEntity))
      return false;

    return ((BaseEntity) other).getUuid().equals(this.getUuid());
  }

  @Override
  public int hashCode() {
    return getUuid().hashCode();
  }

  public boolean isSaved() {
    return getId() != null;
  }

  public Timestamp getCreated() {
    return created;
  }

  public Timestamp getUpdated() {
    return updated;
  }

  public void setCreated(Timestamp created) {
    this.created = created;
  }

  public void setUpdated(Timestamp updated) {
    this.updated = updated;
  }

}
