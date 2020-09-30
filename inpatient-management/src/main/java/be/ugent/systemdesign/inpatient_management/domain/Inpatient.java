package be.ugent.systemdesign.inpatient_management.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.AccessLevel;

@Getter
@Setter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Inpatient {
	private Integer patientId;
	private String firstName, lastName;

	private LocalDate dateOfBirth;
	private String bedId;
	private Treatment treatment;
	
	private InpatientStatus status;		
	private boolean consentReceived;
	
	
	public Inpatient(Integer patientid, String firstName, String lastName, LocalDate dateOfBirth, String treatmentCode, String physicianId, String bedId){
		this.patientId = patientid;
		this.firstName = firstName;
		this.lastName = lastName;
		this.treatment = new Treatment(treatmentCode,physicianId);
		this.dateOfBirth = dateOfBirth;
		this.bedId = bedId;	
		if(this.treatment.isOneDayTreatment())
			throw new InpatientRegisteredForOneDayTreatmentException();
		
		this.status = InpatientStatus.REGISTERED;
	}
	
	public void inform() {
		this.status = InpatientStatus.INWARD;
	}
	
	public void receivesMedicalPermissionToDischargeFrom(String physician) throws OnlyResponsiblePhysicianCanGivePermissionException {
		if(this.treatment.isSupervisedBy(physician)) {
			status = InpatientStatus.DISCHARGEPERMITTED;
		}
		else{
			throw new OnlyResponsiblePhysicianCanGivePermissionException();
		}
	}
	
	public void receivesConsentFromParents() {
		this.consentReceived = true;
	}
		
	public void discharge() {
		if(canDischarge()) {
			this.status = InpatientStatus.DISCHARGED;
		}
	}
	
	public void leavesAgainstMedicalAdvice() {
		this.status = InpatientStatus.LAMA;
	}
	
	private boolean canDischarge() {
		if(status != InpatientStatus.DISCHARGEPERMITTED){ 
			return false;
		}
		final long age = java.time.temporal.ChronoUnit.YEARS.between(dateOfBirth, LocalDate.now());
		if(age < 18 && !this.consentReceived){
			return false;
		}
		return true;
	}
	
}
