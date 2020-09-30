package be.ugent.systemdesign.inpatient_management.infrastructure;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import be.ugent.systemdesign.inpatient_management.domain.InpatientStatus;

public interface InpatientDataModelJPARepository extends JpaRepository<InpatientDataModel, Integer> {
	public List<InpatientDataModel> findInpatientsByLastNameAndStatus(String Lastname, String status);
	public List<InpatientDataModel> findInpatientsByStatus(String status);
}
