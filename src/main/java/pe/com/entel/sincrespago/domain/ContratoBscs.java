package pe.com.entel.sincrespago.domain;

/**
 * Created by Erika Rumiche on 23/09/2018.
 */
public class ContratoBscs {

    public Long clienteBscsId;
    public String simNumber;
    public Long contratoId;
    public String custCode;

    public ContratoBscs(){

    }

    public ContratoBscs(Long clienteBscsId, String simNumber, Long contratoId, String custCode) {
        this.clienteBscsId = clienteBscsId;
        this.simNumber = simNumber;
        this.contratoId = contratoId;
        this.custCode = custCode;
    }

    public Long getClienteBscsId() {
        return clienteBscsId;
    }

    public void setClienteBscsId(Long clienteBscsId) {
        this.clienteBscsId = clienteBscsId;
    }

    public String getSimNumber() {
        return simNumber;
    }

    public void setSimNumber(String simNumber) {
        this.simNumber = simNumber;
    }

    public Long getContratoId() {
        return contratoId;
    }

    public void setContratoId(Long contratoId) {
        this.contratoId = contratoId;
    }

    public String getCustCode() {
        return custCode;
    }

    public void setCustCode(String custCode) {
        this.custCode = custCode;
    }

    @Override
    public String toString() {
        return "ContratoBscs{" +
                "clienteBscsId=" + clienteBscsId +
                ", simNumber='" + simNumber + '\'' +
                ", contratoId=" + contratoId +
                ", custCode='" + custCode + '\'' +
                '}';
    }
}
