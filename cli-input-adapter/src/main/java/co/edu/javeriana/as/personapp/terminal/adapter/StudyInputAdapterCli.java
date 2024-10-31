package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.in.StudyInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.StudyOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.StudyUseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.StudyMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import co.edu.javeriana.as.personapp.terminal.model.StudyModelCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.Optional;

@Slf4j
@Adapter
public class StudyInputAdapterCli {

    @Autowired
    @Qualifier("studyOutputAdapterMaria")
    private StudyOutputPort studyOutputPortMaria;

    @Autowired
    @Qualifier("studyOutputAdapterMongo")
    private StudyOutputPort studyOutputPortMongo;

    // Other output ports
    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfessionOutputPort professionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfessionOutputPort professionOutputPortMongo;

    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputAdapterMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputAdapterMongo;

    // Mappers
    @Autowired
    private StudyMapperCli studyMapperCli;

    @Autowired
    private PersonaMapperCli personaMapperCli;

    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    // Input ports
    @Autowired
    StudyInputPort studyInputPort;

    @Autowired
    PersonInputPort personInputPort;

    @Autowired
    ProfessionInputPort professionInputPort;

    public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            studyInputPort = new StudyUseCase(studyOutputPortMaria, professionOutputPortMaria, personOutputAdapterMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            studyInputPort = new StudyUseCase(studyOutputPortMongo, professionOutputPortMongo, personOutputAdapterMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial ProfessionEntity in Input Adapter");
        studyInputPort.findAll().stream()
                .map(studyMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void findById(Integer ProfessionId, Integer PersonId) throws NoExistException {
        log.info("Into findById PersonaEntity in Input Adapter");

        StudyModelCli study = studyMapperCli.fromDomainToAdapterCli(studyInputPort.findOne(ProfessionId, PersonId));
        if (study != null) {
            System.out.println(study.toString());
        }
        else {
            throw new NoExistException("The study with profession id " + ProfessionId + " and person id " + PersonId + " does not exist into db, cannot be found");
        }
    }

    public void create(Integer professionId, Integer personId, String college, LocalDate date) throws NoExistException {
        // Find the person and profession
        PersonaModelCli personModel = personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(personId));
        ProfesionModelCli professionModel = profesionMapperCli.fromDomainToAdapterCli(professionInputPort.findOne(professionId));

        if (personModel == null) {
            throw new NoExistException("The person with id " + personId + " does not exist in the database, cannot create study.");
        }

        if (professionModel == null) {
            throw new NoExistException("The profession with id " + professionId + " does not exist in the database, cannot create study.");
        }

        // Build the study model
        StudyModelCli studyModel = StudyModelCli.builder()
                .person(personaMapperCli.fromAdapterCliToDomain(personModel))
                .profession(profesionMapperCli.fromAdapterCliToDomain(professionModel))
                .universityName(college)
                .graduationDate(date)
                .build();

        studyInputPort.create(studyMapperCli.fromAdapterCliToDomain(studyModel), professionId, personId);
    }
    
    public void edit(Integer professionId, Integer personId, String college, LocalDate date) throws NoExistException {
        // Find the person and profession
        PersonaModelCli personModel = personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(personId));
        ProfesionModelCli professionModel = profesionMapperCli.fromDomainToAdapterCli(professionInputPort.findOne(professionId));

        if (personModel == null) {
            throw new NoExistException("The person with id " + personId + " does not exist in the database, cannot create study.");
        }

        if (professionModel == null) {
            throw new NoExistException("The profession with id " + professionId + " does not exist in the database, cannot create study.");
        }

        // Build the study model
        StudyModelCli studyModel = StudyModelCli.builder()
                .person(personaMapperCli.fromAdapterCliToDomain(personModel))
                .profession(profesionMapperCli.fromAdapterCliToDomain(professionModel))
                .universityName(college)
                .graduationDate(date)
                .build();

        studyInputPort.edit(studyMapperCli.fromAdapterCliToDomain(studyModel), professionId, personId);
    }

    public void drop (Integer ProfessionId, Integer PersonId) throws NoExistException {
        Optional.ofNullable(studyInputPort.findOne(ProfessionId, PersonId))
                .orElseThrow(() -> new NoExistException("The study with profession id " + ProfessionId + " and person id " + PersonId + " does not exist into db, cannot be found"));
        studyInputPort.drop(ProfessionId, PersonId);
    }

    public void count() {
        System.out.println(studyInputPort.count());
    }
}
