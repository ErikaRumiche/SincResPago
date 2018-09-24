package pe.com.entel.sincrespago.repository;

import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.support.oracle.SqlReturnStructArray;
import org.springframework.data.jdbc.support.oracle.SqlStructArrayValue;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.mapper.ItemOrdenVepMapper;
import pe.com.entel.sincrespago.util.Constants;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Erika Rumiche on 21/09/2018.
 */
@Repository
public class ItemOrdenVepRepository{

    private Logger logger = Logger.getLogger(ItemOrdenVepRepository.class);

    @Autowired
    private DataSource dataSourcePias;

    public List<ItemOrdenVep> obtenerOrdenVep() throws RepositoryException {

        logger.debug("*******Inicio ItemOrdenVepRepository obtenerOrdenVep*******");

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSourcePias);
        jdbcCall.withSchemaName(Constants.WEBSALES);
        jdbcCall.withCatalogName(Constants.PKG_MS_SINC_RESP);
        jdbcCall.withProcedureName("SP_MS_AC_RESP_ORDENCERRADA");

        jdbcCall.addDeclaredParameter(new SqlOutParameter("AT_ITEM_OVEP", Types.ARRAY, "TT_ITEM_OVEP", new SqlReturnStructArray<ItemOrdenVep>(new ItemOrdenVepMapper())));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AVCH_MENSAJE", OracleTypes.VARCHAR));

        MapSqlParameterSource in = new MapSqlParameterSource();
        Map<String, Object> result = jdbcCall.execute(in);
        logger.debug("Se ejecuto procedure");
        logger.debug(result.toString());
        String message = (String) result.get("AVCH_MENSAJE");

        logger.debug("message : " + message);

        if(message != null){
            throw new RepositoryException(message);
        }

        Object[] objects= (Object[])result.get("AT_ITEM_OVEP");

        logger.debug("Tamaño AT_ITEM_OVEP : " + objects.length);
        List<ItemOrdenVep> itemOrdenVepList = new ArrayList<ItemOrdenVep>();
        for(Object object : objects){
            ItemOrdenVep itemOrdenVep = (ItemOrdenVep)object;
            //logger.debug("itemOrdenVep :" + itemOrdenVep.toString());
            itemOrdenVepList.add(itemOrdenVep);
        }

        return itemOrdenVepList;
    }


}
