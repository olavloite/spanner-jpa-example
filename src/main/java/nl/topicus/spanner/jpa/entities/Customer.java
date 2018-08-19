package nl.topicus.spanner.jpa.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Table;
import org.hibernate.annotations.Formula;

@Entity
@Table(indexes = {@Index(name = "IDX_CUSTOMER_LASTNAME", columnList = "lastName")})
public class Customer extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @Column(length = 50, nullable = true)
  private String firstName;

  @Column(length = 100, nullable = false)
  private String lastName;

  @Formula("CONCAT(firstName, ' ', lastName)")
  private String fullName;

  @OneToMany(cascade = CascadeType.ALL)
  @JoinTable(name = "CustomerPhone",
      joinColumns = {@JoinColumn(name = "customer_id", referencedColumnName = "id")},
      inverseJoinColumns = {
          @JoinColumn(name = "phone_id", referencedColumnName = "id", unique = true)})
  @OrderColumn(name = "phone_order")
  private List<Phone> phones = new ArrayList<>();

  protected Customer() {}

  public Customer(String firstName, String lastName) {
    this.firstName = firstName;
    this.lastName = lastName;
  }

  @Override
  public String toString() {
    return String.format("Customer[id=%d, firstName='%s', lastName='%s', fullName='%s']", getId(),
        firstName, lastName, fullName);
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public List<Phone> getPhones() {
    return phones;
  }

  public void setPhones(List<Phone> phones) {
    this.phones = phones;
  }

  public String getFullName() {
    return fullName;
  }

  public void setFullName(String fullName) {
    this.fullName = fullName;
  }
}
