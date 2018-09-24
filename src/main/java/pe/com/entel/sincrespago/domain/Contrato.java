package pe.com.entel.sincrespago.domain;

/**
 * Created by Erika Rumiche on 23/09/2018.
 */
public class Contrato {

    public Long cliente_id;
    public String simnumber;
    public Long contrato_id;
    public String cust_code;

    public Contrato(){

    }

    public Contrato(Long cliente_id, String simnumber, Long contrato_id, String cust_code) {
        this.cliente_id = cliente_id;
        this.simnumber = simnumber;
        this.contrato_id = contrato_id;
        this.cust_code = cust_code;
    }

    public Long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(Long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public String getSimnumber() {
        return simnumber;
    }

    public void setSimnumber(String simnumber) {
        this.simnumber = simnumber;
    }

    public Long getContrato_id() {
        return contrato_id;
    }

    public void setContrato_id(Long contrato_id) {
        this.contrato_id = contrato_id;
    }

    public String getCust_code() {
        return cust_code;
    }

    public void setCust_code(String cust_code) {
        this.cust_code = cust_code;
    }

    @Override
    public String toString() {
        return "Contrato{" +
                "cliente_id=" + cliente_id +
                ", simnumber='" + simnumber + '\'' +
                ", contrato_id=" + contrato_id +
                ", cust_code='" + cust_code + '\'' +
                '}';
    }
}
