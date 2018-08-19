package nl.topicus.spanner.jpa.entities;

import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(indexes = {@Index(name = "IDX_INVOICE_CUSTOMER", columnList = "customer"), @Index(
    name = "IDX_INVOICE_INVOICENUMBER_DESCRIPTION", columnList = "invoiceNumber, description")})
public class Invoice extends BaseEntity {
  private static final long serialVersionUID = 1L;

  @ManyToOne(fetch = FetchType.EAGER)
  @JoinColumn(nullable = false, name = "customer")
  private Customer customer;

  @Column(length = 15, nullable = false)
  private String invoiceNumber;

  @Column(length = 200)
  private String description;

  @Column(nullable = false)
  private BigDecimal amount;

  @Column(nullable = true, columnDefinition = "BYTES(10000000)")
  private byte[] pdf;

  public Invoice() {}

  public Invoice(Customer customer, String invoiceNumber, BigDecimal amount, byte[] pdf) {
    setCustomer(customer);
    setInvoiceNumber(invoiceNumber);
    setAmount(amount);
    setPdf(pdf);
  }

  public String getInvoiceNumber() {
    return invoiceNumber;
  }

  public void setInvoiceNumber(String invoiceNumber) {
    this.invoiceNumber = invoiceNumber;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public byte[] getPdf() {
    return pdf;
  }

  public void setPdf(byte[] pdf) {
    this.pdf = pdf;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  @Override
  public String toString() {
    return String.format("Invoice[id=%d, invoiceNumber='%s', pdf.length='%s']", getId(),
        getInvoiceNumber(), getPdf() == null ? 0 : getPdf().length);
  }

}
