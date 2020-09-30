package be.ugent.systemdesign.inpatient_management.API;

import be.ugent.systemdesign.inpatient_management.application.InpatientReadModel;
import lombok.Getter;

@Getter
public class InpatientViewModel {
    private String firstName;
    private String lastName;
    private String dateOfBirth;
    private String bedId;

    public InpatientViewModel(InpatientReadModel readModel) {
        this.firstName = readModel.getFirstName();
        this.lastName = readModel.getLastName();
        this.dateOfBirth = readModel.getDateOfBirth().toString();
        this.bedId = readModel.getBedId();
    }
}
