package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class StudyUseCase implements StudyInputPort {

    private StudyOutputPort studyPersistence;
    private PersonOutputPort personPersistence;
    private ProfessionOutputPort professionPersistence;

    public StudyUseCase(
            @Qualifier("studyOutputAdapterMaria") StudyOutputPort studyPersistence,
            @Qualifier("profesionOutputAdapterMaria") ProfessionOutputPort professionPersistence,
            @Qualifier("personOutputAdapterMaria")  PersonOutputPort personPersistence
            ) {
        this.studyPersistence=studyPersistence;
        this.professionPersistence = professionPersistence;
        this.personPersistence = personPersistence;
    }

    @Override
    public void setPersistence(StudyOutputPort studyPersistence) {
        this.studyPersistence=studyPersistence;
    }

    @Override
    public Study create(Study study, Integer ProfessionId, Integer PersonId) throws NoExistException {
        log.debug("Into create on Application Domain");
        // Find the person and the profession
        Person person = personPersistence.findById(PersonId);
        Profession profession = professionPersistence.findById(ProfessionId);

        // If the person and the profession exist
        if (person == null) {
            log.error("The person with id " + PersonId + " does not exist into db, cannot be created");
            throw new NoExistException("The person with id " + PersonId + " does not exist into db, cannot be created");
        }
        if (profession == null) {
            log.error("The profession with id " + ProfessionId + " does not exist into db, cannot be created");
            throw new NoExistException("The profession with id " + ProfessionId + " does not exist into db, cannot be created");
        }

        // Set the person and the profession
        study.setPerson(person);
        study.setProfession(profession);

        return studyPersistence.save(study);
    }

    @Override
    public Study edit(Study study, Integer ProfessionId, Integer PersonId) throws NoExistException {
        Study oldStudy = studyPersistence.findById(ProfessionId, PersonId);
        if (oldStudy != null)
            return studyPersistence.save(oldStudy);
        throw new NoExistException(
                "The study with profession id " + ProfessionId + " and person id " + PersonId + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(Integer ProfessionId, Integer PersonId) throws NoExistException {
        Study oldStudy = studyPersistence.findById(ProfessionId, PersonId);
        if (oldStudy != null)
            return studyPersistence.delete(ProfessionId, PersonId);
        throw new NoExistException(
                "The study with profession id " + ProfessionId + " and person id " + PersonId + " does not exist into db, cannot be dropped");
    }

    @Override
    public List<Study> findAll() {
        log.info("Output: " + studyPersistence.getClass());
        return studyPersistence.find();
    }

    @Override
    public Study findOne(Integer ProfessionId, Integer PersonId) throws NoExistException {
        Study oldStudy = studyPersistence.findById(ProfessionId, PersonId);
        if (oldStudy != null)
            return oldStudy;
        throw new NoExistException(
                "The study with profession id " + ProfessionId + " and person id " + PersonId + " does not exist into db, cannot be found");
    }

    @Override
    public Integer count() {
        return studyPersistence.find().size();
    }
}
