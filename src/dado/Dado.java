package dado;

public abstract class Dado {
	
	/* resultado del dado */
	protected int resultado;
	protected int tiradaAbierta;
	protected int tiradaPifia;
	protected int modificador;
	
	public static final int VALOR_MIN = 1;
	
	/**
	 * Permite tirar los dados y guardar el resultado
	 */
	public abstract void tirarDado();
	
	/** 
	 * Permite saber si una tirada ha superado una cantidad
	 * @param v
	 * @return
	 */
	public boolean exito(int v){
		return (resultado > v);
	}
	
	/**
	 * Por si se quiere añadir un penalizador o un bonificador
	 * @param v
	 */
	public void setModificador(int v){
		modificador = v;
	}
	
	
	/**
	 * Devuelve el resultado del dado
	 * @return
	 */
	public int getResultado(){
		return resultado;
	}
}
