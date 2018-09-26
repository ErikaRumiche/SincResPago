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
import pe.com.entel.sincrespago.domain.SiteVep;
import pe.com.entel.sincrespago.exception.RepositoryException;
import pe.com.entel.sincrespago.mapper.SiteVepMapper;
import pe.com.entel.sincrespago.util.Configuration;

import javax.sql.DataSource;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Erika Rumiche on 25/09/2018.
 */
@Repository
public class SiteVepRepository {

    private Logger logger = Logger.getLogger(SiteVepRepository.class);

    @Autowired
    private DataSource dataSourcePias;

    @Autowired
    private Configuration configuration;

    public List<SiteVep> obtenerSiteVep(List<String> custcodeList) throws RepositoryException {

        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSourcePias);
        jdbcCall.withSchemaName(configuration.getWebsalesSchema());
        jdbcCall.withCatalogName(configuration.getSincresppagoPackage());
        jdbcCall.withProcedureName(configuration.getSitebycustcodeSp());

        jdbcCall.addDeclaredParameter(new SqlParameter("AT_CUSTCODE_OVEP", Types.ARRAY, "WEBSALES.TT_CUSTCODE_OVEP"));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AT_SITE_OVEP",Types.ARRAY, "WEBSALES.TT_SITE_OVEP", new SqlReturnStructArray<SiteVep>(new SiteVepMapper())));
        jdbcCall.addDeclaredParameter(new SqlOutParameter("AVCH_MENSAJE", OracleTypes.VARCHAR));

        String[] custcodeArray = custcodeList.toArray(new String[custcodeList.size()]);
        MapSqlParameterSource in = new MapSqlParameterSource();
        in.addValue("AT_CUSTCODE_OVEP", new SqlArrayValue(custcodeArray));

        Map<String, Object> result = jdbcCall.execute(in);
        logger.debug("Se ejecuto procedure");
        String message = (String) result.get("AVCH_MENSAJE");

        logger.debug("message : " + message);

        if(message != null){
            throw new RepositoryException(message);
        }

        Object[] objects= (Object[])result.get("AT_SITE_OVEP");
        logger.debug("Tamaño AT_SITE_OVEP: " + objects.length);
        List<SiteVep> sitesList = new ArrayList<SiteVep>();
        for(Object object : objects){
            SiteVep site = (SiteVep)object;
            sitesList.add(site);
        }
        return sitesList;

    }
}
