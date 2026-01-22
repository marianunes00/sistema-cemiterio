package view;


import dao.ServicoDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepultura;
import model.Servico;
import model.Usuario;

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
        
    }
    
    private Usuario usuarioAutenticado;
     //construtor que está recebendo o usuario que foi autenticado na tela de login
    public TelaServico(Usuario usuario){
        initComponents();
        this.usuarioAutenticado = usuario;
        aplicarPermissoes();
        tblServicos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            preencherCamposDaTabela();
        }
}       );
        listar();

    }
    
    //Permissões de tipos de usuario
    private void aplicarPermissoes(){
        
        if(!usuarioAutenticado.getPerfil().equals("Administrador")){//se o perfil for diferente de administrador
            if(usuarioAutenticado.getPerfil().equals("Manutenção")){
                btnCadastrarServicos.setEnabled(false);
                btnDeletarServicos.setEnabled(false);
            }else if(usuarioAutenticado.getPerfil().equals("Atendente")){
                btnDeletarServicos.setEnabled(false);
            }else if(usuarioAutenticado.getPerfil().equals("Visitante")){
                btnCadastrarServicos.setEnabled(false);
                btnDeletarServicos.setEnabled(false);
                btnAtualizarServicos.setEnabled(false);
            }
        }
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
            JOptionPane.showMessageDialog(this, "Serviço cadastrado com sucesso!");
                        
        }catch(Exception e){
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Erro ao cadastrar Serviço");
        
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
            JOptionPane.showMessageDialog(this, "Erro ao listar Serviços");
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

        jPanel1 = new javax.swing.JPanel();
        txtStatusServico = new javax.swing.JTextField();
        txtNotificacaoServico = new javax.swing.JTextField();
        txtDataServico = new javax.swing.JTextField();
        btnCadastrarServicos = new javax.swing.JButton();
        lblTipoServico = new javax.swing.JLabel();
        btnAtualizarServicos = new javax.swing.JButton();
        btnDeletarServicos = new javax.swing.JButton();
        btnListarServicos = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();
        lblDataServico = new javax.swing.JLabel();
        lblStatusServico = new javax.swing.JLabel();
        btnVoltarMenu = new javax.swing.JButton();
        lblSepulturaServico = new javax.swing.JLabel();
        txtSepulturaServico = new javax.swing.JTextField();
        txtTipoServico = new javax.swing.JTextField();
        lblNotificacaoServico = new javax.swing.JLabel();
        lblTituloServicos = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        txtStatusServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusServicoActionPerformed(evt);
            }
        });

        txtNotificacaoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotificacaoServicoActionPerformed(evt);
            }
        });

        txtDataServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataServicoActionPerformed(evt);
            }
        });

        btnCadastrarServicos.setText("Cadastrar");
        btnCadastrarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarServicosActionPerformed(evt);
            }
        });

        lblTipoServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipoServico.setForeground(new java.awt.Color(255, 255, 255));
        lblTipoServico.setText("Tipo do Serviço:");

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

        tblServicos.setBackground(new java.awt.Color(0, 102, 102));
        tblServicos.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        tblServicos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblServicos.setForeground(new java.awt.Color(255, 255, 255));
        tblServicos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Tipo de Serviço", "Data do Serviço", "Status", "Sepultura correspondente", "Notificação interna"
            }
        ));
        jScrollPane1.setViewportView(tblServicos);

        lblDataServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDataServico.setForeground(new java.awt.Color(255, 255, 255));
        lblDataServico.setText("Data do Serviço:");

        lblStatusServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblStatusServico.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusServico.setText("Status do Serviço:");

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        lblSepulturaServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSepulturaServico.setForeground(new java.awt.Color(255, 255, 255));
        lblSepulturaServico.setText("Sepultura:");

        txtSepulturaServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSepulturaServicoActionPerformed(evt);
            }
        });

        txtTipoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoServicoActionPerformed(evt);
            }
        });

        lblNotificacaoServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNotificacaoServico.setForeground(new java.awt.Color(255, 255, 255));
        lblNotificacaoServico.setText("Notificação interna:");

        lblTituloServicos.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        lblTituloServicos.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloServicos.setText("Gerenciamento de Serviços");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Cemitério Descanso Eterno");

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Área de serviços");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(38, 38, 38)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1299, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnVoltarMenu)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(394, 394, 394)
                                .addComponent(lblTituloServicos)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel3)
                                .addGap(152, 152, 152))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(btnCadastrarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnAtualizarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(34, 34, 34)
                        .addComponent(btnDeletarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41)
                        .addComponent(btnListarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblNotificacaoServico, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(732, 732, 732))
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblDataServico)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(lblSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(lblTituloServicos)
                        .addGap(3, 3, 3)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(btnVoltarMenu)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoServico)
                            .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataServico)
                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatusServico)
                    .addComponent(txtStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSepulturaServico)
                    .addComponent(txtSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNotificacaoServico)
                    .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarServicos)
                    .addComponent(btnAtualizarServicos)
                    .addComponent(btnDeletarServicos)
                    .addComponent(btnListarServicos))
                .addGap(78, 78, 78)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 252, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        Menu menu = new Menu(usuarioAutenticado);          // nome da sua classe de menu
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
    private javax.swing.JPanel jPanel1;
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
