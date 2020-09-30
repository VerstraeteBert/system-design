package be.ugent.systemdesign.inpatient_management;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.domain.Treatment;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModel;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModelJPARepository;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientRepositoryImpl;

@SpringBootApplication
public class InpatientManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(InpatientManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InpatientManagementApplication.class, args);
	}
	
	@Bean
	CommandLineRunner testRepository(InpatientDataModelJPARepository repo) {
		return (args) -> {
			log.info("Testing JPA repo");
			Optional<InpatientDataModel> test = repo.findById(1);
			if (test.isPresent()) {
				log.info("Ok 1");
			} else {
				log.error("Not OK 1");
			}
			List<InpatientDataModel> testLijst = repo.findInpatientsByLastNameAndStatus("Testicus", "REGISTERED");
			if (testLijst.size() == 2) {
				log.info("Ok 2");
			} else {
				log.error("Not Ok 2");
			}
		};
	}
	
	@Bean
	CommandLineRunner testRepositoryImpl(InpatientRepositoryImpl repo) {
		return (args) -> {
			log.info("Testing repo impl");
			Inpatient pat = repo.findOne(1);
			if (pat != null) {
				log.info("ok 1");
			} else {
				log.error("not ok 1");
			}
			
			pat = Inpatient.builder()
				.patientId(4)
				.firstName("tester")
				.lastName("Testicus")
				.consentReceived(false)
				.bedId("1")
				.status(InpatientStatus.REGISTERED)
				.treatment(new Treatment("1", "1"))
				.build();
			
			repo.save(pat);
			
			List<Inpatient> pats = repo.findAllThatAreRegisteredWithLastname("Testicus");
			log.info(Boolean.toString(pats.size() == 3));
		};
	}

	// TODO test InpatientServiceImpl
}
