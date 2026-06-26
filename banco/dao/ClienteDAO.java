package banco.dao;

import banco.model.Cliente;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import banco.dao.ConexaoDB;

public class ClienteDAO {

    public boolean salvar(Cliente cliente) {
        String sql = "INSERT INTO clientes (nome, cpf) VALUES (?, ?)";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao salvar cliente no DAO: " + e.getMessage());
            return false;
        }
    }

    public Cliente buscarPorCpf(String cpf) {
        String sql = "SELECT id, nome, cpf FROM clientes WHERE cpf = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cpf);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Cliente cliente = new Cliente();
                    cliente.setId(rs.getLong("id"));
                    cliente.setNome(rs.getString("nome"));
                    cliente.setCpf(rs.getString("cpf"));
                    return cliente;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar cliente por CPF no DAO: " + e.getMessage());
        }
        return null;
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id, nome, cpf FROM clientes ORDER BY nome ASC";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Cliente cliente = new Cliente();
                cliente.setId(rs.getLong("id"));
                cliente.setNome(rs.getString("nome"));
                cliente.setCpf(rs.getString("cpf"));
                lista.add(cliente);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar clientes no DAO: " + e.getMessage());
        }
        return lista;
    }

    public boolean atualizar(Cliente cliente) {
        String sql = "UPDATE clientes SET nome = ?, cpf = ? WHERE id = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, cliente.getNome());
            stmt.setString(2, cliente.getCpf());
            stmt.setLong(3, cliente.getId());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar cliente no DAO: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(long id) {
        String sql = "DELETE FROM clientes WHERE id = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir cliente no DAO: " + e.getMessage());
            return false;
        }
    }
}