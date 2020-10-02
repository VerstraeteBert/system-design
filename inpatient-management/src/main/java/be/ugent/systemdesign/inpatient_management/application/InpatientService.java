package be.ugent.systemdesign.inpatient_management.application;

import java.time.LocalDate;

public interface InpatientService {
	Response registerInpatient(String patientId, String firstName, String lastName, LocalDate dateOfBirth, String treatmentCode, String physicianId, String bedId);
	Response registerParentConsent(String patientId);
	Response registerIntakeCompleted(String patientId);
	Response givePermissionToDismiss(String patientId, String physicianId);
	Response noteLeftAgainstMedicalAdvice(String patientId);
}
