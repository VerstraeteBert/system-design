package be.ugent.systemdesign.inpatient_management.infrastructure;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.domain.Treatment;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Setter
@Getter
@NoArgsConstructor
public class InpatientDataModel {

	@Id
	@Getter private Integer patientId;

	private String firstName;
	private String lastName;

	private String bedId;
	private LocalDate dateOfBirth;

	private String status;
	private boolean consentReceived;

	private String treatmentCode;
	private String treatmentPhysician;

	public InpatientDataModel(Integer patientId, String firstName, String lastName, Treatment treatment, String bedId,
							  LocalDate dateOfBirth, InpatientStatus status, boolean consentReceived) {
		this.patientId = patientId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.bedId = bedId;
		this.dateOfBirth = dateOfBirth;
		this.status = status.name();
		this.consentReceived = consentReceived;
		this.treatmentCode = treatment.getTreatmentCode();
		this.treatmentPhysician = treatment.getPhysician();
	}



}
