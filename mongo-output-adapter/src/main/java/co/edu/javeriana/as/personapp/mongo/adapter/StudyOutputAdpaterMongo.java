package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;

import java.util.Optional;
import java.util.stream.Collectors;


import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mongo.document.EstudiosDocument;
import co.edu.javeriana.as.personapp.mongo.document.PersonaDocument;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.EstudiosMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.PersonaRepositoryMongo;
import co.edu.javeriana.as.personapp.mongo.repository.ProfesionRepositoryMongo;
import co.edu.javeriana.as.personapp.mongo.repository.StudyRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("studyOutputAdapterMongo")
public class StudyOutputAdpaterMongo implements StudyOutputPort {

    @Autowired
    private StudyRepositoryMongo studyRepositoryMongo;

    @Autowired
    private EstudiosMapperMongo estudiosMapperMongo;

    // Person and Profession repositories
    @Autowired
    private ProfesionRepositoryMongo profesionRepositoryMongo;

    @Autowired
    private PersonaRepositoryMongo personaRepositoryMongo;

    @Override
    public Study save(Study study) {
        log.debug("Into save on Adapter MariaDB");
        EstudiosDocument persistedStudy = studyRepositoryMongo.save(estudiosMapperMongo.fromDomainToAdapter(study));
        return estudiosMapperMongo.fromAdapterToDomain(persistedStudy);
    }

    @Override
    public Boolean delete(Integer ProfessionId, Integer PersonId) {
        log.debug("Into delete on Adapter MariaDB");

        // Find the profession and the person
        Optional<PersonaDocument> person = personaRepositoryMongo.findById(PersonId);
        Optional<ProfesionDocument> profession = profesionRepositoryMongo.findById(ProfessionId);

        // If the person and the profession exist
        if (person.isPresent() && profession.isPresent()) {
            // Find the study
            EstudiosDocument study = studyRepositoryMongo.findByPrimaryPersonaAndPrimaryProfesion(person.get(), profession.get()).orElse(null);
            // If the study exists
            if (study != null) {
                // Delete the study
                studyRepositoryMongo.delete(study);
                return true;
            }
        }

        return false;
    }

    @Override
    public List<Study> find() {
        log.debug("Into find on Adapter MariaDB");
        return studyRepositoryMongo.findAll().stream().map(estudiosMapperMongo::fromAdapterToDomain2)
                .collect(Collectors.toList());
    }

    @Override
    public Study findById(Integer ProfessionId, Integer PersonId) {
        log.debug("Into findById on Adapter MariaDB");

        // Find the person and the profession
        Optional<PersonaDocument> person = personaRepositoryMongo.findById(PersonId);
        Optional<ProfesionDocument> profession = profesionRepositoryMongo.findById(ProfessionId);

        // If the person and the profession exist
        if (person.isPresent() && profession.isPresent()) {
            // Find the study
            EstudiosDocument study = studyRepositoryMongo.findByPrimaryPersonaAndPrimaryProfesion(person.get(), profession.get()).orElse(null);
            // If the study exists
            if (study != null) {
                return estudiosMapperMongo.fromAdapterToDomain2(study);
            }
        }

        return null;
    }

}
