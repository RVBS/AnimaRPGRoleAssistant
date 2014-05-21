package arma;

public abstract class Arma {
	
	protected String nombre;
	protected TipoArma tipoArma;
	protected int da�o;
	protected int turno;
	protected int fue_req_1mano;
	protected int fue_req_2manos; //solo si lo permite el arma
	protected TipoAtaque critico1;
	protected TipoAtaque critico2;
	protected Especial[] especial;
	protected int entereza;
	protected int rotura;
	protected int presencia;

	public int getDa�oArma(){
		return da�o;
	}
	
	public int getTurnoArma(){
		return turno;
	}
	
	public String getNombreArma(){
		return nombre;
	}
	
}
