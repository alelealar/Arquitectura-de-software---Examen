package control;

import ENUMS.Categoria;
import dominio.DetallePedido;
import dominio.Pedido;
import dominio.Producto;
import dominio.Tarjeta;
import externos.SistemaBanco;
import persistencia.BaseDatosSimulada;

import java.util.ArrayList;
import java.util.List;

/**
 * Controlador que orquesta la lógica de negocio para el caso de uso Comprar Producto.
 * Administra el estado del carrito de compras y desacopla la vista del modelo.
 * Contiene exactamente los métodos especificados en el diagrama de clases de diseño.
 *
 * @author Alejandra Leal Armenta
 */
public class ControlCompra {

    private List<DetallePedido> carrito;
    private SistemaBanco sistemaBanco;

    /**
     * Constructor por defecto. Inicializa el carrito en memoria y el servicio bancario.
     */
    public ControlCompra() {
        this.carrito = new ArrayList<>();
        this.sistemaBanco = new SistemaBanco();
    }

    public List<DetallePedido> getCarrito() {
        return carrito;
    }

    public void setCarrito(List<DetallePedido> carrito) {
        this.carrito = carrito;
    }

    public SistemaBanco getSistemaBanco() {
        return sistemaBanco;
    }

    public void setSistemaBanco(SistemaBanco sistemaBanco) {
        this.sistemaBanco = sistemaBanco;
    }

    /**
     * Obtiene el catálogo de productos formateado como lista de cadenas.
     *
     * @return Lista de cadenas con la información de los productos.
     */
    public List<String> obtenerProductos() {
        List<String> catalogo = new ArrayList<>();
        Producto p = new Producto();
        for (Producto prod : p.obtenerProductos()) {
            catalogo.add("ID: " + prod.getId() + " | " + prod.getNombre() + " ($" + prod.getPrecio() + ") - Stock: " + prod.getStock());
        }
        return catalogo;
    }

    /**
     * Agrega un producto al carrito de compras o incrementa su cantidad.
     *
     * @param idProducto Identificador único del producto a agregar.
     * @param cantidad Cantidad de unidades solicitadas.
     * @return {@code true} si el producto se agregó correctamente; {@code false} en caso contrario.
     */
    public boolean agregarArticulo(Long idProducto, int cantidad) {
        if (cantidad <= 0 || idProducto == null) {
            return false;
        }

        Producto productoEncontrado = BaseDatosSimulada.buscarPorId(idProducto);

        if (productoEncontrado == null || !productoEncontrado.isDisponibilidad() || productoEncontrado.getStock() < cantidad) {
            return false;
        }

        for (DetallePedido detalle : carrito) {
            if (detalle.getProducto() != null && detalle.getProducto().getId().equals(idProducto)) {
                detalle.incrementarArticulo(idProducto, cantidad);
                return true;
            }
        }

        DetallePedido nuevoDetalle = new DetallePedido(productoEncontrado, cantidad);
        this.carrito.add(nuevoDetalle);
        return true;
    }

    /**
     * Obtiene los productos filtrados por categoría formateados como lista de cadenas.
     *
     * @param filtro Categoría por la cual se filtrará el catálogo.
     * @return Lista de cadenas con los productos filtrados.
     */
    public List<String> obtenerProductosFiltrados(Categoria filtro) {
        List<String> filtrados = new ArrayList<>();
        Producto p = new Producto();
        for (Producto prod : p.obtenerProductosFiltrados(filtro)) {
            filtrados.add("ID: " + prod.getId() + " | " + prod.getNombre() + " ($" + prod.getPrecio() + ") - Stock: " + prod.getStock());
        }
        return filtrados;
    }

    /**
     * Limpia y vacía todos los artículos del carrito de compras.
     *
     * @return {@code true} al finalizar la limpieza.
     */
    public boolean vaciarCarrito() {
        this.carrito.clear();
        return true;
    }

    /**
     * Obtiene el desglose textual de los artículos contenidos en el carrito de compras.
     *
     * @return Lista de cadenas con el detalle de los artículos.
     */
    public List<String> obtenerDetalles() {
        List<String> resumen = new ArrayList<>();
        for (DetallePedido detalle : carrito) {
            if (detalle.getProducto() != null) {
                resumen.add("- " + detalle.getProducto().getNombre() + " x" + detalle.getCantidad() + " = $" + detalle.getSubtotal());
            }
        }
        return resumen;
    }

    /**
     * Consulta los detalles de emisor y ciudad del banco para un número de tarjeta.
     *
     * @param numeroTarjeta Cadena con el número de la tarjeta bancaria.
     * @return Cadena formateada con la confirmación del banco emisor y la ciudad.
     */
    public String solicitarDatosTarjeta(String numeroTarjeta) {
        Tarjeta tarjeta = new Tarjeta();
        Tarjeta procesada = tarjeta.obtenerDetallesEmisor(numeroTarjeta);
        if (procesada != null) {
            return "Banco: " + procesada.getBancoEmisor() + " | Ciudad: " + procesada.getCiudad();
        }
        return null;
    }

    /**
     * Valida la transacción de pago con el banco, registra el pedido,
     * descuenta el stock en la base de datos y vacía el carrito si es exitoso.
     *
     * @return {@code true} si la transacción fue autorizada; {@code false} en caso contrario.
     */
    public boolean validarPago() {
        if (esVacio()) {
            return false;
        }

        boolean autorizado = sistemaBanco.autorizarPago();

        if (autorizado) {
            Pedido nuevoPedido = new Pedido();
            nuevoPedido.crearPedido(new ArrayList<>(this.carrito));

            for (DetallePedido detalle : this.carrito) {
                Producto prodEnCarrito = detalle.getProducto();
                if (prodEnCarrito != null) {
                    Producto prodBD = BaseDatosSimulada.buscarPorId(prodEnCarrito.getId());
                    if (prodBD != null) {
                        int nuevoStock = prodBD.getStock() - detalle.getCantidad();
                        prodBD.setStock(Math.max(0, nuevoStock));
                    }
                }
            }

            BaseDatosSimulada.guardarPedido(nuevoPedido);
            vaciarCarrito();
        }

        return autorizado;
    }

    /**
     * Inicia la verificación para proceder al pago del carrito actual.
     *
     * @return {@code true} si el carrito contiene productos; {@code false} si está vacío.
     */
    public boolean procederPago() {
        return !esVacio();
    }

    /**
     * Verifica si el carrito de compras carece de elementos.
     *
     * @return {@code true} si el carrito está vacío; {@code false} en caso contrario.
     */
    public boolean esVacio() {
        return this.carrito.isEmpty();
    }
}