package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.terminal.model.PhoneModelCli;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper
public class PhoneMapperCli {

    @Autowired
    PersonaMapperCli personaMapperCli;

    public PhoneModelCli fromDomainToAdapterCli(Phone phone) {
        return PhoneModelCli.builder()
                .num(phone.getNumber())
                .oper(phone.getCompany())
                .duenio(personaMapperCli.fromDomainToAdapterCli(phone.getOwner()))
                .build();
    }

    public Phone fromAdapterCliToDomain(PhoneModelCli phoneModelCli) {
        return Phone.builder()
                .number(phoneModelCli.getNum())
                .company(phoneModelCli.getOper())
                .owner(personaMapperCli.fromAdapterCliToDomain(phoneModelCli.getDuenio()))
                .build();
    }
}
