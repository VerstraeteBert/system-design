package be.ugent.systemdesign.inpatient_management.infrastructure;

import be.ugent.systemdesign.inpatient_management.application.InpatientQuery;
import be.ugent.systemdesign.inpatient_management.application.InpatientReadModel;
import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InpatientQueryImpl implements InpatientQuery {
    @Autowired
    private InpatientDataModelJPARepository repo;

    public InpatientReadModel datamodel_to_readmodel(InpatientDataModel dataModel) {
        return new InpatientReadModel(dataModel.getFirstName(), dataModel.getLastName(), dataModel.getDateOfBirth(), dataModel.getBedId());
    }

    public List<InpatientReadModel> generateInpatientReport(InpatientStatus status) {
        List<InpatientDataModel> res = repo.findInpatientsByStatus(status.toString());
        return res.stream().map(this::datamodel_to_readmodel).collect(Collectors.toList());
    }
}
