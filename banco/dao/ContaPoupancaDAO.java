package banco.dao;

import banco.model.ContaPoupanca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

public class ContaPoupancaDAO{
    
    public ContaPoupanca buscarPorNumero(String numeroConta){
        String sql = "SELECT saldo, taxa_rendimento FROM contas_poupanca WHERE numeroConta = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, numeroConta);
            
            try (ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    double saldo = rs.getDouble("saldo");
                    double taxa = rs.getDouble("taxa_rendimento");
                    
                    return new ContaPoupanca(numeroConta, null, saldo, taxa);
                }
            }
        } catch (SQLException e) {
            System.err.print("Não foi possível consultar o banco: " + e);
        }
        
        return null;
    }
    
    public boolean atualizar(String numeroConta, double novoSaldo){
        String sql = "UPDATE contas_poupanca SET saldo = ? WHERE numeroConta = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setDouble(1, novoSaldo);
            stmt.setString(2, numeroConta);
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Erro ao atualizar os dados: " + e);
            return false;
        }
    }
    
    public boolean registrarTransacao(int contaId, String descricao, double valor){
        String sql = "INSERT INTO transacoes (conta_id, tipo_conta, descricao, valor) VALUES (?, POUPANCA, ?, ?)";
        
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
        String sql = "SELECT descricao, valor, data_hora FROM transacoes WHERE contaId = ?";
        
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
    
    public boolean salvar(ContaPoupanca conta, Long clienteId){
        String sql = "INSERT INTO contas_poupanca (numero_conta, saldo, taxa_rendimento, cliente_id) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, conta.getNumeroConta());
            stmt.setDouble(2, conta.getSaldo());
            stmt.setDouble(3, conta.getTaxaRendimentoMensal());
            stmt.setLong(4, clienteId);
            
            
            int linhasAfetadas = stmt.executeUpdate();
            
            return linhasAfetadas > 0;
            
        } catch (SQLException e){
            System.err.print("Não foi possível registrar a transação: " + e);
            return false;
        }
    }
}