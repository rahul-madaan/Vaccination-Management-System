package sample;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbHandler {

    protected Connection connection;

    public Connection getConnection() {
        final String connectionString = "jdbc:mysql://localhost:3306/user";

        try{
            connection = DriverManager.getConnection(connectionString,"root","@2RamRam");
        }catch(SQLException ex){
            Logger.getLogger(DbHandler.class.getName()).log(Level.SEVERE,null,ex);
            ex.printStackTrace();
        }
        return connection;
    }
}
