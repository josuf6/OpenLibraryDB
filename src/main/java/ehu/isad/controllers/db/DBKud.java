package ehu.isad.controllers.db;

import ehu.isad.utils.Sarea;

import java.lang.reflect.InvocationTargetException;
import java.sql.*;
import java.util.Properties;

public class DBKud {

    private static final DBKud nDBKud = new DBKud();
    Connection conn = null;

    public static DBKud getDBKud() {
        return nDBKud;
    }

    private DBKud() {
        this.conOpen();
    }

    private void conOpen() {

        Properties properties = Sarea.lortuEzarpenak();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver").getConstructor().newInstance();
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/", properties);
            conn.setCatalog(properties.getProperty("dbname"));

        } catch (SQLException ex) {
            // handle any errors
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        } catch (ClassNotFoundException | InvocationTargetException | IllegalAccessException | InstantiationException | NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    private ResultSet query(Statement s, String query) {

        ResultSet rs = null;

        try {
            s.executeQuery(query);
            rs = s.getResultSet();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return rs;
    }

    public ResultSet execSQL(String query) {
        Statement s;
        ResultSet rs = null;
        try {
            s = conn.createStatement();
            if (query.toLowerCase().indexOf("select") == 0) {
                // select agindu bat
                rs = this.query(s, query);
            } else {
                // update, delete, create agindu bat
                s.executeUpdate(query);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }
}
