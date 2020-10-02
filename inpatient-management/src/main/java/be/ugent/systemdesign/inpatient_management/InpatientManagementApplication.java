package be.ugent.systemdesign.inpatient_management;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import be.ugent.systemdesign.inpatient_management.application.InpatientService;
import be.ugent.systemdesign.inpatient_management.application.Response;
import be.ugent.systemdesign.inpatient_management.domain.InpatientRepository;
import be.ugent.systemdesign.inpatient_management.domain.Treatment;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModelJPARepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientDataModel;

@SpringBootApplication
public class InpatientManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(InpatientManagementApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(InpatientManagementApplication.class, args);
	}

	private static void logInpatientDataModels(List<InpatientDataModel> inpatients) {
		log.info("-Number of inpatients found: {}", inpatients.size());
		for(InpatientDataModel inpatient : inpatients) {
			log.info("--patientId {};"
							+ " firstName {}, lastName {}, bedId {}, dateOfBirth {},"
							+ " status {}, consentReceived {}, treatmentCode {}, treatmentPhysician {}.",
					inpatient.getPatientId(),
					inpatient.getFirstName(), inpatient.getLastName(), inpatient.getBedId(), inpatient.getDateOfBirth(),
					inpatient.getStatus(), inpatient.isConsentReceived(), inpatient.getTreatmentCode(), inpatient.getTreatmentPhysician());
		}
	}

	@Bean
	CommandLineRunner testInpatientDataModelJPARepository(InpatientDataModelJPARepository repo) {
		return (args) -> {
			log.info("$Testing InpatientDataModelJPARepository.");

			log.info(">Find all inpatients from database.");
			List<InpatientDataModel> inpatientsAll = repo.findAll();
			logInpatientDataModels(inpatientsAll);

			log.info(">Find all inpatients by status {} from database.", InpatientStatus.REGISTERED);
			List<InpatientDataModel> inpatientsByStatus = repo.findInpatientsByStatus(InpatientStatus.REGISTERED.toString());
			logInpatientDataModels(inpatientsByStatus);

			InpatientDataModel firstInpatient = inpatientsAll.get(0);
			log.info(">Find inpatients by last name {} and status {} from database.", firstInpatient.getLastName(), firstInpatient.getStatus());
			List<InpatientDataModel> inpatientsByLastNameAndStatus = repo.findInpatientsByLastNameAndStatus(firstInpatient.getLastName(), firstInpatient.getStatus());
			logInpatientDataModels(inpatientsByLastNameAndStatus);

			Integer newPatientId = 5;
			log.info(">Save new inpatient with id {} to database.", newPatientId);
			InpatientDataModel newInpatient = new InpatientDataModel(newPatientId,"X Æ A-Xii","Boucher", new Treatment("333", "789"),"627",LocalDate.of(2020,5,4),InpatientStatus.INWARD,true);
			repo.saveAndFlush(newInpatient);

			log.info(">Find inpatient by id {} from database.", newPatientId);
			Optional<InpatientDataModel> inpatientById= repo.findById(newPatientId);
			inpatientById.ifPresent(
					(value)
							-> { logInpatientDataModels(Collections.unmodifiableList(Arrays.asList(value))); }
			);

			log.info(">Delete inpatient by id {} from database.", newPatientId);
			repo.deleteById(newPatientId);
		};
	}

	private static void logInpatients(List<Inpatient> inpatients) {
		log.info("-Number of inpatients found: {}", inpatients.size());
		for(Inpatient inpatient : inpatients) {
			log.info("--patientId {};"
							+ " firstName {}, lastName {}, bedId {}, dateOfBirth {},"
							+ " status {}, consentReceived {}, treatmentCode {}, treatmentPhysician {}.",
					inpatient.getPatientId(),
					inpatient.getFirstName(), inpatient.getLastName(), inpatient.getBedId(), inpatient.getDateOfBirth(),
					inpatient.getStatus(), inpatient.isConsentReceived(), inpatient.getTreatment().getTreatmentCode(), inpatient.getTreatment().getPhysician());
		}
	}

	@Bean
	CommandLineRunner testInpatientRepository(InpatientRepository repo) {
		return (args) -> {
			log.info("$Testing InpatientRepository.");

			log.info(">Find one inpatient by id {} from database.", 0);
			Inpatient inpatientById = repo.findOne(0);
			logInpatients(List.of(inpatientById));

			Integer newPatientId = 5;
			log.info(">Save new inpatient with id {} to database.", newPatientId);
			Inpatient newInpatient = new Inpatient(newPatientId,"X Æ A-Xii","Boucher",LocalDate.of(2020,5,4),"333","789","627");
			repo.save(newInpatient);

			log.info(">Find all inpatients that are registered with last name {}", newInpatient.getLastName());
			List<Inpatient> inpatients = repo.findAllThatAreRegisteredWithLastname(newInpatient.getLastName());
			logInpatients(inpatients);
		};
	}

	private static void logResponse(Response response) {
		log.info("-response status[{}] message[{}]", response.status, response.message);
	}

	@Bean
	CommandLineRunner testInpatientService(InpatientService service) {
		return (args) -> {
			log.info("$Testing InpatientService.");
			Response response;

			log.info(">Register new inpatient (success).");
			response = service.registerInpatient("6","Igloo","Ghost",LocalDate.of(1996,3,8),"642","346","278");
			logResponse(response);

			log.info(">Register new inpatient (fail).");
			response = service.registerInpatient("7","Crystal","Castles",LocalDate.of(1991,2,6),"111","423","371");
			logResponse(response);

			log.info(">Register parent consent (success).");
			response = service.registerIntakeCompleted("4");
			logResponse(response);

			log.info(">Register intake complete (success).");
			response = service.registerParentConsent("2");
			logResponse(response);

			log.info(">Give permission to dismiss (success).");
			response = service.givePermissionToDismiss("3", "744");
			logResponse(response);

			log.info(">Give permission to dismiss (fail).");
			response = service.givePermissionToDismiss("4", "744");
			logResponse(response);

			log.info(">Give permission to dismiss (success).");
			response = service.noteLeftAgainstMedicalAdvice("1");
			logResponse(response);
		};
	}
}
