package be.ugent.systemdesign.inpatient_management.infrastructure;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class InpatientDataModel {
	@Id
	private Integer patientId;
	private String firstName, lastName;
	private String bedId;
	private String dateOfBirth;
	
	private String status;		
	private boolean consentReceived;
	
	private String treatmentCode;
	private String physician;
	
	private InpatientDataModel() {};
	
	public InpatientDataModel(Integer patientid, String firstName, String lastName, String dateOfBirth, String treatmentCode, String physicianId, String bedId, String status){
		
		this.patientId = patientid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.dateOfBirth = dateOfBirth;
		this.bedId = bedId;	

		this.physician = physicianId;
		this.treatmentCode = treatmentCode;
		
		this.status = status;
	}
}
