package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.adapter.PersonaInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PersonaMenu {

	private static final int OPCION_REGRESAR_MODULOS = 0;
	private static final int PERSISTENCIA_MARIADB = 1;
	private static final int PERSISTENCIA_MONGODB = 2;

	private static final int OPCION_REGRESAR_MOTOR_PERSISTENCIA = 0;
	private static final int OPCION_VER_TODO = 1;
	// mas opciones
	private static final int OPCION_VER_ESPECIFICO = 2;
	private static final int OPCION_AGREGAR = 3;
	private static final int OPCION_ACTUALIZAR = 4;
	private static final int OPCION_ELIMINAR = 5;
	private static final int OPCION_CONTAR = 6;

	public void iniciarMenu(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuMotorPersistencia();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
				case OPCION_REGRESAR_MODULOS:
					isValid = true;
					break;
				case PERSISTENCIA_MARIADB:
					personaInputAdapterCli.setPersonOutputPortInjection("MARIA");
					menuOpciones(personaInputAdapterCli,keyboard);
					break;
				case PERSISTENCIA_MONGODB:
					personaInputAdapterCli.setPersonOutputPortInjection("MONGO");
					menuOpciones(personaInputAdapterCli,keyboard);
					break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			}  catch (InvalidOptionException e) {
				log.warn(e.getMessage());
			}
		} while (!isValid);
	}

	private void menuOpciones(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		boolean isValid = false;
		do {
			try {
				mostrarMenuOpciones();
				int opcion = leerOpcion(keyboard);
				switch (opcion) {
					case OPCION_REGRESAR_MOTOR_PERSISTENCIA:
						isValid = true;
						break;
					case OPCION_VER_TODO:
						personaInputAdapterCli.historial();
						break;
					// mas opciones
					case OPCION_VER_ESPECIFICO:
						verEspesifico(personaInputAdapterCli, keyboard);
						break;
					case OPCION_AGREGAR:
						agregarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_ACTUALIZAR:
						actualizarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_ELIMINAR:
						eliminarPersona(personaInputAdapterCli, keyboard);
						break;
					case OPCION_CONTAR:
						personaInputAdapterCli.count();
						break;
				default:
					log.warn("La opción elegida no es válida.");
				}
			} catch (InputMismatchException e) {
				log.warn("Solo se permiten números.");
			} catch (NoExistException e) {
                throw new RuntimeException(e);
            }
        } while (!isValid);
	}

	private void mostrarMenuOpciones() {
		System.out.println("----------------------");
		System.out.println(OPCION_VER_TODO + " para ver todas las personas");
		// implementar otras opciones
		System.out.println(OPCION_VER_ESPECIFICO + " para ver una persona específica");
		System.out.println(OPCION_AGREGAR + " para agregar una persona");
		System.out.println(OPCION_ACTUALIZAR + " para actualizar una persona");
		System.out.println(OPCION_ELIMINAR + " para eliminar una persona");
		System.out.println(OPCION_CONTAR + " para contar personas");
		System.out.println(OPCION_REGRESAR_MOTOR_PERSISTENCIA + " para regresar");
	}

	private void mostrarMenuMotorPersistencia() {
		System.out.println("----------------------");
		System.out.println(PERSISTENCIA_MARIADB + " para MariaDB");
		System.out.println(PERSISTENCIA_MONGODB + " para MongoDB");
		System.out.println(OPCION_REGRESAR_MODULOS + " para regresar");
	}

	private int leerOpcion(Scanner keyboard) {
		try {
			System.out.print("Ingrese una opción: ");
			return keyboard.nextInt();
		} catch (InputMismatchException e) {
			log.warn("Solo se permiten números.");
			return leerOpcion(keyboard);
		}
	}

	private void verEspesifico(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) throws NoExistException {
		System.out.print("Ingrese el id de la persona: ");
		int id = keyboard.nextInt();
		personaInputAdapterCli.findById(id);
	}

	private void agregarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) {
		keyboard.nextLine(); // Limpiar el buffer
		System.out.print("Ingrese el número de identificación de la persona: ");
		int cc = keyboard.nextInt();
		System.out.print("Ingrese el nombre: ");
		String nombre = keyboard.next();
		System.out.print("Ingrese el apellido: ");
		String apellido = keyboard.next();
		System.out.print("Ingrese el género (M/F): ");
		String genero = keyboard.next();
		System.out.print("Ingrese la edad: ");
		Integer edad = keyboard.nextInt();
		personaInputAdapterCli.create(cc, nombre, apellido, genero, edad);
		System.out.println("Persona creada con éxito.");
	}

	private void actualizarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) throws NoExistException {
		keyboard.nextLine(); // Limpiar el buffer
		System.out.print("Ingrese el número de identificación de la persona a editar: ");
		int cc = keyboard.nextInt();
		System.out.print("Ingrese el nuevo nombre: ");
		String nombre = keyboard.next();
		System.out.print("Ingrese el nuevo apellido: ");
		String apellido = keyboard.next();
		System.out.print("Ingrese el nuevo género (M/F): ");
		String genero = keyboard.next();
		System.out.print("Ingrese la nueva edad: ");
		Integer edad = keyboard.nextInt();
		personaInputAdapterCli.edit(cc, nombre, apellido, genero, edad);
		System.out.println("Persona editada con éxito.");
	}

	private void eliminarPersona(PersonaInputAdapterCli personaInputAdapterCli, Scanner keyboard) throws NoExistException {
		System.out.print("Ingrese el número de identificación de la persona a eliminar: ");
		int cc = keyboard.nextInt();
		personaInputAdapterCli.drop(cc);
		System.out.println("Persona eliminada con éxito.");
	}
}
