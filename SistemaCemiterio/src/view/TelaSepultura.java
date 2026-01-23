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

    /**
     * Creates new form Telasimulacao
     */
    public TelaSepultura() {
        initComponents();
        
    }
    //Permissões de tipos de usuario
    private void aplicarPermissoes(){
        
        if(!usuarioAutenticado.getPerfil().equals("Administrador")){//se o perfil for diferente de administrador
            if(usuarioAutenticado.getPerfil().equals("Manutenção")){
                btnCadastrarSepultura.setEnabled(false);
                btnDeletarSepultura.setEnabled(false);
            }else if(usuarioAutenticado.getPerfil().equals("Atendente")){
                btnDeletarSepultura.setEnabled(false);
            }else if(usuarioAutenticado.getPerfil().equals("Visitante")){
                btnCadastrarSepultura.setEnabled(false);
                btnDeletarSepultura.setEnabled(false);
                btnAtualizarSepultura.setEnabled(false);
            }
        }
    }
    
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
        btnVoltarMenu = new javax.swing.JButton();
        txtLote = new javax.swing.JTextField();
        lblLote = new javax.swing.JLabel();
        lblTipoSepultura = new javax.swing.JLabel();
        txtTipoSepultura = new javax.swing.JTextField();
        lblStatusSepultura = new javax.swing.JLabel();
        txtStatusSepultura = new javax.swing.JTextField();
        lblFamiliarResponsavelSepultura = new javax.swing.JLabel();
        lblDataCriacao = new javax.swing.JLabel();
        txtFamiliarResponsavelSepultura = new javax.swing.JTextField();
        txtDataCriacao = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        txtBuscarLoteSepultura = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        btnBuscarLote = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSepulturas = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        btnCadastrarSepultura = new javax.swing.JButton();
        btnAtualizarSepultura = new javax.swing.JButton();
        btnDeletarSepultura = new javax.swing.JButton();
        btnListarSepulturas = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel3.setBackground(new java.awt.Color(0, 102, 102));

        btnVoltarMenu.setText("Voltar ao Menu");
        btnVoltarMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVoltarMenuActionPerformed(evt);
            }
        });

        txtLote.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtLoteActionPerformed(evt);
            }
        });

        lblLote.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblLote.setForeground(new java.awt.Color(255, 255, 255));
        lblLote.setText("Lote:");

        lblTipoSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblTipoSepultura.setForeground(new java.awt.Color(255, 255, 255));
        lblTipoSepultura.setText("Tipo da Sepultura:");

        txtTipoSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTipoSepulturaActionPerformed(evt);
            }
        });

        lblStatusSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblStatusSepultura.setForeground(new java.awt.Color(255, 255, 255));
        lblStatusSepultura.setText("Status da Sepultura:");

        txtStatusSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtStatusSepulturaActionPerformed(evt);
            }
        });

        lblFamiliarResponsavelSepultura.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblFamiliarResponsavelSepultura.setForeground(new java.awt.Color(255, 255, 255));
        lblFamiliarResponsavelSepultura.setText("Familiar Responsável:");

        lblDataCriacao.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        lblDataCriacao.setForeground(new java.awt.Color(255, 255, 255));
        lblDataCriacao.setText("Data de Criação:");

        txtFamiliarResponsavelSepultura.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtFamiliarResponsavelSepulturaActionPerformed(evt);
            }
        });

        txtDataCriacao.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDataCriacaoActionPerformed(evt);
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel1.setForeground(new java.awt.Color(0, 102, 102));

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

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnCadastrarSepultura)
                .addGap(18, 18, 18)
                .addComponent(btnAtualizarSepultura)
                .addGap(18, 18, 18)
                .addComponent(btnDeletarSepultura)
                .addGap(18, 18, 18)
                .addComponent(btnListarSepulturas)
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCadastrarSepultura)
                    .addComponent(btnAtualizarSepultura)
                    .addComponent(btnDeletarSepultura)
                    .addComponent(btnListarSepulturas))
                .addContainerGap(20, Short.MAX_VALUE))
        );

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imgs/logoretangular.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Garamond", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Gerenciamento de Sepulturas");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblFamiliarResponsavelSepultura)
                                    .addComponent(lblDataCriacao))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txtFamiliarResponsavelSepultura, javax.swing.GroupLayout.DEFAULT_SIZE, 411, Short.MAX_VALUE)
                                    .addComponent(txtDataCriacao)))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addComponent(lblStatusSepultura)
                                    .addGap(18, 18, 18)
                                    .addComponent(txtStatusSepultura, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE))
                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                            .addComponent(lblTipoSepultura)
                                            .addGap(19, 19, 19))
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(lblLote)
                                            .addGap(109, 109, 109)))
                                    .addGap(9, 9, 9)
                                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txtTipoSepultura, javax.swing.GroupLayout.DEFAULT_SIZE, 403, Short.MAX_VALUE)
                                        .addComponent(txtLote)))
                                .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(14, 14, 14)
                                .addComponent(btnVoltarMenu)
                                .addGap(403, 403, 403)
                                .addComponent(jLabel2))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1230, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 40, Short.MAX_VALUE)))
                .addGap(20, 20, 20))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(35, 35, 35)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnVoltarMenu)
                            .addComponent(jLabel2))
                        .addGap(47, 47, 47)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblLote)
                            .addComponent(txtLote, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblTipoSepultura)
                            .addComponent(txtTipoSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStatusSepultura)
                            .addComponent(txtStatusSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(12, 12, 12)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblFamiliarResponsavelSepultura)
                            .addComponent(txtFamiliarResponsavelSepultura, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(lblDataCriacao)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addComponent(txtDataCriacao, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2)))))
                .addGap(37, 37, 37)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(202, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(1738, Short.MAX_VALUE))
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
        java.awt.EventQueue.invokeLater(() -> new TelaSepultura().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizarSepultura;
    private javax.swing.JButton btnBuscarLote;
    private javax.swing.JButton btnCadastrarSepultura;
    private javax.swing.JButton btnDeletarSepultura;
    private javax.swing.JButton btnListarSepulturas;
    private javax.swing.JButton btnVoltarMenu;
<<<<<<< HEAD
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
=======
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
>>>>>>> 18c018ed61a4b1fa19500ca28c678a78214cd8dd
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
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
