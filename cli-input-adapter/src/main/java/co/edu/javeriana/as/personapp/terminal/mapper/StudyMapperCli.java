package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.terminal.model.StudyModelCli;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class StudyMapperCli {

    @Autowired
    PersonaMapperCli personaMapperCli;

    @Autowired
    ProfesionMapperCli profesionMapperCli;

    public StudyModelCli fromDomainToAdapterCli(Study study) {
        if (study == null || study.getPerson() == null || study.getProfession() == null) {
            return null;
        }
        return study != null ? StudyModelCli.builder()
                .person(personaMapperCli.fromAdapterCliToDomain(personaMapperCli.fromDomainToAdapterCli(study.getPerson())))
                .profession(profesionMapperCli.fromAdapterCliToDomain(profesionMapperCli.fromDomainToAdapterCli(study.getProfession())))
                .graduationDate(study.getGraduationDate())
                .universityName(study.getUniversityName())
                .build() : null;
    }

    public Study fromAdapterCliToDomain(StudyModelCli studyModelCli) {
        return studyModelCli != null ? Study.builder()
                .person(personaMapperCli.fromAdapterCliToDomain(personaMapperCli.fromDomainToAdapterCli(studyModelCli.getPerson())))
                .profession(profesionMapperCli.fromAdapterCliToDomain(profesionMapperCli.fromDomainToAdapterCli(studyModelCli.getProfession())))
                .graduationDate(studyModelCli.getGraduationDate())
                .universityName(studyModelCli.getUniversityName())
                .build() : null;
    }
}
