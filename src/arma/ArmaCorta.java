package arma;

public class ArmaCorta extends Arma{

	public ArmaCorta(String nombre, int daño, int turno, int fue_requerida, 
			TipoAtaque critico,Especial[] especial, int entereza, int rotura, int presencia){
		
		this.nombre = nombre;
		this.tipoArma = TipoArma.ARMA_CORTA;
		this.daño = daño;
		this.turno = turno;
		this.fue_req_1mano = fue_requerida;
		this.critico1 = critico;
		this.especial = especial;
		this.entereza = entereza;
		this.rotura = rotura;
		this.presencia = presencia;
	}
}
