package co.edu.javeriana.as.personapp.terminal.menu;

import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.adapter.StudyInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class StudyMenu {

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

    public void iniciarMenu(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) {
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
                        studyInputAdapterCli.setPersonOutputPortInjection("MARIA");
                        menuOpciones(studyInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        studyInputAdapterCli.setPersonOutputPortInjection("MONGO");
                        menuOpciones(studyInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) {
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
                        studyInputAdapterCli.historial();
                        break;
                    // mas opciones
                    case OPCION_VER_ESPECIFICO:
                        verEspesifico(studyInputAdapterCli, keyboard);
                        break;
                    case OPCION_AGREGAR:
                        agregarEstudio(studyInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarEstudio(studyInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarEstudio(studyInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        studyInputAdapterCli.count();
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
        System.out.println(OPCION_VER_TODO + " para ver todos los estudios");
        // implementar otras opciones
        System.out.println(OPCION_VER_ESPECIFICO + " para ver un estudio específico");
        System.out.println(OPCION_AGREGAR + " para agregar un estudio");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar un estudio");
        System.out.println(OPCION_ELIMINAR + " para eliminar un estudio");
        System.out.println(OPCION_CONTAR + " para contar estudios");
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

    private void verEspesifico(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        // Ingrese el id de la profesion y el id de la persona
        System.out.print("Ingrese el id del estudio: ");
        int id = keyboard.nextInt();
        System.out.print("Ingrese la cedula de la persona: ");
        int cc = keyboard.nextInt();
        studyInputAdapterCli.findById(id, cc);
    }

    private void agregarEstudio(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        // Ingrese el id de la profesion y el id de la persona
        System.out.print("Ingrese la cedula de la persona: ");
        int cc = keyboard.nextInt();
        System.out.print("Ingrese el id de la profesion: ");
        int idProfesion = keyboard.nextInt();
        System.out.print("Ingrese el nombre de la universidad: ");
        String nombre = keyboard.next();
        System.out.print("Ingrese la la fecha de graduación (yyyy-mm-dd): ");
        String fecha = keyboard.next();
        studyInputAdapterCli.create(idProfesion, cc, nombre, LocalDate.parse(fecha));
    }

    private void actualizarEstudio(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        // Ingrese el id de la profesion y el id de la persona
        System.out.print("Ingrese el id de la profesion del estudio: ");
        int idProfesion = keyboard.nextInt();
        System.out.print("Ingrese la cedula de la persona: ");
        int cc = keyboard.nextInt();
        System.out.print("Ingrese el nuevo nombre de la universidad: ");
        String nombre = keyboard.next();
        System.out.print("Ingrese la nueva fecha de graduación (yyyy-mm-dd): ");
        String fecha = keyboard.next();
        studyInputAdapterCli.edit(idProfesion, cc, nombre, LocalDate.parse(fecha));
    }

    private void eliminarEstudio(StudyInputAdapterCli studyInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        // Ingrese el id de la profesion y el id de la persona
        System.out.print("Ingrese el id del estudio: ");
        int id = keyboard.nextInt();
        System.out.print("Ingrese la cedula de la persona: ");
        int cc = keyboard.nextInt();
        studyInputAdapterCli.drop(id, cc);
    }
}
