/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Váleria Matias
 */
public class ConnectionFactory {
    private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String URL = "jdbc:mysql://localhost:3306/sistema_cemiterio";
    private static final String USER = "root";
    private static final String PASS = "1234";

    public static Connection getConnection(){
    
        try {
            Class.forName(DRIVER);
            return DriverManager.getConnection(URL,USER,PASS);
            
        } catch (ClassNotFoundException  | SQLException ex) {
            throw new RuntimeException("Erro na conexão",ex);
        }
    
    }

}
