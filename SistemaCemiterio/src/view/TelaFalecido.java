 package view;

import java.time.format.DateTimeFormatter;
import dao.FalecidoDao;
import dao.SepulturaDao;
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
        carregarSepulturas();
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
            
            //Faz com que o usuário entenda que tem que selecionar um dos rádios
         if (!jrbCertidaoSim.isSelected() && !jrbCertidaoNao.isSelected()) {
             JOptionPane.showMessageDialog(this, "Informe se possui certidão de óbito (Sim ou Não).");
                return;
}
            
            // Regra: só cadastra se tiver certidão de óbito
         if (!jrbCertidaoSim.isSelected()) {
             JOptionPane.showMessageDialog(this, "Não é possível cadastrar o falecido sem certidão de óbito.");
                return; // não permite o cadastro
    }
         
            boolean possuiCertidao = true; // se chegou aqui, é porque marcou SIM
               
            //Pega do comboBox o objeto Sepultura completo vindo do DAO
            Sepultura sepult = (Sepultura) jcbSepulturaFalecido.getSelectedItem();
            //verifica se selecionou uma opção
            if (sepult == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma sepultura");
                return;
        }
            //Verifica o status
            if (sepult.getStatusSepultura().equals("Ocupada")) {
                JOptionPane.showMessageDialog(null, "A sepultura escolhida está ocupada!");
                return;
        }
            
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
            
             // Marca a sepultura como ocupada
             new SepulturaDao().atualizarStatus(sepult.getIdSepultura(), "Ocupada");

               JOptionPane.showMessageDialog(this,"Falecido cadastrado com sucesso!");
               
               listar();
               carregarSepulturas(); 
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
        if (row == -1){
            JOptionPane.showMessageDialog(this, "Selecione um falecido na tabela para atualizar.");
            return;
        }
        //Pega o id do falecido
        int id = Integer.parseInt(tblFalecidos.getValueAt(row,0).toString());
        
        //Pega o id da sepultura atual que na tabela está na Coluna 4
        int idSepulturaNaTabela = Integer.parseInt(tblFalecidos.getValueAt(row, 4).toString());
        
        try{
            
            // ajusta o modelo da data para o formato BR
            DateTimeFormatter brasil = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        //Faz com que o usuário entenda que tem que selecionar um dos rádios
         if (!jrbCertidaoSim.isSelected() && !jrbCertidaoNao.isSelected()) {
             JOptionPane.showMessageDialog(this, "Informe se possui certidão de óbito (Sim ou Não).");
                return;
         }
         
          boolean possuiCertidao = jrbCertidaoSim.isSelected();

         // Regra: só atualiza se tiver certidão
        if (!possuiCertidao) {
            JOptionPane.showMessageDialog(this, 
                "Não é possível atualizar o falecido sem certidão de óbito.");
            return;
        }
            
            //Pega do comboBox o objeto Sepultura completo vindo do DAO
            Sepultura sepult = (Sepultura) jcbSepulturaFalecido.getSelectedItem();
            if (sepult == null) {
                JOptionPane.showMessageDialog(this, "Selecione uma sepultura.");
                return;
        }
            /* Verifica se a sepultura escolhida já é a do próprio falecido,
             mas se for ocupada por outro falecido, o usuario não pode a escolher.*/
            if (sepult.getStatusSepultura().equalsIgnoreCase("Ocupada")) {
                // ...verificamos se o ID dela é DIFERENTE do ID que estava na tabela.
                // Se for diferente, significa que o usuário tentou escolher a sepultura de outro falecido.
                if (sepult.getIdSepultura() != idSepulturaNaTabela) {
                    JOptionPane.showMessageDialog(this, "A sepultura escolhida já está ocupada por outro falecido");
                    return;
            }
            }
            
            //Cria objeto falecido
            Falecido f = new Falecido(
               id,// id gerado automaticamente
               txtNomeCompleto.getText(),
               LocalDate.parse(txtDataNascimento.getText(), brasil),   
               possuiCertidao,
               txtCpf.getText(),
               sepult,
               LocalDate.parse(txtDataFalecimento.getText(),brasil),
               txtFamiliarResponsavelFalecido.getText()
               );
           
         // chama o DAO para atualizar os dados
            new FalecidoDao().atualizar(f);
            
            // Se trocou de sepultura, libera a antiga e ocupa a nova
            if (sepult.getIdSepultura() != idSepulturaNaTabela) {
                new SepulturaDao().atualizarStatus(idSepulturaNaTabela, "Disponível");
                new SepulturaDao().atualizarStatus(sepult.getIdSepultura(), "Ocupada");
            }
            
            //Atualiza a lista
            listar();
             JOptionPane.showMessageDialog(this,
            "Dados do falecido \"" + f.getNomeCompleto() + "\" atualizados com sucesso!");    
             
             carregarSepulturas(); 
             limparCampos();
             
        } catch(Exception e) {
              e.printStackTrace();
              JOptionPane.showMessageDialog(this,"Falha ao atualizar os dados do falecido com ID:");
            }
        }
    
    private void limparCampos(){
        txtNomeCompleto.setText("");
        txtDataNascimento.setText("");
        buttonGroup1.clearSelection();
        txtCpf.setText("");
        jcbSepulturaFalecido.setSelectedIndex(-1);
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
        txtFamiliarResponsavelFalecido.setText(familiar != null ? familiar.toString() : "");
        
        //Certidão de óbito
        if (certidao != null && certidao.toString().equalsIgnoreCase("Sim")) {
        jrbCertidaoSim.setSelected(true);
            }else {
        jrbCertidaoNao.setSelected(true);
    }
        // Seleciona a sepultura no ComboBox usando apenas o ID
        jcbSepulturaFalecido.setSelectedItem(null); // Limpa seleção atual antes de selecionar
        if (idSepult != null) {
            int id = Integer.parseInt(idSepult.toString());

            for (int i = 0; i < jcbSepulturaFalecido.getItemCount(); i++) {
                Object item = jcbSepulturaFalecido.getItemAt(i);

                // Verifica se o item não é nulo e se é realmente um objeto Sepultura
                if (item instanceof Sepultura) {
                    Sepultura s = (Sepultura) item;
                    if (s.getIdSepultura() == id) {
                        jcbSepulturaFalecido.setSelectedIndex(i);
                        break;
                    }
                }
            }
        }
        
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
    //carrega as sepultura para o falecido
    private void carregarSepulturas() {
        try {
            SepulturaDao dao = new SepulturaDao();

            // listarTodos() garante que tanto as livres (para novos) quanto as ocupadas (para quem já está na tabela) apareçam.
            List<Sepultura> lista = dao.listarTodos();

            jcbSepulturaFalecido.removeAllItems();

            for (Sepultura s : lista) {
                jcbSepulturaFalecido.addItem(s);
            }

            jcbSepulturaFalecido.setSelectedItem(null); // Inicia sem nada selecionado
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
        jPanel2 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        lblNomeCompleto = new javax.swing.JLabel();
        txtNomeCompleto = new javax.swing.JTextField();
        lblDataNascimento = new javax.swing.JLabel();
        txtDataNascimento = new javax.swing.JTextField();
        lblCertidaoObito = new javax.swing.JLabel();
        lblCpf = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        lblSepulturaFalecido = new javax.swing.JLabel();
        lblDataFalecimento = new javax.swing.JLabel();
        txtDataFalecimento = new javax.swing.JTextField();
        lblFamiliarResponsavelFalecido = new javax.swing.JLabel();
        txtFamiliarResponsavelFalecido = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        txtBuscarNomeFalecido = new javax.swing.JTextField();
        btnBuscarNomeFalecido = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        btnCadastrarFalecido = new javax.swing.JButton();
        btnAtualizarFalecido = new javax.swing.JButton();
        btnDeletarFalecido = new javax.swing.JButton();
        btnListarFalecidos = new javax.swing.JButton();
        jrbCertidaoSim = new javax.swing.JRadioButton();
        jrbCertidaoNao = new javax.swing.JRadioButton();
        jcbSepulturaFalecido = new javax.swing.JComboBox<>();
        jPanel5 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnVoltarMenu = new javax.swing.JButton();
        lblTituloFalecidos = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFalecidos = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 153, 153));

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        lblNomeCompleto.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblNomeCompleto.setForeground(new java.awt.Color(0, 102, 102));
        lblNomeCompleto.setText("Nome Completo:");

        txtNomeCompleto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNomeCompletoActionPerformed(evt);
            }
        });

        lblDataNascimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataNascimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDataNascimento.setText("Data de Nascimento:");

        txtDataNascimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataNascimentoActionPerformed(evt);
            }
        });

        lblCertidaoObito.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCertidaoObito.setForeground(new java.awt.Color(0, 102, 102));
        lblCertidaoObito.setText("Possui Certidão de óbito?");

        lblCpf.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblCpf.setForeground(new java.awt.Color(0, 102, 102));
        lblCpf.setText("CPF:");

        txtCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtCpfActionPerformed(evt);
            }
        });

        lblSepulturaFalecido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblSepulturaFalecido.setForeground(new java.awt.Color(0, 102, 102));
        lblSepulturaFalecido.setText("Sepultura:");

        lblDataFalecimento.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataFalecimento.setForeground(new java.awt.Color(0, 102, 102));
        lblDataFalecimento.setText("Data de Falecimento:");

        txtDataFalecimento.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataFalecimentoActionPerformed(evt);
            }
        });

        lblFamiliarResponsavelFalecido.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFamiliarResponsavelFalecido.setForeground(new java.awt.Color(0, 102, 102));
        lblFamiliarResponsavelFalecido.setText("Familiar Responsável:");

        txtFamiliarResponsavelFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelFalecidoActionPerformed(evt);
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

        buttonGroup1.add(jrbCertidaoSim);
        jrbCertidaoSim.setText("Sim");

        buttonGroup1.add(jrbCertidaoNao);
        jrbCertidaoNao.setText("Não");
        jrbCertidaoNao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jrbCertidaoNaoActionPerformed(evt);
            }
        });

        jcbSepulturaFalecido.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jcbSepulturaFalecidoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(48, 48, 48)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataNascimento)
                            .addComponent(lblNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblCertidaoObito)
                            .addComponent(lblCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(45, 45, 45)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jrbCertidaoSim, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jrbCertidaoNao, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtNomeCompleto)
                                .addComponent(txtDataNascimento)
                                .addComponent(txtCpf))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(52, 52, 52)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcbSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(txtDataFalecimento, javax.swing.GroupLayout.DEFAULT_SIZE, 357, Short.MAX_VALUE)
                                .addComponent(txtFamiliarResponsavelFalecido))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(btnListarFalecidos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDeletarFalecido)
                        .addGap(27, 27, 27)
                        .addComponent(btnAtualizarFalecido)
                        .addGap(18, 18, 18)
                        .addComponent(btnCadastrarFalecido)))
                .addGap(248, 248, 248)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 304, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(40, 40, 40))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblNomeCompleto)
                            .addComponent(txtNomeCompleto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblDataNascimento)
                            .addComponent(txtDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblCertidaoObito)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jrbCertidaoSim)
                                .addComponent(jrbCertidaoNao))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCpf)
                    .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSepulturaFalecido)
                    .addComponent(jcbSepulturaFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDataFalecimento, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFamiliarResponsavelFalecido, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarFalecido)
                    .addComponent(btnAtualizarFalecido)
                    .addComponent(btnDeletarFalecido)
                    .addComponent(btnListarFalecidos))
                .addGap(16, 16, 16))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangular.png"))); // NOI18N

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        lblTituloFalecidos.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        lblTituloFalecidos.setForeground(new java.awt.Color(0, 102, 102));
        lblTituloFalecidos.setText("Gerenciamento de Falecidos");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(btnVoltarMenu)
                .addGap(285, 285, 285)
                .addComponent(lblTituloFalecidos)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 269, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(207, 207, 207))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTituloFalecidos)
                    .addComponent(btnVoltarMenu))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

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

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(69, 69, 69)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1213, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 1351, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 2697, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(37, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNomeCompletoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNomeCompletoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNomeCompletoActionPerformed

    private void txtDataNascimentoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDataNascimentoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDataNascimentoActionPerformed

    private void txtCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtCpfActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtCpfActionPerformed

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

    private void jcbSepulturaFalecidoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jcbSepulturaFalecidoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jcbSepulturaFalecidoActionPerformed

    private void jrbCertidaoNaoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jrbCertidaoNaoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jrbCertidaoNaoActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarFalecido;
    private javax.swing.JButton btnBuscarNomeFalecido;
    private javax.swing.JButton btnCadastrarFalecido;
    private javax.swing.JButton btnDeletarFalecido;
    private javax.swing.JButton btnListarFalecidos;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<model.Sepultura> jcbSepulturaFalecido;
    private javax.swing.JRadioButton jrbCertidaoNao;
    private javax.swing.JRadioButton jrbCertidaoSim;
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
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtDataFalecimento;
    private javax.swing.JTextField txtDataNascimento;
    private javax.swing.JTextField txtFamiliarResponsavelFalecido;
    private javax.swing.JTextField txtNomeCompleto;
    // End of variables declaration//GEN-END:variables
}