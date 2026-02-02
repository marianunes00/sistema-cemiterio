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
import model.Administrador;
import model.Atendente;
import model.Financeiro;
import model.Manutencao;
import model.Visitante;

 
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
            
            if (rs.next()) {
            String perfil = rs.getString("perfil");
            int id = rs.getInt("idUsuario");
            String nome = rs.getString("nomeUsuario");
            String log = rs.getString("login");
            String sen = rs.getString("senha");
           
            // Aqui acontece o Polimorfismo:instancia usuario de acordo com seu perfil
            // Dependendo do texto no banco, criamos um objeto diferente
            if ("Administrador".equalsIgnoreCase(perfil)) {
                return new Administrador(id, nome, log, sen, perfil);
            } else if ("Atendente".equalsIgnoreCase(perfil)) {
                return new Atendente(id, nome, log, sen, perfil);
            } else if ("Manutenção".equalsIgnoreCase(perfil)) {
                return new Manutencao(id, nome, log, sen, perfil);
            } else if ("Financeiro".equalsIgnoreCase(perfil)) {
                return new Financeiro(id, nome, log, sen, perfil);
            } else {
                return new Visitante(id, nome, log, sen, perfil);
            }
        }
        } catch (SQLException e) {
            throw new RuntimeException("Usuário não autenticado!" + e.getMessage());
        }
        return null;
    }

}