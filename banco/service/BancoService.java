package banco.service;

import banco.dao.ConexaoDB;
import java.util.List;
import javax.swing.JOptionPane;
import banco.dao.ContaCorrenteDAO;
import banco.dao.ContaPoupancaDAO;
import banco.model.ContaBancaria;
import banco.model.ContaCorrente;
import banco.model.ContaPoupanca;
import java.sql.Connection;
import java.sql.SQLException;

public class BancoService {
    private final ContaCorrenteDAO contaCorrente = new ContaCorrenteDAO();
    private final ContaPoupancaDAO contaPoupanca = new ContaPoupancaDAO();

    public BancoService() {
    }

    public ContaBancaria buscarConta(String numeroConta){
        ContaCorrente cc = contaCorrente.buscaPorNumero(numeroConta);
        if (cc != null){
            return cc;
        }
        
        return contaPoupanca.buscarPorNumero(numeroConta);
    }

    public double calcularPatrimonioTotal(){
        double patrimonio = 0.0;
        
        List<ContaCorrente> correntes = contaCorrente.listarTodas();
        for(ContaCorrente cc : correntes){
            patrimonio += cc.getSaldo();
        }
        
        List<ContaPoupanca> poupancas = contaPoupanca.listarTodas();
        for(ContaPoupanca cp : poupancas){
            patrimonio += cp.getSaldo();
        }
        
        return patrimonio;
    }

    public void exibirRelatorioGeral(){
        List<ContaCorrente> correntes = contaCorrente.listarTodas();
        List<ContaPoupanca> poupancas = contaPoupanca.listarTodas();
        
        ContaBancaria contaMaior = null;
        ContaBancaria contaMenor = null;

        for (ContaCorrente cc : correntes) {
            if (contaMaior == null || cc.getSaldo() > contaMaior.getSaldo()) 
                contaMaior = cc;
            if (contaMenor == null || cc.getSaldo() < contaMenor.getSaldo()) 
                contaMenor = cc;
        }

        for (ContaPoupanca cp : poupancas) {
            if (contaMaior == null || cp.getSaldo() > contaMaior.getSaldo()) 
                contaMaior = cp;
            if (contaMenor == null || cp.getSaldo() < contaMenor.getSaldo()) 
                contaMenor = cp;
        }
        
        if (contaMaior == null || contaMenor == null) {
            JOptionPane.showMessageDialog(null, "Não há contas registradas no banco de dados para gerar relatório.");
            return;
        }

        JOptionPane.showMessageDialog(null, 
            "------- Relatório PostgreSQL -------\n\n" + 
            "Nº de Contas Correntes: " + correntes.size() + 
            "\nNº de Contas Poupanças: " + poupancas.size() + 
            "\nPatrimônio total: R$" + String.format("%.2f", calcularPatrimonioTotal()) + 
            "\nMaior saldo: Conta " + contaMaior.getNumeroConta() + " -> R$" + String.format("%.2f", contaMaior.getSaldo()) + 
            "\nMenor saldo: Conta " + contaMenor.getNumeroConta() + " -> R$" + String.format("%.2f", contaMenor.getSaldo())
        );
    }
    
    public String depositarPoupanca(String numeroConta, double valor){
        if(valor <= 0){
            return "Erro: o valor deve ser maior que zero";
        }
        
        ContaPoupanca poupanca = contaPoupanca.buscarPorNumero(numeroConta);
        if(poupanca == null){
            return "Erro: conta " + numeroConta + " não encontrada";
        }
        
        double novoSaldo = poupanca.getSaldo() + valor;
        boolean atualizou = contaPoupanca.atualizarSaldo(numeroConta, novoSaldo);
        
        if(atualizou){
            contaPoupanca.registrarTransacao(1, "Depósito em dinheiro", valor);
            return "Sucesso: depósito realizado!";
        }
        
        return "Erro: não foi possível atualizar o saldo no banco.";
    }
    
    public String sacarPoupanca(String numeroConta, double valor){
        if(valor <= 0){
            return "Erro: o valor deve ser maior que zero";
        }
        
        ContaPoupanca poupanca = contaPoupanca.buscarPorNumero(numeroConta);
        if(poupanca == null){
            return "Erro: não foi possível encontrar a conta";
        }
        
        if(poupanca.getSaldo() < valor){
            return "Erro: saldo insuficiente.\nSaldo disponível: " + String.format("%.2f", poupanca.getSaldo());
        }
        
        double novoSaldo = poupanca.getSaldo() - valor;
        
        boolean atualizou = contaPoupanca.atualizarSaldo(numeroConta, novoSaldo);
        
        if(atualizou){
            contaPoupanca.registrarTransacao(1, "Saque realizado" + valor, valor);
            return "Sucesso: saque realizado no valor de R$" + String.format("%.2f", valor);
        }
        
        return "Não foi possível realizar o saque";
    }
    
    public String depositarCorrente(String numeroConta, double valor){
        if(valor <= 0){
            return "Erro: o valor deve ser positivo.";
        }
        
        ContaCorrente corrente = contaCorrente.buscaPorNumero(numeroConta);
        if(corrente == null){
            return "Erro: não foi possível encontrar a conta";
        }
        
        double novoSaldo = corrente.getSaldo() + valor;
        
        boolean atualizou = contaCorrente.atualizarSaldo(numeroConta, novoSaldo, corrente.getLimite());
        
        if(atualizou){
            contaCorrente.registrarTransacao(1, "Depósito em dinheiro", valor);
            return "Sucesso: depósito realizado no valor de R$" + String.format("%.2f", valor);
        }
        
        return "Erro: não foi possível atualizar o saldo";
    }
    
    public String sacarCorrente(String numeroConta, double valor){
        if(valor <= 0){
            return "Erro: o valor deve ser positivo.";
        }
        
        ContaCorrente corrente = contaCorrente.buscaPorNumero(numeroConta);
        if(corrente == null){
            return "Erro: não foi possível encontrar a conta";
        }
        
        double saldoDisponivel = corrente.getSaldo() + corrente.getLimite();
        
        if(saldoDisponivel < valor){
            return "Erro: saldo e limite insuficientes para a realização do saque. Disponível: " + String.format("%.2f", saldoDisponivel);
        }
        
        double novoSaldo = corrente.getSaldo() - valor;
        double novoLimite = corrente.getLimite();
        
        if(novoSaldo < 0){
            novoLimite = corrente.getLimite() + novoSaldo;
        }
        
        boolean atualizou = contaCorrente.atualizarSaldo(numeroConta, novoSaldo, novoLimite);
        if(atualizou){
            contaCorrente.registrarTransacao(1, "Saque realizado", valor);
            return "Sucesso: saque realizado no valor de R$" + String.format("%.2f", valor);
        }
        return "Erro: não foi possível realizar o saque.";
    }
    
    public String transferir(String contaOrigem, String contaDestino, double valor) {
        if (valor <= 0) return "Erro: O valor deve ser maior que zero.";
        if (contaOrigem.equals(contaDestino)) return "Erro: Contas iguais.";

        try (Connection conn = ConexaoDB.getConnection()) {
            
            conn.setAutoCommit(false);

            try {
                ContaBancaria origem = buscarConta(contaOrigem);
                ContaBancaria destino = buscarConta(contaDestino);

                if (origem == null || destino == null) {
                    throw new SQLException("Uma das contas não foi localizada.");
                }

                if (origem instanceof ContaPoupanca && origem.getSaldo() < valor) {
                    throw new SQLException("Saldo insuficiente na Poupança.");
                } else if (origem instanceof ContaCorrente) {
                    ContaCorrente cc = (ContaCorrente) origem;
                    if ((cc.getSaldo() + cc.getLimite()) < valor) {
                        throw new SQLException("Saldo/Limite insuficientes na Conta Corrente.");
                    }
                }

                if (origem instanceof ContaPoupanca) {
                    contaPoupanca.atualizarSaldo(contaOrigem, origem.getSaldo() - valor);
                } else {
                    ContaCorrente cc = (ContaCorrente) origem;
                    double novoSaldo = cc.getSaldo() - valor;
                    double novoLimite = novoSaldo < 0 ? cc.getLimite() + novoSaldo : cc.getLimite();
                    contaCorrente.atualizarSaldo(contaOrigem, novoSaldo, novoLimite);
                }

                if (destino instanceof ContaPoupanca) {
                    contaPoupanca.atualizarSaldo(contaDestino, destino.getSaldo() + valor);
                } else {
                    ContaCorrente cc = (ContaCorrente) destino;
                    contaCorrente.atualizarSaldo(contaDestino, cc.getSaldo() + valor, cc.getLimite());
                }

                conn.commit();
                return "Sucesso: Transferência realizada com sucesso!";

            } catch (Exception e) {
                conn.rollback();
                return "Erro na transação: " + e.getMessage() + " . Operação cancelada e valores protegidos.";
            }

        } catch (SQLException e) {
            return "Erro crítico de conexão com o banco de dados: " + e.getMessage();
        }
    }
}