package arma;

public class Desarmado extends Arma {
	
	public Desarmado(String nombre, int d, int t){
		this.nombre = nombre;
		this.da�o = d;
		this.tipoArma = TipoArma.DESARMADO;
		this.turno = t;
		
	}

}
