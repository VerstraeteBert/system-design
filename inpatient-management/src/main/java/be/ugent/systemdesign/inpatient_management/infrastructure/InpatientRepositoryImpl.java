package be.ugent.systemdesign.inpatient_management.infrastructure;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;
import be.ugent.systemdesign.inpatient_management.domain.InpatientRepository;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.domain.Treatment;

@Repository
public abstract class InpatientRepositoryImpl implements InpatientRepository {
	@Autowired
	private InpatientDataModelJPARepository repo; 
	
	public InpatientDataModel model_to_datamodel(Inpatient patient) {
		return new InpatientDataModel(
				patient.getPatientId(),
				patient.getFirstName(),
				patient.getLastName(),
				patient.getDateOfBirth(),
				patient.getTreatment().getTreatmentCode(),
				patient.getTreatment().getPhysician(),
				patient.getBedId(),
				patient.getStatus().toString()
		);
	}
	
	public Inpatient datamodel_to_model(InpatientDataModel patient) {
		return Inpatient.builder()
			.patientId(patient.getPatientId())
			.firstName(patient.getFirstName())
			.lastName(patient.getLastName())
			.dateOfBirth(patient.getDateOfBirth())
			.treatment(new Treatment(patient.getTreatmentCode(), patient.getPhysician()))
			.bedId(patient.getBedId())
			.status(InpatientStatus.valueOf(patient.getStatus()))
			.build();
	}
}
