package be.ugent.systemdesign.inpatient_management.application;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class InpatientReadModel {
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private String bedId;

    public InpatientReadModel(String firstName, String lastName, LocalDate dateOfBirth, String bedId) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.bedId = bedId;
    }
}
