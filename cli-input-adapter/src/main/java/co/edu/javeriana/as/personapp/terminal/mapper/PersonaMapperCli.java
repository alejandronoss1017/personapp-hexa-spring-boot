package co.edu.javeriana.as.personapp.terminal.mapper;

import co.edu.javeriana.as.personapp.common.annotations.Mapper;
import co.edu.javeriana.as.personapp.domain.Gender;
import co.edu.javeriana.as.personapp.domain.Person;
import co.edu.javeriana.as.personapp.terminal.model.PersonaModelCli;
import lombok.NonNull;

@Mapper
public class PersonaMapperCli {

	public PersonaModelCli fromDomainToAdapterCli(Person person) {
		PersonaModelCli personaModelCli = new PersonaModelCli();
		personaModelCli.setCc(person.getIdentification());
		personaModelCli.setNombre(person.getFirstName());
		personaModelCli.setApellido(person.getLastName());
		personaModelCli.setGenero(person.getGender().toString());
		personaModelCli.setEdad(person.getAge());
		return personaModelCli;
	}

	public Person fromAdapterCliToDomain(PersonaModelCli personaModelCli) {
		Person person = new Person();
		person.setIdentification(personaModelCli.getCc());
		person.setFirstName(personaModelCli.getNombre());
		person.setLastName(personaModelCli.getApellido());
		person.setGender(parseGender(personaModelCli.getGenero()));
		person.setAge(personaModelCli.getEdad());
		return person;
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
