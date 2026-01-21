package model;


public class Usuario {
    private int idUsuario;
    private String nomeUsuario;
    private String login;
    private String senha;
    private String perfil;

    public Usuario(){}
    
    public Usuario(int idUsuario, String nomeUsuario, String login, String senha,String perfil) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
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

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
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
