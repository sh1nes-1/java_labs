package lab5.utils;

import lab5.exception.DatabaseConnectionException;
import lab5.connection.ConnectionFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static java.util.Objects.isNull;

// DataBase structure
public class DatabaseStructure {

    private static final String CREATE_SMARTPHONES = "CREATE TABLE smartphones (id SERIAL NOT NULL PRIMARY KEY,name VARCHAR(100) NOT NULL,price integer NOT NULL,releaseDate DATE NOT NULL,color VARCHAR(15) NOT NULL,ram integer NOT NULL,diagonal FLOAT NOT NULL);";
    private static final String DROP_SMARTPHONES = "DROP TABLE smartphones";

    private static final String CREATE_SHOPS = "CREATE TABLE shops (id SERIAL NOT NULL PRIMARY KEY,name integer NOT NULL);";
    private static final String DROP_SHOPS = "DROP TABLE shops";

    private static final String CREATE_SMARTPHONES_SHOPS = "CREATE TABLE catalog_items (id SERIAL NOT NULL PRIMARY KEY,shop_id integer NOT NULL REFERENCES shops(id),smartphone_id integer NOT NULL REFERENCES smartphones(id),smartphone_price integer NOT NULL,smartphone_count integer NOT NULL);";
    private static final String DROP_SMARTPHONES_SHOPS = "DROP TABLE catalog_items";


    private static Connection cachedConnection;


    private static Connection getConnection() throws DatabaseConnectionException {
        return isNull(cachedConnection) ? ConnectionFactory.getConnectionBuilder().getConnection() : cachedConnection;
    }

    private static void createSmartphonesTable() throws DatabaseConnectionException, SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_SMARTPHONES);
    }

    private static void createShopsTable() throws DatabaseConnectionException, SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_SHOPS);
    }

    private static void createSmartphonesShopsTable() throws DatabaseConnectionException, SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(CREATE_SMARTPHONES_SHOPS);
    }


    public static void createTables() throws DatabaseConnectionException, SQLException {
        createSmartphonesTable();
        createShopsTable();
        createSmartphonesShopsTable();
    }

    public static void dropTables() throws DatabaseConnectionException, SQLException {
        Connection connection = getConnection();
        Statement statement = connection.createStatement();
        statement.executeUpdate(DROP_SMARTPHONES_SHOPS);
        statement.executeUpdate(DROP_SMARTPHONES);
        statement.executeUpdate(DROP_SHOPS);
    }


}
