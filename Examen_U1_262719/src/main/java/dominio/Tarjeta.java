package dominio;

/**
 * Clase que representa la información de la tarjeta bancaria del usuario.
 * Permite validar sus datos y obtener la información del banco emisor y ciudad.
 *
 * @author Alejandra Leal Armenta, 262719
 */
public class Tarjeta {

    private Long id;
    private String numeroTarjeta;
    private String bancoEmisor;
    private String ciudad;

    /**
     * Constructor por defecto de Tarjeta.
     */
    public Tarjeta() {
    }

    /**
     * Constructor completo de Tarjeta.
     *
     * @param id Identificador único de la tarjeta en el sistema.
     * @param numeroTarjeta Número de la tarjeta bancaria.
     * @param bancoEmisor Nombre del banco que emite la tarjeta.
     * @param ciudad Ciudad de emisión o del titular.
     */
    public Tarjeta(Long id, String numeroTarjeta, String bancoEmisor, String ciudad) {
        this.id = id;
        this.numeroTarjeta = numeroTarjeta;
        this.bancoEmisor = bancoEmisor;
        this.ciudad = ciudad;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getBancoEmisor() {
        return bancoEmisor;
    }

    public void setBancoEmisor(String bancoEmisor) {
        this.bancoEmisor = bancoEmisor;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    /**
     * Obtiene los detalles de emisor y ciudad a partir del número de tarjeta.
     *
     * @param numeroTarjeta Número de tarjeta ingresado.
     * @return Instancia de Tarjeta con los datos del emisor y ciudad cargados.
     */
    public Tarjeta obtenerDetallesEmisor(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
        this.bancoEmisor = "BBVA México";
        this.ciudad = "Ciudad Obregón";
        return this;
    }
}