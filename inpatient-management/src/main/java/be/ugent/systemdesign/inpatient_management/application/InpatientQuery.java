package be.ugent.systemdesign.inpatient_management.application;

import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;

import java.util.List;

public interface InpatientQuery {
    public List<InpatientReadModel> generateInpatientReport(InpatientStatus status);
}
