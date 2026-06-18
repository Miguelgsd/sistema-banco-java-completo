package banco.dao;

import banco.model.Usuario; // Certifique-se de que sua classe de modelo existe
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    /**
     * Valida as credenciais na tabela 'usuarios' do PostgreSQL.
     * Retorna o objeto Usuario completo se for válido, ou null se falhar.
     */
    public Usuario validarLogin(String loginDigitado, String senhaDigitada) {
        // Query SQL pura que busca o usuário combinando login e senha
        String sql = "SELECT * FROM usuarios WHERE login = ? AND senha = ?";
        
        // Abrimos a conexão usando a sua classe utilitária local
        try (Connection conn = ConexaoDB.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            // Injeta as strings nos pontos de interrogação (?) com total segurança
            stmt.setString(1, loginDigitado);
            stmt.setString(2, senhaDigitada);
            
            // Executa a consulta no PostgreSQL
            try (ResultSet rs = stmt.executeQuery()) {
                // Se o banco encontrou uma linha correspondente...
                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    // Captura os dados das colunas do DBeaver e joga no objeto Java
                    usuario.setId(rs.getLong("id"));
                    usuario.setLogin(rs.getString("login"));
                    usuario.setNome(rs.getString("nome"));
                    usuario.setSenha(rs.getString("senha"));
                    usuario.setPerfil(rs.getString("perfil"));
                    
                    return usuario; // Retorna o usuário autenticado com seu perfil
                }
            }
            
        } catch (SQLException e) {
            System.err.println("Erro ao validar login no banco: " + e.getMessage());
        }
        
        return null; // Se não encontrou ou deu erro, retorna nulo
    }
}