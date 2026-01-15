package dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Servico;
import model.Sepultura;


public class ServicoDao {
    
    public void inserir(Servico servico){
        
        String sql = "INSERT INTO servico(tipoServico,dataServico,statusServico"
                + ",sepultura,notificacaoServico)VALUES(?,?,?,?,?) ";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, servico.getTipoServico());
            stmt.setDate(2, java.sql.Date.valueOf(servico.getDataServico()));
            stmt.setString(3, servico.getStatusServico());
            stmt.setInt(4, servico.getSepultura().getIdSepultura());  
            stmt.setString(5, servico.getNotificacaoServico());
            
            stmt.execute();
            JOptionPane.showMessageDialog(
            null,
            "Serviço cadastrado com sucesso!",
            "Cadastro realizado",
            JOptionPane.INFORMATION_MESSAGE);           
        }catch(SQLException e){
            System.out.println("Erro ao criar ordem de serviço");
            e.printStackTrace();
        }
    }  
    
     public void atualizar(Servico servico){
        
        String sql = "UPDATE servico SET "
            + "tipoServico = ?,"
            + "dataServico = ?,"
            + "statusServico = ?,"
            + "sepultura = ?,"
            + "notificacaoServico = ?"
            + " WHERE idservico = ?";
        
        try( Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)
            )
        {
            stmt.setString(1, servico.getTipoServico());
            stmt.setDate(2, java.sql.Date.valueOf(servico.getDataServico()));
            stmt.setString(3, servico.getStatusServico());
            stmt.setInt(4, servico.getSepultura().getIdSepultura());  
            stmt.setString(5, servico.getNotificacaoServico());
            
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(
            null,
            "Ordem de serviço atualizada com sucesso!",
            "Atualização realizada",
            JOptionPane.INFORMATION_MESSAGE);           
        }catch(SQLException e){
            System.out.println("Erro ao atualizar a ordem de serviço");
            e.printStackTrace();
        }    
    }
     
      public void deletar(int idServico) {
            //detro dessa string coloca o comando sql do metodo para deletar
    String sql = "DELETE FROM servico WHERE idServico = ?";

            //abre a conexão e passa o comando sql para o stmt
    try (Connection conn = ConnectionFactory.getConnection();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        stmt.setInt(1, idServico);

        stmt.executeUpdate();
        
        //abre a caixa de dialogo e exibe uma mensagem de acordo com a operação
        JOptionPane.showMessageDialog(
            null,
            "Ordem de serviço excluída com sucesso!",
            "Exclusão realizada",
            JOptionPane.INFORMATION_MESSAGE
        );
        
        //caso dê erro, exibe a mensagem
    } catch (SQLException e) {
        System.out.println("Erro ao excluir ordem de serviço");
        e.printStackTrace();
    }
  }
    
      public List<Servico> listarTodos(){
        
        String sql = "SELECT * FROM servico";
        
        List<Servico> servicos = new ArrayList<>();
        
      //Abre conexão,Prepara o SQL,Executa o SELECT,Recebe o resultado no ResultSet
        try(Connection conn = ConnectionFactory.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql); 
            ResultSet rs = stmt.executeQuery())
        {
           //equanto existir linhas para serem visualizadas ele vai preenchendo os dados
           while(rs.next()){
           Servico f = new Servico();
           //nome do jeito que está no bd
           f.setIdServico(rs.getInt("idServico"));
           f.setTipoServico(rs.getString("tipoServico"));
           f.setDataServico(rs.getDate("dataServico").toLocalDate());
           f.setNotificacaoServico(rs.getString("notificacaoServico"));
           //adiciona o objeto dentro do array de servicos
          //Para pegar a referencia de sepultura tem que ser assim
            Sepultura s = new Sepultura();
            s.setIdSepultura(rs.getInt("sepultura")); // nome da coluna FK na tabela servico
            f.setSepultura(s);
                       
           servicos.add(f);
           
           
           }      
                       
        }catch(SQLException e){
            System.out.println("Erro ao listar as ordens de serviço");
            e.printStackTrace();
        }  
   
        return servicos;
       
    }

}