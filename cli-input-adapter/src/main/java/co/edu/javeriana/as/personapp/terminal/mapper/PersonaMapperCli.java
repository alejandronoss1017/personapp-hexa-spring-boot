package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.NonNull;

@Mapper
public class PersonaMapperCli {

	public PersonaModelCli fromDomainToAdapterCli(Person person) {
		return person != null ? PersonaModelCli.builder()
				.cc(person.getIdentification())
				.nombre(person.getFirstName())
				.apellido(person.getLastName())
				.edad(person.getAge())
				.genero(String.valueOf(person.getGender()))
				.build() : null;
	}

	public Person fromAdapterCliToDomain(PersonaModelCli personaModelCli) {
		return personaModelCli != null ? Person.builder()
				.identification(personaModelCli.getCc())
				.firstName(personaModelCli.getNombre())
				.lastName(personaModelCli.getApellido())
				.age(personaModelCli.getEdad())
				.gender(personaModelCli.getGenero() != null ? parseGender(personaModelCli.getGenero()) : Gender.OTHER)
				.build() : null;
	}

	private @NonNull Gender parseGender(String genero) {
		switch (genero.toUpperCase()) {
			case "M":
				return Gender.MALE;
			case "F":
				return Gender.FEMALE;
			default:
				return Gender.OTHER;
		}
	}
}
