package pe.com.entel.sincrespago.mapper;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import pe.com.entel.sincrespago.domain.Contrato;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Erika Rumiche on 23/09/2018.
 */
public class ContratoMapper implements StructMapper<Contrato> {

    private static Logger logger = Logger.getLogger(ContratoMapper.class);

    public STRUCT toStruct(Contrato source, Connection conn, String typeName) throws SQLException {

        //logger.debug("ContratoMapper.toStruct : " + source.toString());
        StructDescriptor descriptor = new StructDescriptor(typeName, conn);
        Object[] values = new Object[4];
        values[0] = source.getCliente_id();
        values[1] = source.getSimnumber();
        values[2] = source.getContrato_id();
        values[3] = source.getCust_code();
        return new STRUCT(descriptor, conn, values);
    }

    public Contrato fromStruct(STRUCT struct) throws SQLException {
        Contrato dest = new Contrato();
        Object[] attributes = struct.getAttributes();
        logger.debug("ContratoMapper.fromStruct : " + Arrays.toString(attributes));
        dest.setCliente_id(Long.valueOf(((Number) attributes[0]).longValue()));
        dest.setSimnumber(String.valueOf(attributes[1]));
        dest.setContrato_id(Long.valueOf(((Number) attributes[2]).longValue()));
        dest.setCust_code(String.valueOf(attributes[3]));
        //logger.debug("ContratoMapper.fromStruct : " + dest.toString());
        return dest;
    }
}
