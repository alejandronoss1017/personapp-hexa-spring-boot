package co.edu.javeriana.as.personapp.controller;

import java.time.LocalDateTime;
import java.util.List;

import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.model.response.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.javeriana.as.personapp.adapter.PersonaInputAdapterRest;
import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.model.request.PersonaRequest;
import co.edu.javeriana.as.personapp.model.response.PersonaResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/persona")
public class PersonaControllerV1 {

	@Autowired
	private PersonaInputAdapterRest personaInputAdapterRest;

	@ResponseBody
	@GetMapping(path = "/{database}", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<PersonaResponse> personas(@PathVariable String database) {
		log.info("Fetching all persons from the {} database", database);
		return personaInputAdapterRest.historial(database.toUpperCase());
	}

	@ResponseBody
	@PostMapping(path = "", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public PersonaResponse crearPersona(@RequestBody PersonaRequest request) {
		log.info("Creating a new person");
		return personaInputAdapterRest.crearPersona(request);
	}

	@ResponseBody
	@GetMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> persona(@PathVariable String database, @PathVariable int cc) {
		log.info("Fetching person with ID {} from the {} database", cc, database);
		try {
			return ResponseEntity.ok(personaInputAdapterRest.obtenerPersona(database.toUpperCase(), cc));
		}
		catch (NoExistException e) {
			return ResponseEntity.status(404).body(new Response(String.valueOf(HttpStatus.NOT_FOUND),
					"Person with ID " + cc + " does not exist in the database", LocalDateTime.now()));
		}
	}

	@ResponseBody
	@PutMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> actualizarPersona(@PathVariable String database, @PathVariable Integer cc, @RequestBody PersonaRequest request) {
		log.info("Updating person with ID {} in the {} database", cc, database);
		try {
			return ResponseEntity.ok(personaInputAdapterRest.actualizarPersona(database.toUpperCase(), cc, request));
		}
		catch (NoExistException e) {
			return ResponseEntity.status(404).body(new Response(String.valueOf(HttpStatus.NOT_FOUND),
					"Person with ID " + cc + " does not exist in the database", LocalDateTime.now()));
		}
	}

	@ResponseBody
	@DeleteMapping(path = "/{database}/{cc}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> eliminarPersona(@PathVariable String database, @PathVariable Integer cc)  {
		log.info("Deleting person with ID {} from the {} database", cc, database);
		try {
			return personaInputAdapterRest.eliminarPersona(database.toUpperCase(), cc);
		}
		catch (NoExistException e) {
			return ResponseEntity.status(404).body(new Response(String.valueOf(HttpStatus.NOT_FOUND),
					"Person with ID " + cc + " does not exist in the database", LocalDateTime.now()));
		}
		catch (InvalidOptionException e) {
			return ResponseEntity.status(500).body(new Response(String.valueOf(HttpStatus.INTERNAL_SERVER_ERROR),
					"Bad Request", LocalDateTime.now()));
		}
	}

	@ResponseBody
	@GetMapping(path = "/{database}/count", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> contarPersonas(@PathVariable String database) {
		log.info("Counting all persons in the {} database", database);
		return ResponseEntity.ok(personaInputAdapterRest.count(database.toUpperCase()));
	}
}