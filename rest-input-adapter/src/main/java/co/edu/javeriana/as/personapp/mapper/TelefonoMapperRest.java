package co.edu.javeriana.as.personapp.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Phone;
import co.edu.javeriana.as.personapp.model.response.TelefonoResponse;

@Mapper
public class TelefonoMapperRest {

    public TelefonoResponse fromDomainToAdapterRestMaria(Phone phone) {
        return fromDomainToAdapterRest(phone, "MariaDB");
    }

    public TelefonoResponse fromDomainToAdapterRestMongo(Phone phone) {
        return fromDomainToAdapterRest(phone, "MongoDB");
    }

    public TelefonoResponse fromDomainToAdapterRest(Phone phone, String database) {
        return new TelefonoResponse(
                phone.getNumber(),
                phone.getCompany(),
                phone.getOwner() != null ? String.valueOf(phone.getOwner().getIdentification()) : null,
                database,
                "OK"
        );
    }

    public Phone fromAdapterToDomain(co.edu.javeriana.as.personapp.model.request.TelefonoRequest request, co.edu.javeriana.as.personapp.domain.Person owner) {
        return Phone.builder()
                .number(request.getNum())
                .company(request.getOper())
                .owner(owner)
                .build();
    }
}
