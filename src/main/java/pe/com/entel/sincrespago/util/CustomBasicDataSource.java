package pe.com.entel.sincrespago.util;

import oracle.jdbc.OracleConnection;
import org.apache.commons.dbcp.BasicDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by Admin on 25/09/2018.
 */
public class CustomBasicDataSource extends BasicDataSource{


    private OracleConnection oracleConnection = null;

    @Override
    public Connection getConnection() throws SQLException {
        Connection connection = this.createDataSource().getConnection();
        OracleConnection oracleConnection = null;

        if (connection.isWrapperFor(OracleConnection.class)) {
            oracleConnection = connection.unwrap(OracleConnection.class);
            return oracleConnection;
        }else {
            return connection;
        }
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return getParentLogger();
    }
}
