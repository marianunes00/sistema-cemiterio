package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Falecido;
import model.Sepultura;


/**
 *
 * @author Váleria Matias
 */
public class FalecidoDao {
    
      public void inserir(Falecido falecido){
        
        String sql = "INSERT INTO falecido(nomecompleto,dataNascimento,possuiCertidaoObito"
                + ",cpf,sepultura,dataFalecimento,familiarResponsavel)VALUES(?,?,?,?,?,?,?) ";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, falecido.getNomeCompleto());
            stmt.setDate(2, java.sql.Date.valueOf(falecido.getDataNascimento()));
            stmt.setBoolean(3, falecido.isPossuiCertidaoObito());
            stmt.setString(4, falecido.getCpf());
            stmt.setInt(5, falecido.getSepultura().getIdSepultura());  
            stmt.setDate(6, java.sql.Date.valueOf(falecido.getDataFalecimento()));
            stmt.setString(7, falecido.getFamiliarResponsavel());
            
            stmt.execute();
                       
        } catch (SQLException e) {
        // MUITO IMPORTANTE: Mudar de System.out para THROW
        // Isso impede que a TelaFalecido mostre a mensagem de "Sucesso"
        throw new RuntimeException("Erro ao inserir no banco: " + e.getMessage());
    }
    
    }  
    
    public void atualizar(Falecido falecido){
        
        String sql = "UPDATE falecido SET "
            + "nomecompleto = ?,"
            + "dataNascimento = ?,"
            + "possuiCertidaoObito = ?,"
            + "cpf = ?,"
            + "sepultura = ?,"
            + "dataFalecimento = ?,"
            + "familiarResponsavel = ?"
            + " WHERE idFalecido = ?";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, falecido.getNomeCompleto());
            stmt.setDate(2, java.sql.Date.valueOf(falecido.getDataNascimento()));
            stmt.setBoolean(3, falecido.isPossuiCertidaoObito());
            stmt.setString(4, falecido.getCpf());
            stmt.setInt(5, falecido.getSepultura().getIdSepultura()); 
            stmt.setDate(6, java.sql.Date.valueOf(falecido.getDataFalecimento()));
            stmt.setString(7, falecido.getFamiliarResponsavel());
            stmt.setInt(8, falecido.getIdFalecido());
            
            stmt.executeUpdate();
                   
        }catch(SQLException e){
            System.out.println("Erro ao atualizar Registro de Falecdio");
            e.printStackTrace();
        }    
    }
    
    public void deletar(int idFalecido) {
            //detro dessa string coloca o comando sql do metodo para deletar
    String sql = "DELETE FROM falecido WHERE idFalecido = ?";

            //abre a conexão e passa o comando sql para o stmt
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idFalecido);

        stmt.executeUpdate();
        
        
        //caso dê erro, exibe a mensagem
    } catch (SQLException e) {
        System.out.println("Erro ao excluir Registro");
        e.printStackTrace();
    }
  }
    
    public List<Falecido> listarTodos(){
        
        String sql = "SELECT * FROM falecido";
        
        List<Falecido> falecidos = new ArrayList<>();
        
      //Abre conexão,Prepara o SQL,Executa o SELECT,Recebe o resultado no ResultSet
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery())
        {
           //equando exisitir linhas para serem visualizadas ele vai preenchendo os dados
           while(rs.next()){
           Falecido f = new Falecido();
           //nome do jeito que está no bd
           f.setIdFalecido(rs.getInt("idFalecido"));
           f.setNomeCompleto(rs.getString("nomeCompleto"));
           f.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
           f.setPossuiCertidaoObito(rs.getBoolean("possuiCertidaoObito"));
           f.setCpf(rs.getString("cpf"));
           f.setDataFalecimento(rs.getDate("dataFalecimento").toLocalDate());
           f.setFamiliarResponsavel(rs.getString("familiarResponsavel"));
           //adiciona o objeto dentro do array de falecidos
          //Para pegar a referencia de sepultura tem que ser assim
            Sepultura s = new Sepultura();
            s.setIdSepultura(rs.getInt("sepultura")); // nome da coluna FK na tabela falecido
            f.setSepultura(s);
                       
           falecidos.add(f);
           
           
           }      
                       
        }catch(SQLException e){
            // Além de imprimir, joga o erro para a Tela tratar
            throw new RuntimeException("Erro no Banco de Dados: " + e.getMessage());
        }
           // System.out.println("Erro ao listar os falecidos");
           // e.printStackTrace();Esses só imprime no console
           
    //exibe a lista
    return falecidos;
}
    //mesma coisa de listar, só adiciona o where
    public List<Falecido> buscarPorNome(String nome) {
    String sql = "SELECT * FROM falecido WHERE nomeCompleto LIKE ?";
    List<Falecido> falecidos = new ArrayList<>();

    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setString(1, "%" + nome + "%");

        try (ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Falecido f = new Falecido();
                f.setIdFalecido(rs.getInt("idFalecido"));
                f.setNomeCompleto(rs.getString("nomeCompleto"));
                f.setDataNascimento(rs.getDate("dataNascimento").toLocalDate());
                f.setPossuiCertidaoObito(rs.getBoolean("possuiCertidaoObito"));
                f.setCpf(rs.getString("cpf"));
                f.setDataFalecimento(rs.getDate("dataFalecimento").toLocalDate());
                f.setFamiliarResponsavel(rs.getString("familiarResponsavel"));

                Sepultura s = new Sepultura();
                s.setIdSepultura(rs.getInt("sepultura"));
                f.setSepultura(s);

                falecidos.add(f);
            }
        }
    } catch (SQLException e) {
        throw new RuntimeException("Erro ao buscar falecidos por nome: " + e.getMessage());
    }

    return falecidos;
}

}
