package pe.com.entel.sincrespago.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import pe.com.entel.sincrespago.domain.Contrato;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;
import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.repository.ContratoRepository;
import pe.com.entel.sincrespago.repository.ItemOrdenVepRepository;

import javax.sql.DataSource;
import java.sql.SQLException;
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

    public void sincronizarInfo()  {
        logger.info("Se inicia carga");

        try{
            logger.info("Se obtiene informacion de la orden VEP");
            List<ItemOrdenVep> itemOrdenVepList = itemOrdenVepRepository.obtenerOrdenVep();
            logger.info("Se obtuvo "+itemOrdenVepList.size()+ " items para procesar");

            List<String> simnumberList = new ArrayList<String>();

            for(ItemOrdenVep itemOrdenVep: itemOrdenVepList){
                simnumberList.add(itemOrdenVep.getSimnumber());
            }

            logger.info("Se obtiene informacion de contratos a partir del simnumber de la orden");
            List<Contrato> contratoList = contratoRepository.obtenerContrato(simnumberList);
            logger.info("Se obtuvieron  " +contratoList.size() + " para procesar");
        }catch (RepositoryException e){
            logger.error("Error al invocar procedure ",e);
        }catch (Exception e){
            logger.error("Error inesperado",e);
        }

    }

}
