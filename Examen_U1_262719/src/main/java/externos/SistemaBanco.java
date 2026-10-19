package externos;

/**
 * Servicio de integración con el sistema bancario externo.
 * Encargado de simular la comunicación para la validación y autorización de pagos con tarjeta.
 *
 * @author Alejandra Leal Armenta
 */
public class SistemaBanco {

    /**
     * Constructor por defecto.
     */
    public SistemaBanco() {
    }

    /**
     * Procesa y autoriza la transacción de pago simulada con la entidad bancaria.
     *
     * @return {@code true} si la transacción fue aprobada; {@code false} si fue rechazada (por ejemplo, fondos insuficientes).
     */
    public boolean autorizarPago() {
        return true;
    }
}