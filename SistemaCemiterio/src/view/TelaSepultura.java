package view;

import dao.SepulturaDao;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.Sepultura;
import model.Usuario;
import view.Menu;


/**
 *
 * @author Váleria Matias
 */
public class TelaSepultura extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaSepultura.class.getName());

    
    //Adiciona o construtor que recebe o usuário e guarda o usuario logado em uma variavel
    private Usuario usuarioAutenticado;

    public TelaSepultura(Usuario usuario) {
    initComponents();
    this.usuarioAutenticado = usuario;
    aplicarPermissoes();
    tblSepulturas.addMouseListener(new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            preencherCamposDaTabela();
        }
}       );
        listar();
}
    
      //Permissões de tipos de usuario
    private void aplicarPermissoes() {

        btnCadastrarSepultura.setEnabled(
              usuarioAutenticado.podeCadastrarSepultura());

        btnAtualizarSepultura.setEnabled(
              usuarioAutenticado.podeAtualizarSepultura());

        btnDeletarSepultura.setEnabled(
              usuarioAutenticado.podeDeletarSepultura());
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
            JOptionPane.showMessageDialog(this, "Sepultura cadastrada com sucesso!");
                        
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
        SepulturaDao dao = new SepulturaDao();
        List<Sepultura> lista = dao.listarTodos();
        preencherTabelaSepulturas(lista);
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Erro ao listar sepulturas");
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

    private void preencherTabelaSepulturas(List<Sepultura> lista) {
        DefaultTableModel modelo = (DefaultTableModel) tblSepulturas.getModel();
        modelo.setRowCount(0);

        for (Sepultura s : lista) {
            modelo.addRow(new Object[]{
                s.getIdSepultura(),
                s.getLote(),
                s.getTipoSepultura(),
                s.getStatusSepultura(),
                s.getFamiliarResponsavel(),
                s.getDataCriacao()
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

        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSepulturas = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnVoltarMenu = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        lblLote = new javax.swing.JLabel();
        lblTipoSepultura = new javax.swing.JLabel();
        txtLote = new javax.swing.JTextField();
        txtTipoSepultura = new javax.swing.JTextField();
        lblStatusSepultura = new javax.swing.JLabel();
        txtStatusSepultura = new javax.swing.JTextField();
        lblFamiliarResponsavelSepultura = new javax.swing.JLabel();
        txtFamiliarResponsavelSepultura = new javax.swing.JTextField();
        lblDataCriacao = new javax.swing.JLabel();
        txtDataCriacao = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btnCadastrarSepultura = new javax.swing.JButton();
        btnAtualizarSepultura = new javax.swing.JButton();
        btnDeletarSepultura = new javax.swing.JButton();
        btnListarSepulturas = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarLoteSepultura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBuscarLote = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        tblSepulturas.setBackground(new java.awt.Color(0, 102, 102));
        tblSepulturas.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        tblSepulturas.setForeground(new java.awt.Color(255, 255, 255));
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

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangular.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(0, 102, 102));
        jLabel2.setText("Gerenciamento de Sepulturas");

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addComponent(btnVoltarMenu)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 524, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(187, 187, 187)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVoltarMenu)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(38, 38, 38))))
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblLote.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLote.setForeground(new java.awt.Color(0, 102, 102));
        lblLote.setText("Lote:");

        lblTipoSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoSepultura.setForeground(new java.awt.Color(0, 102, 102));
        lblTipoSepultura.setText("Tipo da Sepultura:");

        txtLote.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoteActionPerformed(evt);
            }
        });

        txtTipoSepultura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txtTipoSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoSepulturaActionPerformed(evt);
            }
        });

        lblStatusSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblStatusSepultura.setForeground(new java.awt.Color(0, 102, 102));
        lblStatusSepultura.setText("Status da Sepultura:");

        txtStatusSepultura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txtStatusSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusSepulturaActionPerformed(evt);
            }
        });

        lblFamiliarResponsavelSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFamiliarResponsavelSepultura.setForeground(new java.awt.Color(0, 102, 102));
        lblFamiliarResponsavelSepultura.setText("Familiar Responsável:");

        txtFamiliarResponsavelSepultura.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txtFamiliarResponsavelSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelSepulturaActionPerformed(evt);
            }
        });

        lblDataCriacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataCriacao.setForeground(new java.awt.Color(0, 102, 102));
        lblDataCriacao.setText("Data de Criação:");

        txtDataCriacao.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 102, 102)));
        txtDataCriacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataCriacaoActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(0, 102, 102));
        jLabel3.setText("Preencha as informações abaixo!");

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

        jPanel2.setBackground(new java.awt.Color(0, 102, 102));
        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel5.setFont(new java.awt.Font("Garamond", 1, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Pesquise a sepultura informando o lote");

        btnBuscarLote.setText("Buscar");
        btnBuscarLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBuscarLoteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addComponent(jLabel5)
                .addGap(18, 18, 18)
                .addComponent(btnBuscarLote)
                .addContainerGap(19, Short.MAX_VALUE))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarLoteSepultura)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtBuscarLoteSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(btnBuscarLote))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFamiliarResponsavelSepultura)
                            .addComponent(lblDataCriacao))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btnDeletarSepultura)
                                .addGap(39, 39, 39)
                                .addComponent(btnListarSepulturas)
                                .addGap(38, 38, 38)
                                .addComponent(btnAtualizarSepultura)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                                .addComponent(btnCadastrarSepultura))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txtDataCriacao, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFamiliarResponsavelSepultura))
                                .addGap(12, 12, 12)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addComponent(lblStatusSepultura)
                                .addGap(18, 18, 18)
                                .addComponent(txtStatusSepultura))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblTipoSepultura)
                                    .addComponent(lblLote))
                                .addGap(28, 28, 28)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtTipoSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 205, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(80, 80, 80))))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addGap(534, 534, 534))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLote)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(14, 14, 14)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblTipoSepultura)
                            .addComponent(txtTipoSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblStatusSepultura)
                    .addComponent(txtStatusSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFamiliarResponsavelSepultura)
                    .addComponent(txtFamiliarResponsavelSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblDataCriacao))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarSepultura)
                    .addComponent(btnListarSepulturas)
                    .addComponent(btnDeletarSepultura)
                    .addComponent(btnAtualizarSepultura))
                .addContainerGap(23, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(67, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jScrollPane1))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(233, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(4867, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(471, 471, 471))
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

    private void btnBuscarLoteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBuscarLoteActionPerformed
        // TODO add your handling code here:
        String lote = txtBuscarLoteSepultura.getText();
        SepulturaDao dao = new SepulturaDao();
        List<Sepultura> lista = dao.buscarPorLote(lote);
        preencherTabelaSepulturas(lista);
    }//GEN-LAST:event_btnBuscarLoteActionPerformed

    private void btnVoltarMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnVoltarMenuActionPerformed
        // TODO add your handling code here:
        Menu menu = new Menu(usuarioAutenticado);          
        menu.setLocationRelativeTo(this);
        menu.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btnVoltarMenuActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarSepultura;
    private javax.swing.JButton btnBuscarLote;
    private javax.swing.JButton btnCadastrarSepultura;
    private javax.swing.JButton btnDeletarSepultura;
    private javax.swing.JButton btnListarSepulturas;
    private javax.swing.JButton btnVoltarMenu;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblDataCriacao;
    private javax.swing.JLabel lblFamiliarResponsavelSepultura;
    private javax.swing.JLabel lblLote;
    private javax.swing.JLabel lblStatusSepultura;
    private javax.swing.JLabel lblTipoSepultura;
    private javax.swing.JTable tblSepulturas;
    private javax.swing.JTextField txtBuscarLoteSepultura;
    private javax.swing.JTextField txtDataCriacao;
    private javax.swing.JTextField txtFamiliarResponsavelSepultura;
    private javax.swing.JTextField txtLote;
    private javax.swing.JTextField txtStatusSepultura;
    private javax.swing.JTextField txtTipoSepultura;
    // End of variables declaration//GEN-END:variables
}
