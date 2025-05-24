package org.example.crud.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {
    public ConnectionFactory(){}
    public static Connection getConnection(){
        try {
            return DriverManager.getConnection(
                    "jdbc:postgresql://localhost:5432/InstituicaoDB",
                    "postgres",
                    "130303"
            );
        } catch (SQLException e){
            throw  new RuntimeException(e);
        }
    }
}
