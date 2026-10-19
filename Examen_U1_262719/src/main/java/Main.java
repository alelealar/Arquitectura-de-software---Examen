import control.ControlCompra;
import vista.VistaCompra;
import java.util.Scanner;

/**
 * Clase principal que inicializa la aplicación y delega toda la interacción a la Vista.
 *
 * @author Alejandra Leal Armenta
 */
public class Main {

    public static void main(String[] args) {
        ControlCompra control = new ControlCompra();
        VistaCompra vista = new VistaCompra(control);
        Scanner scanner = new Scanner(System.in);

        boolean salir = false;

        while (!salir) {
            System.out.println("\n=== MENÚ PRINCIPAL - COMPRAR PRODUCTO ===");
            System.out.println("1. Ver catálogo y seleccionar producto");
            System.out.println("2. Filtrar productos por categoría");
            System.out.println("3. Ver carrito de compras");
            System.out.println("4. Vaciar carrito de compras");
            System.out.println("5. Proceder al pago");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opción: ");

            String opcion = scanner.nextLine().trim();

            switch (opcion) {
                case "1":
                    // La vista carga y muestra el catálogo
                    vista.cargarPagina();

                    System.out.print("\nIngrese el ID del producto a comprar: ");
                    try {
                        Long id = Long.parseLong(scanner.nextLine().trim());
                        System.out.print("Ingrese la cantidad: ");
                        int cantidad = Integer.parseInt(scanner.nextLine().trim());

                        // La vista procesa el evento del usuario
                        vista.seleccionarProducto(id, cantidad);
                    } catch (NumberFormatException e) {
                        System.out.println("Ingrese valores numéricos válidos.");
                    }
                    break;

                case "2":
                    vista.clicFiltro();
                    break;

                case "3":
                    vista.clicCarrito();
                    break;

                case "4":
                    vista.clicVaciarCarrito();
                    break;

                case "5":
                    vista.clicProcederPago();
                    break;

                case "6":
                    System.out.println("Saliendo del sistema...");
                    salir = true;
                    break;

                default:
                    System.out.println("Opción no válida.");
                    break;
            }
        }

        scanner.close();
    }
}