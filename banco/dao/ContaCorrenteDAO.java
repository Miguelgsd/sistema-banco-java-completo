package banco.dao;

import banco.model.ContaCorrente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ContaCorrenteDAO {
    public ContaCorrente buscaPorNumero(String numeroConta){
        String sql = "SELECT saldo, limite_cheque FROM contas_correntes WHERE numero_conta = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, numeroConta);
            
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    double saldo = rs.getDouble("saldo");
                    double limite = rs.getDouble("limite_cheque");
                    
                    return new ContaCorrente(numeroConta, null, saldo, limite);
                }
            }
        } catch (SQLException e) {
            System.err.print("Erro ao buscar conta no DAO: " + e);
        }
        return null;    
    }
    
    public boolean atualizarSaldo(String numeroConta, double novoSaldo, double novoLimite){
        String sql = "UPDATE contas_correntes SET saldo = ?, limite_cheque = ? WHERE numero_conta = ?";
        
        try(Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, novoSaldo);
            stmt.setDouble(2, novoLimite);
            stmt.setString(3, numeroConta);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
            
        } catch (SQLException e){
            System.err.print("Erro ao atualizar os dados: " + e);
            return false;
        }
    }
    
    public boolean registrarTransacao(int contaId, String descricao, double valor){
        String sql = "INSERT INTO transacoes (conta_id, tipo_conta, descricao, valor) VALUES (?, 'CORRENTE', ?, ?)";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, contaId);
            stmt.setString(2, descricao);
            stmt.setDouble(3, valor);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Não foi possível registrar a transação: " + e);
            return false;
        }
    }
    
    public List buscarHistorico(int contaId){
        List<String> historico = new ArrayList<>();
        String sql = "SELECT descricao, valor, data_hora FROM transacoes WHERE conta_id = ?";
        
        try(Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setInt(1, contaId);
            
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String descricao = rs.getString("descricao");
                    double valor = rs.getDouble("valor");
                    String data = rs.getTimestamp("data_hora").toString();
                    
                    String linha = data + " - " + descricao + ": R$ " + String.format("%.2f", valor);
                    historico.add(linha);
                }
            }
        
        } catch(SQLException e){
            System.err.print("Não foi possível buscar o histórico: " + e);
        }
        return historico;
    }
    
    public boolean salvar(ContaCorrente conta, Long clienteId){
        String sql = "INSERT INTO contas_correntes (numero_conta, saldo, limite_cheque, cliente_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, conta.getNumeroConta());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setDouble(3, conta.getLimite());
            stmt.setLong(4, clienteId);
            
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Não foi possível registrar a transação: " + e);
            return false;
        }
    }
    
    public boolean excluir(String numeroConta){
        String sql = "DELETE FROM contas_correntes WHERE numero_conta = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, numeroConta);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Erro ao excluir a conta: " + e);
            return false;
        }
    }
    
    public List listarTodas(){
        List<ContaCorrente> contas = new ArrayList<>();
        String sql = "SELECT numero_conta, saldo, limite_cheque FROM contas_correntes";
        
        try(Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            
            try(ResultSet rs = stmt.executeQuery()){
                while(rs.next()){
                    String numero = rs.getString("numero_conta");
                    double saldo = rs.getDouble("saldo");
                    double limite = rs.getDouble("limite_cheque");
                    
                    ContaCorrente conta = new ContaCorrente(numero, null, saldo, limite);
                    contas.add(conta);
                }
            }
        
        } catch(SQLException e){
            System.err.print("Não foi possível buscar as contas: " + e);
        }
        return contas;
        
    }
    
    public boolean atualizar(ContaCorrente conta){
        String sql = "UPDATE contas_correntes SET limite_cheque = ? WHERE numero_conta = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setDouble(1, conta.getLimite());
            stmt.setString(2, conta.getNumeroConta());
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Erro ao atualizar os dados cadastrais: " + e);
            return false;
        }
    }

}