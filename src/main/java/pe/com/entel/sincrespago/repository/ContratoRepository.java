package pe.com.entel.sincrespago.repository;

import oracle.jdbc.OracleTypes;
import oracle.sql.STRUCT;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.data.jdbc.support.oracle.SqlReturnStructArray;
import org.springframework.data.jdbc.support.oracle.SqlStructArrayValue;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.com.entel.sincrespago.domain.Contrato;
import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.mapper.ContratoMapper;
import pe.com.entel.sincrespago.util.Constants;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Erika Rumiche on 23/09/2018.
 */
@Repository
public class ContratoRepository {

    private Logger logger = Logger.getLogger(ContratoRepository.class);

    @Autowired
    private DataSource dataSourcePbscs;

    public List<Contrato> obtenerContrato(List<String> simnumberList) throws RepositoryException {


        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSourcePbscs);
        jdbcCall.withSchemaName(Constants.APIBSCS);
        jdbcCall.withCatalogName(Constants.PKG_MS_SINC_RESP);
        jdbcCall.withProcedureName("SP_MS_AC_CUSTCODE_BY_SIM");

        //jdbcCall.addDeclaredParameter(new SqlParameter("AT_ITEM_OVEP", Types.ARRAY, "TT_ITEM_OVEP"));
        jdbcCall.addDeclaredParameter(new SqlParameter("AT_SIMNUMBER_OVEP", Types.ARRAY, "APIBSCS.TT_SIMNUMBER_OVEP"));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AT_CONTRATO_OVEP",Types.ARRAY, "APIBSCS.TT_CONTRATO_OVEP", new SqlReturnStructArray<Contrato>(new ContratoMapper())));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AVCH_MENSAJE", OracleTypes.VARCHAR));

       //ItemOrdenVep[] itemOrdenVepsArray = itemOrdenVepList.toArray(new ItemOrdenVep[itemOrdenVepList.size()]);
        String[] simNumbersArray = simnumberList.toArray(new String[simnumberList.size()]);
        for(String simnumber: simnumberList){
            logger.debug("simnumber:" + simnumber.toString());
        }
        MapSqlParameterSource in = new MapSqlParameterSource();
        //in.addValue("AT_ITEM_OVEP", new SqlStructArrayValue<ItemOrdenVep>(itemOrdenVepsArray, new ItemOrdenVepMapper(), "APIBSCS.TO_ITEM_OVEP"));
        in.addValue("AT_SIMNUMBER_OVEP", new SqlArrayValue(simNumbersArray));

        Map<String, Object> result = jdbcCall.execute(in);

        logger.debug("Se ejecuto procedure");
        String message = (String) result.get("AVCH_MENSAJE");

        logger.debug("message : " + message);
        
        if(message != null){
            throw new RepositoryException(message);
        }

        Object[] objects= (Object[])result.get("APIBSCS.TT_CONTRATO_OVEP");
        logger.debug("Tamaño AT_CONTRATO_OVEP: " + objects.length);
        List<Contrato> contratoList = new ArrayList<Contrato>();
        for(Object object : objects){
            Contrato contrato = (Contrato)object;
            logger.debug("contrato :" + contrato.toString());
            contratoList.add(contrato);
        }
        return contratoList;
    }
}
