package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

    protected Connection connection;

    public Connection getConnection() {
        final String connectionString = "jdbc:mysql://localhost:3306/vaccine_cfc";

        try{
            connection = DriverManager.getConnection(connectionString,"root","Iamme123");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}
