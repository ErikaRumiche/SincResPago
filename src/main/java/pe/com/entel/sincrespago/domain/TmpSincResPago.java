package pe.com.entel.sincrespago.domain;

/**
 * Created by Erika Rumiche on 2/10/2018.
 */
public class TmpSincResPago {

    private Long orderId;
    private Long orderSiteId;
    private Long clienteCrmId;
    private Long ovepId;
    private String custcode;
    private Long siteId;
    private String siteEstado;

    @Override
    public String toString() {
        return "TmpSincResPago{" +
                "orderId=" + orderId +
                ", orderSiteId=" + orderSiteId +
                ", clienteCrmId=" + clienteCrmId +
                ", ovepId=" + ovepId +
                ", custcode='" + custcode + '\'' +
                ", siteId=" + siteId +
                ", siteEstado='" + siteEstado + '\'' +
                '}';
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getOrderSiteId() {
        return orderSiteId;
    }

    public void setOrderSiteId(Long orderSiteId) {
        this.orderSiteId = orderSiteId;
    }

    public Long getClienteCrmId() {
        return clienteCrmId;
    }

    public void setClienteCrmId(Long clienteCrmId) {
        this.clienteCrmId = clienteCrmId;
    }

    public Long getOvepId() {
        return ovepId;
    }

    public void setOvepId(Long ovepId) {
        this.ovepId = ovepId;
    }

    public String getCustcode() {
        return custcode;
    }

    public void setCustcode(String custcode) {
        this.custcode = custcode;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteEstado() {
        return siteEstado;
    }

    public void setSiteEstado(String siteEstado) {
        this.siteEstado = siteEstado;
    }
}
