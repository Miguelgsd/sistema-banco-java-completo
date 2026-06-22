package banco.dao;

import banco.model.ContaPoupanca;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
    
    public boolean atualizarSaldo(String numeroConta, double novoSaldo){
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
}