package vista;

import control.ControlCompra;
import ENUMS.Categoria;

import java.util.List;
import java.util.Scanner;

/**
 * Vista que gestiona la interacción gráfica/consola para la compra de productos.
 * Totalmente desacoplada del paquete de dominio, consumiendo únicamente tipos
 * primitivos y cadenas formateadas expuestas por el controlador.
 *
 * @author Alejandra Leal Armenta
 */
public class VistaCompra {

    private ControlCompra control;
    private Scanner scanner;

    /**
     * Constructor que inicializa la vista con la instancia del controlador.
     *
     * @param control Instancia de ControlCompra.
     */
    public VistaCompra(ControlCompra control) {
        this.control = control;
        this.scanner = new Scanner(System.in);
    }

    /**
     * Carga e inicializa la pantalla desplegando el catálogo de productos disponibles.
     */
    public void cargarPagina() {
        System.out.println("\n--- CATÁLOGO DE PRODUCTOS DISPONIBLES ---");
        List<String> productos = control.obtenerProductos();
        for (String linea : productos) {
            System.out.println(linea);
        }
        actualizarVista();
    }

    /**
     * Captura el evento de selección de un producto y su cantidad.
     *
     * @param idProducto Identificador único del producto.
     * @param cantidad Cantidad de unidades solicitadas.
     */
    public void seleccionarProducto(Long idProducto, int cantidad) {
        boolean exito = control.agregarArticulo(idProducto, cantidad);
        if (exito) {
            System.out.println("✔ Producto agregado al carrito exitosamente.");
            actualizarVista();
        } else {
            mostrarAvisoAgregarArticulo();
        }
    }

    /**
     * Despliega la opción para filtrar productos por categoría y muestra los resultados.
     */
    public void clicFiltro() {
        System.out.print("Ingrese la categoría a filtrar (FRUTAS_VERDURAS, LACTEOS, PANADERIA, CARNES, ESPECIAS, DESPENSA): ");
        String catInput = scanner.nextLine();
        try {
            Categoria categoria = Categoria.valueOf(catInput.toUpperCase().trim());
            List<String> filtrados = control.obtenerProductosFiltrados(categoria);
            System.out.println("\n--- PRODUCTOS EN " + categoria + " ---");
            for (String linea : filtrados) {
                System.out.println(linea);
            }
        } catch (IllegalArgumentException e) {
            System.out.println("Categoría no válida.");
        }
    }

    /**
     * Refresca en pantalla los artículos que el usuario tiene agregados en su carrito.
     */
    public void actualizarVista() {
        List<String> resumenCarrito = control.obtenerDetalles();
        if (!resumenCarrito.isEmpty()) {
            System.out.println("\n--- CARRITO ACTUAL ---");
            for (String linea : resumenCarrito) {
                System.out.println(linea);
            }
        }
    }

    /**
     * Inicia la fase de procesamiento de pago verificando el estado del carrito.
     */
    public void clicProcederPago() {
        if (control.procederPago()) {
            ingresarDatos();
        } else {
            mostrarAvisoAgregarArticulos();
        }
    }

    /**
     * Muestra el mensaje general de despliegue de precio de compra.
     */
    public void mostrarPrecioCompra() {
        System.out.println("Mostrando precio de compra...");
    }

    /**
     * Muestra el resumen del carrito de compras actual.
     */
    public void clicCarrito() {
        actualizarVista();
    }

    /**
     * Captura el número de tarjeta y CVV, enviando los datos al controlador para confirmación.
     */
    public void ingresarDatos() {
        System.out.println("\n--- INGRESO DE DATOS DE TARJETA ---");
        System.out.print("Número de Tarjeta (16 dígitos): ");
        String numero = scanner.nextLine().trim();

        System.out.print("CVV (3 dígitos): ");
        String cvv = scanner.nextLine().trim();

        String datosConfirmados = control.solicitarDatosTarjeta(numero);
        if (datosConfirmados != null) {
            System.out.println("✔ " + datosConfirmados);
            mostrarDatosTarjeta();
            clicPagar();
        } else {
            mostrarErrorPago();
        }
    }

    /**
     * Muestra la confirmación de la tarjeta procesada con el banco emisor.
     */
    public void mostrarDatosTarjeta() {
        System.out.println("Datos de la tarjeta confirmados exitosamente.");
    }

    /**
     * Procesa la solicitud final de pago contra el controlador.
     */
    public void clicPagar() {
        if (control.validarPago()) {
            mostrarPantallaTicket();
        } else {
            mostrarErrorPago();
        }
    }

    /**
     * Despliega la pantalla con la confirmación exitosa de la compra.
     */
    public void mostrarPantallaTicket() {
        System.out.println("\n=================================");
        System.out.println("      TICKET DE COMPRA          ");
        System.out.println("=================================");
        System.out.println("¡Transacción exitosa! Gracias por su compra.");
    }

    /**
     * Despliega el aviso cuando el usuario intenta proceder al pago con el carrito vacío.
     */
    public void mostrarAvisoAgregarArticulos() {
        System.out.println("\n[AVISO] Su carrito está vacío. Debe agregar artículos antes de proceder al pago.");
    }

    /**
     * Muestra el mensaje de error cuando el banco rechaza la transacción.
     */
    public void mostrarErrorPago() {
        System.out.println("\n[ERROR] Transacción rechazada. Tarjeta sin fondos o datos incorrectos.");
    }

    /**
     * Sobrecarga de método para desplegar el importe total a pagar de la compra.
     *
     * @param total Importe total calculado.
     */
    public void mostrarPrecioCompra(Double total) {
        System.out.println("\nTotal de la compra a pagar: $" + total);
    }

    /**
     * Muestra la advertencia cuando no se puede agregar el producto especificado.
     */
    public void mostrarAvisoAgregarArticulo() {
        System.out.println("\n[AVISO] No se pudo agregar el artículo. Verifique el ID o el stock disponible.");
    }

    /**
     * Procesa la cancelación de la compra limpiando los productos del carrito.
     */
    public void clicVaciarCarrito() {
        System.out.print("¿Está seguro de vaciar el carrito? (S/N): ");
        String respuesta = scanner.nextLine().trim();
        if (respuesta.equalsIgnoreCase("S")) {
            control.vaciarCarrito();
            System.out.println("Carrito vaciado.");
            actualizarVista();
        }
    }
}