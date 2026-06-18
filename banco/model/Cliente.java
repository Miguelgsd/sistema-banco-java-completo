package banco.model;

public class Cliente {
	private String nome;
	private String cpf;
	private String telefone;

	public Cliente(String nome, String cpf, String telefone){
		this.nome = nome;
		this.cpf = cpf;
		this.telefone = telefone;
	}

	public void setNome(String nome){
		this.nome = nome;
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