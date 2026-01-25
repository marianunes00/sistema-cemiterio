package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import model.Sepultura;
import connection.ConnectionFactory;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import java.sql.ResultSet;

/**
 *
 * @author Váleria Matias
 */
public class SepulturaDao {
    
    //metodo principal, onde os dados serão inseridos no banco de dados
    //usando o try catch não precisa usar uma classe para fechar a comunicação com o banco;
    public void inserir(Sepultura sepultura){
        
        String sql = "INSERT INTO sepultura(lote,tipoSepultura,statusSepultura,familiarResponsavel,dataCriacao)VALUES(?,?,?,?,?) ";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, sepultura.getLote());
            stmt.setString(2, sepultura.getTipoSepultura());
            stmt.setString(3, sepultura.getStatusSepultura());
            stmt.setString(4, sepultura.getFamiliarResponsavel());
            stmt.setDate(5, java.sql.Date.valueOf(sepultura.getDataCriacao()));
            
            stmt.execute();
            
        }catch(SQLException e){
            System.out.println("Erro ao criar Sepultura");
            e.printStackTrace();
        }
    
    
    }  
    
    public void atualizar(Sepultura sepultura){
        
        String sql = "UPDATE sepultura SET "
                + "lote = ?,"
                + "tipoSepultura = ?,"
                + "statusSepultura = ?,"
                + "familiarResponsavel = ?,"
                + "dataCriacao = ?"
                + " WHERE idSepultura = ?";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, sepultura.getLote());
            stmt.setString(2, sepultura.getTipoSepultura());
            stmt.setString(3, sepultura.getStatusSepultura());
            stmt.setString(4, sepultura.getFamiliarResponsavel());
            stmt.setDate(5, java.sql.Date.valueOf(sepultura.getDataCriacao()));
            stmt.setInt(6, sepultura.getIdSepultura());
            
            stmt.executeUpdate();
                     
        }catch(SQLException e){
            System.out.println("Erro ao atualizar Sepultura");
            e.printStackTrace();
        }    
    }
    
    public void deletar(int idSepultura) {
            //detro dessa string coloca o comando sql do metodo para deletar
    String sql = "DELETE FROM sepultura WHERE idSepultura = ?";

            //abre a conexão e passa o comando sql para o stmt
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idSepultura);

        stmt.executeUpdate();
        
        //caso dê erro, exibe a mensagem
    } catch (SQLException e) {
        System.out.println("Erro ao excluir Sepultura");
        e.printStackTrace();
    }
  }
    
    public List<Sepultura> listarTodos(){
        
        String sql = "SELECT * FROM sepultura";
        
        List<Sepultura> sepulturas = new ArrayList<>();
        
      //Abre conexão,Prepara o SQL,Executa o SELECT,Recebe o resultado no ResultSet
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery())
        {
           //equando exisitir linhas para serem visualizadas ele vai preenchendo os dados
           while(rs.next()){
           Sepultura s = new Sepultura();
           //nome do jeito que está no bd
           s.setIdSepultura(rs.getInt("idSepultura"));
           s.setLote(rs.getString("lote"));
           s.setTipoSepultura(rs.getString("tipoSepultura"));
           s.setStatusSepultura(rs.getString("statusSepultura"));
           s.setFamiliarResponsavel(rs.getString("familiarResponsavel"));
           s.setDataCriacao(rs.getDate("dataCriacao").toLocalDate());
           //adiciona o objeto dentro do array de sepulturas
           sepulturas.add(s);
           
           
           }       
                       
        }catch(SQLException e){
            System.out.println("Erro ao listar as sepulturas");
            e.printStackTrace();
        }  
   
        return sepulturas;
    }
    
    public List<Sepultura> buscarPorLote(String lote) {
        String sql = "SELECT * FROM sepultura WHERE lote LIKE ?";
        List<Sepultura> sepulturas = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + lote + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Sepultura s = new Sepultura();
                    s.setIdSepultura(rs.getInt("idSepultura"));
                    s.setLote(rs.getString("lote"));
                    s.setTipoSepultura(rs.getString("tipoSepultura"));
                    s.setStatusSepultura(rs.getString("statusSepultura"));
                    s.setFamiliarResponsavel(rs.getString("familiarResponsavel"));
                    s.setDataCriacao(rs.getDate("dataCriacao").toLocalDate());
                    sepulturas.add(s);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar sepulturas por lote: " + e.getMessage());
        }

        return sepulturas;
}

    public List<Sepultura> listarPorStatus(String status) {
        List<Sepultura> lista = new ArrayList<>();
        String sql;

        if (status.equalsIgnoreCase("TODOS")) {
            sql = "SELECT * FROM sepultura";
        } else {
            sql = "SELECT * FROM sepultura WHERE statusSepultura = ?";
        }

        try (Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (!status.equalsIgnoreCase("TODOS")) {
                stmt.setString(1, status);
            }

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Sepultura s = new Sepultura();
                s.setIdSepultura(rs.getInt("idSepultura"));
                s.setLote(rs.getString("lote"));
                s.setTipoSepultura(rs.getString("tipoSepultura"));
                s.setStatusSepultura(rs.getString("statusSepultura"));
                s.setFamiliarResponsavel(rs.getString("familiarResponsavel"));
                lista.add(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lista;
}

}
