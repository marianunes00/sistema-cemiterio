/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Váleria Matias
 */
public class Atendente extends Usuario{
    
    public Atendente(int idUsuario, String nomeUsuario,String login,String senha){
        super(idUsuario, nomeUsuario, login, senha);
    }
    
    
    public boolean podeExcluirRegistro(String login){
        if(login == "admin"){
       return true;
    }else{
        return false;
        }
          
    }
}
