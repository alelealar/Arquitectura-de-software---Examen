package dominio;

import ENUMS.EstadoPedido;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Representa la entidad Pedido dentro del sistema de compras.
 * Funciona como el agregador principal que administra la colección de detalles de compra,
 * el cálculo de importes totales, el folio único y la tarjeta utilizada para el pago.
 *
 * @author Alejandra Leal Armenta
 */
public class Pedido {

    private Long id;
    private Double total;
    private EstadoPedido estado;
    private String folio;
    private List<DetallePedido> detalles;
    private Tarjeta tarjeta;

    /**
     * Constructor por defecto. Inicializa la lista interna de detalles.
     */
    public Pedido() {
        this.detalles = new ArrayList<>();
        this.total = 0.0;
    }

    /**
     * Constructor para instanciar un pedido con la lista de detalles y la tarjeta de pago.
     *
     * @param detalles Lista de elementos pertenecientes al pedido.
     * @param tarjeta Instancia de la tarjeta asociada al pago.
     */
    public Pedido(List<DetallePedido> detalles, Tarjeta tarjeta) {
        this.detalles = detalles != null ? detalles : new ArrayList<>();
        this.tarjeta = tarjeta;
        this.total = calcularTotal();
    }

    /**
     * Constructor completo con todos los atributos de la entidad.
     *
     * @param id Identificador único del pedido.
     * @param total Monto total acumulado.
     * @param estado Estado actual del pedido.
     * @param folio Código de identificación único.
     * @param detalles Lista de renglones o artículos.
     * @param tarjeta Tarjeta bancaria utilizada.
     */
    public Pedido(Long id, Double total, EstadoPedido estado, String folio, List<DetallePedido> detalles, Tarjeta tarjeta) {
        this.id = id;
        this.total = total;
        this.estado = estado;
        this.folio = folio;
        this.detalles = detalles != null ? detalles : new ArrayList<>();
        this.tarjeta = tarjeta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public List<DetallePedido> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePedido> detalles) {
        this.detalles = detalles;
        calcularTotal();
    }

    public Tarjeta getTarjeta() {
        return tarjeta;
    }

    public void setTarjeta(Tarjeta tarjeta) {
        this.tarjeta = tarjeta;
    }

    /**
     * Agrega un nuevo artículo al pedido o incrementa su cantidad en caso de que ya se encuentre presente.
     *
     * @param idProducto Identificador del producto a agregar.
     * @param cantidad Cantidad de unidades a sumar.
     */
    public void agregarArticulo(Long idProducto, int cantidad) {
        if (idProducto == null || cantidad <= 0) {
            return;
        }

        for (DetallePedido detalle : detalles) {
            if (detalle.getProducto() != null && detalle.getProducto().getId().equals(idProducto)) {
                detalle.incrementarArticulo(idProducto, cantidad);
                calcularTotal();
                return;
            }
        }
    }

    /**
     * Limpia la lista de detalles y restablece el monto total del pedido a cero.
     */
    public void limpiarPedido() {
        if (this.detalles != null) {
            this.detalles.clear();
        }
        this.total = 0.0;
    }

    /**
     * Verifica si el pedido carece de artículos en su lista de detalles.
     *
     * @return {@code true} si la lista está vacía o no existe; {@code false} en caso contrario.
     */
    public boolean esVacio() {
        return this.detalles == null || this.detalles.isEmpty();
    }

    /**
     * Suma los subtotales de cada detalle asociado para obtener y actualizar el monto total del pedido.
     *
     * @return El monto total recalculado como {@link Double}.
     */
    public Double calcularTotal() {
        double suma = 0.0;
        if (detalles != null) {
            for (DetallePedido d : detalles) {
                if (d.getSubtotal() != null) {
                    suma += d.getSubtotal();
                }
            }
        }
        this.total = suma;
        return this.total;
    }

    /**
     * Cambia el estado del pedido a {@link EstadoPedido#FINALIZADO} tras procesarse exitosamente la transacción.
     */
    public void completarTransaccion() {
        this.estado = EstadoPedido.FINALIZADO;
    }

    /**
     * Inicializa la estructura del pedido asignando la lista de detalles, cambiando el estado a pendiente,
     * generando un folio único y calculando el importe total.
     *
     * @param detalles Lista de detalles que conformarán la compra.
     */
    public void crearPedido(List<DetallePedido> detalles) {
        if (detalles != null) {
            this.detalles = detalles;
        } else {
            this.detalles = new ArrayList<>();
        }
        this.estado = EstadoPedido.PENDIENTE;

        if (this.folio == null || this.folio.isEmpty()) {
            this.folio = "PED-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase();
        }

        calcularTotal();
    }

    /**
     * Obtiene la colección de detalles o artículos ligados al pedido.
     *
     * @return Lista de objetos {@link DetallePedido}.
     */
    public List<DetallePedido> obtenerDetalles() {
        return getDetalles();
    }
}