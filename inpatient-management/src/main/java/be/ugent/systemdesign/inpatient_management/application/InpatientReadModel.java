package be.ugent.systemdesign.inpatient_management.application;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
public class InpatientReadModel {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bedId;
}
