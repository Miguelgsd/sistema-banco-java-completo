package banco.service;

import banco.dao.UsuarioDAO;
import banco.model.Usuario;

public class UsuarioService {

    private final UsuarioDAO usuarioDAO = new UsuarioDAO();

    public Usuario fazerLogin(String login, String senha) {
        if (login == null || login.trim().isEmpty() || senha == null || senha.trim().isEmpty()) {
            return null; 
        }

        Usuario usuario = usuarioDAO.validarLogin(login, senha);

        return usuario;
    }

    public String cadastrarNovoUsuario(Usuario novoUsuario) {
        Usuario existente = usuarioDAO.buscarPorLogin(novoUsuario.getLogin());
        if (existente != null) {
            return "Erro: O login '" + novoUsuario.getLogin() + "' já está em uso por outro funcionário.";
        }
        
        boolean salvou = usuarioDAO.salvar(novoUsuario);
        if (salvou) {
            return "Sucesso: Usuário cadastrado com sucesso!";
        }
        return "Erro: Falha física ao salvar usuário no banco de dados.";
    }
}