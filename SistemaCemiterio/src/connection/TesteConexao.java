/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package connection;
import java.sql.Connection;
/**
 *
 * @author Váleria Matias
 */
public class TesteConexao {
    public static void main(String[] args) {
        try (Connection con = ConnectionFactory.getConnection()) {
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
