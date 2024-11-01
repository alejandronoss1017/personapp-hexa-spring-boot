package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.domain.Phone;

import java.util.List;

@Port
public interface PhoneOutputPort {
    public List<Phone> find();
    public Phone findById(String PhoneId);
    public Phone save(Phone phone);
    public Boolean delete(String PhoneId);
}
