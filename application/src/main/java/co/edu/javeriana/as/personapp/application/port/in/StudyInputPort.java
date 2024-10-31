package co.edu.javeriana.as.personapp.application.port.in;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Study;

import java.util.List;

@Port
public interface StudyInputPort {

    public void setPersistence(StudyOutputPort professionPersistance);
    public List<Study> findAll();
    public Study findOne(Integer ProfessionId, Integer PersonId) throws NoExistException;
    public Study create(Study study, Integer ProfessionId, Integer PersonId) throws NoExistException;
    public Study edit(Study study, Integer ProfessionId, Integer PersonId) throws NoExistException;
    public Boolean drop( Integer ProfessionId, Integer PersonId) throws NoExistException;
    public Integer count();
}
