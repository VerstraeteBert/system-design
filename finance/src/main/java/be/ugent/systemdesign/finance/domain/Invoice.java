package be.ugent.systemdesign.finance.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDate;
import java.util.List;

@Document
@Getter
@NoArgsConstructor
public class Invoice {
    @MongoId
    private String invoiceId;
    private String patientId;
    private String hospitalStayId;
    private InvoiceStatus status;
    private LocalDate created;
    private List<LineItem> lineItems;

    public Invoice(String invoiceId, String patientId, String hospitalStayId, InvoiceStatus status, LocalDate created, List<LineItem> lineItems) {
        this.invoiceId = invoiceId;
        this.patientId = patientId;
        this.hospitalStayId = hospitalStayId;
        this.status = status;
        this.created = created;
        this.lineItems = lineItems;
    }

    void addLineItem(String name, Integer cost) {
        if (this.status == InvoiceStatus.CLOSED) {
            throw new ClosedInvoicesCannotBeChangedException();
        }
        this.lineItems.add(new LineItem(name, cost));
    }

    void removeLineItem(String name, Integer cost) {
        if (this.status == InvoiceStatus.CLOSED) {
            throw new ClosedInvoicesCannotBeChangedException();
        }
        lineItems.remove(new LineItem(name, cost));
    }

    void closeInvoice() {
        this.status = InvoiceStatus.CLOSED;
    }

    void openInvoice() {
        if (this.status == InvoiceStatus.CLOSED) {
            throw new ClosedInvoiceCannotBeOpenedException();
        }
        this.status = InvoiceStatus.OPEN;
    }

    void markInvoicePaid() {
        this.status = InvoiceStatus.PAID;
    }
}
