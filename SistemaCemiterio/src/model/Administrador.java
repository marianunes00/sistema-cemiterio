package model;

/**
 *
 * @author Váleria Matias
 */
public class Administrador extends Usuario {
    
    public Administrador(int idUsuario, String nomeUsuario, String login, String senha, String perfil) {
        super(idUsuario, nomeUsuario, login, senha, perfil);
    }
    
    //O administrador pode cadastrar,atualizar e deletar sepulturas
   @Override
    public boolean podeCadastrarSepultura() {
        return true;
    }

    @Override
    public boolean podeAtualizarSepultura() {
        return true;
    }

    @Override
    public boolean podeDeletarSepultura() {
        return true;
    }
    
     //O administrador pode cadastrar,atualizar e deletar falecidos
    @Override
    public boolean podeCadastrarFalecido() {
        return true;
    }

    @Override
    public boolean podeAtualizarFalecido() {
        return true;
    }

    @Override
    public boolean podeDeletarFalecido() {
        return true;
    } 
    
     // O administrador pode cadastrar,atualizar e deletar serviços
    @Override
    public boolean podeCadastrarServicos() {
        return true;
    }
    @Override
    public boolean podeAtualizarServicos() {
        return true;
    }

    @Override
    public boolean podeDeletarServicos() {
        return true;
    } 

    //Somente o administrador pode cadastrar um novo usuário
    @Override
    public boolean podeCadastrarUsuario() {
        return true;
    }  
    
    @Override
    public boolean podeVerAvisosInternos() {
        return true;
}

    @Override
     public boolean podeAcessarRelatorios() {
        return true;
}

}
