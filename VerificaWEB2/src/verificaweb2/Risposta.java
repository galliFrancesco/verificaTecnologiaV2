package verificaweb2;

/**
 *
 * @author galliFrancesco
 *
 * @brief Prende le cose dal JSON e le trasmuta in un oggetto per non impazzire
 */
public class Risposta {

    Boolean stato;
    String dato;

    public Risposta() {

    }

    // SET
    /**
     * TRUE -> Fai le cose FALSE -> Non fare cose
     */
    public void setStato(boolean stato) {
        this.stato = stato;
    }

    public void setDato(String dato) {
        this.dato = dato;
    }

    // GET
    public boolean getStato() {
        return stato;
    }

    public String getDato() {
        return dato;
    }
}
