package nl.topicus.spanner.jpa.entities;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface InvoiceRepository extends CrudRepository<Invoice, Long> {

  @Query(nativeQuery = true,
      value = "SELECT * FROM Invoice@{FORCE_INDEX=IDX_INVOICE_INVOICENUMBER_DESCRIPTION} i where invoiceNumber=:invoiceNumber and description=:description")
  Page<Invoice> findInvoiceByNumberAndDescription(Pageable pageable,
      @Param("invoiceNumber") String invoiceNumber, @Param("description") String description);
}
