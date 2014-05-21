package dado;

import java.util.ArrayList;

public class Dado100 extends Dado{

	public static final int VALOR_MAX = 99;
	private int numTiradasAbiertas;
	private int numTiradasPifia;
	
	public Dado100(int tiradaAbierta, int tiradaPifia, int modificador){
		this.tiradaAbierta = tiradaAbierta;
		this.tiradaPifia = tiradaPifia;
		this.modificador = modificador;
		this.numTiradasAbiertas = 0;
		this.numTiradasPifia = 0;
		
	}
	
	@Override
	public void tirarDado() {
		resultado = 0;
		int x; //resultado intermedio
		numTiradasAbiertas = 0;
		numTiradasPifia = 0;
		boolean tirarOtraVez = true;
		/* mientras que sean tiradas abiertas, se suman */
		while (tirarOtraVez){
			//calculamos el valor aleatorio entre 1 y 100
			x = ((int) (Math.random() * VALOR_MAX))+1;
	
			// si es >= tiradaAbierta + numTiradasAbiertas, se vuelve a tirar
			if (x >= (tiradaAbierta+numTiradasAbiertas)){
				resultado += x;
				if (numTiradasAbiertas <= VALOR_MAX)
					numTiradasAbiertas++;
				tirarOtraVez = true;
			} else if (x < tiradaPifia){
				// vuelve a tirar 1 vez
				x = ((int) (Math.random() * VALOR_MAX))+1;
				resultado += -x + modificador;
				numTiradasPifia++;
				tirarOtraVez = false;
			}
			else{
				resultado += x + modificador;
				tirarOtraVez = false;
			}
		}
		
	}
	
	/**
	 * Version de tirar dado con un modificador temporal
	 * @param modificador
	 */
	public ArrayList<Integer> tirarDado(int modificador,boolean abiertas) {
		
		ArrayList<Integer> tiradas = new ArrayList<>();
		
		int x; //resultado intermedio
		int modificadorPifia;
		resultado = 0;
		numTiradasAbiertas = 0;
		numTiradasPifia = 0;
		boolean tirarOtraVez = true;
		/* mientras que sean tiradas abiertas, se suman */
		while (tirarOtraVez){
			//calculamos el valor aleatorio entre 1 y 100
			x = ((int) (Math.random() * VALOR_MAX))+1;
			tiradas.add(x);
			// si es >= tiradaAbierta + numTiradasAbiertas, se vuelve a tirar
			if (((x > (tiradaAbierta+numTiradasAbiertas) || x == 100)) && abiertas){
				resultado += x;
				if (numTiradasAbiertas <= VALOR_MAX)
					numTiradasAbiertas++;
				tirarOtraVez = true;
			} else if (x < tiradaPifia){
				modificadorPifia = calcularNivelPifia(x);
				// vuelve a tirar 1 vez
				x = ((int) (Math.random() * VALOR_MAX))+1;
				tiradas.add(x);
				resultado += -x + modificadorPifia + modificador;
				numTiradasPifia++;
				tirarOtraVez = false;
			}
			else{
				resultado += x + modificador;
				tirarOtraVez = false;
			}
		}
		
		return tiradas;
		
	}
	
	private int calcularNivelPifia(int x){
		if (x <= 1){
			return -15;
		}
		else if (x <= 2){
			return 0;
		}
		// else x>2 (3,4,5)
		return 15;
		
	}
	
	public int getNumAbiertas(){
		return numTiradasAbiertas;
	}
	
	public int getNumPifias(){
		return numTiradasPifia;
	}
	
	public static final void main(String args[]){
		Dado100 d = new Dado100(90,5,0);
		for (int i = 0; i < 10; i++){
		ArrayList<Integer> tiradas = d.tirarDado(0,true);
		System.out.println(tiradas);
		System.out.println(d.getResultado());
		}
	}

}
