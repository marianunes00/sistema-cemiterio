package model;

/**
 *
 * @author Microsoft
 */
public class Visitante extends Usuario{

    public Visitante(int idUsuario, String nomeUsuario, String login, String senha, String perfil) {
        super(idUsuario, nomeUsuario, login, senha, perfil);
    }
    //Somente o visitante não tem acesso aos botões de avisos internos e relatórios 
}
