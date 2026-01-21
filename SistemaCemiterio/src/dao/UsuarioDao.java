 package dao;

import model.Usuario;
import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

 
public class UsuarioDao {

    public UsuarioDao() {
    }
    

    public void inserir(Usuario usuario) {
        String sql = "INSERT INTO usuario(nomeUsuario, login, senha, perfil) VALUES (?,?,?,?)";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, usuario.getNomeUsuario());
            stmt.setString(2, usuario.getLogin());
            stmt.setString(3, usuario.getSenha()); 
            stmt.setString(4, usuario.getPerfil());
            

            stmt.executeUpdate();
           
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    public List<Usuario> listarTodos() {
        String sql = "SELECT * FROM usuario";
        List<Usuario> usuarios = new ArrayList<>();

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Usuario u = new Usuario();
                u.setIdUsuario(rs.getInt("idUsuario"));
                u.setNomeUsuario(rs.getString("nomeUsuario"));
                u.setLogin(rs.getString("login"));
                u.setSenha(rs.getString("senha"));
                u.setPerfil(rs.getString("perfil"));
                usuarios.add(u);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar usuários: " + e.getMessage());
        }

        return usuarios;
    }
    
    public Usuario autenticar(String login ,String senha) {
        String sql = "SELECT * FROM usuario WHERE login = ? AND senha = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, login);
            stmt.setString(2, senha);
            
            ResultSet rs = stmt.executeQuery();
            
            if(rs.next()){
                return new Usuario(
                rs.getInt("idUsuario"),
                rs.getString("nomeUsuario"),
                rs.getString("login"),
                rs.getString("senha"),
                rs.getString("perfil"));
            }
           
        } catch (SQLException e) {
            throw new RuntimeException("Usuário não autenticado!" + e.getMessage());
        }
        return null;
    }

}