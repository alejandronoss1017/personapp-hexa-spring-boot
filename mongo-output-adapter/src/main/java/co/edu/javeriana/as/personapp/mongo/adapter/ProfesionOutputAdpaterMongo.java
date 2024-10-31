package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;

import java.util.stream.Collectors;

import co.edu.javeriana.as.personapp.application.port.out.ProfessionOutputPort;
import co.edu.javeriana.as.personapp.domain.Profession;
import co.edu.javeriana.as.personapp.mongo.document.ProfesionDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.ProfesionMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.ProfesionRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("profesionOutputAdapterMongo")
public class ProfesionOutputAdpaterMongo implements ProfessionOutputPort {

    @Autowired
    private ProfesionRepositoryMongo professionRepositoryMongo;

    @Autowired
    private ProfesionMapperMongo professionMapperMongo;

    @Override
    public Profession save(Profession profession) {
        log.debug("Into save on Adapter MariaDB");
        ProfesionDocument persistedProfesion = professionRepositoryMongo.save(professionMapperMongo.fromDomainToAdapter(profession));
        return professionMapperMongo.fromAdapterToDomain(persistedProfesion);
    }

    @Override
    public Boolean delete(Integer identification) {
        log.debug("Into delete on Adapter MariaDB");
        professionRepositoryMongo.deleteById(identification);
        return professionRepositoryMongo.findById(identification).isEmpty();
    }

    @Override
    public List<Profession> find() {
        log.debug("Into find on Adapter MariaDB");
        return professionRepositoryMongo.findAll().stream().map(professionMapperMongo::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Profession findById(Integer identification) {
        log.debug("Into findById on Adapter MariaDB");
        if (professionRepositoryMongo.findById(identification).isEmpty()) {
            return null;
        } else {
            return professionMapperMongo.fromAdapterToDomain(professionRepositoryMongo.findById(identification).get());
        }
    }

}
