package be.ugent.systemdesign.inpatient_management.API;

import be.ugent.systemdesign.inpatient_management.application.*;
import be.ugent.systemdesign.inpatient_management.application.ResponseStatus;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
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
    @Autowired
    InpatientQuery inpatientQuery;
    @Autowired
    InpatientService inpatientService;

    @GetMapping()
    public ResponseEntity<List<InpatientViewModel>> getInpatientsByStatus(@RequestParam(value = "status") String status) {
        InpatientStatus status_enum;
        try {
            // Ensure that status exists
            status_enum = InpatientStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException except) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        }

        List<InpatientReadModel> readModels = inpatientQuery.generateInpatientReport(status_enum);

        return new ResponseEntity<>(readModels.stream().map(InpatientViewModel::new).collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/{id}/parentconsent")
    public ResponseEntity<String> provideParentalConsentForInpatient(@PathVariable("id") String id) {
        Response res = inpatientService.registerIntakeCompleted(id);
        if (res.status == ResponseStatus.FAIL) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/dischargepermission")
    public ResponseEntity<String> provideDischargePermission(@PathVariable("id") String id, @RequestParam("physicianId") String physicianId) {
        Response res = inpatientService.givePermissionToDismiss(id, physicianId);
        if (res.status == ResponseStatus.FAIL) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }

    @PutMapping("/{id}/")
    public ResponseEntity<String> provideDischargePermission(@PathVariable String id) {
        Response res = inpatientService.noteLeftAgainstMedicalAdvice(id);
        if (res.status == ResponseStatus.FAIL) {
            return new ResponseEntity<>(null, HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>(null, HttpStatus.OK);
        }
    }
}
