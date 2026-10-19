package dominio;

/**
 * Representa el desglose o línea individual de un producto dentro de un pedido.
 * Mantiene la cantidad, el precio unitario pactado al momento de la compra
 * y el cálculo correspondiente del subtotal.
 *
 * @author Alejandra Leal Armenta
 */
public class DetallePedido {

    private Long id;
    private int cantidad;
    private Double precioUnitario;
    private Double subtotal;
    private Producto producto;

    /**
     * Constructor por defecto. Inicializa los valores numéricos en cero.
     */
    public DetallePedido() {
        this.cantidad = 0;
        this.precioUnitario = 0.0;
        this.subtotal = 0.0;
    }

    /**
     * Constructor completo para instanciar un detalle con todos sus campos.
     *
     * @param id Identificador único del detalle.
     * @param cantidad Cantidad de unidades solicitadas.
     * @param precioUnitario Precio unitario del producto.
     * @param subtotal Monto subtotal acumulado.
     * @param producto Instancia del producto asociado.
     */
    public DetallePedido(Long id, int cantidad, Double precioUnitario, Double subtotal, Producto producto) {
        this.id = id;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
        this.producto = producto;
    }

    /**
     * Constructor para inicializar un detalle a partir de un producto y una cantidad,
     * calculando automáticamente el precio unitario y el subtotal.
     *
     * @param producto Producto a agregar.
     * @param cantidad Cantidad requerida.
     */
    public DetallePedido(Producto producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
        if (producto != null) {
            this.precioUnitario = producto.getPrecio();
        } else {
            this.precioUnitario = 0.0;
        }
        recalcularSubtotal();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
        recalcularSubtotal();
    }

    public Double getPrecioUnitario() {
        return precioUnitario;
    }

    public void setPrecioUnitario(Double precioUnitario) {
        this.precioUnitario = precioUnitario;
        recalcularSubtotal();
    }

    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
        if (producto != null) {
            this.precioUnitario = producto.getPrecio();
            recalcularSubtotal();
        }
    }

    /**
     * Incrementa la cantidad de unidades para el producto especificado y recalcula el subtotal.
     *
     * @param idProducto Identificador del producto a verificar.
     * @param cantidad Cantidad de unidades adicionales a sumar.
     */
    public void incrementarArticulo(Long idProducto, int cantidad) {
        if (this.producto != null && this.producto.getId().equals(idProducto)) {
            this.cantidad += cantidad;
            recalcularSubtotal();
        }
    }

    /**
     * Reinicia los atributos del detalle, eliminando la referencia al producto y restableciendo
     * la cantidad, el precio unitario y el subtotal a cero.
     */
    public void eliminarDetalles() {
        this.producto = null;
        this.cantidad = 0;
        this.precioUnitario = 0.0;
        this.subtotal = 0.0;
    }

    /**
     * Calcula el subtotal multiplicando la cantidad actual por el precio unitario.
     */
    private void recalcularSubtotal() {
        if (this.precioUnitario != null) {
            this.subtotal = this.cantidad * this.precioUnitario;
        } else {
            this.subtotal = 0.0;
        }
    }
}