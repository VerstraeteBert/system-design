package be.ugent.systemdesign.inpatient_management;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModel;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModelJPARepository;

@SpringBootApplication
public class InpatientManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(InpatientManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InpatientManagementApplication.class, args);
	}
	
	@Bean
	CommandLineRunner testRepository(InpatientDataModelJPARepository repo) {
		return (args) -> {
			log.info("Testing");
			Optional<InpatientDataModel> test = repo.findById(1);
			if (test.isPresent()) {
				log.info("Ok 1");
			} else {
				log.error("Not OK 1");
			}
			List<InpatientDataModel> testLijst = repo.findInpatientsByLastNameAndStatus("Testicus", "registered");
			if (testLijst.size() == 2) {
				log.info("Ok 2");
			} else {
				log.error(("Not Ok 2"));
			}
		};
	}
	
}