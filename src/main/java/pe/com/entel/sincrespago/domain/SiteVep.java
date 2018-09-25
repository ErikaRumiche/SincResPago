package pe.com.entel.sincrespago.domain;

/**
 * Created by Admin on 25/09/2018.
 */
public class SiteVep {
    private Long siteId;
    private Long clienteCrmId;
    private String custCode;
    private String estado;

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getClienteCrmId() {
        return clienteCrmId;
    }

    public void setClienteCrmId(Long clienteCrmId) {
        this.clienteCrmId = clienteCrmId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

    @Override
    public String toString() {
        return "SiteVep{" +
                "siteId=" + siteId +
                ", clienteCrmId=" + clienteCrmId +
                ", custCode='" + custCode + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
