package banco.app;

import banco.ui.TelaLogin;

public class SistemaBanco {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new banco.ui.TelaLogin().setVisible(true);
            }
        });
    }
}