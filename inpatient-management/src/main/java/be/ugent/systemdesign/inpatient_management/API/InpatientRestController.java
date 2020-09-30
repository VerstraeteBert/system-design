package be.ugent.systemdesign.inpatient_management.API;

import be.ugent.systemdesign.inpatient_management.application.InpatientReadModel;
import be.ugent.systemdesign.inpatient_management.application.Response;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientQueryImpl;
import be.ugent.systemdesign.inpatient_management.infrastructure.InpatientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path="/inpatients")
@CrossOrigin(origins="*")
public class InpatientRestController {
    @Autowired InpatientQueryImpl queryRepo;
    @Autowired InpatientServiceImpl serviceRepo;

    @GetMapping()
    public ResponseEntity<List<InpatientViewModel>> getInpatientsByStatus(@RequestParam(value = "status") String status) {
        InpatientStatus status_enum;
        try {
            // Ensure that status exists
            status_enum = InpatientStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException except) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        List<InpatientReadModel> readModels = queryRepo.generateInpatientReport(status_enum);

        return new ResponseEntity<>(readModels.stream().map(InpatientViewModel::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/{id}/parentconsent")
    public ResponseEntity<String> provideParentalConsentForInpatient(@PathVariable int id) {
        Response res = serviceRepo.registerInpatientParentalConsent(id);
        if (!res.getSuccessful()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/dischargepermission")
    public ResponseEntity<String> provideDischargePermission(@PathVariable int id, @RequestParam(value = "physicianId") String physicianId) {
        Response res = serviceRepo.registerInpatientDischargePermission(id, physicianId);
        if (!res.getSuccessful()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<String> provideDischargePermission(@PathVariable int id) {
        Response res = serviceRepo.registerInpatientLAMA(id);
        if (!res.getSuccessful()) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
