package be.ugent.systemdesign.inpatient_management.infrastructure;

import be.ugent.systemdesign.inpatient_management.application.InpatientService;
import be.ugent.systemdesign.inpatient_management.application.Response;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.domain.OnlyResponsiblePhysicianCanGivePermissionException;
import org.springframework.beans.factory.annotation.Autowired;

import be.ugent.systemdesign.inpatient_management.domain.Inpatient;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientRepositoryImpl;

public class InpatientServiceImpl implements InpatientService {
	@Autowired
	private InpatientRepositoryImpl repo;

	public Response registerInpatient(Inpatient patient) {
		repo.save(patient);

		return new Response(true, "Patient succesfully registered");
	}

	public Response registerInpatientCompletedIntake(int patientId) {
		Inpatient patient = repo.findOne(patientId);
		if (patient == null) {
			return new Response(false, "Inpatient not found");
		}


		patient.discharge();
		if (patient.getStatus() != InpatientStatus.DISCHARGED) {
			return new Response(false, "Inpatient did not have permission to be discharges");
		}

		repo.save(patient);
		
		return new Response(true, "Sucessfully registered inpatient completed intake");
	}

	public Response registerInpatientParentalConsent(int patientId) {
		Inpatient patient = repo.findOne(patientId);
		if (patient == null) {
			return new Response(false, "Inpatient not found");
		}

		if (!patient.isConsentReceived()) {
			patient.receivesConsentFromParents();
			repo.save(patient);
		}

		return new Response(true, "Sucessfully registered consent from parents");
	}

	public Response registerInpatientDischargePermission(int patientId, String physician_id) {
		Inpatient patient = repo.findOne(patientId);
		if (patient == null) {
			return new Response(false, "Inpatient not found");
		}

		if (patient.getStatus() == InpatientStatus.DISCHARGEPERMITTED) {
			return new Response(true, "Discharge permission sucessfully registered");
		}

		try {
			patient.receivesMedicalPermissionToDischargeFrom(physician_id);
		} catch (OnlyResponsiblePhysicianCanGivePermissionException excep) {
			return new Response(false, "Only responsible physician can give discharge permission");
		}

		repo.save(patient);

		return new Response(true, "Discharge permission sucessfully registered");
	}

	public Response registerInpatientLAMA(int patientId) {
		Inpatient patient = repo.findOne(patientId);
		if (patient == null) {
			return new Response(false, "Inpatient not found");
		}

		if (patient.getStatus() == InpatientStatus.LAMA) {
			return new Response(true, "LAMA sucessfully registered");
		}

		patient.leavesAgainstMedicalAdvice();
		repo.save(patient);

		return new Response(true, "LAMA sucessfully registered");
	}
}
