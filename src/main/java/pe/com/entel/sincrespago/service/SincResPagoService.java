package pe.com.entel.sincrespago.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pe.com.entel.sincrespago.domain.*;
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
        logger.info("************ Se inicia sincronizacion ************");
        try{

            truncateTemporales();
            obtenerInfoOrdenVep();
            obtenerInfoBscs();
            obtenerInfoSite();
            llenarEstructura();
            actualizarOvepInsTmp();
            logger.info("************Se finaliza sincronizacion ************");

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
        long inicio = System.currentTimeMillis();
        long fin= 0;
        int contador = 0;
        logger.info("Se van a procesar "+ ordenList.size() + " ordenes");
        for (Orden orden : ordenList) {

            simnumbers:
            for (String simnumber : orden.getSimnumberList()) {
                String custcode = buscarCustCode(simnumber);

                if (custcode != null) {
                    SiteVep siteVep = buscarSiteId(custcode);
                    if(siteVep != null) {
                        if(orden.getSiteOvepId().longValue()!= siteVep.getSiteId().longValue()) {
                            logger.debug(orden.toString());
                            logger.debug("Se va actualizar: "+ orden.getOrdenId()+ " - " + siteVep.getSiteId());
                            itemOrdenVepRepository.actualizarOrdenVep(orden.getOrdenId(), siteVep.getSiteId());
                            TmpSincResPago tmpSincResPago = new TmpSincResPago();
                            tmpSincResPago.setOrderId(orden.getOrdenId());
                            tmpSincResPago.setOrderSiteId(orden.getSiteOvepId());
                            tmpSincResPago.setClienteCrmId(orden.getClienteCrmId());
                            tmpSincResPago.setOvepId(orden.getOvepId());
                            tmpSincResPago.setCustcode(custcode);
                            tmpSincResPago.setSiteId(siteVep.getSiteId());
                            tmpSincResPago.setSiteEstado(siteVep.getEstado());
                            logger.debug("Se va insertar temporal: " + tmpSincResPago.toString());
                            itemOrdenVepRepository.insertarTemporal(tmpSincResPago);
                            contador++;
                        }

                    }
                    break simnumbers;
                } else {
                    continue simnumbers;
                }
            }
        }
        fin = System.currentTimeMillis();
        logger.info("Se actualizaron "+ contador + " ordenes, tiempo de ejecucion "+ (fin-inicio)+ " ms");
    }

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
            Orden orden = buscarOrden(itemOrdenVep.getOrdenId().longValue());

            if(orden == null){
                orden = new Orden(itemOrdenVep.getOrdenId().longValue(),itemOrdenVep.getClienteCrmId().longValue(),itemOrdenVep.getSiteId().longValue(),itemOrdenVep.getOvepId().longValue());
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
