package pe.com.entel.sincrespago.mapper;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import pe.com.entel.sincrespago.domain.ItemOrdenVep;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Erika Rumiche on 22/09/2018.
 */

public class ItemOrdenVepMapper implements StructMapper<ItemOrdenVep> {

    private static Logger logger = Logger.getLogger(ItemOrdenVepMapper.class);

    public STRUCT toStruct(ItemOrdenVep source, Connection conn, String typeName) throws SQLException {
        StructDescriptor descriptor = new StructDescriptor(typeName, conn);
        Object[] values = new Object[5];
        values[0] = source.getOrden_id();
        values[1] = source.getCliente_id();
        values[2] = source.getItem_id();
        values[3] = source.getItemdev_id();
        values[4] = source.getSimnumber();
        return new STRUCT(descriptor, conn, values);
    }

    public ItemOrdenVep fromStruct(STRUCT struct) throws SQLException {
        ItemOrdenVep dest = new ItemOrdenVep();
        Object[] attributes = struct.getAttributes();
        dest.setOrden_id(Long.valueOf(((Number) attributes[0]).longValue()));
        dest.setCliente_id(Long.valueOf(((Number) attributes[1]).longValue()));
        dest.setItem_id(Long.valueOf(((Number) attributes[2]).longValue()));
        dest.setItemdev_id(Long.valueOf(((Number) attributes[3]).longValue()));
        dest.setSimnumber(String.valueOf(attributes[4]));
        return dest;
    }

}
