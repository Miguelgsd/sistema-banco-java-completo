package banco.dao;

import banco.model.ContaCorrente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ContaCorrenteDAO {
    public ContaCorrente buscaPorNumero(String numeroConta){
        String sql = "SELECT saldo, limite_cheque, FROM contas_correntes WHERE numero_conta = ?";
        
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
    
    public boolean atualizar(String numeroConta, double novoSaldo, double novoLimite){
        String sql = "UPDATE contas_correntes SET saldo = ?, limite_cheque = ?, WHERE numero_conta = ?";
        
        try(Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setDouble(1, novoSaldo);
            stmt.setDouble(1, novoLimite);
            stmt.setString(1, numeroConta);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
            
        } catch (SQLException e){
            System.err.print("Erro ao atualizar os dados: " + e);
            return false;
        }
    }

}