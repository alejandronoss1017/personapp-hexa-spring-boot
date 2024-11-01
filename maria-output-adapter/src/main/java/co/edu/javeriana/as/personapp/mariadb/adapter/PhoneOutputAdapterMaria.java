package co.edu.javeriana.as.personapp.mariadb.adapter;

import java.util.List;

import java.util.stream.Collectors;

import javax.transaction.Transactional;

import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.mariadb.entity.TelefonoEntity;
import co.edu.javeriana.as.personapp.mariadb.mapper.TelefonoMapperMaria;
import co.edu.javeriana.as.personapp.mariadb.repository.PhoneRepositoryMaria;
import org.springframework.beans.factory.annotation.Autowired;
import co.edu.javeriana.as.personapp.common.annotations.Adapter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Adapter("phoneOutputAdapterMaria")
@Transactional
public class PhoneOutputAdapterMaria implements PhoneOutputPort {

    @Autowired
    private PhoneRepositoryMaria phoneRepositoryMaria;

    @Autowired
    private TelefonoMapperMaria telefonoMapperMaria;

    @Override
    @Transactional
    public Phone save(Phone phone) {
        TelefonoEntity telefonoEntity = telefonoMapperMaria.fromDomainToAdapter(phone);
        telefonoEntity = phoneRepositoryMaria.save(telefonoEntity);
        return telefonoMapperMaria.fromAdapterToDomain(telefonoEntity);
    }

    @Override
    public List<Phone> find() {
        log.debug("Into find on Adapter MariaDB");
        return phoneRepositoryMaria.findAll().stream().map(telefonoMapperMaria::fromAdapterToDomain)
                .collect(Collectors.toList());
    }

    @Override
    public Phone findById(String PhoneId) {
        log.debug("Into findById on Adapter MariaDB");
        // Find the phone
        return phoneRepositoryMaria.findById(PhoneId)
                .map(telefonoMapperMaria::fromAdapterToDomain)
                .orElse(null);
    }

    @Override
    public Boolean delete(String PhoneId) {
        log.debug("Into delete on Adapter MariaDB");
        // Delete the phone
        phoneRepositoryMaria.deleteById(PhoneId);
        // Return if the phone was deleted
        return phoneRepositoryMaria.findById(PhoneId).isEmpty();
    }
}
