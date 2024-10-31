package co.edu.javeriana.as.personapp.terminal.adapter;

import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.application.usecase.ProfesionUseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.mapper.ProfesionMapperCli;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.setup.DatabaseOption;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter
public class ProfesionInputAdapterCli {

    @Autowired
    @Qualifier("profesionOutputAdapterMaria")
    private ProfessionOutputPort professionOutputPortMaria;

    @Autowired
    @Qualifier("profesionOutputAdapterMongo")
    private ProfessionOutputPort professionOutputPortMongo;

    @Autowired
    private ProfesionMapperCli profesionMapperCli;

    ProfessionInputPort professionInputPort;

    public void setPersonOutputPortInjection(String dbOption) throws InvalidOptionException {
        if (dbOption.equalsIgnoreCase(DatabaseOption.MARIA.toString())) {
            professionInputPort = new ProfesionUseCase(professionOutputPortMaria);
        } else if (dbOption.equalsIgnoreCase(DatabaseOption.MONGO.toString())) {
            professionInputPort = new ProfesionUseCase(professionOutputPortMongo);
        } else {
            throw new InvalidOptionException("Invalid database option: " + dbOption);
        }
    }

    public void historial() {
        log.info("Into historial ProfessionEntity in Input Adapter");
        professionInputPort.findAll().stream()
                .map(profesionMapperCli::fromDomainToAdapterCli)
                .forEach(System.out::println);
    }

    public void findById(int id) throws NoExistException {
        log.info("Into findById PersonaEntity in Input Adapter");
        ProfesionModelCli profession = profesionMapperCli.fromDomainToAdapterCli(professionInputPort.findOne(id));
        if (profession != null) {
            System.out.println(profession.toString());
        }
        else {
            throw new NoExistException("No existe la persona con id: " + id);
        }
    }

    public void create (int id, String name, String description){
        ProfesionModelCli profession = ProfesionModelCli.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
        professionInputPort.create(profesionMapperCli.fromAdapterCliToDomain(profession));
    }

    public void edit (int id, String name, String description) throws NoExistException {
        ProfesionModelCli profession = ProfesionModelCli.builder()
                .id(id)
                .name(name)
                .description(description)
                .build();
        professionInputPort.edit(id, profesionMapperCli.fromAdapterCliToDomain(profession));
    }

    public void drop (int id) throws NoExistException {
        professionInputPort.drop(id);
    }

    public void count() {
        System.out.println(professionInputPort.count());
    }
}
