package co.edu.javeriana.as.personapp.application.usecase;

import java.util.List;

import co.edu.javeriana.as.personapp.application.port.in.PhoneInputPort;
import co.edu.javeriana.as.personapp.application.port.in.ProfessionInputPort;
import co.edu.javeriana.as.personapp.application.port.out.PersonOutputPort;
import co.edu.javeriana.as.personapp.application.port.out.PhoneOutputPort;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.domain.Profession;
import org.springframework.beans.factory.annotation.Qualifier;
import co.edu.javeriana.as.personapp.common.annotations.UseCase;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UseCase
public class PhoneUseCase implements PhoneInputPort {

    private PhoneOutputPort phonePersistence;
    private PersonOutputPort personPersistence;

    public PhoneUseCase(
            @Qualifier("phoneOutputAdapterMaria")
            PhoneOutputPort phonePersistence,
            @Qualifier("personOutputAdapterMaria")
            PersonOutputPort personPersistence) {
        this.phonePersistence=phonePersistence;
        this.personPersistence = personPersistence;
    }

    @Override
    public void setPersistence(PhoneOutputPort phonePersistence) {
        this.phonePersistence=phonePersistence;
    }

    @Override
    public List<Phone> findAll() {
        log.info("Output: " + phonePersistence.getClass());
        return phonePersistence.find();
    }

    @Override
    public Phone findOne(String identification) throws NoExistException {
        Phone oldPhone = phonePersistence.findById(identification);
        if (oldPhone != null)
            return oldPhone;
        throw new NoExistException("The phone with id " + identification + " does not exist into db, cannot be found");
    }

    @Override
    public Phone create(Phone phone, Integer person) {
        log.debug("Into create on Application Domain");
        // Find the person
        Person personObj = personPersistence.findById(person);
        // If the person not exists
        if (personObj == null) {
            log.error("The person with id " + person + " does not exist into db, cannot be created");
            return null;
        }
        // Set the person
        phone.setOwner(personObj);
        return phonePersistence.save(phone);
    }

    @Override
    public Phone edit(String identification, Phone phone) throws NoExistException {
        Phone oldPhone = phonePersistence.findById(identification);
        if (oldPhone != null)
            return phonePersistence.save(phone);
        throw new NoExistException(
                "The phone with id " + identification + " does not exist into db, cannot be edited");
    }

    @Override
    public Boolean drop(String identification) throws NoExistException {
        Phone oldPhone = phonePersistence.findById(identification);
        if (oldPhone != null)
            return phonePersistence.delete(identification);
        throw new NoExistException(
                "The phone with id " + identification + " does not exist into db, cannot be dropped");
    }

    @Override
    public Integer count() {
        return findAll().size();
    }
}
