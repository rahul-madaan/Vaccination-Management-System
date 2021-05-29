package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbHandler {

    protected Connection connection;

    public Connection getConnection() {
        final String connectionString = "jdbc:mysql://localhost:3306/Vaccine_CodeForCovid_DB";

        try{
            connection = DriverManager.getConnection(connectionString,"root","@2RamRam");
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return connection;
    }
}
