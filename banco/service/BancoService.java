package banco.service;

import banco.dao.ConexaoDB;
import java.util.List;
import banco.dao.ContaCorrenteDAO;
import banco.dao.ContaPoupancaDAO;
import banco.model.ContaBancaria;
import banco.model.ContaCorrente;
import banco.model.ContaPoupanca;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.PreparedStatement;

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

    public String exibirRelatorioGeral(){
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
            return "Não há contas registradas no banco de dados para gerar o relatório.";
        }

        StringBuilder sb = new StringBuilder();
        sb.append("==================================================\n");
        sb.append("             RELATÓRIO DE AUDITORIA GERAL         \n");
        sb.append("==================================================\n\n");
        sb.append("Nº de Contas Correntes Ativas:  ").append(correntes.size()).append("\n");
        sb.append("Nº de Contas Poupanças Ativas:  ").append(poupancas.size()).append("\n");
        sb.append("Patrimônio Total Custodiado:    R$ ").append(String.format("%.2f", calcularPatrimonioTotal())).append("\n\n");
        sb.append("--------------------------------------------------\n");
        sb.append("Destaques de Saldo:\n");
        sb.append(" -> MAIOR SALDO: Conta ").append(contaMaior.getNumeroConta())
          .append(" (R$ ").append(String.format("%.2f", contaMaior.getSaldo())).append(")\n");
        sb.append(" -> MENOR SALDO: Conta ").append(contaMenor.getNumeroConta())
          .append(" (R$ ").append(String.format("%.2f", contaMenor.getSaldo())).append(")\n");
        sb.append("==================================================");

        return sb.toString();
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
            int idReal = (int) poupanca.getId();
            contaPoupanca.registrarTransacao(idReal, "Depósito em dinheiro", valor);
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
            int idReal = (int) poupanca.getId();
            contaPoupanca.registrarTransacao(idReal, "Saque realizado" + valor, valor);
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
            int idReal = (int) corrente.getId();
            contaCorrente.registrarTransacao(idReal, "Depósito em dinheiro", valor);
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
            int idReal = (int) corrente.getId();
            contaCorrente.registrarTransacao(idReal, "Saque realizado", valor);
            return "Sucesso: saque realizado no valor de R$" + String.format("%.2f", valor);
        }
        return "Erro: não foi possível realizar o saque.";
    }
    
    public String transferir(String contaOrigem, String contaDestino, double valor) {
        if (valor <= 0) return "Erro: O valor deve ser maior que zero.";
        if (contaOrigem.equals(contaDestino)) return "Erro: Contas iguais.";

        String sqlDebitoPoupanca = "UPDATE contas_poupanca SET saldo = ? WHERE numero_conta = ?";
        String sqlCreditoPoupanca = "UPDATE contas_poupanca SET saldo = ? WHERE numero_conta = ?";
        String sqlDebitoCorrente = "UPDATE contas_correntes SET saldo = ?, limite_cheque = ? WHERE numero_conta = ?";
        String sqlCreditoCorrente = "UPDATE contas_correntes SET saldo = ? WHERE numero_conta = ?";
        
        String sqlLogTransacao = "INSERT INTO transacoes (conta_id, tipo_conta, descricao, valor) VALUES (?, ?, ?, ?)";

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
                    try (PreparedStatement stmt = conn.prepareStatement(sqlDebitoPoupanca)) {
                        stmt.setDouble(1, origem.getSaldo() - valor);
                        stmt.setString(2, contaOrigem);
                        stmt.executeUpdate();
                    }
                } else {
                    ContaCorrente cc = (ContaCorrente) origem;
                    double novoSaldo = cc.getSaldo() - valor;
                    double novoLimite = novoSaldo < 0 ? cc.getLimite() + novoSaldo : cc.getLimite();
                    try (PreparedStatement stmt = conn.prepareStatement(sqlDebitoCorrente)) {
                        stmt.setDouble(1, novoSaldo);
                        stmt.setDouble(2, novoLimite);
                        stmt.setString(3, contaOrigem);
                        stmt.executeUpdate();
                    }
                }

                if (destino instanceof ContaPoupanca) {
                    try (PreparedStatement stmt = conn.prepareStatement(sqlCreditoPoupanca)) {
                        stmt.setDouble(1, destino.getSaldo() + valor);
                        stmt.setString(2, contaDestino);
                        stmt.executeUpdate();
                    }
                } else {
                    ContaCorrente cc = (ContaCorrente) destino;
                    try (PreparedStatement stmt = conn.prepareStatement(sqlCreditoCorrente)) {
                        stmt.setDouble(1, cc.getSaldo() + valor);
                        stmt.setString(2, contaDestino);
                        stmt.executeUpdate();
                    }
                }

                String tipoOrigem = (origem instanceof ContaCorrente) ? "CORRENTE" : "POUPANCA";
                String tipoDestino = (destino instanceof ContaCorrente) ? "CORRENTE" : "POUPANCA";
                
                try (PreparedStatement stmtLog = conn.prepareStatement(sqlLogTransacao)) {
                    stmtLog.setInt(1, (int) origem.getId());
                    stmtLog.setString(2, tipoOrigem);
                    stmtLog.setString(3, "Transferência enviada para conta " + contaDestino);
                    stmtLog.setDouble(4, valor);
                    stmtLog.executeUpdate();

                    stmtLog.setInt(1, (int) destino.getId());
                    stmtLog.setString(2, tipoDestino);
                    stmtLog.setString(3, "Transferência recebida da conta " + contaOrigem);
                    stmtLog.setDouble(4, valor);
                    stmtLog.executeUpdate();
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