package banco.model;

import javax.swing.JOptionPane;

public class ContaCorrente extends ContaBancaria {
    private double limiteChequEspecial;

    public ContaCorrente(String numeroConta, Cliente titular, double saldoInicial, double limite){
        super(numeroConta, titular, saldoInicial);
        this.limiteChequEspecial = limite;
    }

    @Override
    public boolean sacar(double valor){
        if (valor <= 0) {
        JOptionPane.showMessageDialog(null, "Digite um valor positivo para sacar", "Erro", JOptionPane.ERROR_MESSAGE);
        return false;
        }

        double saldoAtual = getSaldo();
        double limiteDisponivel = saldoAtual + this.limiteChequEspecial;

        if(valor <= limiteDisponivel){
            if(valor <= saldoAtual){
                setSaldo(saldoAtual - valor);
                registrarTransacao("Valor retirado: R$" + String.format("%.2f", valor));
                JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");
            } else {
                double usadoCheque = valor - saldoAtual;
                setSaldo(0.0);
                this.limiteChequEspecial -= usadoCheque;
                registrarTransacao("Valor retirado: R$" + String.format("%.2f", valor) + " (cheque especial usado: R$" + String.format("%.2f", usadoCheque) + ")");
                JOptionPane.showMessageDialog(null, "Você está utilizando seu cheque especial", "Aviso", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Saque realizado com sucesso!");
            }
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Saldo e limite insuficientes para realizar o saque", "Erro", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void usarChequeEspecial(double valor){
        if(valor <= this.limiteChequEspecial){
            this.limiteChequEspecial -= valor;
        } else {
            JOptionPane.showMessageDialog(null, "Valor do cheque insuficiente!", "Erro", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void exibirHistorico(){
        StringBuilder history = new StringBuilder();
        history.append("--------- Histórico ---------\n\n");

        for(String evento : getHistorico()){
            history.append(evento).append("\n");
        }

        JOptionPane.showMessageDialog(null, history.toString(), "Histórico da Conta Corrente", JOptionPane.INFORMATION_MESSAGE);
    }

    public double getLimite(){
        return this.limiteChequEspecial;
    }

    @Override
    public void gerarExtrato(){
        JOptionPane.showMessageDialog(null, "-------- Extrato -----------\n\nTitular: " + getTitular().getNome() + "\nNúmero da conta: " + getNumeroConta() + "\nSaldo: R$" + String.format("%.2f", getSaldo()) + "\nLimite especial: R$" + String.format("%.2f", getLimite()) + "\nHistórico: \n" + getHistorico(), "Extrato da Conta", JOptionPane.INFORMATION_MESSAGE);
    }
}