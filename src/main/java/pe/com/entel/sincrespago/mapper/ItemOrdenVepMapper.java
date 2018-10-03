package pe.com.entel.sincrespago.mapper;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Erika Rumiche on 22/09/2018.
 */

public class ItemOrdenVepMapper implements StructMapper<ItemOrdenVep> {

    private static Logger logger = Logger.getLogger(ItemOrdenVepMapper.class);

    public STRUCT toStruct(ItemOrdenVep source, Connection conn, String typeName) throws SQLException {
        StructDescriptor descriptor = new StructDescriptor(typeName, conn);
        Object[] values = new Object[5];
        values[0] = source.getOrdenId();
        values[1] = source.getClienteCrmId();
        values[2] = source.getSiteId();
        values[3] = source.getSimNumber();
        values[4] = source.getOvepId();
        return new STRUCT(descriptor, conn, values);
    }

    public ItemOrdenVep fromStruct(STRUCT struct) throws SQLException {
        ItemOrdenVep dest = new ItemOrdenVep();
        Object[] attributes = struct.getAttributes();
        //logger.debug("ItemOrdenVepMapper.fromStruct : " + Arrays.toString(attributes));
        dest.setOrdenId(Long.valueOf(((Number) attributes[0]).longValue()));
        dest.setClienteCrmId(Long.valueOf(((Number) attributes[1]).longValue()));
        dest.setSiteId(Long.valueOf(((Number) attributes[2]).longValue()));
        dest.setSimNumber(String.valueOf(attributes[3]));
        dest.setOvepId(Long.valueOf(((Number) attributes[4]).longValue()));
        return dest;
    }

}
