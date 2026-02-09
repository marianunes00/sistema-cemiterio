package view;


import dao.SepulturaDao;
import dao.ServicoDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepultura;
import model.Servico;
import model.Usuario;
import view.Menu;

public class TelaServico extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaServico.class.getName());
    
    private Usuario usuarioAutenticado;
    
     //construtor que está recebendo o usuario que foi autenticado na tela de login
    public TelaServico(Usuario usuario){
        initComponents();
        this.usuarioAutenticado = usuario;
        aplicarPermissoes();
        carregarSepulturas();
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
        
        btnCadastrarServicos.setEnabled(
            usuarioAutenticado.podeCadastrarServicos());
        
        btnDeletarServicos.setEnabled(
            usuarioAutenticado.podeDeletarServicos());
        
        btnAtualizarServicos.setEnabled(
            usuarioAutenticado.podeAtualizarServicos());
            }
   
    private void cadastrar(){
        try{
            
            if (txtDataServico.getText().isBlank()) {
                JOptionPane.showMessageDialog(this, "Informe a data do serviço.");
                return;
            }

            //ajusta a data para o padrao do Brasil
            DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dataServico = LocalDate.parse(txtDataServico.getText(), brasil);
            
            //Validação dos ComboBoxes de Tipo de serviço e sepultura
            if (jcbTipoServico.getSelectedItem() == null || jcbSepulturaServico.getSelectedItem() == null) {
                JOptionPane.showMessageDialog(this, "Selecione o Tipo de serviço e a Sepultura.");
                return;
            }

            //Validação do Radio Button
            if (!jrb1StatusServico.isSelected() && !jrb2StatusServico.isSelected() && !jrb3StatusServico.isSelected()) {
                JOptionPane.showMessageDialog(this, "Informe o status do serviço.");
                return;
            }
            Sepultura sepult = (Sepultura) jcbSepulturaServico.getSelectedItem();
            //Cria o obj sepultura e dps seta os atributos com base nos valores inseridos nos campos
            //o s.setLote é a referencia ao atributo, o txtLote é o nome do campo, e getText é o metodo para pegar
            // o valor do campo com base no tipo;[
            Servico se = new Servico();
            se.setSepultura(sepult); // Passa o objeto sepultura completo
            se.setNotificacaoServico(txtNotificacaoServico.getText());
            se.setDataServico(dataServico);
            // Tipo do serviço
            se.setTipoServico(jcbTipoServico.getSelectedItem().toString());
            
            // Status do serviço via radio
            if (jrb1StatusServico.isSelected()) {
                se.setStatusServico("Pendente");
            } else if (jrb2StatusServico.isSelected()) {
                se.setStatusServico("Em andamento");
            } else if (jrb3StatusServico.isSelected()) {
                se.setStatusServico("Concluído");
            }

            
            //Cria o objeto Dao e depois chama o metodo de dao, inserir;
           ServicoDao dao = new ServicoDao();
            dao.inserir(se);
            //metodo listar e limpar campo
            listar();
            carregarSepulturas();
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
        
        if (txtDataServico.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Informe a data do serviço.");
            return;
        }


        DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy"); 
        LocalDate dataServico = LocalDate.parse(txtDataServico.getText(), brasil);
        
        //Validação dos ComboBoxes de Tipo de serviço e sepultura
        if (jcbTipoServico.getSelectedItem() == null || jcbSepulturaServico.getSelectedItem() == null) {
            JOptionPane.showMessageDialog(this, "Selecione o Tipo do serviço e a sepultura.");
            return;
        }

        //Validação do Radio Button
        if (!jrb1StatusServico.isSelected() && !jrb2StatusServico.isSelected() && !jrb3StatusServico.isSelected()) {
            JOptionPane.showMessageDialog(this, "Informe o status do serviço.");
            return;
        }
        Sepultura sepult = (Sepultura) jcbSepulturaServico.getSelectedItem();
        //Cria o obj sepultura e dps seta os atributos com base nos valores inseridos nos campos
        //o s.setLote é a referencia ao atributo, o txtLote é o nome do campo, e getText é o metodo para pegar
        // o valor do campo com base no tipo;[
        Servico se = new Servico();
        se.setIdServico(id);
        se.setSepultura(sepult); // Passa o objeto sepultura completo
        se.setNotificacaoServico(txtNotificacaoServico.getText());
        se.setDataServico(dataServico);
        // Tipo do serviço
        se.setTipoServico(jcbTipoServico.getSelectedItem().toString());

        // Status do serviço via radio
        if (jrb1StatusServico.isSelected()) {
            se.setStatusServico("Pendente");
        } else if (jrb2StatusServico.isSelected()) {
            se.setStatusServico("Em andamento");
        } else if (jrb3StatusServico.isSelected()) {
            se.setStatusServico("Concluído");
        }

        ServicoDao dao = new ServicoDao();
        dao.atualizar(se);

        listar();
        carregarSepulturas();
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
        jcbTipoServico.setSelectedIndex(-1);
        jcbSepulturaServico.setSelectedIndex(-1);
        buttonGroup1.clearSelection();
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
       
        txtNotificacaoServico.setText(notificacao != null ? notificacao.toString() : "");
        
         // 1. Preencher ComboBox de Tipo (String)
         if (tipoServico != null) {
             jcbTipoServico.setSelectedItem(tipoServico.toString());
         }

         // 2. Marcar o Radio Button de Status correto
         if (statusServ != null) {
             String s = statusServ.toString();
             if (s.equalsIgnoreCase("Pendente")) {
                 jrb1StatusServico.setSelected(true);
             } else if (s.equalsIgnoreCase("Em Andamento")) {
                 jrb2StatusServico.setSelected(true);
             } else if (s.equalsIgnoreCase("Concluído")) {
                 jrb3StatusServico.setSelected(true);
             }
         }

         // 3. Preencher ComboBox de Sepultura 
         jcbSepulturaServico.setSelectedItem(null);
         if (idSepult != null) {
             int id = Integer.parseInt(idSepult.toString());

             for (int i = 0; i < jcbSepulturaServico.getItemCount(); i++) {
                 Object item = jcbSepulturaServico.getItemAt(i);

                 if (item instanceof Sepultura) {
                     Sepultura s = (Sepultura) item;
                     if (s.getIdSepultura() == id) {
                         jcbSepulturaServico.setSelectedIndex(i);
                         break;
                     }
                 }
             }
         }
         
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
    
     //carrega as sepultura para o serviços
    private void carregarSepulturas() {
        try {
            SepulturaDao dao = new SepulturaDao();

            // listarTodos() garante que tanto as livres (para novos) quanto as ocupadas (para quem já está na tabela) apareçam.
            List<Sepultura> lista = dao.listarTodos();

            jcbSepulturaServico.removeAllItems();

            for (Sepultura s : lista) {
                jcbSepulturaServico.addItem(s);
            }

            jcbSepulturaServico.setSelectedItem(null); // Inicia sem nada selecionado
        } catch (Exception e) {
            e.printStackTrace();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblServicos = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        txtNotificacaoServico = new javax.swing.JTextField();
        btnDeletarServicos = new javax.swing.JButton();
        btnAtualizarServicos = new javax.swing.JButton();
        btnCadastrarServicos = new javax.swing.JButton();
        btnListarServicos = new javax.swing.JButton();
        lblNotificacaoServico = new javax.swing.JLabel();
        lblSepulturaServico = new javax.swing.JLabel();
        lblStatusServico = new javax.swing.JLabel();
        txtDataServico = new javax.swing.JTextField();
        lblDataServico = new javax.swing.JLabel();
        lblTipoServico = new javax.swing.JLabel();
        jcbTipoServico = new javax.swing.JComboBox<>();
        jrb1StatusServico = new javax.swing.JRadioButton();
        jrb2StatusServico = new javax.swing.JRadioButton();
        jrb3StatusServico = new javax.swing.JRadioButton();
        jcbSepulturaServico = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        btnVoltarMenu = new javax.swing.JButton();
        lblTituloServicos = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txtNotificacaoServico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtNotificacaoServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNotificacaoServicoActionPerformed(evt);
            }
        });

        btnDeletarServicos.setText("Deletar");
        btnDeletarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeletarServicosActionPerformed(evt);
            }
        });

        btnAtualizarServicos.setText("Atualizar");
        btnAtualizarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarServicosActionPerformed(evt);
            }
        });

        btnCadastrarServicos.setText("Cadastrar");
        btnCadastrarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCadastrarServicosActionPerformed(evt);
            }
        });

        btnListarServicos.setText("Listar");
        btnListarServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnListarServicosActionPerformed(evt);
            }
        });

        lblNotificacaoServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblNotificacaoServico.setForeground(new java.awt.Color(0, 102, 102));
        lblNotificacaoServico.setText("Notificação interna:");

        lblSepulturaServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblSepulturaServico.setForeground(new java.awt.Color(0, 102, 102));
        lblSepulturaServico.setText("Sepultura:");

        lblStatusServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblStatusServico.setForeground(new java.awt.Color(0, 102, 102));
        lblStatusServico.setText("Status do Serviço:");

        txtDataServico.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        txtDataServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataServicoActionPerformed(evt);
            }
        });

        lblDataServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblDataServico.setForeground(new java.awt.Color(0, 102, 102));
        lblDataServico.setText("Data do Serviço:");

        lblTipoServico.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        lblTipoServico.setForeground(new java.awt.Color(0, 102, 102));
        lblTipoServico.setText("Tipo do Serviço:");

        jcbTipoServico.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sepultamento", "Exumação e translados", "Limpeza ou Manutenção" }));

        buttonGroup1.add(jrb1StatusServico);
        jrb1StatusServico.setText("Pendente");
        jrb1StatusServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb1StatusServicoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jrb2StatusServico);
        jrb2StatusServico.setText("Em andamento");
        jrb2StatusServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrb2StatusServicoActionPerformed(evt);
            }
        });

        buttonGroup1.add(jrb3StatusServico);
        jrb3StatusServico.setText("Concluído");

        jcbSepulturaServico.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSepulturaServicoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblDataServico)
                                    .addComponent(lblTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jcbTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblStatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 141, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jrb1StatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(64, 64, 64)
                                        .addComponent(jrb2StatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(69, 69, 69)
                                        .addComponent(jrb3StatusServico, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(32, 32, 32))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(3, 3, 3)
                                        .addComponent(jcbSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE)))))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addComponent(lblNotificacaoServico, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 27, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                .addComponent(btnCadastrarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnAtualizarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btnDeletarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnListarServicos, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(57, 57, 57))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTipoServico)
                    .addComponent(jcbTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDataServico)
                    .addComponent(txtDataServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(15, 15, 15)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStatusServico)
                    .addComponent(jrb1StatusServico)
                    .addComponent(jrb2StatusServico)
                    .addComponent(jrb3StatusServico))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSepulturaServico)
                    .addComponent(jcbSepulturaServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNotificacaoServico)
                    .addComponent(txtNotificacaoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnListarServicos)
                    .addComponent(btnDeletarServicos)
                    .addComponent(btnAtualizarServicos)
                    .addComponent(btnCadastrarServicos))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        btnVoltarMenu.setBackground(new java.awt.Color(0, 102, 102));
        btnVoltarMenu.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        btnVoltarMenu.setForeground(new java.awt.Color(255, 255, 255));
        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        lblTituloServicos.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        lblTituloServicos.setForeground(new java.awt.Color(0, 102, 102));
        lblTituloServicos.setText("Gerenciamento de Serviços");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangular.png"))); // NOI18N

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(btnVoltarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTituloServicos)
                .addGap(188, 188, 188)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addGap(11, 11, 11))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnVoltarMenu, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblTituloServicos))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Realize o cadastro do Serviço");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(550, 550, 550)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 262, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(41, 41, 41)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1299, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(53, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(33, 33, 33)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 118, Short.MAX_VALUE)
                .addGap(129, 129, 129))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 211, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDataServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataServicoActionPerformed

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

    private void jrb2StatusServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb2StatusServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrb2StatusServicoActionPerformed

    private void jrb1StatusServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrb1StatusServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrb1StatusServicoActionPerformed

    private void jcbSepulturaServicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSepulturaServicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSepulturaServicoActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarServicos;
    private javax.swing.JButton btnCadastrarServicos;
    private javax.swing.JButton btnDeletarServicos;
    private javax.swing.JButton btnListarServicos;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<model.Sepultura> jcbSepulturaServico;
    private javax.swing.JComboBox<String> jcbTipoServico;
    private javax.swing.JRadioButton jrb1StatusServico;
    private javax.swing.JRadioButton jrb2StatusServico;
    private javax.swing.JRadioButton jrb3StatusServico;
    private javax.swing.JLabel lblDataServico;
    private javax.swing.JLabel lblNotificacaoServico;
    private javax.swing.JLabel lblSepulturaServico;
    private javax.swing.JLabel lblStatusServico;
    private javax.swing.JLabel lblTipoServico;
    private javax.swing.JLabel lblTituloServicos;
    private javax.swing.JTable tblServicos;
    private javax.swing.JTextField txtDataServico;
    private javax.swing.JTextField txtNotificacaoServico;
    // End of variables declaration//GEN-END:variables
}
