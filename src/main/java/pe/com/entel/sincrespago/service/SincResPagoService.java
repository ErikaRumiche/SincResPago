package pe.com.entel.sincrespago.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.entel.sincrespago.domain.ContratoBscs;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;
import pe.com.entel.sincrespago.domain.Orden;
import pe.com.entel.sincrespago.domain.SiteVep;
import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.repository.ContratoRepository;
import pe.com.entel.sincrespago.repository.ItemOrdenVepRepository;
import pe.com.entel.sincrespago.repository.SiteVepRepository;

import java.util.*;

/**
 * Created by Erika Rumiche on 22/09/2018.
 */
@Service
public class SincResPagoService {

    public static Logger logger = Logger.getLogger(SincResPagoService.class);

    @Autowired
    private ItemOrdenVepRepository itemOrdenVepRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private SiteVepRepository siteVepRepository;

    private List<ItemOrdenVep> itemOrdenVepList;

    private List<ContratoBscs> contratoBscsList;

    private List<SiteVep> siteVepList;

    private List<Orden> ordenList = new ArrayList<Orden>();

    public void sincronizarResPago()  {
        logger.info("Se inicia carga");

        try{

            truncateTemporales();
            obtenerInfoOrdenVep();
            obtenerInfoBscs();
            obtenerInfoSite();
            llenarEstructura();
            actualizarOvepInsTmp();

        }catch (RepositoryException e){
            logger.error("Error al invocar procedure ",e);
        }catch (Exception e){
            logger.error("Error inesperado",e);
        }

    }

    public void obtenerInfoOrdenVep() throws RepositoryException{

        logger.info("Se obtiene informacion de la orden VEP");
        itemOrdenVepList = itemOrdenVepRepository.obtenerOrdenVep();
        logger.info("Se obtuvo "+itemOrdenVepList.size()+ " items para procesar");

    }

    public void obtenerInfoBscs() throws RepositoryException{

        List<String> simnumberList = new ArrayList<String>();
        for(ItemOrdenVep itemOrdenVep: itemOrdenVepList){
            simnumberList.add(itemOrdenVep.getSimNumber());
        }
        logger.info("Se obtiene informacion de contratos a partir del simnumber de la orden");
        contratoBscsList = contratoRepository.obtenerContrato(simnumberList);
        logger.info("Se obtuvieron  ContratoBSCS: " + contratoBscsList.size() + " para procesar");

    }

    public void obtenerInfoSite() throws RepositoryException{

        List<String> custCodeList = new ArrayList<String>();
        for(ContratoBscs contratoBscs: contratoBscsList){
            custCodeList.add(contratoBscs.getCustCode());
        }
        logger.info("Se obtiene informacion de site a partir de los custcode para asociar a la orden");
        siteVepList = siteVepRepository.obtenerSiteVep(custCodeList);
        logger.info("Se obtuvieron Site: " + siteVepList.size() + " para procesar");

    }

    public void actualizarOvepInsTmp()  throws RepositoryException {

        for (Orden orden : ordenList) {
            logger.debug(orden.toString());
            simnumbers:
            for (String simnumber : orden.getSimnumberList()) {
                String custcode = buscarCustCode(simnumber);

                if (custcode != null) {
                    SiteVep siteVep = buscarSiteId(custcode);
                    if(siteVep != null) {
                        logger.debug("Toca Site : " + siteVep.toString());
                        itemOrdenVepRepository.actualizarOrdenVep(orden.getOrdenId(), siteVep.getSiteId());
                        itemOrdenVepRepository.insertarTemporal(orden.getOrdenId(), orden.getSiteOvepId(), orden.getClienteCrmId(),
                                custcode, siteVep.getSiteId(), siteVep.getEstado());
                    }
                    break simnumbers;
                } else {
                    continue simnumbers;
                }
            }

        }
    }

    /*
        for(SiteVep siteVep:siteVepList){
            for(ContratoBscs contratoBscs:contratoBscsList){
                if(siteVep.getCustCode().equals(contratoBscs.getCustCode())){
                    for(ItemOrdenVep itemOrdenVep:itemOrdenVepList){
                        if(itemOrdenVep.getSimNumber().equals(contratoBscs.getSimNumber())){
                            long siteId = siteVep.getSiteId().longValue();
                            long orderId = itemOrdenVep.getOrdenId().longValue();

                            if(siteVep.getSiteId().longValue() != itemOrdenVep.getSiteId().longValue()) {
                                if (siteVep.getClienteCrmId().longValue() == itemOrdenVep.getClienteCrmId().longValue()) {
                                    orderIdList.add(orderId);

                                    if (itemOrdenVep.getOrdenId().longValue() != orderIdList.get(count)){
                                       // orderIdList.add(orderId);
                                        logger.debug("Resultado{" +
                                                "siteId=" + siteVep.getSiteId().longValue() +
                                                ", siteCustCode='" + siteVep.getCustCode() + '\'' +
                                                ", siteClienteCrmId=" + siteVep.getClienteCrmId().longValue() +
                                                ", siteEstado='" + siteVep.getEstado() + '\'' +
                                                ", orderId=" + itemOrdenVep.getOrdenId().longValue() +
                                                ", orderSiteId=" + itemOrdenVep.getSiteId().longValue() +
                                                ", orderClienteCrmId=" + itemOrdenVep.getClienteCrmId().longValue() +
                                                '}');

                                        itemOrdenVepRepository.actualizarOrdenVep(orderId, siteId);
                                        itemOrdenVepRepository.insertarTemporal(orderId, itemOrdenVep.getSiteId().longValue(),
                                                itemOrdenVep.getClienteCrmId().longValue(), siteVep.getCustCode(),
                                                siteId,siteVep.getEstado());

                                        count++;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }

        logger.info("Cantidad de Ordenes VEP para actualizar: " + count);
    }
    */

    public void truncateTemporales() throws RepositoryException{
        itemOrdenVepRepository.truncateTemporal();
    }

    public String buscarCustCode (String simnumber) throws RepositoryException{

        for(ContratoBscs contratoBscs:contratoBscsList){
            if(contratoBscs.getSimNumber().equals(simnumber)){
                return contratoBscs.getCustCode();
            }
        }

        return null;
    }

    public SiteVep buscarSiteId (String custcode) throws RepositoryException{
        for(SiteVep siteVep:siteVepList) {
            if (siteVep.getCustCode().equals(custcode)) {
                 return siteVep;
            }
        }
        return null;
    }

    public void llenarEstructura() throws RepositoryException{
        for(ItemOrdenVep itemOrdenVep:itemOrdenVepList) {
            Orden orden = buscarOrden(itemOrdenVep.getOrdenId());

            if(orden == null){
                orden = new Orden(itemOrdenVep.getOrdenId(),itemOrdenVep.getClienteCrmId(),itemOrdenVep.getSiteId());
                orden.getSimnumberList().add(itemOrdenVep.getSimNumber());
                ordenList.add(orden);
            }else{
                orden.getSimnumberList().add(itemOrdenVep.getSimNumber());
            }

        }
    }

    public Orden buscarOrden (long ordenId) throws RepositoryException{
        for(Orden orden:ordenList){
            if(orden.getOrdenId().longValue() == ordenId){
                return orden;
            }
        }

        return null;
    }
}
