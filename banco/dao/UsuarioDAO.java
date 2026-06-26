package banco.dao;

import banco.model.Usuario;
import banco.dao.ConexaoDB;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public Usuario validarLogin(String loginDigitado, String senhaDigitada) {
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, loginDigitado);
            stmt.setString(2, senhaDigitada);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Usuario usuario = new Usuario();

                    usuario.setId(rs.getLong("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    
                    return usuario;
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao validar login no banco: " + e.getMessage());
        }
        
        return null;
    }
    
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuarios ORDER BY id ASC";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setId(rs.getLong("id"));
                usuario.setLogin(rs.getString("login"));
                usuario.setNome(rs.getString("nome"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPerfil(rs.getString("perfil"));
                
                lista.add(usuario);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar usuários no DAO: " + e.getMessage());
        }
        return lista;
    }

    public boolean salvar(Usuario usuario) {
        String sql = "INSERT INTO usuarios (login, nome, senha, perfil) VALUES (?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar usuário no DAO: " + e.getMessage());
            return false;
        }
    }
    
    public boolean excluir(long id) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
           int linhasAfetadas = stmt.executeUpdate();
           return linhasAfetadas > 0;
            
        } catch (SQLException e) {
            System.err.println("Erro ao excluir usuário no DAO: " + e.getMessage());
            return false;
        }
    }
    
    public Usuario buscarPorLogin(String login){
        String sql = "SELECT * FROM usuarios WHERE login = ?";
        
        try(Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, login);
            
            try(ResultSet rs = stmt.executeQuery()){
                if(rs.next()){
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getLong("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    return usuario;
                }
            }
        } catch (SQLException e){
            System.err.print("Não foi possível buscar o usuário: " + e);
        }
        return null;
        
    }
    
    public boolean atualizar(Usuario usuario){
        String sql = "UPDATE usuarios SET login = ?, nome = ?, senha = ?, perfil = ? WHERE id = ?";
        
        try(Connection conn = ConexaoDB.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)){
            
            stmt.setString(1, usuario.getLogin());
            stmt.setString(2, usuario.getNome());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getPerfil());
            
            int linhasAfetadas = stmt.executeUpdate();
            return linhasAfetadas > 0;
            
            
        } catch (SQLException e){
            System.err.print("Erro ao atualizar o usuário: " + e);
            return false;
        }
    }
}