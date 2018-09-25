package pe.com.entel.sincrespago.domain;

/**
 * Created by Erika Rumiche on 20/09/2018.
 */
public class ItemOrdenVep {

    private Long ordenId;
    private Long clienteCrmId;
    private Long siteId;
    private String simNumber;

    public ItemOrdenVep(){

    }

    public ItemOrdenVep(Long ordenId, Long clienteCrmId, Long siteId, String simNumber) {
        this.ordenId = ordenId;
        this.clienteCrmId = clienteCrmId;
        this.siteId = siteId;
        this.simNumber = simNumber;
    }

    public Long getOrdenId() {
        return ordenId;
    }

    public void setOrdenId(Long ordenId) {
        this.ordenId = ordenId;
    }

    public Long getClienteCrmId() {
        return clienteCrmId;
    }

    public void setClienteCrmId(Long clienteCrmId) {
        this.clienteCrmId = clienteCrmId;
    }

    public Long getSiteId() {
        return siteId;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    @Override
    public String toString() {
        return "ItemOrdenVep{" +
                "ordenId=" + ordenId +
                ", clienteCrmId=" + clienteCrmId +
                ", siteId=" + siteId +
                ", simNumber='" + simNumber + '\'' +
                '}';
    }
}
