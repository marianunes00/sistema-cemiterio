 package view;

import java.time.format.DateTimeFormatter;
import dao.FalecidoDao;
import java.time.LocalDate;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Falecido;
import model.Sepultura;
import model.Usuario;
import view.Menu;
 
public class TelaFalecido extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaFalecido.class.getName());

        private Usuario usuarioAutenticado;
        
     //construtor que está recebendo o usuario que foi autenticado na tela de login
    public TelaFalecido(Usuario usuario){
        initComponents();
        this.usuarioAutenticado = usuario;
        aplicarPermissoes();
        tblFalecidos.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            preencherCamposDaTabela();
        }
}       );
        listar();

    }
    
    //Permissões de tipos de usuario
    private void aplicarPermissoes(){
                 
        btnCadastrarFalecido.setEnabled(
            usuarioAutenticado.podeCadastrarFalecido());
        
        btnDeletarFalecido.setEnabled(
            usuarioAutenticado.podeDeletarFalecido());
        
        btnAtualizarFalecido.setEnabled(
            usuarioAutenticado.podeAtualizarFalecido());
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
    
   private void listar() {
    try {
        FalecidoDao dao = new FalecidoDao();
        List<Falecido> lista = dao.listarTodos();
        preencherTabelaFalecidos(lista);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao listar falecidos");
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
        //pegando a linha
        int row = tblFalecidos.getSelectedRow();
        if (row == -1) return;

        //mapeando cada coluna da tabela para uma variável do formulário.
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
    
    private void preencherTabelaFalecidos(List<Falecido> lista) {
    DefaultTableModel modelo = (DefaultTableModel) tblFalecidos.getModel();
    modelo.setRowCount(0);

    for (Falecido f : lista) {
        String temCertidao = f.isPossuiCertidaoObito() ? "Sim" : "Não";

        modelo.addRow(new Object[]{
            f.getIdFalecido(),
            f.getNomeCompleto(),
            f.getCpf(),
            temCertidao,
            f.getSepultura().getIdSepultura(),
            f.getDataNascimento(),
            f.getDataFalecimento(),
            f.getFamiliarResponsavel()
        });
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

        jPanel2 = new javax.swing.JPanel();
        txtSepulturaFalecido = new javax.swing.JTextField();
        lblDataFalecimento = new javax.swing.JLabel();
        txtDataFalecimento = new javax.swing.JTextField();
        lblFamiliarResponsavelFalecido = new javax.swing.JLabel();
        txtFamiliarResponsavelFalecido = new javax.swing.JTextField();
        txtNomeCompleto = new javax.swing.JTextField();
        txtCertidaoObito = new javax.swing.JTextField();
        txtDataNascimento = new javax.swing.JTextField();
        lblTituloFalecidos = new javax.swing.JLabel();
        lblDataNascimento = new javax.swing.JLabel();
        lblCertidaoObito = new javax.swing.JLabel();
        lblCpf = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        lblSepulturaFalecido = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFalecidos = new javax.swing.JTable();
        lblNomeCompleto = new javax.swing.JLabel();
        btnVoltarMenu = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarNomeFalecido = new javax.swing.JTextField();
        btnBuscarNomeFalecido = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        btnCadastrarFalecido = new javax.swing.JButton();
        btnAtualizarFalecido = new javax.swing.JButton();
        btnDeletarFalecido = new javax.swing.JButton();
        btnListarFalecidos = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        txtSepulturaFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtSepulturaFalecidoActionPerformed(evt);
            }
        });

        lblDataFalecimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataFalecimento.setForeground(new java.awt.Color(255, 255, 255));
        lblDataFalecimento.setText("Data de Falecimento:");

        txtDataFalecimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFalecimentoActionPerformed(evt);
            }
        });

        lblFamiliarResponsavelFalecido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFamiliarResponsavelFalecido.setForeground(new java.awt.Color(255, 255, 255));
        lblFamiliarResponsavelFalecido.setText("Familiar Responsável:");

        txtFamiliarResponsavelFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelFalecidoActionPerformed(evt);
            }
        });

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

        lblTituloFalecidos.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        lblTituloFalecidos.setForeground(new java.awt.Color(255, 255, 255));
        lblTituloFalecidos.setText("Gerenciamento de Falecidos");

        lblDataNascimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataNascimento.setForeground(new java.awt.Color(255, 255, 255));
        lblDataNascimento.setText("Data de Nascimento:");

        lblCertidaoObito.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCertidaoObito.setForeground(new java.awt.Color(255, 255, 255));
        lblCertidaoObito.setText("Possui Certidão de óbito?");

        lblCpf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCpf.setForeground(new java.awt.Color(255, 255, 255));
        lblCpf.setText("CPF:");

        txtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfActionPerformed(evt);
            }
        });

        lblSepulturaFalecido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSepulturaFalecido.setForeground(new java.awt.Color(255, 255, 255));
        lblSepulturaFalecido.setText("Sepultura:");

        tblFalecidos.setBackground(new java.awt.Color(0, 102, 102));
        tblFalecidos.setFont(new java.awt.Font("Segoe UI", 1, 12)); // NOI18N
        tblFalecidos.setForeground(new java.awt.Color(255, 255, 255));
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

        lblNomeCompleto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNomeCompleto.setForeground(new java.awt.Color(255, 255, 255));
        lblNomeCompleto.setText("Nome Completo:");

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txtBuscarNomeFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarNomeFalecidoActionPerformed(evt);
            }
        });

        btnBuscarNomeFalecido.setText("Buscar");
        btnBuscarNomeFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarNomeFalecidoActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Pesquise pelo nome completo");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(461, 461, 461)
                        .addComponent(jLabel1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtBuscarNomeFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnBuscarNomeFalecido)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(txtBuscarNomeFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(btnBuscarNomeFalecido))
                .addContainerGap(14, Short.MAX_VALUE))
        );

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));
        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

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

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadastrarFalecido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAtualizarFalecido)
                .addGap(18, 18, 18)
                .addComponent(btnDeletarFalecido)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
                .addComponent(btnListarFalecidos)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarFalecido)
                    .addComponent(btnAtualizarFalecido)
                    .addComponent(btnDeletarFalecido)
                    .addComponent(btnListarFalecidos))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangular.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(447, 447, 447)
                                .addComponent(lblTituloFalecidos))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(25, 25, 25)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                        .addComponent(lblCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(lblSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(lblDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 734, Short.MAX_VALUE)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblDataNascimento)
                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(btnVoltarMenu)
                                                .addComponent(lblNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(37, 37, 37)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addComponent(lblCertidaoObito)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCertidaoObito)
                                            .addComponent(txtCpf)
                                            .addComponent(txtSepulturaFalecido)
                                            .addComponent(txtDataFalecimento)
                                            .addComponent(txtFamiliarResponsavelFalecido))))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(50, 50, 50))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTituloFalecidos)
                            .addComponent(btnVoltarMenu))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNomeCompleto)
                            .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataNascimento)
                            .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(7, 7, 7)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCertidaoObito)
                            .addComponent(txtCertidaoObito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCpf)
                            .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(lblSepulturaFalecido))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 56, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(2764, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
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

    private void txtBuscarNomeFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarNomeFalecidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarNomeFalecidoActionPerformed

    private void btnBuscarNomeFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarNomeFalecidoActionPerformed
        // TODO add your handling code here:
        String nome = txtBuscarNomeFalecido.getText();
        FalecidoDao dao = new FalecidoDao();
        List<Falecido> lista = dao.buscarPorNome(nome);
        preencherTabelaFalecidos(lista);
    }//GEN-LAST:event_btnBuscarNomeFalecidoActionPerformed

    private void btnVoltarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarMenuActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu(usuarioAutenticado);          // nome da sua classe de menu
        menu.setLocationRelativeTo(this);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarMenuActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarFalecido;
    private javax.swing.JButton btnBuscarNomeFalecido;
    private javax.swing.JButton btnCadastrarFalecido;
    private javax.swing.JButton btnDeletarFalecido;
    private javax.swing.JButton btnListarFalecidos;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
    private javax.swing.JTextField txtBuscarNomeFalecido;
    private javax.swing.JTextField txtCertidaoObito;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtDataFalecimento;
    private javax.swing.JTextField txtDataNascimento;
    private javax.swing.JTextField txtFamiliarResponsavelFalecido;
    private javax.swing.JTextField txtNomeCompleto;
    private javax.swing.JTextField txtSepulturaFalecido;
    // End of variables declaration//GEN-END:variables
}