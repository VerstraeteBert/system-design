package be.ugent.systemdesign.inpatient_management.infrastructure;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;
import be.ugent.systemdesign.inpatient_management.domain.InpatientRepository;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.domain.Treatment;

@Repository
public class InpatientRepositoryImpl implements InpatientRepository {
	@Autowired
	private InpatientDataModelJPARepository repo; 
	
	private InpatientDataModel model_to_datamodel(Inpatient patient) {
		return new InpatientDataModel(
				patient.getPatientId(),
				patient.getFirstName(),
				patient.getLastName(),
				new Treatment(patient.getTreatment().getTreatmentCode(), patient.getTreatment().getPhysician()),
				patient.getBedId(),
				patient.getDateOfBirth(),
				patient.getStatus(),
				patient.isConsentReceived()
		);
	}
	
	private Inpatient datamodel_to_model(InpatientDataModel patient) {
		return Inpatient.builder()
			.patientId(patient.getPatientId())
			.firstName(patient.getFirstName())
			.lastName(patient.getLastName())
			.dateOfBirth(patient.getDateOfBirth())
			.treatment(Treatment.builder()
					.treatmentCode(patient.getTreatmentCode())
					.physician(patient.getTreatmentPhysician())
					.build())
			.bedId(patient.getBedId())
			.status(InpatientStatus.valueOf(patient.getStatus()))
			.build();
	}
	
	public Inpatient findOne(Integer id) {
		Optional<InpatientDataModel> data_model_res = repo.findById(id);
		return data_model_res.map(this::datamodel_to_model).orElse(null);
	}
	
	public void save(Inpatient _p) {
		repo.save(model_to_datamodel(_p));
	}
	
	public List<Inpatient> findAllThatAreRegisteredWithLastname(String lastName) {
		return repo.findInpatientsByLastNameAndStatus(lastName, InpatientStatus.REGISTERED.name())
				.stream()
				.map(this::datamodel_to_model)
				.collect(Collectors.toList());
	}
}
