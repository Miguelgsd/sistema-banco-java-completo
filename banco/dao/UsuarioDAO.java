package banco.dao;

import banco.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        
        return null; // Se não encontrou ou deu erro, retorna nulo
    }
}