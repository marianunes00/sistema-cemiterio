/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package view;


import dao.ServicoDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepultura;
import model.Servico;
import view.Menu;

/**
 *
 * @author Váleria Matias
 */
public class TelaServico extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaServico.class.getName());

    /**
     * Creates new form Telasimulacao
     */
    public TelaServico() {
        initComponents();
        tblServicos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            preencherCamposDaTabela();
        }
}       );
        listar();
    }
    
    private void cadastrar(){
        try{
            //ajusta a data para o padao do Brasil
            DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataServico = LocalDate.parse(txtDataServico.getText(), brasil);
            int idSepultura = Integer.parseInt(txtSepulturaServico.getText());
            
            Sepultura sepult = new Sepultura();
            sepult.setIdSepultura(idSepultura);
            //Cria o obj sepultura e dps seta os atributos com base nos valores inseridos nos campos
            //o s.setLote é a referencia ao atributo, o txtLote é o nome do campo, e getText é o metodo para pegar
            // o valor do campo com base no tipo;[
            Servico se = new Servico();
            se.setTipoServico(txtTipoServico.getText());
            se.setStatusServico(txtStatusServico.getText());
            se.setSepultura(sepult);
            se.setNotificacaoServico(txtNotificacaoServico.getText());
            se.setDataServico(dataServico);
            
            //Cria o objeto Dao e depois chama o metodo de dao, inserir;
           ServicoDao dao = new ServicoDao();
            dao.inserir(se);
            //metodo listar e limpar campo
            listar();
            limparCampos();
                        
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Sepultura");
        
        }
        
    
    
    }
    
    private void atualizar() {
    int row = tblServicos.getSelectedRow();
    if (row == -1) return; // nada selecionado

    try {
        int id = Integer.parseInt(tblServicos.getValueAt(row, 0).toString());

        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        LocalDate dataServico = LocalDate.parse(txtDataServico.getText(), brasil);
        
        //mesmo processo para pegar o id da sepultura
        int idSepultura = Integer.parseInt(txtSepulturaServico.getText());
        Sepultura sepult = new Sepultura();
        sepult.setIdSepultura(idSepultura);

        Servico se = new Servico();
            se.setIdServico(id);
            se.setTipoServico(txtTipoServico.getText());
            se.setStatusServico(txtStatusServico.getText());
            se.setSepultura(sepult);
            se.setNotificacaoServico(txtNotificacaoServico.getText());
            se.setDataServico(dataServico);

        ServicoDao dao = new ServicoDao();
        dao.atualizar(se);

        listar();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Servico atualizado com sucesso!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao atualizar Servico");
    }
}

    private void deletar() {
        int row = tblServicos.getSelectedRow();
        if (row == -1) return;

        try {
            int id = Integer.parseInt(tblServicos.getValueAt(row, 0).toString());

            ServicoDao dao = new ServicoDao();
            dao.deletar(id);

            listar();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Serviço excluído com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao excluir Serviço");
        }
}
    private void listar() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblServicos.getModel();
            modelo.setRowCount(0);

            ServicoDao dao = new ServicoDao();
            List<Servico> lista = dao.listarTodos();

            for (Servico s : lista) {
                modelo.addRow(new Object[] {
                    s.getIdServico(),
                    s.getTipoServico(),
                    s.getDataServico(),      // tem que estar igual a ordem da tabela
                    s.getStatusServico(),
                    s.getSepultura().getIdSepultura(), 
                    s.getNotificacaoServico()
});
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao listar Sepulturas");
       }
}
    private void limparCampos() {
        txtSepulturaServico.setText("");
        txtStatusServico.setText("");
        txtTipoServico.setText("");
        txtDataServico.setText("");
        txtNotificacaoServico.setText("");
          
  }
    
     private void preencherCamposDaTabela() {
        //pegando a linha
        int row = tblServicos.getSelectedRow();
        if (row == -1) return;

        //mapeando cada coluna da tabela para uma variável do formulário.
        Object tipoServico       = tblServicos.getValueAt(row, 1);
        Object dataServ        = tblServicos.getValueAt(row, 2);
        Object statusServ   = tblServicos.getValueAt(row, 3);
        Object idSepult   = tblServicos.getValueAt(row, 4);
        Object notificacao   = tblServicos.getValueAt(row, 5);
       
        txtTipoServico.setText(tipoServico != null ? tipoServico.toString() : "");
        txtStatusServico.setText(statusServ != null ? statusServ.toString() : "");
        txtSepulturaServico.setText(idSepult != null ? idSepult.toString() : "");
        txtNotificacaoServico.setText(notificacao != null ? notificacao.toString() : "");

        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Tratamento da Data do servico, verifica se é do tipo localdate se for aplica a formatação
        //se for de outro tipo converte pra string e senão coloca como vazio
        if (dataServ != null) {
            if (dataServ instanceof java.time.LocalDate ld) {
                txtDataServico.setText(ld.format(brasil));
            } else {
                txtDataServico.setText(dataServ.toString());
            }
        } else {
            txtDataServico.setText("");
        }

        
}

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtTipoServico = new javax.swing.JTextField();
        txtStatusServico = new javax.swing.JTextField();
        txtDataServico = new javax.swing.JTextField();
        lblTipoServico = new javax.swing.JLabel();
        lblTituloServicos = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDataServico = new javax.swing.JLabel();
        lblStatusServico = new javax.swing.JLabel();
        lblSepulturaServico = new javax.swing.JLabel();
        txtSepulturaServico = new javax.swing.JTextField();
        lblNotificacaoServico = new javax.swing.JLabel();
        txtNotificacaoServico = new javax.swing.JTextField();
        btnCadastrarServicos = new javax.swing.JButton();
        btnAtualizarServicos = new javax.swing.JButton();
        btnDeletarServicos = new javax.swing.JButton();
        btnListarServicos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();
        btnVoltarMenu = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtTipoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoServicoActionPerformed(evt);
            }
        });

        txtStatusServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusServicoActionPerformed(evt);
            }
        });

        txtDataServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataServicoActionPerformed(evt);
            }
        });

        lblTipoServico.setText("Tipo do Serviço:");

        lblTituloServicos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloServicos.setText("Sistema de Gerenciamento de Cemitérios");

        jLabel3.setText("Área de serviços");

        jLabel4.setText("Cemitério Descanso Eterno");

        lblDataServico.setText("Data do Serviço:");

        lblStatusServico.setText("Status do Serviço:");

        lblSepulturaServico.setText("Sepultura:");

        txtSepulturaServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSepulturaServicoActionPerformed(evt);
            }
        });

        lblNotificacaoServico.setText("Notificação interna:");

        txtNotificacaoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotificacaoServicoActionPerformed(evt);
            }
        });

        btnCadastrarServicos.setText("Cadastrar");
        btnCadastrarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarServicosActionPerformed(evt);
            }
        });

        btnAtualizarServicos.setText("Atualizar");
        btnAtualizarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarServicosActionPerformed(evt);
            }
        });

        btnDeletarServicos.setText("Deletar");
        btnDeletarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarServicosActionPerformed(evt);
            }
        });

        btnListarServicos.setText("Listar");
        btnListarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarServicosActionPerformed(evt);
            }
        });

        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tipo de Serviço", "Data do Serviço", "Status", "Sepultura correspondente", "Notificação interna"
            }
        ));
        jScrollPane1.setViewportView(tblServicos);

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnCadastrarServicos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAtualizarServicos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeletarServicos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnListarServicos, javax.swing.GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTipoServico))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNotificacaoServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDataServico)
                                            .addComponent(lblStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lblSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtStatusServico)
                                    .addComponent(txtSepulturaServico, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                                    .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                                    .addComponent(txtDataServico, javax.swing.GroupLayout.Alignment.TRAILING))))
                        .addGap(458, 458, 458))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(jLabel4))
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnVoltarMenu)
                                .addGap(295, 295, 295)
                                .addComponent(lblTituloServicos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)))
                        .addGap(31, 31, 31))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lblTituloServicos)
                    .addComponent(btnVoltarMenu))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel4)
                .addGap(13, 13, 13)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoServico)
                    .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataServico)
                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatusServico)
                    .addComponent(txtStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSepulturaServico)
                    .addComponent(txtSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNotificacaoServico)
                    .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarServicos)
                    .addComponent(btnAtualizarServicos)
                    .addComponent(btnDeletarServicos)
                    .addComponent(btnListarServicos))
                .addGap(37, 37, 37)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 374, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtTipoServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoServicoActionPerformed

    private void txtDataServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataServicoActionPerformed

    private void txtStatusServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusServicoActionPerformed

    private void txtSepulturaServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSepulturaServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSepulturaServicoActionPerformed

    private void txtNotificacaoServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNotificacaoServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNotificacaoServicoActionPerformed

    private void btnDeletarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarServicosActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeletarServicosActionPerformed

    private void btnCadastrarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarServicosActionPerformed
        // TODO add your handling code here:
        cadastrar();
    }//GEN-LAST:event_btnCadastrarServicosActionPerformed

    private void btnListarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarServicosActionPerformed
        // TODO add your handling code here:
        listar();
    }//GEN-LAST:event_btnListarServicosActionPerformed

    private void btnAtualizarServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarServicosActionPerformed
        atualizar();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAtualizarServicosActionPerformed

    private void btnVoltarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarMenuActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu();          // nome da sua classe de menu
        menu.setLocationRelativeTo(this);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarMenuActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new TelaServico().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarServicos;
    private javax.swing.JButton btnCadastrarServicos;
    private javax.swing.JButton btnDeletarServicos;
    private javax.swing.JButton btnListarServicos;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataServico;
    private javax.swing.JLabel lblNotificacaoServico;
    private javax.swing.JLabel lblSepulturaServico;
    private javax.swing.JLabel lblStatusServico;
    private javax.swing.JLabel lblTipoServico;
    private javax.swing.JLabel lblTituloServicos;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTextField txtDataServico;
    private javax.swing.JTextField txtNotificacaoServico;
    private javax.swing.JTextField txtSepulturaServico;
    private javax.swing.JTextField txtStatusServico;
    private javax.swing.JTextField txtTipoServico;
    // End of variables declaration//GEN-END:variables
}
