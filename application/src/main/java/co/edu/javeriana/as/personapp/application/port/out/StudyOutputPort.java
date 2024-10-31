package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

@Port
public interface StudyOutputPort {
    public List<Study> find();
    public Study findById(Integer ProfessionId, Integer PersonId);
    public Study save(Study study);
    public Boolean delete(Integer ProfessionId, Integer PersonId);
}
