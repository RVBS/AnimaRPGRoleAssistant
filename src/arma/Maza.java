package arma;

public class Maza extends Arma{
	
	public Maza(String nombre, int da�o, int turno, int fue_requerida, 
			TipoAtaque critico, TipoAtaque critico2,Especial[] especial, int entereza, int rotura, int presencia){
		
		this.nombre = nombre;
		this.tipoArma = TipoArma.MAZA;
		this.da�o = da�o;
		this.turno = turno;
		this.fue_req_1mano = fue_requerida;
		this.critico1 = critico;
		this.critico2 = critico2;
		this.especial = especial;
		this.entereza = entereza;
		this.rotura = rotura;
		this.presencia = presencia;
	}

}
