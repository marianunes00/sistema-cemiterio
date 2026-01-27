package connection;
import java.sql.Connection;

public class TesteConexao {
    public static void testar() {
        try (Connection con = ConnectionFactory.getConnection()) {
            System.out.println("Conectado com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
