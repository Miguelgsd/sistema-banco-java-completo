package banco.interfaces;

public interface Operavel {
	public void depositar(double valor);
	public boolean sacar(double valor);
	public void exibirSaldo();
}