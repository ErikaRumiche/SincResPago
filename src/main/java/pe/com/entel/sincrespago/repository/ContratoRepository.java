package pe.com.entel.sincrespago.repository;

import oracle.jdbc.OracleTypes;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jdbc.support.oracle.SqlArrayValue;
import org.springframework.data.jdbc.support.oracle.SqlReturnStructArray;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;
import pe.com.entel.sincrespago.domain.ContratoBscs;
import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.mapper.ContratoMapper;
import pe.com.entel.sincrespago.util.Configuration;

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

    @Autowired
    private Configuration configuration;

    public List<ContratoBscs> obtenerContrato(List<String> simnumberList) throws RepositoryException {


        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSourcePbscs);
        jdbcCall.withSchemaName(configuration.getApibscsSchema());
        jdbcCall.withCatalogName(configuration.getSincresppagoPackage());
        jdbcCall.withProcedureName(configuration.getCustcodebysimSp());

        jdbcCall.addDeclaredParameter(new SqlParameter("AT_SIMNUMBER_OVEP", Types.ARRAY, "APIBSCS.TT_SIMNUMBER_OVEP"));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AT_CONTRATO_OVEP",Types.ARRAY, "APIBSCS.TT_CONTRATO_OVEP", new SqlReturnStructArray<ContratoBscs>(new ContratoMapper())));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AVCH_MENSAJE", OracleTypes.VARCHAR));

        String[] simNumbersArray = simnumberList.toArray(new String[simnumberList.size()]);
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("AT_SIMNUMBER_OVEP", new SqlArrayValue(simNumbersArray));

        Map<String, Object> result = jdbcCall.execute(in);
        logger.debug("Se ejecuto procedure");
        String message = (String) result.get("AVCH_MENSAJE");

        logger.debug("message : " + message);
        
        if(message != null){
            throw new RepositoryException(message);
        }

        Object[] objects= (Object[])result.get("AT_CONTRATO_OVEP");
        logger.debug("Tamaño AT_CONTRATO_OVEP: " + objects.length);
        List<ContratoBscs> contratoBscsList = new ArrayList<ContratoBscs>();
        for(Object object : objects){
            ContratoBscs contratoBscs = (ContratoBscs)object;
            contratoBscsList.add(contratoBscs);
        }
        return contratoBscsList;
    }
}
