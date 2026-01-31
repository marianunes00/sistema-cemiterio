package model;


public class Financeiro extends Usuario {

    public Financeiro() {
    }

    public Financeiro(int idUsuario, String nomeUsuario, String login, String senha, String perfil) {
        super(idUsuario, nomeUsuario, login, senha, perfil);
    }
    
    @Override
    public boolean podeVerAvisosInternos() {
        return true;
}
    @Override
    public boolean podeAcessarRelatorios(){
        return true;       
    }
}
