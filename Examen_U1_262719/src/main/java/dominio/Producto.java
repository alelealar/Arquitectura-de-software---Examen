package dominio;

import ENUMS.Categoria;
import ENUMS.UnidadMedida;
import persistencia.BaseDatosSimulada;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa un producto dentro del catálogo del sistema de ventas.
 * Contiene los datos descriptivos, de inventario, precio y categoría,
 * así como métodos de consulta para recuperar el catálogo general o filtrado.
 *
 * @author Alejandra Leal Armenta
 */
public class Producto {

    private Long id;
    private String nombre;
    private String descripcion;
    private UnidadMedida unidadMedida;
    private Categoria categoria;
    private boolean disponibilidad;
    private String imagen;
    private int stock;
    private Double precio;

    /**
     * Constructor por defecto.
     */
    public Producto() {
    }

    /**
     * Constructor completo que incluye todos los atributos del producto, incluida la imagen.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre comercial del producto.
     * @param descripcion Descripción detallada.
     * @param unidadMedida Unidad de medida asignada.
     * @param categoria Categoría a la que pertenece.
     * @param disponibilidad Estado de disponibilidad para la venta.
     * @param imagen Ruta o identificador de la imagen.
     * @param stock Cantidad de unidades disponibles en inventario.
     * @param precio Precio unitario de venta.
     */
    public Producto(Long id, String nombre, String descripcion, UnidadMedida unidadMedida, Categoria categoria, boolean disponibilidad, String imagen, int stock, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
        this.imagen = imagen;
        this.stock = stock;
        this.precio = precio;
    }

    /**
     * Constructor alternativo sin la ruta de la imagen.
     *
     * @param id Identificador único del producto.
     * @param nombre Nombre comercial del producto.
     * @param descripcion Descripción detallada.
     * @param unidadMedida Unidad de medida asignada.
     * @param categoria Categoría a la que pertenece.
     * @param disponibilidad Estado de disponibilidad para la venta.
     * @param stock Cantidad de unidades disponibles en inventario.
     * @param precio Precio unitario de venta.
     */
    public Producto(Long id, String nombre, String descripcion, UnidadMedida unidadMedida, Categoria categoria, boolean disponibilidad, int stock, Double precio) {
        this.id = id;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.unidadMedida = unidadMedida;
        this.categoria = categoria;
        this.disponibilidad = disponibilidad;
        this.stock = stock;
        this.precio = precio;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(UnidadMedida unidadMedida) {
        this.unidadMedida = unidadMedida;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public boolean isDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(boolean disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Consulta y obtiene el listado completo de productos registrados.
     *
     * @return Lista de objetos {@link Producto}.
     */
    public List<Producto> obtenerProductos() {
        return BaseDatosSimulada.getProductos();
    }

    /**
     * Consulta y filtra el catálogo de productos según la categoría proporcionada.
     *
     * @param filtro Categoría por la cual se realizará la selección.
     * @return Lista de {@link Producto} filtrados.
     */
    public List<Producto> obtenerProductosFiltrados(Categoria filtro) {
        List<Producto> productosFiltrados = new ArrayList<>();
        if (filtro != null) {
            for (Producto p : BaseDatosSimulada.getProductos()) {
                if (p.getCategoria() != null && p.getCategoria().equals(filtro)) {
                    productosFiltrados.add(p);
                }
            }
        }
        return productosFiltrados;
    }
}