package co.edu.javeriana.as.personapp.application.port.out;

import co.edu.javeriana.as.personapp.common.annotations.Port;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.domain.Profession;

import java.util.List;

@Port
public interface ProfessionOutputPort {
    public List<Profession> find();
    public Profession findById(Integer identification);
    public Profession save(Profession profession);
    public Boolean delete(Integer identification);

}
