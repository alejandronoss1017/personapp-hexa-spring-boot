package co.edu.javeriana.as.personapp.terminal.menu;

import java.util.InputMismatchException;
import java.util.Scanner;

import co.edu.javeriana.as.personapp.common.exceptions.InvalidOptionException;
import co.edu.javeriana.as.personapp.common.exceptions.NoExistException;
import co.edu.javeriana.as.personapp.terminal.adapter.PhoneInputAdapterCli;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PhoneMenu {

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

    public void iniciarMenu(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) {
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
                        phoneInputAdapterCli.setPersonOutputPortInjection("MARIA");
                        menuOpciones(phoneInputAdapterCli,keyboard);
                        break;
                    case PERSISTENCIA_MONGODB:
                        phoneInputAdapterCli.setPersonOutputPortInjection("MONGO");
                        menuOpciones(phoneInputAdapterCli,keyboard);
                        break;
                    default:
                        log.warn("La opción elegida no es válida.");
                }
            }  catch (InvalidOptionException e) {
                log.warn(e.getMessage());
            }
        } while (!isValid);
    }

    private void menuOpciones(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) {
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
                        phoneInputAdapterCli.historial();
                        break;
                    // mas opciones
                    case OPCION_VER_ESPECIFICO:
                        verEspesifico(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_AGREGAR:
                        agregarTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_ACTUALIZAR:
                        actualizarTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_ELIMINAR:
                        eliminarTelefono(phoneInputAdapterCli, keyboard);
                        break;
                    case OPCION_CONTAR:
                        phoneInputAdapterCli.count();
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
        System.out.println(OPCION_VER_TODO + " para ver todas los telefonos");
        // implementar otras opciones
        System.out.println(OPCION_VER_ESPECIFICO + " para ver un telefono específico");
        System.out.println(OPCION_AGREGAR + " para agregar un telefono");
        System.out.println(OPCION_ACTUALIZAR + " para actualizar un telefono");
        System.out.println(OPCION_ELIMINAR + " para eliminar un telefono");
        System.out.println(OPCION_CONTAR + " para contar los telefonos");
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

    private void verEspesifico(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        System.out.print("Ingrese el id del telefono: ");
        String id = keyboard.next();
        phoneInputAdapterCli.findOne(id);
    }

    private void agregarTelefono(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número del telefono: ");
        String number = keyboard.next();
        System.out.print("Ingrese la compañia del telefono: ");
        String company = keyboard.next();
        System.out.print("Ingrese el id del dueño del telefono: ");
        Integer ownerId = keyboard.nextInt();
        phoneInputAdapterCli.create(number, company, ownerId);
    }

    private void actualizarTelefono(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        keyboard.nextLine(); // Limpiar el buffer
        System.out.print("Ingrese el número del telefono que quiere actualizar: ");
        String number = keyboard.next();
        System.out.print("Ingrese la compañia del telefono a actualizar: ");
        String company = keyboard.next();
        System.out.print("Ingrese el id del dueño del telefono a actualizar: ");
        Integer ownerId = keyboard.nextInt();
        phoneInputAdapterCli.edit(number, company, ownerId);
    }

    private void eliminarTelefono(PhoneInputAdapterCli phoneInputAdapterCli, Scanner keyboard) throws NoExistException {
        System.out.print("Ingrese el número del telefono a borrar: ");
        String number = keyboard.next();
        phoneInputAdapterCli.drop(number);
        System.out.println("Teléfono eliminado con éxito.");
    }
}
