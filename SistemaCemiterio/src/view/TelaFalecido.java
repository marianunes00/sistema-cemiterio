 package view;

import java.time.format.DateTimeFormatter;
import dao.FalecidoDao;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Falecido;
import model.Sepultura;

/**
 *
 * @author Váleria Matias
 */
public class TelaFalecido extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaFalecido.class.getName());

    /**
     * Creates new form Telasimulacao
     */
    public TelaFalecido() {
        initComponents();
        tblFalecidos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            preencherCamposDaTabela();
        }
}       );
        listar();
    }
    
    
    private void cadastrar(){
         try{
            // Converter data de nascimento e falecimento
            DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataNascimento = LocalDate.parse(txtDataNascimento.getText(),brasil);
            LocalDate dataFalecimento = LocalDate.parse(txtDataFalecimento.getText(),brasil);

            // Converte certidão de óbito (entrada: "sim" ou "não")
            boolean possuiCertidao = txtCertidaoObito.getText().equalsIgnoreCase("sim");
            
            //Pega o texto do campo onde o usuário digitou o ID da sepultura e converte para inteiro
            int idSepultura = Integer.parseInt(txtSepulturaFalecido.getText());


            // Cria sepultura apenas com o id
            Sepultura sepult = new Sepultura();
            sepult.setIdSepultura(idSepultura);

            
            //Cria objeto falecido
            Falecido f = new Falecido(
               0,// id gerado automaticamente
               txtNomeCompleto.getText(),
               dataNascimento,
               possuiCertidao,
               txtCpf.getText(),
               sepult,
               dataFalecimento,
               txtFamiliarResponsavelFalecido.getText()
               );
            
               //inserindo no banco
            new FalecidoDao().inserir(f);
               JOptionPane.showMessageDialog(this,"Falecido cadastrado com sucesso!");
               
               listar();
               limparCampos();
               
        } catch(Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this,"Erro ao cadastrar");
        }
    }               
    
    private void listar(){
        try {
            DefaultTableModel modelo = (DefaultTableModel) tblFalecidos.getModel();
            modelo.setRowCount(0);
            
            List<Falecido> lista = new FalecidoDao().listarTodos();
            
            for(Falecido f : lista) {
                System.out.println("Encontrei: " + f.getNomeCompleto()); // ADICIONE ISSO
                String temCertidao = f.isPossuiCertidaoObito() ? "Sim" : "Não";
                
                modelo.addRow(new Object[]{f.getIdFalecido(),f.getNomeCompleto(),f.getCpf(), temCertidao,
                f.getSepultura().getIdSepultura(),f.getDataNascimento(),f.getDataFalecimento(),f.getFamiliarResponsavel()});         
                }
            
        }catch (Exception e) {
            e.printStackTrace();
        }
    }    
    
    private void deletar(){
        //pega a linha que o usuário selecionar
        int row = tblFalecidos.getSelectedRow();
        //Quando nenhuma linha é escolhida
        if (row == -1) return;
        
        int id = Integer.parseInt(tblFalecidos.getValueAt(row,0).toString());
        
        try{
         // chama o DAO para deletar
            new FalecidoDao().deletar(id);
            //Atualiza a lista
            listar();
            
            JOptionPane.showMessageDialog(this,"Falecido com ID: \"" + id + "\" foi excluído com sucesso!");
            
            limparCampos();
            
        } catch(Exception e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(this,"Falha ao Excluir falecido com ID:\""+ id +"\"");
            }
        }
    
    private void atualizar(){
        //pega a linha que o usuário selecionar
        int row = tblFalecidos.getSelectedRow();
        //Quando nenhuma linha é escolhida
        if (row == -1) return;
        
        int id = Integer.parseInt(tblFalecidos.getValueAt(row,0).toString());
        
        try{
            
            // ajusta o modelo da data para o formato BR
            DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            
            //Pega o texto do campo onde o usuário digitou o ID da sepultura e converte para inteiro
            int idSepultura = Integer.parseInt(txtSepulturaFalecido.getText());
            
            // Cria sepultura apenas com o id
            Sepultura sepult = new Sepultura();
            sepult.setIdSepultura(idSepultura);

            //Cria objeto falecido
            Falecido f = new Falecido(
               id,// id gerado automaticamente
               txtNomeCompleto.getText(),
               LocalDate.parse(txtDataNascimento.getText(), brasil),   
               txtCertidaoObito.getText().equalsIgnoreCase("Sim"),
               txtCpf.getText(),
               sepult,
               LocalDate.parse(txtDataFalecimento.getText(),brasil),
               txtFamiliarResponsavelFalecido.getText()
               );
           
         // chama o DAO para atualizar os dados
            new FalecidoDao().atualizar(f);
            
            //Atualiza a lista
            listar();
             JOptionPane.showMessageDialog(this,
            "Dados do falecido \"" + f.getNomeCompleto() + "\" atualizados com sucesso!");    
             
             limparCampos();
             
        } catch(Exception e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(this,"Falha ao atualizar os dados do falecido com ID:");
            }
        }
    
    private void limparCampos(){
        txtNomeCompleto.setText("");
        txtDataNascimento.setText("");
        txtCertidaoObito.setText("");
        txtCpf.setText("");
        txtSepulturaFalecido.setText("");
        txtDataFalecimento.setText("");
        txtFamiliarResponsavelFalecido.setText("");
        txtNomeCompleto.requestFocus();//O cursor volta pro inicio
    }
    
    private void preencherCamposDaTabela() {
        int row = tblFalecidos.getSelectedRow();
        if (row == -1) return;

        Object nome       = tblFalecidos.getValueAt(row, 1);
        Object cpf        = tblFalecidos.getValueAt(row, 2);
        Object certidao   = tblFalecidos.getValueAt(row, 3);
        Object idSepult   = tblFalecidos.getValueAt(row, 4);
        Object dataNasc   = tblFalecidos.getValueAt(row, 5);
        Object dataFalec  = tblFalecidos.getValueAt(row, 6);
        Object familiar   = tblFalecidos.getValueAt(row, 7);

        txtNomeCompleto.setText(nome != null ? nome.toString() : "");
        txtCpf.setText(cpf != null ? cpf.toString() : "");
        txtCertidaoObito.setText(certidao != null ? certidao.toString() : "");
        txtSepulturaFalecido.setText(idSepult != null ? idSepult.toString() : "");
        txtFamiliarResponsavelFalecido.setText(familiar != null ? familiar.toString() : "");

        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        // Data de nascimento
        if (dataNasc != null) {
            if (dataNasc instanceof java.time.LocalDate ld) {
                txtDataNascimento.setText(ld.format(brasil));
            } else {
                txtDataNascimento.setText(dataNasc.toString());
            }
        } else {
            txtDataNascimento.setText("");
        }

        // Data de falecimento
        if (dataFalec != null) {
            if (dataFalec instanceof java.time.LocalDate ld2) {
                txtDataFalecimento.setText(ld2.format(brasil));
            } else {
                txtDataFalecimento.setText(dataFalec.toString());
            }
        } else {
            txtDataFalecimento.setText("");
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

        txtNomeCompleto = new javax.swing.JTextField();
        txtCertidaoObito = new javax.swing.JTextField();
        txtDataNascimento = new javax.swing.JTextField();
        lblNomeCompleto = new javax.swing.JLabel();
        lblTituloFalecidos = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblDataNascimento = new javax.swing.JLabel();
        lblCertidaoObito = new javax.swing.JLabel();
        lblCpf = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        lblSepulturaFalecido = new javax.swing.JLabel();
        txtSepulturaFalecido = new javax.swing.JTextField();
        btnCadastrarFalecido = new javax.swing.JButton();
        btnAtualizarFalecido = new javax.swing.JButton();
        btnDeletarFalecido = new javax.swing.JButton();
        btnListarFalecidos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFalecidos = new javax.swing.JTable();
        lblDataFalecimento = new javax.swing.JLabel();
        txtDataFalecimento = new javax.swing.JTextField();
        lblFamiliarResponsavelFalecido = new javax.swing.JLabel();
        txtFamiliarResponsavelFalecido = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        txtNomeCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCompletoActionPerformed(evt);
            }
        });

        txtCertidaoObito.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCertidaoObitoActionPerformed(evt);
            }
        });

        txtDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataNascimentoActionPerformed(evt);
            }
        });

        lblNomeCompleto.setText("Nome Completo:");

        lblTituloFalecidos.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTituloFalecidos.setText("Sistema de Gerenciamento de Cemitérios");

        jLabel3.setText("Área de falecidos");

        jLabel4.setText("Cemitério Descanso Eterno");

        lblDataNascimento.setText("Data de Nascimento:");

        lblCertidaoObito.setText("Possui Certidão de óbito?");

        lblCpf.setText("CPF:");

        txtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfActionPerformed(evt);
            }
        });

        lblSepulturaFalecido.setText("Sepultura:");

        txtSepulturaFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSepulturaFalecidoActionPerformed(evt);
            }
        });

        btnCadastrarFalecido.setText("Cadastrar");
        btnCadastrarFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarFalecidoActionPerformed(evt);
            }
        });

        btnAtualizarFalecido.setText("Atualizar");
        btnAtualizarFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarFalecidoActionPerformed(evt);
            }
        });

        btnDeletarFalecido.setText("Deletar");
        btnDeletarFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarFalecidoActionPerformed(evt);
            }
        });

        btnListarFalecidos.setText("Listar");
        btnListarFalecidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarFalecidosActionPerformed(evt);
            }
        });

        tblFalecidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome Completo", "CPF", "Possui Certidão de Óbito?", "Sepultura", "Data de Nascimento", "Data de Falecimento", "Familiar Responsável"
            }
        ));
        jScrollPane1.setViewportView(tblFalecidos);

        lblDataFalecimento.setText("Data de Falecimento:");

        txtDataFalecimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFalecimentoActionPerformed(evt);
            }
        });

        lblFamiliarResponsavelFalecido.setText("Familiar Responsável:");

        txtFamiliarResponsavelFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelFalecidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(btnCadastrarFalecido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnAtualizarFalecido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnDeletarFalecido)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnListarFalecidos, javax.swing.GroupLayout.DEFAULT_SIZE, 98, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(lblNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtNomeCompleto))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblCpf, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblSepulturaFalecido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDataNascimento)
                                            .addComponent(lblCertidaoObito))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(lblDataFalecimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtCertidaoObito)
                                    .addComponent(txtDataNascimento)
                                    .addComponent(txtCpf, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                    .addComponent(txtSepulturaFalecido, javax.swing.GroupLayout.DEFAULT_SIZE, 194, Short.MAX_VALUE)
                                    .addComponent(txtDataFalecimento)
                                    .addComponent(txtFamiliarResponsavelFalecido))))
                        .addGap(458, 458, 458))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(9, 9, 9)
                                .addComponent(jLabel4)
                                .addGap(77, 77, 77)
                                .addComponent(lblTituloFalecidos)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)))
                        .addGap(28, 28, 28))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(lblTituloFalecidos))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNomeCompleto)
                    .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataNascimento)
                    .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCertidaoObito)
                    .addComponent(txtCertidaoObito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpf)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSepulturaFalecido)
                    .addComponent(txtSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblDataFalecimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(txtDataFalecimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtFamiliarResponsavelFalecido)
                    .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarFalecido)
                    .addComponent(btnAtualizarFalecido)
                    .addComponent(btnDeletarFalecido)
                    .addComponent(btnListarFalecidos))
                .addGap(36, 36, 36)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCompletoActionPerformed

    private void txtDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataNascimentoActionPerformed

    private void txtCertidaoObitoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCertidaoObitoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCertidaoObitoActionPerformed

    private void txtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfActionPerformed

    private void txtSepulturaFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtSepulturaFalecidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtSepulturaFalecidoActionPerformed

    private void btnDeletarFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeletarFalecidoActionPerformed
        deletar();

    }//GEN-LAST:event_btnDeletarFalecidoActionPerformed

    private void txtDataFalecimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataFalecimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataFalecimentoActionPerformed

    private void txtFamiliarResponsavelFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtFamiliarResponsavelFalecidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtFamiliarResponsavelFalecidoActionPerformed

    private void btnCadastrarFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCadastrarFalecidoActionPerformed
        cadastrar();
    }//GEN-LAST:event_btnCadastrarFalecidoActionPerformed

    private void btnAtualizarFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarFalecidoActionPerformed
        atualizar();
    }//GEN-LAST:event_btnAtualizarFalecidoActionPerformed

    private void btnListarFalecidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnListarFalecidosActionPerformed
        listar();
    }//GEN-LAST:event_btnListarFalecidosActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> new TelaFalecido().setVisible(true));
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarFalecido;
    private javax.swing.JButton btnCadastrarFalecido;
    private javax.swing.JButton btnDeletarFalecido;
    private javax.swing.JButton btnListarFalecidos;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCertidaoObito;
    private javax.swing.JLabel lblCpf;
    private javax.swing.JLabel lblDataFalecimento;
    private javax.swing.JLabel lblDataNascimento;
    private javax.swing.JLabel lblFamiliarResponsavelFalecido;
    private javax.swing.JLabel lblNomeCompleto;
    private javax.swing.JLabel lblSepulturaFalecido;
    private javax.swing.JLabel lblTituloFalecidos;
    private javax.swing.JTable tblFalecidos;
    private javax.swing.JTextField txtCertidaoObito;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtDataFalecimento;
    private javax.swing.JTextField txtDataNascimento;
    private javax.swing.JTextField txtFamiliarResponsavelFalecido;
    private javax.swing.JTextField txtNomeCompleto;
    private javax.swing.JTextField txtSepulturaFalecido;
    // End of variables declaration//GEN-END:variables
}