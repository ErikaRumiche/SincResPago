package pe.com.entel.sincrespago.mapper;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import pe.com.entel.sincrespago.domain.SiteVep;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Admin on 25/09/2018.
 */
public class SiteVepMapper implements StructMapper<SiteVep>{

    @Override
    public STRUCT toStruct(SiteVep siteVep, Connection connection, String typeName) throws SQLException {
        StructDescriptor descriptor = new StructDescriptor(typeName, connection);
        Object[] values = new Object[4];
        values[0] = siteVep.getSiteId();
        values[1] = siteVep.getClienteCrmId();
        values[2] = siteVep.getCustCode();
        values[3] = siteVep.getEstado();
        return new STRUCT(descriptor, connection, values);
    }

    @Override
    public SiteVep fromStruct(STRUCT struct) throws SQLException {
        SiteVep siteVep  = new SiteVep();
        Object[] attributes = struct.getAttributes();
        siteVep.setSiteId(Long.valueOf(((Number) attributes[0]).longValue()));
        siteVep.setClienteCrmId(Long.valueOf(((Number) attributes[1]).longValue()));
        siteVep.setCustCode(String.valueOf(attributes[2]));
        siteVep.setEstado(String.valueOf(attributes[3]));
        return siteVep;
    }
}
