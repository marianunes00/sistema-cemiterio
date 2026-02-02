package model;

public class Atendente extends Usuario{
    
    public Atendente(int idUsuario, String nomeUsuario,String login,String senha, String perfil){
        super(idUsuario, nomeUsuario, login, senha, perfil);
    }
    
    
    @Override
    public boolean podeVerAvisosInternos() {
        return true;
}
    @Override
     public boolean podeAcessarRelatorios() {
        return true;
}    
    //O atendente pode cadastrar ou atualizar sepulturas
    @Override
    public boolean podeCadastrarSepultura() {
        return true;
    }

    @Override
    public boolean podeAtualizarSepultura() {
        return true;
    }

    //Atendente pode cadastrar e atualizar falecidos
    @Override
    public boolean podeCadastrarFalecido() {
        return true;
    }

    @Override
    public boolean podeAtualizarFalecido() {
        return true;
        
    }
    @Override
    //Atendente pode cadastrar e atualizar serviços
    public boolean podeCadastrarServicos() {
        return true;
    }
    @Override
    public boolean podeAtualizarServicos() {
        return true;
    }
}
