package nl.topicus.spanner.jpa.entities;

import java.sql.Timestamp;
import javax.persistence.AttributeConverter;
import nl.topicus.jdbc.statement.CloudSpannerPreparedStatement;

public class CommitTimestampAttributeConverter implements AttributeConverter<Timestamp, Timestamp> {

  @Override
  public Timestamp convertToDatabaseColumn(Timestamp attribute) {
    return CloudSpannerPreparedStatement.getSpannerCommitTimestamp();
  }

  @Override
  public Timestamp convertToEntityAttribute(Timestamp dbData) {
    return dbData;
  }

}
