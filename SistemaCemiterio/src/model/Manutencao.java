package model;

/**
 *
 * @author Váleria Matias
 */
public class Manutencao extends Usuario {
    
    public Manutencao(int idUsuario, String nomeUsuario,String login, String senha, String perfil){
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
    
    //A manutenção pode atualizar sepulturas, falecidos e serviços
    @Override
    public boolean podeAtualizarSepultura() {
        return true;
    }
    @Override
    public boolean podeAtualizarFalecido() {
        return true;
    }
    @Override
    public boolean podeAtualizarServicos() {
        return true;
    }

}
