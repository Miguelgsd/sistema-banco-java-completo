package banco.service;

import java.util.List;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import banco.model.ContaBancaria;
import banco.model.ContaCorrente;
import banco.model.ContaPoupanca;

public class BancoService {
    private List<ContaCorrente> contasCorrentes;
    private List<ContaPoupanca> contasPoupanca;

    public BancoService() {
        this.contasCorrentes = new ArrayList<>();
        this.contasPoupanca = new ArrayList<>();
    }

    public void cadastrarContaCorrente(ContaCorrente cc){
        this.contasCorrentes.add(cc);
    }

    public void cadastrarContaPoupanca(ContaPoupanca cp){
        this.contasPoupanca.add(cp);
    }

    public ContaBancaria buscarConta(String numeroConta){

        for(ContaCorrente conta : contasCorrentes){
            if(conta.getNumeroConta().equals(numeroConta)){
                return conta;
            }
        }

        for(ContaPoupanca conta : contasPoupanca){
            if(conta.getNumeroConta().equals(numeroConta)){
                return conta;
            }
        }
        
        return null;
    }

    public void listarTodasAsContas(){
        StringBuilder infos = new StringBuilder();
        infos.append("---------- Informações Gerais ----------\n\n");
        infos.append("Conta Corrente:\n\n");
        for(ContaCorrente conta : contasCorrentes){
            infos.append("Número: ").append(conta.getNumeroConta()).append("\n");
            infos.append("Titular: ").append(conta.getTitular().getNome()).append("\n");
            infos.append("Saldo: R$").append(String.format("%.2f", conta.getSaldo())).append("\n\n");
        }

        infos.append("Conta Poupança:\n\n");
        for(ContaPoupanca conta : contasPoupanca){
            infos.append("Número: ").append(conta.getNumeroConta()).append("\n");
            infos.append("Titular: ").append(conta.getTitular().getNome()).append("\n");
            infos.append("Saldo: R$").append(String.format("%.2f", conta.getSaldo())).append("\n\n");
        }

        JOptionPane.showMessageDialog(null, infos.toString(), "Contas", JOptionPane.INFORMATION_MESSAGE);
    }

    public double calcularPatrimonioTotal(){
        double patrimonio = 0.0;

        for(ContaCorrente cc : contasCorrentes){
            patrimonio += cc.getSaldo();
        }

        for(ContaPoupanca cp : contasPoupanca){
            patrimonio += cp.getSaldo();
        }

        return patrimonio;
    }

    public void exibirRelatorioGeral(){
        ContaBancaria contaMaior = null;
        ContaBancaria contaMenor = null;

        for (ContaCorrente cc : contasCorrentes) {
            if (contaMaior == null || cc.getSaldo() > contaMaior.getSaldo()) {
                contaMaior = cc;
            }
            if (contaMenor == null || cc.getSaldo() < contaMenor.getSaldo()) {
                contaMenor = cc;
            }
        }

        for (ContaPoupanca cp : contasPoupanca) {
            if (contaMaior == null || cp.getSaldo() > contaMaior.getSaldo()) {
                contaMaior = cp;
            }
            if (contaMenor == null || cp.getSaldo() < contaMenor.getSaldo()) {
                contaMenor = cp;
            }
        }
            JOptionPane.showMessageDialog(null, "------- Relatório -------\n\n" + "Nº de Contas Correntes: " + contasCorrentes.size() + "\nNº de Contas Poupanças: " + contasPoupanca.size() + "\nPatrimônio total: R$" + String.format("%.2f", calcularPatrimonioTotal()) + "\nMaior saldo: " + contaMaior.getTitular().getNome() + "; " + contaMaior.getNumeroConta() + "; R$" + String.format("%.2f", contaMaior.getSaldo()) + "\nMenor saldo: " + contaMenor.getTitular().getNome() + "; " + contaMenor.getNumeroConta() + "; R$" + String.format("%.2f", contaMenor.getSaldo()));
        }
}