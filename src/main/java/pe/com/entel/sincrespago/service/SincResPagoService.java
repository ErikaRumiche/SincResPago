package pe.com.entel.sincrespago.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.entel.sincrespago.domain.ContratoBscs;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;
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

    public void sincronizarResPago()  {
        logger.info("Se inicia carga");

        try{
            logger.info("Se obtiene informacion de la orden VEP");
            List<ItemOrdenVep> itemOrdenVepList = itemOrdenVepRepository.obtenerOrdenVep();
            logger.info("Se obtuvo "+itemOrdenVepList.size()+ " items para procesar");

            List<String> simnumberList = new ArrayList<String>();

            for(ItemOrdenVep itemOrdenVep: itemOrdenVepList){
                simnumberList.add(itemOrdenVep.getSimNumber());
            }

            logger.info("Se obtiene informacion de contratos a partir del simnumber de la orden");
            List<ContratoBscs> contratoBscsList = contratoRepository.obtenerContrato(simnumberList);
            logger.info("Se obtuvieron  ContratoBSCS: " + contratoBscsList.size() + " para procesar");

            List<String> custCodeList = new ArrayList<String>();

            for(ContratoBscs contratoBscs: contratoBscsList){
                custCodeList.add(contratoBscs.getCustCode());
            }

            logger.info("Se obtiene informacion de site a partir de los custcode para asociar a la orden");
            List<SiteVep> siteVepList = siteVepRepository.obtenerSiteVep(custCodeList);
            logger.info("Se obtuvieron Site: " + siteVepList.size() + " para procesar");

            logger.info("Buscar en listas");
            int countUpd = actualizarOvep(itemOrdenVepList, contratoBscsList, siteVepList);
            logger.info("Cantidad de Ordenes VEP actualizadas: " + countUpd);


        }catch (RepositoryException e){
            logger.error("Error al invocar procedure ",e);
        }catch (Exception e){
            logger.error("Error inesperado",e);
        }

    }

    public int actualizarOvep( List<ItemOrdenVep> itemOrdenVepList,
                               List<ContratoBscs> contratoBscsList,
                               List<SiteVep> siteVepList )  throws RepositoryException {
        int count = 0;

        List<Long> orderIdList = new ArrayList<Long>();

        for(SiteVep siteVep:siteVepList){
            for(ContratoBscs contratoBscs:contratoBscsList){
                if(siteVep.getCustCode().equals(contratoBscs.getCustCode())){
                    for(ItemOrdenVep itemOrdenVep:itemOrdenVepList){
                        if(itemOrdenVep.getSimNumber().equals(contratoBscs.getSimNumber())){
                            long siteId = siteVep.getSiteId().longValue();
                            long orderId = itemOrdenVep.getOrdenId().longValue();

                            if(siteVep.getSiteId().longValue() != itemOrdenVep.getSiteId().longValue()) {
                                if (siteVep.getClienteCrmId().longValue() == itemOrdenVep.getClienteCrmId().longValue()) {
                                    if(count == 0){
                                        orderIdList.add(orderId);
                                        count++;
                                        logger.debug("Resultado{" +
                                                "siteId=" + siteVep.getSiteId().longValue() +
                                                ", siteCustCode='" + siteVep.getCustCode() + '\'' +
                                                ", siteClienteCrmId=" + siteVep.getClienteCrmId().longValue() +
                                                ", siteEstado='" + siteVep.getEstado() + '\'' +
                                                ", orderId=" + itemOrdenVep.getOrdenId().longValue() +
                                                ", orderSiteId=" + itemOrdenVep.getSiteId().longValue() +
                                                ", orderClienteCrmId=" + itemOrdenVep.getClienteCrmId().longValue() +
                                                '}');
                                        logger.info("Ordenes VEP para actualizar: " + orderIdList.size());
                                        itemOrdenVepRepository.actualizarOrdenVep(orderId, siteId);
                                    } else {
                                        for (Long objOrderId : orderIdList) {
                                            if (itemOrdenVep.getOrdenId().longValue() != objOrderId.longValue()) {
                                                orderIdList.add(orderId);
                                                count++;
                                                logger.debug("Resultado{" +
                                                        "siteId=" + siteVep.getSiteId().longValue() +
                                                        ", siteCustCode='" + siteVep.getCustCode() + '\'' +
                                                        ", siteClienteCrmId=" + siteVep.getClienteCrmId().longValue() +
                                                        ", siteEstado='" + siteVep.getEstado() + '\'' +
                                                        ", orderId=" + itemOrdenVep.getOrdenId().longValue() +
                                                        ", orderSiteId=" + itemOrdenVep.getSiteId().longValue() +
                                                        ", orderClienteCrmId=" + itemOrdenVep.getClienteCrmId().longValue() +
                                                        '}');
                                                logger.info("Ordenes VEP para actualizar: " + orderIdList.size());
                                                itemOrdenVepRepository.actualizarOrdenVep(orderId, siteId);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return count;
    }

}
