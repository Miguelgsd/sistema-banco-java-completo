package banco.model;

import javax.swing.JOptionPane;

public class ContaPoupanca extends ContaBancaria {
    private double taxaRendimentoMensal;

    public ContaPoupanca(String numeroConta, Cliente titular, double saldoInicial, double taxa){
        super(numeroConta, titular, saldoInicial);
        this.taxaRendimentoMensal = taxa;
    }

    public double calcularRendimento(){
        return getSaldo() * this.taxaRendimentoMensal;
    }

    public void aplicarRendimento(){
        double rendimento = calcularRendimento();
        setSaldo(getSaldo() + rendimento);
        registrarTransacao("Rendimento aplicado ao saldo: R$" + String.format("%.2f", rendimento));
    }

    @Override
    public void gerarExtrato(){
        String titularNome;
        if (getTitular() != null) {
            titularNome = getTitular().getNome();
        } else {
            titularNome = "Não definido";
        }

        JOptionPane.showMessageDialog(null,
            "--------- Extrato ----------\n\n" +
            "Titular:" + titularNome + "\n" +
            "Número: " + getNumeroConta() + "\n" +
            "Saldo: R$" + String.format("%.2f", getSaldo()) + "\n" +
            "Taxa de rendimento: " + this.taxaRendimentoMensal + "\n" +
            "Rendimento estimado: R$" + String.format("%.2f", calcularRendimento()) + "\n" +
            "Histórico: \n" + getHistorico(),
            "Extrato da Poupança",
            JOptionPane.INFORMATION_MESSAGE);
    }
}