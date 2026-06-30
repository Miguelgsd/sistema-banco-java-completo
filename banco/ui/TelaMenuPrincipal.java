package banco.ui;

import banco.service.BancoService;

public class TelaMenuPrincipal extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(TelaMenuPrincipal.class.getName());

    private String nomeUsuario;
    private String perfilUsuario;
    
    public TelaMenuPrincipal(String nome, String perfil) {
        this.nomeUsuario = nome;
        this.perfilUsuario = perfil;
        
        initComponents();
        
        configurarTela();
    }
    
    private void configurarTela(){
        lbl_title.setText("Bem-vindo, " + nomeUsuario);
        
        if(perfilUsuario.equalsIgnoreCase("OPERADOR")){
            btn_gerenciar_usuarios.setVisible(false);
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        btn_cad_cliente = new javax.swing.JButton();
        btn_deposito = new javax.swing.JButton();
        btn_cad_corrente = new javax.swing.JButton();
        btn_saque = new javax.swing.JButton();
        btn_cad_poupanca = new javax.swing.JButton();
        btn_transferencia = new javax.swing.JButton();
        btn_relatorio = new javax.swing.JButton();
        btn_extrato = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        lbl_title = new javax.swing.JLabel();
        btn_logout = new javax.swing.JButton();
        btn_gerenciar_usuarios = new javax.swing.JButton();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        btn_cad_cliente.setText("Cadastrar Cliente");
        btn_cad_cliente.addActionListener(this::btn_cad_clienteActionPerformed);

        btn_deposito.setText("Depósito");
        btn_deposito.addActionListener(this::btn_depositoActionPerformed);

        btn_cad_corrente.setText("Cadastrar Conta Corrente");
        btn_cad_corrente.addActionListener(this::btn_cad_correnteActionPerformed);

        btn_saque.setText("Saque");
        btn_saque.addActionListener(this::btn_saqueActionPerformed);

        btn_cad_poupanca.setText("Cadastrar Conta Poupança");
        btn_cad_poupanca.addActionListener(this::btn_cad_poupancaActionPerformed);

        btn_transferencia.setText("Transferência");
        btn_transferencia.addActionListener(this::btn_transferenciaActionPerformed);

        btn_relatorio.setText("Relatório Geral");
        btn_relatorio.addActionListener(this::btn_relatorioActionPerformed);

        btn_extrato.setText("Extrato");
        btn_extrato.addActionListener(this::btn_extratoActionPerformed);

        lbl_title.setFont(new java.awt.Font("Cantarell", 0, 24)); // NOI18N
        lbl_title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_title.setText("teste");

        btn_logout.setText("Sair");
        btn_logout.addActionListener(this::btn_logoutActionPerformed);

        btn_gerenciar_usuarios.setText("Gerenciar Usuários");
        btn_gerenciar_usuarios.addActionListener(this::btn_gerenciar_usuariosActionPerformed);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(294, 294, 294)
                .addComponent(lbl_title, javax.swing.GroupLayout.PREFERRED_SIZE, 287, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_gerenciar_usuarios)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_logout, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_logout)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_gerenciar_usuarios)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbl_title)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_cad_cliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cad_corrente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(433, 433, 433)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btn_deposito, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addComponent(btn_saque, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(btn_relatorio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btn_cad_poupanca, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(433, 433, 433)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_transferencia, javax.swing.GroupLayout.DEFAULT_SIZE, 211, Short.MAX_VALUE)
                            .addComponent(btn_extrato, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cad_cliente)
                    .addComponent(btn_deposito))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_cad_corrente)
                    .addComponent(btn_saque))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_cad_poupanca)
                    .addComponent(btn_transferencia))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_relatorio)
                    .addComponent(btn_extrato))
                .addContainerGap(164, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_logoutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_logoutActionPerformed
        // TODO add your handling code here:
        new TelaLogin().setVisible(true);
        this.dispose();
    }//GEN-LAST:event_btn_logoutActionPerformed

    private void btn_cad_correnteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_correnteActionPerformed
        // TODO add your handling code here:
        new TelaCadastroContaCorrente().setVisible(true);
    }//GEN-LAST:event_btn_cad_correnteActionPerformed

    private void btn_depositoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_depositoActionPerformed
        // TODO add your handling code here:
        new TelaOperacoes("DEPOSITO").setVisible(true);
    }//GEN-LAST:event_btn_depositoActionPerformed

    private void btn_gerenciar_usuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_gerenciar_usuariosActionPerformed
        // TODO add your handling code here:
        new TelaGerenciarUsuarios().setVisible(true);
    }//GEN-LAST:event_btn_gerenciar_usuariosActionPerformed

    private void btn_relatorioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_relatorioActionPerformed
        // TODO add your handling code here:
        new TelaRelatorio().setVisible(true);
    }//GEN-LAST:event_btn_relatorioActionPerformed

    private void btn_saqueActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_saqueActionPerformed
        // TODO add your handling code here:
        new TelaOperacoes("SAQUE").setVisible(true);
    }//GEN-LAST:event_btn_saqueActionPerformed

    private void btn_transferenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_transferenciaActionPerformed
        // TODO add your handling code here:
        new TelaOperacoes("TRANSFERENCIA").setVisible(true);
    }//GEN-LAST:event_btn_transferenciaActionPerformed

    private void btn_cad_clienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_clienteActionPerformed
        // TODO add your handling code here:
        new TelaCadastroCliente().setVisible(true);
    }//GEN-LAST:event_btn_cad_clienteActionPerformed

    private void btn_cad_poupancaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_cad_poupancaActionPerformed
        // TODO add your handling code here:
        new TelaCadastroContaPoupanca().setVisible(true);
    }//GEN-LAST:event_btn_cad_poupancaActionPerformed

    private void btn_extratoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_extratoActionPerformed
        // TODO add your handling code here:
        new TelaExtrato().setVisible(true);
    }//GEN-LAST:event_btn_extratoActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_cad_cliente;
    private javax.swing.JButton btn_cad_corrente;
    private javax.swing.JButton btn_cad_poupanca;
    private javax.swing.JButton btn_deposito;
    private javax.swing.JButton btn_extrato;
    private javax.swing.JButton btn_gerenciar_usuarios;
    private javax.swing.JButton btn_logout;
    private javax.swing.JButton btn_relatorio;
    private javax.swing.JButton btn_saque;
    private javax.swing.JButton btn_transferencia;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lbl_title;
    // End of variables declaration//GEN-END:variables
}
