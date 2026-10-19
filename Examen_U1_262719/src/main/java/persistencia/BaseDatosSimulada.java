package persistencia;

import ENUMS.Categoria;
import ENUMS.UnidadMedida;
import dominio.Pedido;
import dominio.Producto;
import java.util.ArrayList;
import java.util.List;

/**
 * Simula el comportamiento de una base de datos en memoria
 * almacenando colecciones de productos y pedidos.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class BaseDatosSimulada {

    private static List<Producto> listaProductos = new ArrayList<>();
    private static List<Pedido> listaPedidos = new ArrayList<>();

    static {
        inicializarDatos();
    }

    /**
     * Carga registros iniciales de prueba para el catálogo de productos.
     */
    private static void inicializarDatos() {
        // Frutas y Verduras
        listaProductos.add(new Producto(1L, "Manzana Roja", "Manzana fresca por kilo", UnidadMedida.KG, Categoria.FRUTAS_VERDURAS, true, 50, 45.00));
        listaProductos.add(new Producto(2L, "Plátano Tabasco", "Plátano maduro de alta calidad", UnidadMedida.KG, Categoria.FRUTAS_VERDURAS, true, 40, 25.50));
        listaProductos.add(new Producto(3L, "Zanahoria", "Zanahoria fresca de manojo", UnidadMedida.KG, Categoria.FRUTAS_VERDURAS, true, 35, 18.00));

        // Lácteos
        listaProductos.add(new Producto(4L, "Leche Entera", "Litro de leche de vaca", UnidadMedida.L, Categoria.LACTEOS, true, 30, 27.00));
        listaProductos.add(new Producto(5L, "Queso Panela", "Queso panela fresco de 400g", UnidadMedida.GR, Categoria.LACTEOS, true, 25, 65.00));
        listaProductos.add(new Producto(6L, "Yogurt Natural", "Yogurt batido natural 1L", UnidadMedida.L, Categoria.LACTEOS, true, 20, 38.00));

        // Panadería
        listaProductos.add(new Producto(7L, "Pan de Caja", "Pan blanco para sándwich", UnidadMedida.UNIDAD, Categoria.PANADERIA, true, 20, 42.00));
        listaProductos.add(new Producto(8L, "Donas Glaseadas", "Paquete con docena de donas", UnidadMedida.DOCENA, Categoria.PANADERIA, true, 15, 120.00));

        // Carnes
        listaProductos.add(new Producto(9L, "Pechuga de Pollo", "Pechuga de pollo deshuesada sin piel", UnidadMedida.KG, Categoria.CARNES, true, 18, 110.00));
        listaProductos.add(new Producto(10L, "Carne Molida de Res", "Carne molida 90/10", UnidadMedida.KG, Categoria.CARNES, true, 22, 160.00));

        // Especias
        listaProductos.add(new Producto(11L, "Pimienta Negra", "Pimienta negra molida 100g", UnidadMedida.GR, Categoria.ESPECIAS, true, 40, 32.00));
        listaProductos.add(new Producto(12L, "Orégano Seco", "Bolsa de orégano seco entero 50g", UnidadMedida.GR, Categoria.ESPECIAS, true, 30, 15.00));

        // Despensa
        listaProductos.add(new Producto(13L, "Aceite Vegetal", "Botella de aceite comestible 800ml", UnidadMedida.ML, Categoria.DESPENSA, true, 28, 50.00));
        listaProductos.add(new Producto(14L, "Arroz Blanco", "Bolsa de arroz súper extra 1kg", UnidadMedida.KG, Categoria.DESPENSA, true, 50, 24.00));
        listaProductos.add(new Producto(15L, "Frijol Negro", "Bolsa de frijol negro seleccionado 1kg", UnidadMedida.KG, Categoria.DESPENSA, true, 45, 36.00));
    }

    /**
     * Obtiene la lista completa de productos almacenados.
     *
     * @return Lista de objetos {@link Producto}.
     */
    public static List<Producto> getProductos() {
        return listaProductos;
    }

    /**
     * Registra un nuevo producto en la colección simulada.
     *
     * @param producto Instancia de Producto a agregar.
     */
    public static void agregarProducto(Producto producto) {
        listaProductos.add(producto);
    }

    /**
     * Obtiene la lista de pedidos registrados.
     *
     * @return Lista de objetos {@link Pedido}.
     */
    public static List<Pedido> getPedidos() {
        return listaPedidos;
    }

    /**
     * Guarda un nuevo pedido en la base de datos simulada.
     *
     * @param pedido Instancia del pedido a registrar.
     */
    public static void guardarPedido(Pedido pedido) {
        listaPedidos.add(pedido);
    }

    /**
     * Busca y retorna un producto a partir de su identificador único.
     *
     * @param id Identificador del producto a buscar.
     * @return Objeto {@link Producto} si se encuentra; {@code null} en caso contrario.
     */
    public static Producto buscarPorId(Long id) {
        if (id == null) return null;
        for (Producto p : listaProductos) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }
}