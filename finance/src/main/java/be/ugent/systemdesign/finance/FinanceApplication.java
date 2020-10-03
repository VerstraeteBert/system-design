package be.ugent.systemdesign.finance;

import be.ugent.systemdesign.finance.domain.Invoice;
import be.ugent.systemdesign.finance.domain.InvoiceRepository;
import be.ugent.systemdesign.finance.domain.InvoiceStatus;
import be.ugent.systemdesign.finance.domain.LineItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@SpringBootApplication
public class FinanceApplication {

    private static final Logger log = LoggerFactory.getLogger(FinanceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(FinanceApplication.class, args);
    }

    void logInvoices(List<Invoice> invoices) {
        log.info("--- Logging {} invoices ---", invoices.size());
        invoices.forEach(invoice -> {
            log.info("--- Id: {}, Created: {} StayId: {}, patientId: {}, status: {}",
                    invoice.getInvoiceId(),
                    invoice.getCreated().toString(),
                    invoice.getHospitalStayId(),
                    invoice.getPatientId(),
                    invoice.getStatus().name()
            );
            invoice.getLineItems().forEach(
                    lineItem -> {
                        log.info("  Lineitem: {}, cost: {}", lineItem.getName(), lineItem.getCost().toString());
                    }
            );
        });

    }


    void populateDatabase(InvoiceRepository repo) {
        repo.save(
                new Invoice("1",
                        "43",
                        "24",
                        InvoiceStatus.OPEN,
                        LocalDate.of(2020, 6, 2),
                        Arrays.asList(
                                new LineItem("ToiletPapier", 20),
                                new LineItem("Chips", 2)
                        )
                )
        );
        repo.save(
                new Invoice("2",
                        "24",
                        "33",
                        InvoiceStatus.OPEN,
                        LocalDate.of(2020, 6, 3),
                        Arrays.asList(
                                new LineItem("Mappen",  342),
                                new LineItem("Dagschotel", 3)
                        )
                )
        );
    }

    @Bean
    CommandLineRunner testInvoiceMongo(InvoiceRepository repo) {
        return (args) -> {
            populateDatabase(repo);
            List<Invoice> all = repo.findAll();
            logInvoices(all);
        };
    }

}
