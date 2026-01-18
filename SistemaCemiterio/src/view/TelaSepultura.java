package view;

import dao.SepulturaDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepultura;

/**
 *
 * @author Váleria Matias
 */
public class TelaSepultura extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaSepultura.class.getName());

    /**
     * Creates new form Telasimulacao
     */
    public TelaSepultura() {
        initComponents();
        tblSepulturas.addMouseListener(new java.awt.event.MouseAdapter() {
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
            LocalDate dataCriacao = LocalDate.parse(txtDataCriacao.getText(), brasil);
            
            //Cria o obj sepultura e dps seta os atributos com base nos valores inseridos nos campos
            //o s.setLote é a referencia ao atributo, o txtLote é o nome do campo, e getText é o metodo para pegar
            // o valor do campo com base no tipo;[
            Sepultura s = new Sepultura();
            s.setLote(txtLote.getText());
            s.setTipoSepultura(txtTipoSepultura.getText());
            s.setStatusSepultura(txtStatusSepultura.getText());
            s.setFamiliarResponsavel(txtFamiliarResponsavelSepultura.getText());
            s.setDataCriacao(dataCriacao);
            
            //Cria o objeto Dao e depois chama o metodo de dao, inserir;
            SepulturaDao dao = new SepulturaDao();
            dao.inserir(s);
            //metodo listar e limpar campo
            listar();
            limparCampos();
                        
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Sepultura");
        
        }
        
    
    
    }
    
    private void atualizar() {
    int row = tblSepulturas.getSelectedRow();
    if (row == -1) return; // nada selecionado

    try {
        int id = Integer.parseInt(tblSepulturas.getValueAt(row, 0).toString());

        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        LocalDate dataCriacao = LocalDate.parse(txtDataCriacao.getText(), brasil);

        Sepultura s = new Sepultura();
        s.setIdSepultura(id);
        s.setLote(txtLote.getText());
        s.setTipoSepultura(txtTipoSepultura.getText());
        s.setStatusSepultura(txtStatusSepultura.getText());
        s.setFamiliarResponsavel(txtFamiliarResponsavelSepultura.getText());
        s.setDataCriacao(dataCriacao);

        SepulturaDao dao = new SepulturaDao();
        dao.atualizar(s);

        listar();
        limparCampos();
        JOptionPane.showMessageDialog(this, "Sepultura atualizada com sucesso!");
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao atualizar Sepultura");
    }
}

    private void deletar() {
        int row = tblSepulturas.getSelectedRow();
        if (row == -1) return;

        try {
            int id = Integer.parseInt(tblSepulturas.getValueAt(row, 0).toString());

            SepulturaDao dao = new SepulturaDao();
            dao.deletar(id);

            listar();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Sepultura excluída com sucesso!");
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao excluir Sepultura");
        }
}
    private void listar() {
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblSepulturas.getModel();
            modelo.setRowCount(0);

            SepulturaDao dao = new SepulturaDao();
            List<Sepultura> lista = dao.listarTodos();

            for (Sepultura s : lista) {
                modelo.addRow(new Object[] {
                    s.getIdSepultura(),
                    s.getLote(),
                    s.getTipoSepultura(),
                    s.getStatusSepultura(),
                    s.getFamiliarResponsavel(),
                    s.getDataCriacao()
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao listar Sepulturas");
       }
}
    private void limparCampos() {
        txtLote.setText("");
        txtTipoSepultura.setText("");
        txtStatusSepultura.setText("");
        txtFamiliarResponsavelSepultura.setText("");
        txtDataCriacao.setText("");
        txtLote.requestFocus();
}

    private void preencherCamposDaTabela() {
        int row = tblSepulturas.getSelectedRow();
        if (row == -1) return;

        Object lote     = tblSepulturas.getValueAt(row, 1);
        Object tipo     = tblSepulturas.getValueAt(row, 2);
        Object status   = tblSepulturas.getValueAt(row, 3);
        Object familiar = tblSepulturas.getValueAt(row, 4);
        Object data     = tblSepulturas.getValueAt(row, 5);

        txtLote.setText(lote != null ? lote.toString() : "");
        txtTipoSepultura.setText(tipo != null ? tipo.toString() : "");
        txtStatusSepultura.setText(status != null ? status.toString() : "");
        txtFamiliarResponsavelSepultura.setText(familiar != null ? familiar.toString() : "");

        if (data != null) {
            if (data instanceof java.time.LocalDate ld) {
                DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                txtDataCriacao.setText(ld.format(brasil));
            } else {
            // se já vier como String (ex.: do próprio model ou do DAO)
                txtDataCriacao.setText(data.toString());
            }
        } else {
            txtDataCriacao.setText("");
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

        txtLote = new javax.swing.JTextField();
        txtStatusSepultura = new javax.swing.JTextField();
        txtTipoSepultura = new javax.swing.JTextField();
        lblLote = new javax.swing.JLabel();
        lblTituloSepulturas = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblTipoSepultura = new javax.swing.JLabel();
        lblStatusSepultura = new javax.swing.JLabel();
        lblFamiliarResponsavelSepultura = new javax.swing.JLabel();
        txtFamiliarResponsavelSepultura = new javax.swing.JTextField();
        lblDataCriacao = new javax.swing.JLabel();
        txtDataCriacao = new javax.swing.JTextField();
        btnCadastrarSepultura = new javax.swing.JButton();
        btnAtualizarSepultura = new javax.swing.JButton();
        btnDeletarSepultura = new javax.swing.JButton();
        btnListarSepulturas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSepulturas = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoteActionPerformed(evt);
            }
        });

        txtStatusSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusSepulturaActionPerformed(evt);
            }
        });

        txtTipoSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoSepulturaActionPerformed(evt);
            }
        });

        lblLote.setText("Lote:");

        lblTituloSepulturas.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloSepulturas.setText("Sistema de Gerenciamento de Cemitérios");

        jLabel3.setText("Área das Sepulturas");

        jLabel4.setText("Cemitério Descanso Eterno");

        lblTipoSepultura.setText("Tipo da Sepultura:");

        lblStatusSepultura.setText("Status da Sepultura:");

        lblFamiliarResponsavelSepultura.setText("Familiar Responsável:");

        txtFamiliarResponsavelSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelSepulturaActionPerformed(evt);
            }
        });

        lblDataCriacao.setText("Data de Criação:");

        txtDataCriacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataCriacaoActionPerformed(evt);
            }
        });

        btnCadastrarSepultura.setText("Cadastrar");
        btnCadastrarSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarSepulturaActionPerformed(evt);
            }
        });

        btnAtualizarSepultura.setText("Atualizar");
        btnAtualizarSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarSepulturaActionPerformed(evt);
            }
        });

        btnDeletarSepultura.setText("Deletar");
        btnDeletarSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarSepulturaActionPerformed(evt);
            }
        });

        btnListarSepulturas.setText("Listar");
        btnListarSepulturas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarSepulturasActionPerformed(evt);
            }
        });

        tblSepulturas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Id", "Lote", "Tipo  da Sepultura", "Status da Sepultura", "familiar Responsável", "Data de criacão"
            }
        ));
        jScrollPane1.setViewportView(tblSepulturas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 64, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCadastrarSepultura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizarSepultura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeletarSepultura)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnListarSepulturas)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTipoSepultura)
                                    .addComponent(lblStatusSepultura)
                                    .addComponent(lblFamiliarResponsavelSepultura, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(lblDataCriacao))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtStatusSepultura)
                                    .addComponent(txtTipoSepultura)
                                    .addComponent(txtFamiliarResponsavelSepultura, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                    .addComponent(txtDataCriacao, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblLote)
                                .addGap(18, 18, 18)
                                .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 279, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(458, 458, 458))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(77, 77, 77)
                        .addComponent(lblTituloSepulturas)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3)
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(lblTituloSepulturas))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblLote)
                    .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoSepultura)
                    .addComponent(txtTipoSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatusSepultura)
                    .addComponent(txtStatusSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFamiliarResponsavelSepultura)
                    .addComponent(txtFamiliarResponsavelSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataCriacao)
                    .addComponent(txtDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarSepultura)
                    .addComponent(btnAtualizarSepultura)
                    .addComponent(btnDeletarSepultura)
                    .addComponent(btnListarSepulturas))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 351, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtLoteActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtLoteActionPerformed

    private void txtTipoSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTipoSepulturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTipoSepulturaActionPerformed

    private void txtStatusSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtStatusSepulturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtStatusSepulturaActionPerformed

    private void txtFamiliarResponsavelSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFamiliarResponsavelSepulturaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFamiliarResponsavelSepulturaActionPerformed

    private void txtDataCriacaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataCriacaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataCriacaoActionPerformed

    private void btnDeletarSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarSepulturaActionPerformed
        // TODO add your handling code here:
        deletar();
    }//GEN-LAST:event_btnDeletarSepulturaActionPerformed

    private void btnCadastrarSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarSepulturaActionPerformed
        // TODO add your handling code here:
      cadastrar();
    }//GEN-LAST:event_btnCadastrarSepulturaActionPerformed

    private void btnAtualizarSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarSepulturaActionPerformed
        // TODO add your handling code here:
        atualizar();
    }//GEN-LAST:event_btnAtualizarSepulturaActionPerformed

    private void btnListarSepulturasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarSepulturasActionPerformed
        // TODO add your handling code here:
        listar();
    }//GEN-LAST:event_btnListarSepulturasActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TelaSepultura().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarSepultura;
    private javax.swing.JButton btnCadastrarSepultura;
    private javax.swing.JButton btnDeletarSepultura;
    private javax.swing.JButton btnListarSepulturas;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataCriacao;
    private javax.swing.JLabel lblFamiliarResponsavelSepultura;
    private javax.swing.JLabel lblLote;
    private javax.swing.JLabel lblStatusSepultura;
    private javax.swing.JLabel lblTipoSepultura;
    private javax.swing.JLabel lblTituloSepulturas;
    private javax.swing.JTable tblSepulturas;
    private javax.swing.JTextField txtDataCriacao;
    private javax.swing.JTextField txtFamiliarResponsavelSepultura;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtStatusSepultura;
    private javax.swing.JTextField txtTipoSepultura;
    // End of variables declaration//GEN-END:variables
}
