/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Váleria Matias
 */
public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String login;
    private String senha;

    public Usuario(){}
    
    public Usuario(int idUsuario, String nomeUsuario, String login, String senha) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNomeUsuario() {
        return nomeUsuario;
    }

    public void setNomeUsuario(String nomeUsuario) {
        this.nomeUsuario = nomeUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
    
     public boolean autenticar(String login, String senha) {
        // poderia ter mais regras depois (hash de senha, ativo/inativo etc.)
        return this.login != null
                && this.login.equals(login)
                && this.senha != null
                && this.senha.equals(senha);
    }
    
    public boolean podeExcluirRegistros(){
        return false;
    }
    
    
    
    
    
    
}
