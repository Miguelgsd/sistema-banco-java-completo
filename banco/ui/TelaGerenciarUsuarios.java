package banco.ui;

import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import banco.dao.UsuarioDAO;
import banco.model.Usuario;
import java.util.List;

public class TelaGerenciarUsuarios extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaGerenciarUsuarios.class.getName());

    private DefaultTableModel tabelaModelo;
    private boolean confirmacao = false;
    
    public TelaGerenciarUsuarios() {
        initComponents();
        
        this.tabelaModelo = (DefaultTableModel) tbl_users.getModel();
        
        carregarDadosBanco();
    }
    
    public void carregarDadosBanco() {
       tabelaModelo.setNumRows(0);
        
        UsuarioDAO dao = new UsuarioDAO();
        List<Usuario> usuarios = dao.listarTodos();
        
        for (Usuario u : usuarios) {
            tabelaModelo.addRow(new Object[]{
                u.getId(),
                u.getLogin(),
                u.getNome(),
                u.getPerfil()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lbl_title = new javax.swing.JLabel();
        btn_back = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_users = new javax.swing.JTable();
        btn_new = new javax.swing.JButton();
        btn_edit = new javax.swing.JButton();
        btn_delete = new javax.swing.JButton();
        lbl_warnings = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        lbl_title.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        lbl_title.setText("Gerenciar Usuários");

        btn_back.setText("Voltar");
        btn_back.addActionListener(this::btn_backActionPerformed);

        tbl_users.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "ID", "Login", "Nome", "Perfil"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tbl_users);

        btn_new.setText("Novo");
        btn_new.addActionListener(this::btn_newActionPerformed);

        btn_edit.setText("Editar");

        btn_delete.setText("Excluir");
        btn_delete.addActionListener(this::btn_deleteActionPerformed);

        lbl_warnings.setFont(new java.awt.Font("Cantarell", 0, 17)); // NOI18N
        lbl_warnings.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbl_warnings, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 735, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btn_new, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_edit, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_delete, javax.swing.GroupLayout.PREFERRED_SIZE, 183, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_back, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(20, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addGap(286, 286, 286)
                .addComponent(lbl_title)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addComponent(lbl_title)
                .addGap(53, 53, 53)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_new)
                    .addComponent(btn_delete)
                    .addComponent(btn_back)
                    .addComponent(btn_edit))
                .addGap(33, 33, 33)
                .addComponent(lbl_warnings, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(63, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_backActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_backActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_backActionPerformed

    private void btn_newActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_newActionPerformed
        // TODO add your handling code here:
        new TelaCadastroUsuario(this).setVisible(true);
    }//GEN-LAST:event_btn_newActionPerformed

    private void btn_deleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_deleteActionPerformed
        // TODO add your handling code here:
        int linhaSelecionada = tbl_users.getSelectedRow();
        
        if(linhaSelecionada == -1){
            lbl_warnings.setForeground(Color.red);
            lbl_warnings.setText("Por favor, selecione uma linha válida.");
            btn_delete.setBackground(null);
            btn_delete.setForeground(Color.black);
            btn_delete.setText("Excluir");
            return;
        }
        
        long idUsuario = (long) tabelaModelo.getValueAt(linhaSelecionada, 0);
        String loginUsuario = (String) tabelaModelo.getValueAt(linhaSelecionada, 1);
        
        if(loginUsuario.equalsIgnoreCase("miguel") || idUsuario == 1L){
            lbl_warnings.setForeground(Color.red);
            lbl_warnings.setText("Erro: não é possível excluir o usuário administrador do sistema.");
            return;
        } 
        
        if(!confirmacao){
            confirmacao = true;
            
            btn_delete.setText("Confirmar");
            btn_delete.setBackground(Color.red);
            btn_delete.setForeground(Color.white);
            
            lbl_warnings.setText("Confirme a exclusão do usuário.");
            lbl_warnings.setForeground(Color.red);
        } else {
            UsuarioDAO dao = new UsuarioDAO();
            boolean deletouNoBanco = dao.excluir(idUsuario);
            
            if (deletouNoBanco) {
                tabelaModelo.removeRow(linhaSelecionada);
                
                lbl_warnings.setText("Usuário '" + loginUsuario + "' removido com sucesso!");
                lbl_warnings.setForeground(new java.awt.Color(0, 150, 0));
            } else {
                lbl_warnings.setText("Erro crítico: Não foi possível remover do banco de dados.");
                lbl_warnings.setForeground(Color.red);
            }
            
            btn_delete.setBackground(null);
            btn_delete.setForeground(Color.black);
            btn_delete.setText("Excluir");
            confirmacao = false;
        }
        
        
    }//GEN-LAST:event_btn_deleteActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_back;
    private javax.swing.JButton btn_delete;
    private javax.swing.JButton btn_edit;
    private javax.swing.JButton btn_new;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbl_title;
    private javax.swing.JLabel lbl_warnings;
    private javax.swing.JTable tbl_users;
    // End of variables declaration//GEN-END:variables
}
