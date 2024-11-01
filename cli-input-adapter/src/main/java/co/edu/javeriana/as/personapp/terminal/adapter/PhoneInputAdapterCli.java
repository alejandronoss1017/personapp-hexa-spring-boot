package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.PersonInputPort;
import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.PhoneUseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.PersonaMapperCli;
import co.edu.javeriana.as.personapp.terminal.mapper.PhoneMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import co.edu.javeriana.as.personapp.terminal.model.PhoneModelCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

@Slf4j
@Adapter
public class PhoneInputAdapterCli {

    @Autowired
    @Qualifier("phoneOutputAdapterMaria")
    private PhoneOutputPort phoneOutputPortMaria;

    @Autowired
    @Qualifier("phoneOutputAdapterMongo")
    private PhoneOutputPort phoneOutputPortMongo;

    // Other output ports
    @Autowired
    @Qualifier("personOutputAdapterMaria")
    private PersonOutputPort personOutputAdapterMaria;

    @Autowired
    @Qualifier("personOutputAdapterMongo")
    private PersonOutputPort personOutputAdapterMongo;

    // Mappers
    @Autowired
    private PhoneMapperCli phoneMapperCli;
    
    @Autowired
    private PersonaMapperCli personaMapperCli;
    
    // Input ports
    @Autowired
    PhoneInputPort phoneInputPort;
    
    @Autowired
    PersonInputPort personInputPort;
    
    public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMaria, personOutputAdapterMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            phoneInputPort = new PhoneUseCase(phoneOutputPortMongo, personOutputAdapterMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial PhoneEntity in Input Adapter");
        phoneInputPort.findAll().stream()
                .map(phoneMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void findOne(String phoneId) throws NoExistException {
        log.info("Into findById PhoneEntity in Input Adapter");
        PhoneModelCli Phone = phoneMapperCli.fromDomainToAdapterCli(phoneInputPort.findOne(phoneId));
        if (Phone != null) {
            System.out.println(Phone);
        }
        else {
            throw new NoExistException("The phone with id " + phoneId + " does not exist into db, cannot be found");
        }
    }

    public void create(String phoneId, String company, Integer personId) throws NoExistException {
        // Find the person
        PersonaModelCli personModel = personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(personId));

        if (personModel == null) {
            throw new NoExistException("The person with id " + personId + " does not exist in the database, cannot create study.");
        }

        // Build the phone model
        PhoneModelCli phoneModel = PhoneModelCli.builder()
                .num(phoneId)
                .oper(company)
                .duenio(personModel)
                .build();

        phoneInputPort.create(phoneMapperCli.fromAdapterCliToDomain(phoneModel), personId);
    }

    public void edit(String phoneId, String company, Integer personId) throws NoExistException {
        // Find the person
        PersonaModelCli personModel = personaMapperCli.fromDomainToAdapterCli(personInputPort.findOne(personId));

        if (personModel == null) {
            throw new NoExistException("The person with id " + personId + " does not exist in the database, cannot create study.");
        }

        // Build the phone model
        PhoneModelCli phoneModel = PhoneModelCli.builder()
                .num(phoneId)
                .oper(company)
                .duenio(personModel)
                .build();

        phoneInputPort.edit(phoneId, phoneMapperCli.fromAdapterCliToDomain(phoneModel));
    }

    public void drop (String phoneId) throws NoExistException {
        Optional.ofNullable(phoneInputPort.findOne(phoneId))
                .orElseThrow(() -> new NoExistException("The phone with id " + phoneId + " does not exist in the database, cannot be deleted."));
        phoneInputPort.drop(phoneId);
    }

    public void count() {
        System.out.println(phoneInputPort.count());
    }
}
