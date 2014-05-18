package dado;

public class Dado10 extends Dado{

	public static final int VALOR_MAX = 9;
	
	
	public void tirarDado() {
		int x = ((int) Math.random() * VALOR_MAX)+1;
		resultado = x + modificador;
	}

}
