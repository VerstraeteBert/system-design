package be.ugent.systemdesign.inpatient_management.domain;

import lombok.Getter;
import lombok.Builder;


@Getter
@Builder
public class Treatment {
	private String treatmentCode;
	private String physician;
	
	private Treatment() {}
	
	public Treatment(String treatmentcode, String physiciancode) {
		this.treatmentCode = treatmentcode;
		this.physician = physiciancode;
	}
	
	public boolean isOneDayTreatment() {
		return treatmentCode.startsWith("1");
	}
	
	public boolean isSupervisedBy(String _physician) {
		return physician.equals(_physician);
	}

}
