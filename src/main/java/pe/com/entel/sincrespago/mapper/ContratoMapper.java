package pe.com.entel.sincrespago.mapper;

import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.log4j.Logger;
import org.springframework.data.jdbc.support.oracle.StructMapper;
import pe.com.entel.sincrespago.domain.ContratoBscs;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;

/**
 * Created by Erika Rumiche on 23/09/2018.
 */
public class ContratoMapper implements StructMapper<ContratoBscs> {

    private static Logger logger = Logger.getLogger(ContratoMapper.class);

    public STRUCT toStruct(ContratoBscs source, Connection conn, String typeName) throws SQLException {

        //logger.debug("ContratoMapper.toStruct : " + source.toString());
        StructDescriptor descriptor = new StructDescriptor(typeName, conn);
        Object[] values = new Object[4];
        values[0] = source.getClienteBscsId();
        values[1] = source.getSimNumber();
        values[2] = source.getContratoId();
        values[3] = source.getCustCode();
        return new STRUCT(descriptor, conn, values);
    }

    public ContratoBscs fromStruct(STRUCT struct) throws SQLException {
        ContratoBscs dest = new ContratoBscs();
        Object[] attributes = struct.getAttributes();
        //logger.debug("ContratoMapper.fromStruct : " + Arrays.toString(attributes));
        dest.setClienteBscsId(Long.valueOf(((Number) attributes[0]).longValue()));
        dest.setSimNumber(String.valueOf(attributes[1]));
        dest.setContratoId(Long.valueOf(((Number) attributes[2]).longValue()));
        dest.setCustCode(String.valueOf(attributes[3]));
        return dest;
    }
}
