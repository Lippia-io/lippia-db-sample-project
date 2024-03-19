package database.util;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.crowdar.core.PropertyManager;
import org.testng.Assert;

public class DatabaseUtils {

    private static Connection getConnection() throws SQLException {
        String host = System.getProperty("CROWDAR_DB_HOST").trim();
        String port = System.getProperty("CROWDAR_DB_PORT").trim();
        String user = System.getProperty("CROWDAR_DB_USER").trim();
        String password = System.getProperty("CROWDAR_DB_PASSWORD").trim();
        String dbName = System.getProperty("CROWDAR_DB_NAME").trim();

        String url = "jdbc:mysql://" + host + ":" + port + "/" + dbName;

        return DriverManager.getConnection(url, user, password);
    }

    public static void closeConnection() throws SQLException {
        getConnection().close();
    }

    public static List<Map<String, Object>> executeQuery(String queryString) throws SQLException {
        List<Map<String, Object>> records = null;
        ResultSet rs;
        try {
            try (Statement st = getConnection().createStatement()) {
                rs = st.executeQuery(queryString);
                if (rs != null) {
                    ResultSetMetaData md = rs.getMetaData();
                    int columns = md.getColumnCount();
                    // list to have all rows
                    records = new ArrayList<>(columns);
                    // load data for each column / record
                    while (rs.next()) {
                        HashMap<String, Object> row = new HashMap<>(columns);
                        // add column/value for a record
                        for (int i = 1; i <= columns; ++i) {
                            row.put(md.getColumnName(i), rs.getObject(i));
                        }
                        records.add(row);
                    }
                }
            }
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                getConnection().close();
            } catch (SQLException e) {
                throw e;
            }
        }

        return records;
    }

    public static boolean executeUpdate(String ddlString) throws SQLException {
        int recAffected = 0;
        try (Statement st = getConnection().createStatement()) {
            recAffected = st.executeUpdate(ddlString);
        } catch (SQLException e) {
            throw e;
        } finally {
            try {
                getConnection().close();
            } catch (SQLException e) {
                throw e;
            }
        }
        return recAffected >= 0;
    }

    public static  Object executeQueryWithParameters(String sqlString, Map<Integer, Object> parameters) throws SQLException {
        /*
         * establish connection here
         */
        PreparedStatement preparedStatement = getConnection().prepareStatement(sqlString);

        if(parameters != null){
            /*
             * Iterate over the map to set parameters
             */
            for(Integer key : parameters.keySet()){
                preparedStatement.setObject(key, parameters.get(key));
            }
        }

        if(sqlString.startsWith("UPDATE") || sqlString.startsWith("DELETE") || sqlString.startsWith("INSERT")|| sqlString.startsWith("SET")){
            int recAffected = preparedStatement.executeUpdate();
            return recAffected > 0;
        }else{
            ResultSet rs = preparedStatement.executeQuery();
            return rs;
        }
    }

    public static  Object executeMixQuery(String sqlString, Map<Integer, Object> parameters) throws SQLException {
        /*
         * establish connection here
         */
        PreparedStatement preparedStatement = getConnection().prepareStatement(sqlString);

        if(parameters != null){
            /*
             * Iterate over the map to set parameters
             */
            for(Integer key : parameters.keySet()){
                preparedStatement.setObject(key, parameters.get(key));
            }
        }
        ResultSet rs = null;
        if(sqlString.startsWith("SET")){
            rs = preparedStatement.executeQuery();
            return rs;
        }
        return rs;
    }

    public static int executeUpdateWithParametersAndResultsNumber(String sqlString, Map<Integer, Object> parameters) throws SQLException {
        /*
         * establish connection here
         */
        PreparedStatement preparedStatement = getConnection().prepareStatement(sqlString);
        for (int key = 1; key<=parameters.size(); key++ ){
            System.out.println("Parámetros cargados: " + parameters.get(key));
        }
        System.out.println("Cantidad parámetros: " + parameters.size());

        if(parameters != null){
            /*
             * Iterate over the map to set parameters
             */
            for(Integer key : parameters.keySet()){
                preparedStatement.setObject(key, parameters.get(key));
            }
        }

        if(sqlString.startsWith("DECLARE") || sqlString.startsWith("UPDATE") || sqlString.startsWith("DELETE") || sqlString.startsWith("INSERT")|| sqlString.startsWith("SET")){
            return preparedStatement.executeUpdate();
        } else return -1;
    }
}
