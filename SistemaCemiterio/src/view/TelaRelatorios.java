package view;

import dao.FalecidoDao;
import dao.SepulturaDao;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import model.Falecido;
import model.Sepultura;
import model.Usuario;
import view.Menu;
import util.RelatorioPDFUtil;




/**
 *
 * @author Váleria Matias
 */
public class TelaRelatorios extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaRelatorios.class.getName());
    
    /**
     * Creates new form TelaRelatorio
     */
    private Usuario usuarioAutenticado;
    
     //construtor que está recebendo o usuario que foi autenticado na tela de login
    public TelaRelatorios(Usuario usuario){
        initComponents();
        this.usuarioAutenticado = usuario;
    }

    
    
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblRelatorioSepultura = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblRelatorioFalecidos = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        btnPdfSepultura = new javax.swing.JButton();
        btnPdfFalecidos = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        btnVoltarAoMenu = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        cbStatus = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        cbTipo = new javax.swing.JComboBox<>();
        btnGerarTipo = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtIdSepulturaFal = new javax.swing.JTextField();
        btnGerarId = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

        tblRelatorioSepultura.setBackground(new java.awt.Color(0, 102, 102));
        tblRelatorioSepultura.setForeground(new java.awt.Color(255, 255, 255));
        tblRelatorioSepultura.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Lote", "Tipo", "Status", "Familiar Responsável"
            }
        ));
        tblRelatorioSepultura.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblRelatorioSepulturaAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane1.setViewportView(tblRelatorioSepultura);

        tblRelatorioFalecidos.setBackground(new java.awt.Color(0, 102, 102));
        tblRelatorioFalecidos.setForeground(new java.awt.Color(255, 255, 255));
        tblRelatorioFalecidos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome ", "Data Nascimento", "Certidão de obito", "Cpf", "Sepultura", "Familiar Responsável"
            }
        ));
        tblRelatorioFalecidos.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                tblRelatorioFalecidosAncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jScrollPane2.setViewportView(tblRelatorioFalecidos);

        jLabel7.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Relátorio - Sepultura");

        jLabel8.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Relátorio - Falecidos");

        btnPdfSepultura.setText("Exportar PDF");
        btnPdfSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfSepulturaActionPerformed(evt);
            }
        });

        btnPdfFalecidos.setText("Exportar PDF");
        btnPdfFalecidos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPdfFalecidosActionPerformed(evt);
            }
        });

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangularnova.png"))); // NOI18N

        jLabel1.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 102, 102));
        jLabel1.setText("Relátorios");

        btnVoltarAoMenu.setBackground(new java.awt.Color(0, 102, 102));
        btnVoltarAoMenu.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        btnVoltarAoMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltarAoMenu.setText("Voltar ao menu");
        btnVoltarAoMenu.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btnVoltarAoMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarAoMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addComponent(btnVoltarAoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(414, 414, 414)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVoltarAoMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE)
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Área Sepultura", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Garamond", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Informe o status da sepultura para o relátorio");

        cbStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "DISPONIVEL", "OCUPADO ", "RESERVADO", "MANUTENCAO" }));
        cbStatus.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbStatus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbStatusActionPerformed(evt);
            }
        });

        jButton1.setText("Gerar");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Informe o tipo da sepultura para o relátorio");

        cbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TODOS", "PERPETUO", "OSSUARIO", "TEMPORARIO", "GAVETA" }));
        cbTipo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        cbTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbTipoActionPerformed(evt);
            }
        });

        btnGerarTipo.setText("Gerar");
        btnGerarTipo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarTipoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(32, 32, 32)
                        .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnGerarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cbStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(cbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Área Falecido", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Garamond", 1, 14), new java.awt.Color(0, 102, 102))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(0, 102, 102));
        jLabel6.setText("Informe o ID da sepultura para ver os falecidos");

        txtIdSepulturaFal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtIdSepulturaFalActionPerformed(evt);
            }
        });

        btnGerarId.setText("Gerar");
        btnGerarId.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGerarIdActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel6)
                .addGap(18, 18, 18)
                .addComponent(txtIdSepulturaFal, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnGerarId, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(22, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtIdSepulturaFal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGerarId, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(144, 144, 144)
                .addComponent(jLabel7)
                .addGap(42, 42, 42)
                .addComponent(btnPdfSepultura)
                .addGap(421, 421, 421)
                .addComponent(jLabel8)
                .addGap(39, 39, 39)
                .addComponent(btnPdfFalecidos)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(63, 63, 63)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 55, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(32, 32, 32)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(btnPdfSepultura)
                    .addComponent(jLabel8)
                    .addComponent(btnPdfFalecidos))
                .addGap(31, 31, 31)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 356, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(20, 20, 20))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(307, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(214, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        String status = cbStatus.getSelectedItem().toString();
    
        SepulturaDao dao = new SepulturaDao();
        List<Sepultura> lista = dao.listarPorStatus(status);

        DefaultTableModel model = (DefaultTableModel) tblRelatorioSepultura.getModel();
        model.setRowCount(0);

        for (Sepultura s : lista) {
            model.addRow(new Object[]{
                s.getIdSepultura(),
                s.getLote(),
                s.getTipoSepultura(),
                s.getStatusSepultura(),
                s.getFamiliarResponsavel()
            });
       
        }
    
    }//GEN-LAST:event_jButton1ActionPerformed

    private void tblRelatorioSepulturaAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblRelatorioSepulturaAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRelatorioSepulturaAncestorAdded

    private void cbStatusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbStatusActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbStatusActionPerformed

    private void btnVoltarAoMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarAoMenuActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu(usuarioAutenticado);
        menu.setLocationRelativeTo(this);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarAoMenuActionPerformed

    private void cbTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbTipoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbTipoActionPerformed

    private void btnGerarTipoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarTipoActionPerformed
        // TODO add your handling code here:
        String tipo = cbTipo.getSelectedItem().toString();
    
        SepulturaDao dao = new SepulturaDao();
        List<Sepultura> lista = dao.listarPorTipo(tipo);

        DefaultTableModel model = (DefaultTableModel) tblRelatorioSepultura.getModel();
        model.setRowCount(0);

        for (Sepultura s : lista) {
            model.addRow(new Object[]{
                s.getIdSepultura(),
                s.getLote(),
                s.getTipoSepultura(),
                s.getStatusSepultura(),
                s.getFamiliarResponsavel()
            });
       
        }
    
                   
    }//GEN-LAST:event_btnGerarTipoActionPerformed

    private void btnGerarIdActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGerarIdActionPerformed
        // TODO add your handling code here:
        int idSepultura = Integer.parseInt(txtIdSepulturaFal.getText());

        FalecidoDao dao = new FalecidoDao();
        List<Falecido> lista = dao.listarPorSepultura(idSepultura);

        DefaultTableModel model = (DefaultTableModel) tblRelatorioFalecidos.getModel();
        model.setRowCount(0);

        for (Falecido f : lista) {
        model.addRow(new Object[] {
            f.getIdFalecido(),
            f.getNomeCompleto(),
            f.getDataNascimento(),
            f.getDataFalecimento(),
            f.getCpf(),
            f.getSepultura(),
            f.getFamiliarResponsavel()
        });
    }
    }//GEN-LAST:event_btnGerarIdActionPerformed

    private void tblRelatorioFalecidosAncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_tblRelatorioFalecidosAncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_tblRelatorioFalecidosAncestorAdded

    private void txtIdSepulturaFalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtIdSepulturaFalActionPerformed
        // TODO add your handling code here:
        
    }//GEN-LAST:event_txtIdSepulturaFalActionPerformed

    private void btnPdfSepulturaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfSepulturaActionPerformed
        // TODO add your handling code here:
        try {
        // Gerar data e hora para nome único do arquivo
        String dataHora = java.time.LocalDateTime.now()
            .format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));

        // Caminho e nome do arquivo PDF
        String caminho = "relatorios/relatorio_sepulturas_" + dataHora + ".pdf";

    
        // Gerar o relatório
        util.RelatorioPDFUtil.gerarRelatorio(
            tblRelatorioSepultura,
            "Relatório de Sepulturas",
            caminho,
            usuarioAutenticado.getNomeUsuario()
        );

    
        // Mensagem de sucesso
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Relatório de Sepulturas gerado com sucesso!\nArquivo: " + caminho
        );

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Erro ao gerar PDF: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPdfSepulturaActionPerformed

    private void btnPdfFalecidosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPdfFalecidosActionPerformed
        // TODO add your handling code here:
        try {
        // Gera a data e hora para nome único do arquivo para evitar que sobrescreva
        String dataHora = java.time.LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));

        // deefine uma variavel para que o caminho  seja o caminho e o nome do arquivo PDF
        String caminho = "relatorios/relatorio_sepulturas_" + dataHora + ".pdf";

    
        // Gera o relatório passando as informações que ele espera
        util.RelatorioPDFUtil.gerarRelatorio(
            tblRelatorioFalecidos,
            "Relatório de Falecidos por Sepultura",
            caminho,
            usuarioAutenticado.getNomeUsuario()
        );

    
        // Mensagem de sucesso
        javax.swing.JOptionPane.showMessageDialog(
            this,
            "Relatório de falecidos gerado com sucesso!\nArquivo: " + caminho
        );

        } catch (Exception e) {
            javax.swing.JOptionPane.showMessageDialog(
                this,
                "Erro ao gerar PDF: " + e.getMessage());
        }
    }//GEN-LAST:event_btnPdfFalecidosActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGerarId;
    private javax.swing.JButton btnGerarTipo;
    private javax.swing.JButton btnPdfFalecidos;
    private javax.swing.JButton btnPdfSepultura;
    private javax.swing.JButton btnVoltarAoMenu;
    private javax.swing.JComboBox<String> cbStatus;
    private javax.swing.JComboBox<String> cbTipo;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblRelatorioFalecidos;
    private javax.swing.JTable tblRelatorioSepultura;
    private javax.swing.JTextField txtIdSepulturaFal;
    // End of variables declaration//GEN-END:variables
}