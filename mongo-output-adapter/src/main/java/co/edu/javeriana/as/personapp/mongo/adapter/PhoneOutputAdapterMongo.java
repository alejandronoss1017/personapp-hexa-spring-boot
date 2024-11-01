package co.edu.javeriana.as.personapp.mongo.adapter;

import java.util.List;


import java.util.stream.Collectors;


import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mongo.document.TelefonoDocument;
import co.edu.javeriana.as.personapp.mongo.mapper.TelefonoMapperMongo;
import co.edu.javeriana.as.personapp.mongo.repository.PhoneRepositoryMongo;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("phoneOutputAdapterMongo")
public class PhoneOutputAdapterMongo implements PhoneOutputPort {

    @Autowired
    private PhoneRepositoryMongo phoneRepositoryMongo;

    @Autowired
    private TelefonoMapperMongo telefonoMapperMongo;

    @Override
    public Phone save(Phone phone) {
        log.debug("Into save on Adapter MariaDB");
        TelefonoDocument persistedPhone = phoneRepositoryMongo.save(telefonoMapperMongo.fromDomainToAdapter(phone));
        return telefonoMapperMongo.fromAdapterToDomain(persistedPhone);
    }

    @Override
    public List<Phone> find() {
        log.debug("Into find on Adapter MariaDB");
        return phoneRepositoryMongo.findAll().stream().map(telefonoMapperMongo::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Phone findById(String PhoneId) {
        log.debug("Into findById on Adapter MariaDB");

        // Find the phone
        return phoneRepositoryMongo.findById(PhoneId)
                .map(telefonoMapperMongo::fromAdapterToDomain)
                .orElse(null);
    }

    @Override
    public Boolean delete(String PhoneId) {
        log.debug("Into delete on Adapter MariaDB");

        // Delete the phone
        phoneRepositoryMongo.deleteById(PhoneId);
        return phoneRepositoryMongo.findById(PhoneId).isEmpty();
    }
}
