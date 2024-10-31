package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.adapter.ProfesionInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ProfesionMenu {

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

    public void iniciarMenu(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        profesionInputAdapterCli.setPersonOutputPortInjection("MARIA");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        profesionInputAdapterCli.setPersonOutputPortInjection("MONGO");
                        menuOpciones(profesionInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
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
                        profesionInputAdapterCli.historial();
                        break;
                    // mas opciones
                    case OPCION_VER_ESPECIFICO:
                        verEspesifico(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_AGREGAR:
                        agregarProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarProfesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarprofesion(profesionInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        profesionInputAdapterCli.count();
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
        System.out.println(OPCION_VER_TODO + " para ver todas las profesiones");
        // implementar otras opciones
        System.out.println(OPCION_VER_ESPECIFICO + " para ver una profesion específica");
        System.out.println(OPCION_AGREGAR + " para agregar una profesion");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar una profesion");
        System.out.println(OPCION_ELIMINAR + " para eliminar una profesion");
        System.out.println(OPCION_CONTAR + " para contar profesiones");
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

    private void verEspesifico(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) throws NoExistException {
        System.out.print("Ingrese el id de la profesion: ");
        int id = keyboard.nextInt();
        profesionInputAdapterCli.findById(id);
    }

    private void agregarProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la id de la profesion: ");
        int id = keyboard.nextInt();
        System.out.print("Ingrese el nombre de la profesion: ");
        String nombre = keyboard.next();
        System.out.print("Ingrese la descripcion de la profesion: ");
        String descripcion = keyboard.next();
        profesionInputAdapterCli.create(id, nombre, descripcion);
        System.out.println("profesion creada con éxito.");
    }

    private void actualizarProfesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese la id de la profesion: ");
        int id = keyboard.nextInt();
        System.out.print("Ingrese el nombre de la profesion: ");
        String nombre = keyboard.next();
        System.out.print("Ingrese la descripcion de la profesion: ");
        String descripcion = keyboard.next();
        profesionInputAdapterCli.edit(id, nombre, descripcion);
        System.out.println("profesion editada con éxito.");
    }

    private void eliminarprofesion(ProfesionInputAdapterCli profesionInputAdapterCli, Scanner keyboard) throws NoExistException {
        System.out.print("Ingrese el id de la profesion a borrar: ");
        int id = keyboard.nextInt();
        profesionInputAdapterCli.drop(id);
        System.out.println("profesion eliminada con éxito.");
    }
}
