package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.domain.Study;
import co.edu.javeriana.as.personapp.model.request.EstudioRequest;
import co.edu.javeriana.as.personapp.model.response.EstudioResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@Mapper
public class EstudioMapperRest {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Método genérico para mapear Study a EstudioResponse
    public EstudioResponse fromDomainToAdapterRestMaria(Study study) {
        return fromDomainToAdapterRest(study, "MariaDB");
    }

    // Método genérico para mapear Study a EstudioResponse
    public EstudioResponse fromDomainToAdapterRestMongo(Study study) {
        return fromDomainToAdapterRest(study, "MongoDB");
    }

    // Método genérico para mapear Study a EstudioResponse
    public EstudioResponse fromDomainToAdapterRest(Study study, String database) {
        if (study == null || study.getPerson() == null || study.getProfession() == null) {
            return new EstudioResponse(null, null, null, null, database, "Error: Incomplete study data");
        }

        return new EstudioResponse(
                String.valueOf(study.getPerson().getIdentification()),
                String.valueOf(study.getProfession().getIdentification()),
                study.getUniversityName(),
                study.getGraduationDate() != null ? study.getGraduationDate().format(DATE_TIME_FORMATTER) : null,
                database,
                "OK"
        );
    }

    // Método genérico para mapear Study a EstudioResponse
    public Study fromAdapterToDomain(EstudioRequest request, Person person, Profession profession) {
        LocalDate graduationDate;
        try {
            graduationDate = LocalDate.parse(request.getGraduationDate(), DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format for graduationDate. Expected format: dd-MM-yyyy");
        }

        return Study.builder()
                .person(person)
                .profession(profession)
                .universityName(request.getUniversityName())
                .graduationDate(graduationDate)
                .build();
    }

}
