package banco.model;

public class Cliente {
        private long id;
	private String nome;
	private String cpf;
	private String telefone;

	public Cliente(long id, String nome, String cpf, String telefone){
		this.id = id;
                this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
	}

        public Cliente() {
        }

	public void setNome(String nome){
		this.nome = nome;
	}

        public long getId() {
            return id;
        }
        
        public void setId(long id) {
           this.id = id;
        }

	public String getNome(){
		return this.nome;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getCpf(){
		return this.cpf;
	}

	public void setTelefone(String telefone){
		this.telefone = telefone;
	}

	public String getTelefone(){
		return this.telefone;
	}

	public String toString(){
		return "Nome: " + this.nome + ".\nCPF: " + this.cpf + ".\nTelefone: " + this.telefone + "\n";
	}
}