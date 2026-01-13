package model;

import java.time.LocalDate;

/**
 *
 * @author Váleria Matias
 */
public class Sepultura {
    private int idSepultura;
    private String lote;
    private String familiarResponsavel;
    private String tipoSepultura;
    private LocalDate dataCriacao;
    private String statusSepultura;

    public Sepultura() {
    }

    public Sepultura(int idSepultura, String lote, String familiarResponsavel, String tipoSepultura, LocalDate dataCriacao, String statusSepultura) {
        this.idSepultura = idSepultura;
        this.lote = lote;
        this.familiarResponsavel = familiarResponsavel;
        this.tipoSepultura = tipoSepultura;
        this.dataCriacao = dataCriacao;
        this.statusSepultura = statusSepultura;
    }

    public int getIdSepultura() {
        return idSepultura;
    }

    public void setIdSepultura(int idSepultura) {
        this.idSepultura = idSepultura;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public String getFamiliarResponsavel() {
        return familiarResponsavel;
    }

    public void setFamiliarResponsavel(String familiarResponsavel) {
        this.familiarResponsavel = familiarResponsavel;
    }

    public String getTipoSepultura() {
        return tipoSepultura;
    }

    public void setTipoSepultura(String tipoSepultura) {
        this.tipoSepultura = tipoSepultura;
    }

    public LocalDate getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDate dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public String getStatusSepultura() {
        return statusSepultura;
    }

    public void setStatusSepultura(String statusSepultura) {
        this.statusSepultura = statusSepultura;
    }
    
    public boolean estaDisponivel(){
        return this.getStatusSepultura() != null && this.getStatusSepultura().equalsIgnoreCase("Disponível");
    }
    
}
