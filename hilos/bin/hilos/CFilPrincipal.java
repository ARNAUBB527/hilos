package hilos;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CFilPrincipal {
    private static int temporizacionPadre; // Variable para almacenar la temporización del padre

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("Seleccione una opción:");
            System.out.println("1. Modificar temporización para que el proceso hijo termine antes que el padre");
            System.out.println("2. Modificar temporización para que el proceso hijo termine después que el padre");
            System.out.println("3. Asegurar que el proceso padre siempre termine después de sus procesos hijos");
            System.out.println("4. Iniciar ejecución de procesos hijos con diferentes temporizaciones");
            System.out.println("0. Salir");
            opcion = scanner.nextInt();

            CFil procesoPadre = new CFil("Proceso Padre");

            switch (opcion) {
                case 1:
                    modificarTemporizacionAntes(procesoPadre);
                    break;
                case 2:
                    modificarTemporizacionDespues(procesoPadre);
                    break;
                case 3:
                    asegurarTerminacion(procesoPadre);
                    break;
                case 4:
                    iniciarProcesosHijosConTemporizacionesDiferentes(scanner, procesoPadre);
                    break;
                case 0:
                    System.out.println("Saliendo...");
                    break;
                default:
                    System.out.println("Opción no válida");
            }
        } while (opcion != 0);
    }

    private static void modificarTemporizacionAntes(CFil procesoPadre) {
        List<CFil> procesosHijos = new ArrayList<>();
        CFil procesoHijo = new CFil("Proceso Hijo");
        procesosHijos.add(procesoHijo);

        procesoHijo.start();

        try {
            procesoHijo.join();

            // Hacer que el proceso padre espere un tiempo más largo después de que el hijo ha terminado
            procesoPadre.setTemporizacion(temporizacionPadre);
            procesoPadre.start();
            procesoPadre.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void modificarTemporizacionDespues(CFil procesoPadre) {
        CFil procesoHijo = new CFil("Proceso Hijo");

        try {
            procesoPadre.setTemporizacion(temporizacionPadre);
            procesoHijo.start();
            procesoPadre.start();

            procesoHijo.join();
            procesoPadre.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void asegurarTerminacion(CFil procesoPadre) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Ingrese el número de procesos hijos: ");
        int numHijos = scanner.nextInt();

        List<CFil> procesosHijos = new ArrayList<>();
        for (int i = 1; i <= numHijos; i++) {
            CFil procesoHijo = new CFil("Proceso Hijo " + i);
            procesosHijos.add(procesoHijo);
        }

        // Inicia todos los procesos hijos antes de iniciar al padre
        for (CFil procesoHijo : procesosHijos) {
            procesoHijo.start();
        }

        // Espera a que todos los procesos hijos terminen antes de iniciar al padre
        try {
            for (CFil procesoHijo : procesosHijos) {
                procesoHijo.join();
            }

            // Inicia y espera a que el proceso padre termine después de que todos los hijos han terminado
            procesoPadre.start();
            procesoPadre.join();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void iniciarProcesosHijosConTemporizacionesDiferentes(Scanner scanner, CFil procesoPadre) {
        System.out.println("Ingrese el número de procesos hijos: ");
        int numProcesos = scanner.nextInt();
        if (numProcesos > 10) {
            System.out.println("Número de procesos hijos exageradamente grande. Saliendo...");
            return;
        }

        List<CFil> procesosHijos = new ArrayList<>();
        for (int i = 1; i <= numProcesos; i++) {
            CFil procesoHijo = new CFil("Proceso Hijo " + i);

            // Asigna temporizaciones aleatorias entre 1 y 5000 milisegundos
            int temporizacionHijo = (int) (Math.random() * 10000) + 1;
            procesoHijo.setTemporizacion(temporizacionHijo);

            procesosHijos.add(procesoHijo);
        }

        try {
            // Inicia todos los procesos hijos antes de iniciar al padre
            for (CFil procesoHijo : procesosHijos) {
                procesoHijo.start();
            }

            // Espera a que todos los procesos hijos terminen antes de iniciar al padre
            for (CFil procesoHijo : procesosHijos) {
                procesoHijo.join();
            }

            // Inicia y espera a que el proceso padre termine después de que todos los hijos han terminado
            procesoPadre.start();
            procesoPadre.join();

            System.out.println("Todos los procesos han terminado.");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
