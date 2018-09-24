package pe.com.entel.sincrespago.domain;

/**
 * Created by Erika Rumiche on 20/09/2018.
 */
public class ItemOrdenVep {

    private long orden_id;
    private long cliente_id;
    private long item_id;
    private long itemdev_id;
    private String simnumber;

    public ItemOrdenVep(){

    }

    public ItemOrdenVep(Long orden_id, Long cliente_id,Long item_id, Long itemdev_id, String simnumber){
        this.orden_id = orden_id.longValue();
        this.cliente_id = cliente_id.longValue();
        this.item_id = item_id.longValue();
        this.itemdev_id = itemdev_id.longValue();
        this.simnumber = simnumber;
    }

    public long getOrden_id() {
        return orden_id;
    }

    public void setOrden_id(long orden_id) {
        this.orden_id = orden_id;
    }

    public long getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(long cliente_id) {
        this.cliente_id = cliente_id;
    }

    public long getItem_id() {
        return item_id;
    }

    public void setItem_id(long item_id) {
        this.item_id = item_id;
    }

    public long getItemdev_id() {
        return itemdev_id;
    }

    public void setItemdev_id(long itemdev_id) {
        this.itemdev_id = itemdev_id;
    }

    public String getSimnumber() {
        return simnumber;
    }

    public void setSimnumber(String simnumber) {
        this.simnumber = simnumber;
    }

    @Override
    public String toString() {
        return "ItemOrdenVep{" +
                "orden_id=" + orden_id +
                ", cliente_id=" + cliente_id +
                ", item_id=" + item_id +
                ", itemdevid=" + itemdev_id +
                ", simnumber='" + simnumber + '\'' +
                '}';
    }
}
