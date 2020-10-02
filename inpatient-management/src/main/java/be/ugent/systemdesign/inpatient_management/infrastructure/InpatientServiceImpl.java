package be.ugent.systemdesign.inpatient_management.infrastructure;

import be.ugent.systemdesign.inpatient_management.application.InpatientService;
import be.ugent.systemdesign.inpatient_management.application.Response;
import be.ugent.systemdesign.inpatient_management.application.ResponseStatus;
import be.ugent.systemdesign.inpatient_management.domain.*;
import org.springframework.beans.factory.annotation.Autowired;

import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientRepositoryImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;

@Service
@Transactional
public class InpatientServiceImpl implements InpatientService {
	@Autowired
	InpatientRepository inpatientRepo;

	@Override
	public Response registerInpatient(String patientId, String firstName, String lastName, LocalDate dateOfBirth,
									  String treatmentCode, String physicianId, String bedId) {

		try {
			Inpatient p = new Inpatient(Integer.parseInt(patientId), firstName, lastName, dateOfBirth, treatmentCode, physicianId, bedId);
			inpatientRepo.save(p);
		} catch(InpatientRegisteredForOneDayTreatmentException e) {
			return new Response(ResponseStatus.FAIL,"Patients with one-day treatment cannot be registerd as inpatient");
		} catch(RuntimeException e) {
			return new Response(ResponseStatus.FAIL,"Patient could not be registered");
		}
		return new Response(ResponseStatus.SUCCESS,"id: "+patientId);
	}

	@Override
	public Response registerIntakeCompleted(String patientId) {
		try {
			Inpatient p = inpatientRepo.findOne(Integer.parseInt(patientId));
			p.receivesConsentFromParents();
			inpatientRepo.save(p);
		} catch(RuntimeException e) {
			return new Response(ResponseStatus.FAIL,"id "+patientId);
		}

		return new Response(ResponseStatus.SUCCESS,"id "+patientId);

	}

	@Override
	public Response givePermissionToDismiss(String patientId, String physicianId) {

		try {
			Inpatient p = inpatientRepo.findOne(Integer.parseInt(patientId));
			p.receivesMedicalPermissionToDischargeFrom(physicianId);
			inpatientRepo.save(p);
		} catch(OnlyResponsiblePhysicianCanGivePermissionException e) {
			return new Response(ResponseStatus.FAIL,"Only responsible physician can give permission");
		} catch(RuntimeException e) {
			return new Response(ResponseStatus.FAIL,"Could not register permission");
		}
		return new Response(ResponseStatus.SUCCESS,"");
	}

	@Override
	public Response noteLeftAgainstMedicalAdvice(String patientId) {
		try {
			Inpatient p = inpatientRepo.findOne(Integer.parseInt(patientId));
			p.leavesAgainstMedicalAdvice();
			inpatientRepo.save(p);
		} catch(RuntimeException e) {
			return new Response(ResponseStatus.FAIL,"Could not register lama status.");
		}
		return new Response(ResponseStatus.SUCCESS,"");

	}

	@Override
	public Response registerParentConsent(String patientId) {
		try {
			Inpatient p = inpatientRepo.findOne(Integer.parseInt(patientId));
			p.inform();
			inpatientRepo.save(p);
		} catch(RuntimeException e) {
			return new Response(ResponseStatus.FAIL,"Could not register completion of intake procedure.");
		}
		return new Response(ResponseStatus.SUCCESS,"");
	}
}
