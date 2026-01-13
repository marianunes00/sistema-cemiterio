package model;

import java.time.LocalDate;

/**
 *
 * @author Váleria Matias
 */
public class Falecido {
    private int idFalecido;
    private String nomeCompleto;
    private LocalDate dataNascimento;
    private boolean possuiCertidaoObito;
    private String cpf;
    private Sepultura sepultura;
    private LocalDate dataFalecimento;
    private String familiarResponsavel;

    public Falecido() {
    }

    public Falecido(int idFalecido, String nomeCompleto, LocalDate dataNascimento, boolean possuiCertidaoObito, String cpf, Sepultura sepultura, LocalDate dataFalecimento, String familiarResponsavel) {
        this.idFalecido = idFalecido;
        this.nomeCompleto = nomeCompleto;
        this.dataNascimento = dataNascimento;
        this.possuiCertidaoObito = possuiCertidaoObito;
        this.cpf = cpf;
        this.sepultura = sepultura;
        this.dataFalecimento = dataFalecimento;
        this.familiarResponsavel = familiarResponsavel;
    }

    public int getIdFalecido() {
        return idFalecido;
    }

    public void setIdFalecido(int idFalecido) {
        this.idFalecido = idFalecido;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public boolean isPossuiCertidaoObito() {
        return possuiCertidaoObito;
    }

    public void setPossuiCertidaoObito(boolean possuiCertidaoObito) {
        this.possuiCertidaoObito = possuiCertidaoObito;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public Sepultura getSepultura() {
        return sepultura;
    }

    public void setSepultura(Sepultura sepultura) {
        this.sepultura = sepultura;
    }

    public LocalDate getDataFalecimento() {
        return dataFalecimento;
    }

    public void setDataFalecimento(LocalDate dataFalecimento) {
        this.dataFalecimento = dataFalecimento;
    }

    public String getFamiliarResponsavel() {
        return familiarResponsavel;
    }

    public void setFamiliarResponsavel(String familiarResponsavel) {
        this.familiarResponsavel = familiarResponsavel;
    }
    
}
