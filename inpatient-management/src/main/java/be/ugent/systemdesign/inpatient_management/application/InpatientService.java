package be.ugent.systemdesign.inpatient_management.application;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;

public interface InpatientService {
	Response registerInpatient(Inpatient patient);
	Response registerInpatientCompletedIntake(int patientId);
	Response registerInpatientParentalConsent(int patientId);
	Response registerInpatientDischargePermission(int patientId, String physicianId);
	Response registerInpatientLAMA(int patientId);
}
