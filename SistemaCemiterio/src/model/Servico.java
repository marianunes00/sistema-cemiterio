package model;

import java.time.LocalDate;

/**
 *
 * @author Váleria Matias
 */
public class Servico {
    private int idServico;
    private String tipoServico;
    private LocalDate dataServico;
    private String statusServico;
    private Sepultura sepultura;
    private String notificacaoServico;

    public Servico() {
    }

    public Servico(int idServico, String tipoServico, LocalDate dataServico, String statusServico, Sepultura sepultura, String notificacaoServico) {
        this.idServico = idServico;
        this.tipoServico = tipoServico;
        this.dataServico = dataServico;
        this.statusServico = statusServico;
        this.sepultura = sepultura;
        this.notificacaoServico = notificacaoServico;
    }

    public int getIdServico() {
        return idServico;
    }

    public void setIdServico(int idServico) {
        this.idServico = idServico;
    }

    public String getTipoServico() {
        return tipoServico;
    }

    public void setTipoServico(String tipoServico) {
        this.tipoServico = tipoServico;
    }
    
    public LocalDate getDataServico() {
        return dataServico;
    }

    public void setDataServico(LocalDate dataServico) {
        this.dataServico = dataServico;
    }

    public String getStatusServico() {
        return statusServico;
    }

    public void setStatusServico(String statusServico) {
        this.statusServico = statusServico;
    }

    public Sepultura getSepultura() {
        return sepultura;
    }

    public void setSepultura(Sepultura sepultura) {
        this.sepultura = sepultura;
    }

    public String getNotificacaoServico() {
        return notificacaoServico;
    }

    public void setNotificacaoServico(String notificacaoServico) {
        this.notificacaoServico = notificacaoServico;
    }
    
    
    
}
