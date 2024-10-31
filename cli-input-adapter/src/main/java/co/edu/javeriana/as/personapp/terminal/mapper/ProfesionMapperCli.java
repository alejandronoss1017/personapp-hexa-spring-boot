package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.terminal.model.ProfesionModelCli;

@Mapper
public class ProfesionMapperCli {

    public ProfesionModelCli fromDomainToAdapterCli(Profession profession) {
        return profession != null ? ProfesionModelCli.builder()
                .id(profession.getIdentification())
                .name(profession.getName())
                .description(profession.getDescription())
                .build() : null;
    }

    public Profession fromAdapterCliToDomain(ProfesionModelCli profesionModelCli) {
        return profesionModelCli != null ? Profession.builder()
                .identification(profesionModelCli.getId())
                .name(profesionModelCli.getName())
                .description(profesionModelCli.getDescription())
                .build() : null;
    }
}
