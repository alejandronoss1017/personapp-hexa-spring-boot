package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntity;
import co.edu.javeriana.as.personapp.mariadb.entity.EstudiosEntityPK;
import co.edu.javeriana.as.personapp.mariadb.mapper.EstudiosMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.PersonaRepositoryMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.ProfesionRepositoryMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.StudyRepositoryMaria;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("studyOutputAdapterMaria")
@Transactional
public class StudyOutputAdapterMaria implements StudyOutputPort {

    @Autowired
    private StudyRepositoryMaria studyRepositoryMaria;

    @Autowired
    private EstudiosMapperMaria estudiosMapperMaria;

    @Override
    @Transactional
    public Study save(Study study) {
        EstudiosEntity estudiosEntity = estudiosMapperMaria.fromDomainToAdapter(study);
        estudiosEntity = studyRepositoryMaria.save(estudiosEntity);
        return estudiosMapperMaria.fromAdapterToDomain(estudiosEntity);
    }

    private EstudiosEntityPK buildPrimaryKey(Integer ProfessionId, Integer PersonId) {
        EstudiosEntityPK studyPK = new EstudiosEntityPK();
        studyPK.setIdProf(ProfessionId);
        studyPK.setCcPer(PersonId);
        return studyPK;
    }

    @Override
    public Boolean delete(Integer ProfessionId, Integer PersonId) {
        log.debug("Into delete on Adapter MariaDB");
        // Create the primary key
        EstudiosEntityPK studyPK = buildPrimaryKey(ProfessionId, PersonId);
        // Delete the study
        studyRepositoryMaria.deleteById(studyPK);
        // Return if the study was deleted
        return studyRepositoryMaria.findById(studyPK).isEmpty();
    }

    @Override
    public Study findById(Integer ProfessionId, Integer PersonId) {
        log.debug("Into findById on Adapter MariaDB");
        // Create the primary key
        EstudiosEntityPK studyPK = buildPrimaryKey(ProfessionId, PersonId);
        // Find the study
        return studyRepositoryMaria.findById(studyPK)
                .map(estudiosMapperMaria::fromAdapterToDomain)
                .orElse(null);
    }

    @Override
    public List<Study> find() {
        log.debug("Into find on Adapter MariaDB");
        return studyRepositoryMaria.findAll().stream().map(estudiosMapperMaria::fromAdapterToDomain)
                .collect(Collectors.toList());
    }
}
