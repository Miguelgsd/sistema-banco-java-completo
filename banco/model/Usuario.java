package banco.model;

public class Usuario {
    private Long id;
    private String login;
    private String nome;
    private String senha;
    private String perfil;

    public Usuario(Long id, String login, String nome, String senha, String perfil) {
        this.id = id;
        this.login = login;
        this.nome = nome;
        this.senha = senha;
        this.perfil = perfil;
    }
    
    public Usuario(){}

    public Long getId() {
        return id;
    }

    public String getLogin() {
        return login;
    }

    public String getNome() {
        return nome;
    }

    public String getSenha() {
        return senha;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }
}
